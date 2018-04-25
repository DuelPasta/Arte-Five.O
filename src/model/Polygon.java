package model;


import javafx.geometry.Point2D;

import java.util.ArrayList;

public class Polygon extends Shape {

    private ArrayList<Point2D> pol;

    public Polygon() {
        super();
        pol = new ArrayList<>();
    }

    public void setPoint(double x, double y) {
        Point2D point = new Point2D(x, y);
        this.pol.add(point);

    }

    public double getArea(double[] x, double[] y, int numPoints) {
        double area = 0;         // Accumulates area in the loop
        int j = numPoints - 1;  // The last vertex is the 'previous' one to the first

        for (int i = 0; i < numPoints; i++) {
            area = area + (x[j] + x[i]) * (y[j] - y[i]);
            j = i;  //j is previous vertex to i
        }
        return area / 2;
    }
}

