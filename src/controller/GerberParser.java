package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GerberParser {

    private Scanner scan;
    private static final Pattern REGEX_FIND_APERTURES = Pattern.compile("[%ADD](\\d*)(R|C|O),(\\d*[.]\\d*)*X?(\\d[.]\\d*)?");
    private static final Pattern REGEX_FLASHES = Pattern.compile("D03");
    private Pattern pattRect;
    private Matcher matcher;


    public GerberParser() {
        System.out.println("File Received");
    }

    public void startParsing(File file) {

        try {scan = new Scanner (file);}
        catch (FileNotFoundException e) {e.printStackTrace();}

        System.out.println("Loaded the following file and start parsing " + file);

        while (scan.hasNext()) {
            String line = scan.next();
            parseRect(line);
        }
    }

    private void parseRect(String line) {
                Matcher matcher = REGEX_FIND_APERTURES.matcher(line);
                if (matcher.find()) {
                    System.out.println("D-code: " + matcher.group(1) + "\t" + "Shape:" + matcher.group(2) + "\t\t" + "X: " + matcher.group(3) + "\t\t" + "Y: " + matcher.group(4) );
                }
    }
}
