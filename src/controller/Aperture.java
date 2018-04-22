package controller;

import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Aperture {
    private static final Pattern REGEX_FIND_PADS = Pattern.compile("[%ADD](\\d*)([RCO]),(\\d*[.]\\d*)*X?(\\d[.]\\d*)?");
    private Scanner scan;

    private Rectangle rectangle;
    private Circle circle;
    private Obround obround;
    private ArrayList<Shape> aperturesList = new ArrayList<>();
    private Apertures apertures;
    private ArrayList<Circle> circles;
    private ArrayList<Obround> obrounds;
    private ArrayList<Macro> macros;
    private ArrayList<Polygon> polygons;
    private double thickness;

    public void startParsing(File file, double thickness) {
        apertures = new Apertures();
        this.thickness = thickness;
        try {scan = new Scanner (file);}
        catch (FileNotFoundException e) {e.printStackTrace();}
        System.out.println("Loaded the following file and start parsing " + file);
        while (scan.hasNext()) {
            String line = scan.next();
            parsePads(line);
        }

        Apertures apertures = new Apertures();
        apertures.addApertures(aperturesList);
        apertures.sortList();
    }

    private void parsePads(String line) {
            int dCode;
            double x;
            double y = 0;

            Matcher matcher = REGEX_FIND_PADS.matcher(line);

            if (matcher.find()) {
                dCode = Integer.parseInt(matcher.group(1));
                x = Double.parseDouble(matcher.group(3));
                if (matcher.group(4) != null) {
                    y = Double.parseDouble(matcher.group(4));
                }

                switch (matcher.group(2)) {
                    case "R":
                        parseRect(dCode,x,y);
                        break;
                    case "O":
                        parseObround(dCode,x,y);
                        break;
                    case "C":
                        x /= 2; //Need radius for calculations
                        parseCircle(dCode, x, y);
                        break;
                }
            }
    }

    private void parseRect(int dCode, double  x, double y) {

        rectangle = new Rectangle();
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
        obround = new Obround();
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
        circle = new Circle();
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

    public void parsePolygon() {

    } //TODO


}
