package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GerberParser {

    private Scanner scan;
    private static final Pattern REGEX_RECT = Pattern.compile("<tag>(.+?)</tag>");
    private static final Pattern REGEX_CIRC = Pattern.compile("<tag>(.+?)</tag>");
    private static final Pattern REGEX_OBLONG = Pattern.compile("<tag>(.+?)</tag>");
    private static final Pattern REGEX_POLYGON = Pattern.compile("<tag>(.+?)</tag>");
    private static final Pattern REGEX_MACRO = Pattern.compile("<tag>(.+?)</tag>");
    private static final Pattern REGEX_FLASHES = Pattern.compile("D03");
    private Pattern pattRect;


    public GerberParser() {
        System.out.println("File Received");
    }

    public void startParsing(File file) {

        try {scan = new Scanner (file);}
        catch (FileNotFoundException e) {e.printStackTrace();}

        System.out.println("Loaded the following file and start parsing " + file);
        scan.useDelimiter(",");

        while (scan.hasNext()) {
            String line = scan.next();
            parseRect(line);
        }
    }

    private void parseRect(String line) {
        Matcher matcher = REGEX_RECT.matcher(line);
    }
}
