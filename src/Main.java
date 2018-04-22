import controller.Aperture;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {

    private static double thickness;

    public static void main(String[] args) {
        Initialize();
    }

    private static void Initialize() {
        //TODO Find better alternative for JFilechooser as it's buggy.
        File file = null;
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            Aperture parser = new Aperture();
            getThickness();
            parser.startParsing(file, thickness);
        }  else {
            JOptionPane.showMessageDialog(null, "No file selected");
            System.exit(1);
        }
    }

    private static void getThickness() {

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter thickness:");
        thickness = scan.nextDouble()/1000;
    }
}