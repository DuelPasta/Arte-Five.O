package model;

import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;

public class Circle extends Shape {

    @Override
    public void getArea() {
        area = Math.PI * power(x,2);
    }

    @Override
    public void getAreaRatio() {
        areaRatio = area / (Math.PI * x * thickness);
    }

    @Override
    public void getTransferEffeciency() {
        transferEffeciency = x / thickness;

    }
}
