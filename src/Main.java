import logic.GerberParser;

import javax.swing.*;
import java.io.File;

public class Main {



    public static void main(String[] args) {

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