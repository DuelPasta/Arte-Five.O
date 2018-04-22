package model;

public class Rectangle {

    int dCode;
    double area;
    double x;
    double y;
    double areaRatio;
    double transferEffeciency;
    double thickness;

    public Rectangle() {
        this.dCode = 0;
        this.area = 0.0;
        this.x = 0.0;
        this.y = 0.0;
        this.thickness = 0.0;
        this.areaRatio = 0.0;

    }

    //Getters
    public String getOutput() {
        return String.format("DCode: %-7.0f  \"%-9s\" \t - Size: %5.3fmm x %5.3fmm - \t Area: %6.3fmm² \t Area Ratio: %5.2f \t Transfer Effeciency: %2.1f",  (double) dCode, "Rectangle",  x, y, area, areaRatio, transferEffeciency);
    }

    public void getArea() {
        area = x * y;
    }

    public void getAreaRatio() {
        areaRatio = area/(2 * (x + y) * thickness);
    }

    public double compareAreaRatio() {
        return this.areaRatio;
    }

    public void getTransferEffeciency() {
        double w;
        w = (x < y ? x : y);
        transferEffeciency = w / thickness;

    }

    //Setters
    public void setdCode(int dCode) {this.dCode = dCode;}
    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}
    public void setThickness(double thickness) {this.thickness = thickness;}


}
