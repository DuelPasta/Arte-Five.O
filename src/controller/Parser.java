package controller;

import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final Pattern REGEX_FIND_PADS = Pattern.compile("%ADD(\\d*)([RCO]),(\\d*[.]\\d*)*X?(\\d[.]\\d*)?");
    private static final Pattern REGEX_FIND_POLYGONS = Pattern.compile("X(\\d*)Y(\\d*)");
    private static final Pattern REGEX_FIND_DCODES = Pattern.compile("G54D(\\d*)");
    private static final Pattern REGEX_FIND_MACRO = Pattern.compile("%AM(.*)\\*");
    private static final Pattern REGEX_FIND_MACRO_PARAMS = Pattern.compile("\\d*,\\d*,(\\d*\\.\\d*),(\\d*\\.\\d*),.*\\*");
    private static final String beginCode = "G36*";
    private static final String endCode = "G37*";
    private double thickness;
    private Matcher matcher;
    private ArrayList<Shape> aperturesList = new ArrayList<>();
    private ArrayList<Integer> dCodeList = new ArrayList<>();
    private Scanner scan;
    private String fileLocation;
    private File file;


    public void startParsing(File file, double thickness) {

        this.file = file;


        this.thickness = thickness;
        try {
            scan = new Scanner(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Loaded the following file " + file + " and start parsing ");
        while (scan.hasNext()) {
            String line = scan.next();
            parsePads(line);
            parseMacro(line);
            parsePolygons(line);
            countApertures(line);
        }

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
                    dCodeList.add(dCode);
                    break;
                case "O":
                    Obround obround;
                    obround = new Obround(dCode, x, y, thickness, "Oblong");
                    aperturesList.add(obround);
                    dCodeList.add(dCode);
                    break;
                case "C":
                    x /= 2; //Need radius for calculations
                    Circle circle;
                    circle = new Circle(dCode, x, y, thickness, "Circle");
                    aperturesList.add(circle);
                    dCodeList.add(dCode);
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
                    pointsX.add((Double.parseDouble(matcher.group(1)) / 1000));
                    pointsY.add((Double.parseDouble(matcher.group(2)) / 1000));
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
                line = scan.next();
                matcher = REGEX_FIND_MACRO_PARAMS.matcher(line);
            }
            size = highestSize(sizeX, sizeY);
            Macro macro = new Macro(0000, size[0], size[1], thickness, "Custom");
            aperturesList.add(macro);
            matcher = REGEX_FIND_MACRO.matcher(line);
            //TODO: dCodeList.add(dCode);
        }
    }

    private void countApertures(String line) {

        for (Shape shape : aperturesList) {
            if (!shape.getShape().equals("Polygon")) {
                Matcher countPads = REGEX_FIND_DCODES.matcher(line);
                if (countPads.find()) {
                    int dCode = shape.getdCode();
                    int count = 0;
                    if (dCode == Integer.parseInt(countPads.group(1))) {
                        line = scan.next();
                        while (line.contains("D03")) {
                            count++;
                            line = scan.next();
                        }
                        shape.setNumbOfApertures(count);
                    }
                }
            } else {
                shape.setNumbOfApertures(1);
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
        Apertures apertures = new Apertures(aperturesList);
        fileLocation = file.getParent();
        apertures.createReport(fileLocation);
        apertures.showApertures();
    }
}
