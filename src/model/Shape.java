package model;

/**
 *  Super class for all shapes.
 */

public class Shape {

    private int dCode;
    private double area;
    private double x;
    private double y;
    private double thickness;
    private String shape;

    /**
     * This method created a formatted string to write to a report file.
     * @return Returns formatted string with all attributes.
     * */
    public String getOutput() {
        return String.format("DCode: %-7.0f  \"%-9s\" \t - Size: %5.3fmm x %5.3fmm - \t Area: %6.3fmm² \t Area Ratio: %5.2f \t Transfer Effeciency: %2.1f"
                , (double) dCode
                , shape
                , x
                , y
                , getArea()
                , getAreaRatio()
                , getTransferEffeciency());}

    /**
     * This method calculates the area of the shape.
     */
    public double getArea() {
        return x * y;
    }

    /**
     * This method calculates the Area Ratio of the shape.
     */
    public double getAreaRatio() {

        return getArea()/(2 * (getX() + getY()) * getThickness());
    }

    /**
     * This method calculates the Transfer Effeciency
     */
    public double getTransferEffeciency() {
        double w;
        w = (x < y ? x : y);
        return w / thickness;

    }

    // Setters
    public void setdCode(int dCode)                 {this.dCode = dCode;}
    public void setX(double x)                      {this.x = x;}
    public void setY(double y)                      {this.y = y;}
    public void setThickness(double thickness)      {this.thickness = thickness;}
    public void setShape(String shape)              {this.shape = shape;}

    // Getters
    public int getdCode()                           {return dCode;}
    public double getX()                            {return x;}
    public double getY()                            {return y;}
    public double getThickness()                    {return thickness;}
    public String getShape()                        {return shape;}
}
