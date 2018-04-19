package model;

public class Circle {

    private double radius;
    private double area;
    private int areaRatio;
    private int transferEffeciency;

    public Circle(int dCode, double radius) {

        this.radius = radius;
        System.out.println("Circle received!!");

    }

    public double getArea() {

        return Math.PI * (radius * radius);

    }
}
