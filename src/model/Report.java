package model;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;

public class Report {

    private ArrayList<String> report;
    private ArrayList<Shape> apertures;
    private String fileLocation;

    public Report(ArrayList<Shape> apertures, String fileLocation) {
        this.fileLocation = fileLocation;
        report = new ArrayList<>();
        this.apertures = apertures;
        checkAreaRatio();
        createReport();
    }

    private void checkAreaRatio() {

        double areaRatio;
        int dCode;

        for (Shape shape : apertures) {

            areaRatio = shape.getAreaRatio();
            dCode = shape.getdCode();

            if (areaRatio <= 0.6)
            report.add(String.format(">--- WARNING!!! Aperture with Dcode: %5d is %2.2f. Should be at least 0.6 ---<", dCode, areaRatio));
        }
        report.add("\n\n\n\n\n");
    }

    public void createReport() {

        System.out.println(fileLocation);
        Path file = Paths.get(fileLocation + "\\Report.txt");
        try {
            Files.write(file, report, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
