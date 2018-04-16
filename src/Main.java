import controller.GerberParser;
import model.Circle;
import model.Polygons;

import javax.swing.*;
import java.io.File;

public class Main {

    public static void main(String[] args) {

        //class tests
Polygons poly = new Polygons();

poly.setPoint(4.10,0.05);
        poly.setPoint(0.03,0.03);
        poly.setPoint(-0.07,2.86);
        poly.setPoint(3.97,2.92);
        poly.setPoint(2.49,1.4);
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