package model;

public class Obround extends Shape {

    public Obround(int dCode, double x, double y, double thickness, String shape) {
        super.setdCode(dCode);
        super.setX(x);
        super.setY(y);
        super.setThickness(thickness);
        super.setShape(shape);
        super.getArea();
        super.getAreaRatio();
        super.getTransferEffeciency();
    }

}
