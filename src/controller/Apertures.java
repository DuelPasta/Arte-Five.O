package controller;

import model.Shape;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Apertures {

    private ArrayList<Shape> apertures;

    Apertures() {

        apertures = new ArrayList<>();

    }

    public void addApertures(ArrayList<Shape> apertures) {
        this.apertures = apertures;
    }

    public void sortList() {
        apertures.sort(Comparator.comparingDouble(Shape::AreaRatio));
        for (Shape aperture : apertures) {
            System.out.println(aperture.getOutput());
        }
    }
}
