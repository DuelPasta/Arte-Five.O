package model;


import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Collections;

public class Polygon extends Shape {

    private int counter;
    private ArrayList<Double> x;
    private ArrayList<Double> y;
    private double minx;
    private double miny;
    private double sizeX;
    private double sizeY;
    private double thickness =0;

    public Polygon() {
        super.setdCode(counter += 1);
        x = new ArrayList<>();
        y = new ArrayList<>();

    }

    public void addPoint(double x, double y) {
        this.x.add(x);
        this.y.add(y);
        minx = Collections.min(this.x);
        miny = Collections.min(this.y);
    }

    public double getArea() {
        double area = 0;         // Accumulates area in the loop
        int numPoints = x.size();
        int j = numPoints - 1;  // The last vertex is the 'previous' one to the first

        for (int i = 0; i < numPoints; i++) {
            area = area + (this.x.get(j) + this.x.get(i)) * (this.y.get(j) - this.y.get(i));
            j = i;  //j is previous vertex to i
        }
        return area / 2;
    }

    public double getTransferEffeciency() {
        double w;

        w = (minx < miny ? minx : miny);
        return w / thickness;

    }

    public String getOutput() {
        return String.format("DCode: %-7.0f  \"%-9s\" \t - Size: %5.3fmm x %5.3fmm - \t Area: %6.3fmm² \t Area Ratio: %5.2f \t Transfer Effeciency: %2.1f"
                , (double) super.getdCode()
                , "Polygon"
                , sizeX
                , sizeY
                , getArea()
                , getAreaRatio()
                , getTransferEffeciency());
    }

    public void setSize() {
        sizeX = Collections.max(this.x) - Collections.min(this.x);
        sizeY = Collections.max(this.y) - Collections.min(this.y);
    }
}

