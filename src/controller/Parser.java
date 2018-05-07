package controller;

import model.*;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final Pattern REGEX_FIND_PADS = Pattern.compile("ADD(\\d*)([RCO]),(\\d*[.]\\d*)*X?(\\d[.]\\d*)?");
    private static final Pattern REGEX_FIND_POLYGONS = Pattern.compile("X(\\d*)Y(\\d*)");
    private static final Pattern REGEX_FIND_DCODES = Pattern.compile("(?:G54)D(\\d*)|^(?!ADD)D(\\d*)\\*");
    private static final Pattern REGEX_FIND_MACRO = Pattern.compile("AM(.*)\\*");
    private static final Pattern REGEX_FIND_MACRO_PARAMS = Pattern.compile("\\d*,\\d*,(-?\\d*\\.-?\\d*),(-?\\d*\\.-?\\d*).*");
    private static final String beginCode = "G36*";
    private static final String endCode = "G37*";
    private final String fileLocation;
    private double thickness;
    private GerberSettings settings = new GerberSettings();
    private Path src;
    private Path dst;
    private Matcher matcher;
    private ArrayList<Shape> aperturesList = new ArrayList<>();
    private Scanner scan;
    private Scanner scanCount;
    private File file;
    private File tempFile;

    public Parser(File file, double thickness) {
        this.thickness = thickness;
        this.file = file;
        this.fileLocation = file.getParent();
        cleanUpFile();

    }

    public void parse() {
        try {
            scan = new Scanner(tempFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Loaded the following file " + file + " and start parsing ");
        while (scan.hasNext()) {
            String line = scan.next();
            getSettings(line);
            parsePads(line);
            parseMacro(line);
            parsePolygons(line);

        }
        countApertures();
        createFiles();
    }

    private void parsePads(String line) {

        Matcher matcher = REGEX_FIND_PADS.matcher(line);
        int dCode;
        double x;
        double y = 0;

        if (matcher.find()) {
            dCode = Integer.parseInt(matcher.group(1));
            x = Double.parseDouble(matcher.group(3));
            if (matcher.group(4) != null) {
                y = Double.parseDouble(matcher.group(4));
            }

            switch (matcher.group(2)) {
                case "R":
                    Rectangle rectangle;
                    rectangle = new Rectangle(dCode, x, y, thickness, "Rectangle");
                    aperturesList.add(rectangle);
                    break;
                case "O":
                    Obround obround;
                    obround = new Obround(dCode, x, y, thickness, "Oblong");
                    aperturesList.add(obround);
                    break;
                case "C":
                    x /= 2; //Need radius for calculations
                    Circle circle;
                    circle = new Circle(dCode, x, y, thickness, "Circle");
                    aperturesList.add(circle);
                    break;
            }
        }

    }

    private void parsePolygons(String line) {
        ArrayList<Double> pointsX = new ArrayList<>();
        ArrayList<Double> pointsY = new ArrayList<>();
        double[] size;
        if (line.contains(beginCode)) {
            line = scan.next();
            while (!line.contains(endCode)) {
                Matcher matcher = REGEX_FIND_POLYGONS.matcher(line);
                if (matcher.find()) {
                    pointsX.add((Double.parseDouble(matcher.group(1)) / settings.getPrecisionX()));
                    pointsY.add((Double.parseDouble(matcher.group(2)) / settings.getPrecisionY()));
                    line = scan.next();
                } else {
                    line = scan.next();
                }
            }
            size = findSize(pointsX, pointsY);
            Polygon polygon;
            polygon = new Polygon(9999, size[0], size[1], thickness, "Polygon");
            aperturesList.add(polygon);
        }
    }

    private void parseMacro(String line) {

        //TODO: match dcode with name of the macro
        ArrayList<Double> sizeX = new ArrayList<>();
        ArrayList<Double> sizeY = new ArrayList<>();
        double[] size;

        matcher = REGEX_FIND_MACRO.matcher(line);
        if (matcher.find()) {
            line = scan.next();
            matcher = REGEX_FIND_MACRO_PARAMS.matcher(line);

            while (matcher.find()) {
                sizeX.add(Double.parseDouble(line.split(",")[2]));
                sizeY.add(Double.parseDouble(line.split(",")[3]));
                System.out.println(line);
            }
            size = highestSize(sizeX, sizeY);
            Macro macro = new Macro(0000, size[0], size[1], thickness, "Custom");
            aperturesList.add(macro);
            matcher = REGEX_FIND_MACRO.matcher(line);
        }
    }

    private void countApertures() {

        int dCodeLine;
        try {
            scanCount = new Scanner(tempFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanCount.hasNext()) {
            String line = scanCount.next();
            Matcher matcher = REGEX_FIND_DCODES.matcher(line);
            while (matcher.find()) {
                int count = 0;

                //Hack for REGEX failed grouping
                if (matcher.group(1) != null) {
                    dCodeLine = Integer.parseInt(matcher.group(1));
                } else {
                    dCodeLine = Integer.parseInt(matcher.group(2));
                }

                line = scanCount.next();
                while (line.contains("D03*") || line.contains("D3*")) {
                    count++;
                    line = scanCount.next();
                }
                matcher = REGEX_FIND_DCODES.matcher(line);
                for (Shape shape : aperturesList) {
                    int dCode = shape.getdCode();
                    if (dCode == dCodeLine) {
                        shape.setNumbOfApertures(count);
                        break;
                    }
                }
            }
        }
    }

    private double[] findSize(ArrayList<Double> x, ArrayList<Double> y) {
        double[] size = new double[2];
        size[0] = Collections.max(x) - Collections.min(x);
        size[1] = Collections.max(y) - Collections.min(y);
        return size;
    }

    private double[] highestSize(ArrayList<Double> x, ArrayList<Double> y) {
        double[] size = new double[2];
        size[0] = Collections.max(x);
        size[1] = Collections.max(y);
        return size;
    }

    private void createFiles() {
        Report report = new Report(aperturesList);
        report.createReport(fileLocation);
    }

    private void cleanUpFile() {
        //Run this to clean up the gerber file in order for regex to work on all different software exports.
        //Run through the file and enter newline after each (*) symbol. Removes % blocks.

        this.src = Paths.get(this.file.getAbsolutePath());
        this.dst = Paths.get(src + "-temp");
        this.tempFile = new File(dst.toString());
        BufferedReader reader;
        BufferedWriter writer;
        int ch;
        try {
            reader = Files.newBufferedReader(src, StandardCharsets.UTF_8);
            writer = Files.newBufferedWriter(dst, StandardCharsets.UTF_8);
            while ((ch = reader.read()) != -1) {
                switch (ch) {
                    default:
                        writer.write(ch);
                        break;
                    case 42:
                        writer.write(ch);
                        writer.newLine();
                        break;
                    case 10:
                        break;
                    case 37:
                        break;
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Missing file");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Empty file");
            System.exit(0);
        }
    }

    private void getSettings(String line) {

        if (line.contains("MOMM*")) {
            settings.setUnit("MM");
        } else if (line.contains("MOIN*")) {
            settings.setUnit("INCH");
        }

        Pattern pattern = Pattern.compile("FSLAX\\d(\\d)Y\\d(\\d)\\*");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            settings.setPrecisionX(Integer.parseInt(matcher.group(1)));
            settings.setPrecisionY(Integer.parseInt(matcher.group(2)));
        }
    }
}


