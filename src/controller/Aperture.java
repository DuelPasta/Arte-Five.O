package controller;

import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Aperture {
    private static final Pattern REGEX_FIND_PADS = Pattern.compile("%ADD(\\d*)([RCO]),(\\d*[.]\\d*)*X?(\\d[.]\\d*)?");
    private static final Pattern REGEX_FIND_POLYGONS = Pattern.compile("X(\\d*)Y(\\d*)D\\d*\\*");
    private static final Pattern REGEX_FIND_DCODES = Pattern.compile("[G54D]?(\\d*)\\*");
    private ArrayList<Shape> aperturesList = new ArrayList<>();
    private static final String beginCode = "G36*";
    private static final String endCode = "G37*";
    private Scanner scan;
    private double thickness;

    public void startParsing(File file, double thickness) {
        Apertures apertures = new Apertures();
        this.thickness = thickness;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Loaded the following file " + file + " and start parsing ");
        while (scan.hasNext()) {
            String line = scan.next();

            /**
             * Check of we niet gewoon heel die lijn naar de methodes kunnen doorsluisen ipv hier al check te liggen doen.
             * Misschien line klas scope geven en file doorsluisen naar die file. Daarna lezen en elke lijn doorsturen naar alle 3 de methodes.
             * Dan in de methodes telkens de scan.next() doen.
             * Dus scanner ook efkes globaal maken.
             */

            parsePads(line);
            parseMacro(line);
            if (line.equals(beginCode)) {
                line = scan.next();
                parsePolygons(line);
            }
        }

        apertures.addApertures(aperturesList);
        apertures.sortList();
        System.out.println("Found: " + Polygon.count + " polygon apertures");

    }

    private void parsePads(String line) {

        Matcher matcherPads = REGEX_FIND_PADS.matcher(line);
        int dCode;
        double x;
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
        double sizeX;
        double sizeY;

        while (!line.equals(endCode)) {

            Matcher matcherPolygons = REGEX_FIND_POLYGONS.matcher(line);

            if (matcherPolygons.find()) {
                pointsX.add((Double.parseDouble(matcherPolygons.group(1)) / 1000));
                pointsY.add((Double.parseDouble(matcherPolygons.group(2)) / 1000));
                line = scan.next();
            } else {
                line = scan.next();
            }
        }
        sizeX = Collections.max(pointsX) - Collections.min(pointsX);
        sizeY = Collections.max(pointsY) - Collections.min(pointsY);
        Polygon polygon;
        polygon = new Polygon(9999, sizeX, sizeY, thickness, "Polygon");
        aperturesList.add(polygon);
    }

    private void parseMacro(String line) {

        //Grootste afmeting uit die lijnen halen, dat is de totale lengte/breedte van de aperture.
        //De rondjes op de hoeken zijn niet belangrijk omdat Area Ratio verschil minimaal is.
        //21 Center Line Exposure(1), Width (0.522), Hight (0.658), Center X, Center Y (kan 0 zijn), Rotation (kan 0 zijn)
        //Voor rounded rectangle ideaal omdat we enkel de afmeting moeten terugvinden.
        //I.E.:
       //           %AMROUNDED19*
        //         21,1,0.522000,0.658000,0,0,0.0*
           //         21,1,0.372000,0.808000,0,0,0.0*
           //         1,1,0.150000,0.186000,0.329000*
           //         1,1,0.150000,0.186000,-0.329000*
        //         1,1,0.150000,-0.186000,-0.329000*
         //         1,1,0.150000,-0.186000,0.329000*%
        //  %ADD19ROUNDED19*%
    }
}
