package model;

public class Circle {

    private double radius;
    private double area;
    private int areaRatio;
    private int transferEffeciency;

    public Circle(double radius) {

        this.radius = radius;

    }

    public double getArea() {

        return Math.PI * (radius * radius);

    }
}
