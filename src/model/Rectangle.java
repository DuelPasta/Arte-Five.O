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

    public Rectangle() {
        this.dCode = 0;
        this.area = 0.0;
        this.x = 0.0;
        this.y = 0.0;
        this.thickness = 0.0;

    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "dCode=" + dCode +
                ", area=" + area +
                ", x=" + x +
                ", y=" + y +
                ", areaRatio=" + areaRatio +
                ", transferEffeciency=" + transferEffeciency +
                ", thickness=" + thickness +
                '}';
    }

    public double calculateArea() {
        return this.x * this.y;
    }


    /* TODO
    *     public void calculateAreaRatio() {
    }
    *
    * */



        /* TODO
         * Calculate TE based on Area Ratio
            public double transferEffeciency() {
                    return x;
    }
         */

    //Getters
    private double getArea(double x, double y) {return x * y;}
    private double getAreaRatio(double thickness, double x, double y) {return areaRatio;}
    private double getTransferEffeciency(double areaRatio) {return transferEffeciency;}

    //Setters
    public void setdCode(int dCode) {this.dCode = dCode;}
    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}
    public void setThickness(double thickness) {this.thickness = thickness;}


}
