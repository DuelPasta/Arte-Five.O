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
    private static final Pattern REGEX_FIND_POLYGONS = Pattern.compile(".*?X*?(\\d*)Y??(\\d*)D\\d*\\*");
    private Polygon polygon;
    private Scanner scan;
    private ArrayList<Shape> aperturesList = new ArrayList<>();
    int counter = 0;
    private double thickness;

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
        }
        apertures.addApertures(aperturesList);
        apertures.sortList();
    }

    private void parsePads(String line) {
        Matcher matcherPads = REGEX_FIND_PADS.matcher(line);

        String beginCode = "G36*";
        String endCode = "G37*";
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
                    parseRect(dCode, x, y);
                    break;
                case "O":
                    parseObround(dCode, x, y);
                    break;
                case "C":
                    x /= 2; //Need radius for calculations
                    parseCircle(dCode, x, y);
                    break;
            }
        }

        //Extract all lines between polygon tags and send to polygon model to extract info
        if (line.equals(beginCode)) {
            counter++;
            polygon = new Polygon();
            line = scan.next();
            while (!line.equals(endCode)){
               Matcher matcherPolygons = REGEX_FIND_POLYGONS.matcher(line);
                if (matcherPolygons.find() && !line.equals("G74*") && !line.equals("G75*") && !line.equals("G75*") && !line.equals("G01*"))  {
                    dCode = counter;
                    x = (Double.parseDouble(matcherPolygons.group(1)) /1000);
                    y = (Double.parseDouble(matcherPolygons.group(2)) /1000);
                    polygon.setPoint(x, y);
                }
                line = scan.next();
                //parsePolygon(dCode, x, y);
            }

        }
    }

    private void parseRect(int dCode, double x, double y) {
        Rectangle rectangle = new Rectangle();
        rectangle.setShape("Rectangle");
        rectangle.setdCode(dCode);
        rectangle.setThickness(thickness);
        rectangle.setX(x);
        rectangle.setY(y);
        rectangle.getArea();
        rectangle.getAreaRatio();
        rectangle.getTransferEffeciency();
        aperturesList.add(rectangle);
    }

    private void parseObround(int dCode, double x, double y) {
        Obround obround = new Obround();
        obround.setShape("Oblong");
        obround.setdCode(dCode);
        obround.setThickness(thickness);
        obround.setX(x);
        obround.setY(y);
        obround.getArea();
        obround.getAreaRatio();
        obround.getTransferEffeciency();
        aperturesList.add(obround);
    }

    private void parseCircle(int dCode, double x, double y) {
        Circle circle = new Circle();
        circle.setShape("Circle");
        circle.setdCode(dCode);
        circle.setThickness(thickness);
        circle.setX(x);
        circle.setY(y);
        circle.getArea();
        circle.getAreaRatio();
        circle.getTransferEffeciency();
        aperturesList.add(circle);
    }

    private void parsePolygon(int dCode, double x, double y) {

        polygon.setShape("Custom");
        polygon.setdCode(dCode);
        polygon.getArea();
        aperturesList.add(polygon);

    } //TODO


}
