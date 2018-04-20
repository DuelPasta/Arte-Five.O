package model;

import controller.Apertures;

public class Rectangle {

    private int dCode;
    private double area;
    private double x;
    private double y;
    private double areaRatio;
    private double transferEffeciency;
    private double thickness;
    private Apertures apertures;

    public Rectangle(int dCode, double x, double y) {

        this.dCode = dCode;
        this.x = x;
        this.y = y;
        this.area = getArea(x, y);
        this.areaRatio = getAreaRatio(thickness, x, y);
        transferEffeciency = getTransferEffeciency(this.areaRatio);

        apertures = new Apertures("R", thickness, x, y, area, areaRatio, transferEffeciency);
    }

    private double getArea(double x, double y) {return x * y;}
    private double getAreaRatio(double thickness, double x, double y) {return areaRatio;}
    private double getTransferEffeciency(double areaRatio) {return transferEffeciency;}

}
