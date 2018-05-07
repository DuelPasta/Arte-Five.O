package model;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Report {

    private ArrayList<String> report;
    private ArrayList<Shape> apertures;

    public Report(ArrayList<Shape> apertures) {
        report = new ArrayList<>();
        this.apertures = apertures;
        checkApertures();
        checkAreaRatio();
        checkBGA();
        checkUltraFinePitch();
    }

    private void checkApertures() {
        apertures.sort(Comparator.comparingDouble(Shape::getAreaRatio));
        for (Shape aperture : apertures) {
            System.out.println(aperture.getOutput());
            if (!aperture.getShape().equals("Polygon")) {
                report.add(aperture.getOutput());
            }
        }
        report.add("\n\n\n\n\n");
    }

    private void checkAreaRatio() {
        for (Shape shape : apertures) {
            if (shape.getAreaRatio() <= 0.6)
                report.add(String.format(">--- WARNING!!! Aperture with Dcode: %5d is %2.2f. Should be at least 0.6 ---<", shape.getdCode(), shape.getAreaRatio()));
        }
        report.add("\n\n\n\n\n");
    }

    private void checkBGA() {
        for (Shape shape : apertures) {
            if (shape.getX() == 0.28 && shape.getY() == 0.28) {
                report.add(">--- INFO   !!! Pitch .5 µBGA possibly found. Advise a Type 4   solderpaste");
            } else if (shape.getX() == 0.245 && shape.getY() == 0.245) {
                report.add(">--- INFO   !!! Pitch .4 µBGA possibly found. Advise a Type 4.5 solderpaste");
            }
        }
    }

    private void checkUltraFinePitch() {
        for (Shape shape : apertures) {
            if ((shape.getX() == 0.16 || shape.getY() == 0.16) || (shape.getX() == 0.15 || shape.getY() == 0.15)) {
                report.add(">--- INFO   !!! Pitch .35 Fine Pitch possibly found. Advise a Type 4 solderpaste");
            }
        }
    }

    public void createReport(String fileLocation) {
        System.out.println(fileLocation);
        Path file = Paths.get(fileLocation + "\\Report.txt");
        try {
            Files.write(file, report, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(ArrayList<String> addToReport) {
        report.addAll(addToReport);
    }


}
