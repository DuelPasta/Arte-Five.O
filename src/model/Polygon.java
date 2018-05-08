package model;

public class Polygon extends Shape {

    private static int counter;

    public Polygon(int dCode, double x, double y, double thickness, String shape) {
        super(dCode,x,y,thickness,shape);
        counter++;
    }

    public static int getCounter() {
        return counter;
    }

}

