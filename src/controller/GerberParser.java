package controller;

import model.Circle;
import model.Obround;
import model.Rectangle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GerberParser {

    private Scanner scan;
    private static final Pattern REGEX_FIND_APERTURES = Pattern.compile("[%ADD](\\d*)(R|C|O),(\\d*[.]\\d*)*X?(\\d[.]\\d*)?");
    private Rectangle rectangle;
    private Circle circle;
    private Obround obround;

    public void startParsing(File file) {
        try {scan = new Scanner (file);}
        catch (FileNotFoundException e) {e.printStackTrace();}
        System.out.println("Loaded the following file and start parsing " + file);
        while (scan.hasNext()) {
            String line = scan.next();
            parsePads(line);
        }
    }

    private void parsePads(String line) {
            int dCode;
            double x;
            double y = 0;

            Matcher matcher = REGEX_FIND_APERTURES.matcher(line);

            if (matcher.find()) {
                dCode = Integer.parseInt(matcher.group(1));
                x = Double.parseDouble(matcher.group(3));
                if (matcher.group(4) != null) {
                    y = Double.parseDouble(matcher.group(4));
                }

                switch (matcher.group(2)) {
                    case "R":
                        rectangle = new Rectangle(dCode, x, y);
                        break;
                    case "C":
                        circle = new Circle(dCode, x);
                        break;
                    case "O":
                        obround = new Obround(dCode, x, y);
                        break;
                }
            }
    }
}
