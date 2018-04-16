package model;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

import java.awt.*;
import java.util.ArrayList;

public class Polygons {

    private ArrayList<Point2D> pol;

    public Polygons() {
        pol = new ArrayList<>();
    }

    public void setPoint(double x, double y) {
        Point2D point = new Point2D(x, y);

        this.pol.add(point);
        System.out.println(pol.toString());
    }

    public double getPolArea() {

        int size = pol.size() ;
        double[] x = new double[size];
        double[] y = new double[size];
        for (int i = 0; i < size; i++) {
            x[i] = pol.get(i).getX();
            y[i] = pol.get(i).getY();
            System.out.println(x[i] + " " + y[i]);
        }

        return polygonArea(x,y,size);
        }

    private double polygonArea(double[] x, double[] y, int numPoints) {
        double area = 0;         // Accumulates area in the loop
        int j = numPoints-1;  // The last vertex is the 'previous' one to the first

        for (int i=0; i<numPoints; i++)
        { area = area +  (x[j]+x[i]) * (y[j]-y[i]);
            j = i;  //j is previous vertex to i
        }
        return area/2;
    }
}