package controller;

import model.Report;
import model.Shape;

import java.util.ArrayList;
import java.util.Comparator;

public class Apertures {

    private ArrayList<Shape> apertures;

    Apertures(ArrayList<Shape> apertures) {
        this.apertures = apertures;
    }

    void showApertures() {
        apertures.sort(Comparator.comparingDouble(Shape::getAreaRatio));
        for (Shape aperture : apertures) {
            System.out.println(aperture.getOutput());
        }
    }

    public void createReport(String fileLocation) {
        Report report = new Report(apertures, fileLocation);
        report.createReport();
    }
}
