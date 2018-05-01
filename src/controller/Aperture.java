package controller;

import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Aperture {
    private static final Pattern REGEX_FIND_PADS = Pattern.compile("%ADD(\\d*)([RCO]),(\\d*[.]\\d*)*X?(\\d[.]\\d*)?");
    private static final Pattern REGEX_FIND_POLYGONS = Pattern.compile("X(\\d*)Y(\\d*)D\\d*\\*");
    private Polygon polygon;
    private Scanner scan;
    private ArrayList<Shape> aperturesList = new ArrayList<>();
    private int counter;
    private double thickness;
    private int dCode = 0;
    private double x = 0;
    private double y = 0;
    private String beginCode = "G36*";
    private String endCode = "G37*";


    public void startParsing(File file, double thickness) {

        Apertures apertures = new Apertures();
        this.thickness = thickness;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Loaded the following file and start parsing " + file);
        while (scan.hasNext()) {
            String line = scan.next();
            parsePads(line);

            if (line.equals(beginCode)) {
                line = scan.next();
                parsePolygons(line);
            }
        }

        apertures.addApertures(aperturesList);
        apertures.sortList();

    }

    private void parsePads(String line) {
        Matcher matcherPads = REGEX_FIND_PADS.matcher(line);

        int dCode = 0;
        double x = 0;
        double y = 0;

        if (matcherPads.find()) {
            dCode = Integer.parseInt(matcherPads.group(1));
            x = Double.parseDouble(matcherPads.group(3));
            if (matcherPads.group(4) != null) {
                y = Double.parseDouble(matcherPads.group(4));
            }

            switch (matcherPads.group(2)) {
                case "R":
                    Rectangle rectangle;
                    rectangle = new Rectangle(dCode, x, y, thickness, "Rectangle");
                    aperturesList.add(rectangle);
                    break;
                case "O":
                    Obround obround;
                    obround = new Obround(dCode, x, y, thickness, "Rectangle");
                    aperturesList.add(obround);
                    break;
                case "C":
                    x /= 2; //Need radius for calculations
                    Circle circle;
                    circle = new Circle(dCode, x, y, thickness, "Rectangle");
                    aperturesList.add(circle);
                    break;
            }
        }
    }

    private void parsePolygons(String line) {

        dCode = counter += 1;
        polygon = new Polygon();
        polygon.setThickness(thickness);

        while (!line.equals(endCode)) {

            Matcher matcherPolygons = REGEX_FIND_POLYGONS.matcher(line);

            if (matcherPolygons.find()) {
                x = (Double.parseDouble(matcherPolygons.group(1)) / 1000);
                y = (Double.parseDouble(matcherPolygons.group(2)) / 1000);
                polygon.addPoint(x, y);
                line = scan.next();
            } else {
                line = scan.next();
            }
            polygon.setSize();
            aperturesList.add(polygon);
        }
    }
}
