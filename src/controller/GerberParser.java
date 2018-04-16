package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GerberParser {

    private Scanner scan;
    public GerberParser() {
        System.out.println("File Received");
    }

    public void startParsing(File file) {

        try {scan = new Scanner (file);}
        catch (FileNotFoundException e) {e.printStackTrace();}

        System.out.println("Loaded the following file and start parsing " + file);
        scan.useDelimiter(",");

        while (scan.hasNext()) {
            System.out.println(scan.next());
        }
    }
}
