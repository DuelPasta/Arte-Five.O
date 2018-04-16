import controller.GerberParser;
import model.Circle;
import model.Polygons;

import javax.swing.*;
import java.io.File;

public class Main {

    public static void main(String[] args) {

        //class tests
Polygons poly = new Polygons();

poly.setPoint(0,0);
        poly.setPoint(-5,-5);
        poly.setPoint(-3,2);
        poly.setPoint(9,4);

        System.out.println(poly.getPolArea());


        //Start the program
        Initialize();
    }

    public static void Initialize() {
        File file = null;
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            GerberParser parser = new GerberParser();
            parser.startParsing(file);
        }  else {
            JOptionPane.showMessageDialog(null, "No file selected");
            System.exit(1);
        }
    }
}