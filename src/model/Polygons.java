package model;

import javafx.collections.ObservableList;
import javafx.scene.shape.Polygon;

public class Polygons {

    private Polygon pol;

    public Polygons() {
        pol = new Polygon();
    }

    public void setPoint(double x, double y) {
        this.pol.getPoints().addAll(x, y);
        System.out.println(pol.toString());
    }

    public double getPolArea() {


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