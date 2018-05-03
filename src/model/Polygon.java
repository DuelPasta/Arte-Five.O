package model;

public class Polygon extends Shape {

    public static int count = 0;

    public Polygon(int dCode, double x, double y, double thickness, String shape) {
        super(dCode,x,y,thickness,shape);
        count++;
    }
}

