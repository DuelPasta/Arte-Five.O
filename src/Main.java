import controller.Parser;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.util.Scanner;

public class Main extends Application {

    private static double thickness;

    public void start(Stage primaryStage) {
        FileChooser configFileChooser = new FileChooser();

        File configFile = configFileChooser.showOpenDialog(null);
        getThickness();
        Parser parser = new Parser(configFile, thickness);
        parser.parse();
        System.exit(1);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void getThickness() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter thickness:");
        thickness = scan.nextDouble() / 1000;
    }
}