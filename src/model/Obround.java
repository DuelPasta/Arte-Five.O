package model;

public class Obround extends Rectangle {

    private int dCode;
    private double area;
    private double x;
    private double y;
    private double areaRatio;
    private double transferEffeciency;
    private double thickness;

    public Obround() {
            this.dCode = 0;
            this.area = 0.0;
            this.x = 0.0;
            this.y = 0.0;
            this.thickness = 0.0;
    }

    @Override
    public String toString() {
        return "Obround{" +
                "dCode=" + dCode +
                ", area=" + area +
                ", x=" + x +
                ", y=" + y +
                ", areaRatio=" + areaRatio +
                ", transferEffeciency=" + transferEffeciency +
                ", thickness=" + thickness +
                '}';
    }

    @Override
    public void setdCode(int dCode) {
        this.dCode = dCode;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }
}
