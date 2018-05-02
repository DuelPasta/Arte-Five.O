package controller;

import model.Shape;
import java.util.ArrayList;
import java.util.Comparator;

public class Apertures {

    private ArrayList<Shape> apertures;

    Apertures() {

        apertures = new ArrayList<>();

    }

     void addApertures(ArrayList<Shape> apertures) {
        this.apertures = apertures;
    }

    void sortList() {
        apertures.sort(Comparator.comparingDouble(Shape::getAreaRatio));
        for (Shape aperture : apertures) {
            System.out.println(aperture.getOutput());
        }
    }
}
