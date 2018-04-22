package model;

/**
 *  Super class for all shapes.
 */

public class Shape {

    int dCode;
    double area;
    double x;
    double y;
    double areaRatio;
    double transferEffeciency;
    double thickness;
    String shape;

    Shape() {
        this.dCode = 0;
        this.area = 0.0;
        this.x = 0.0;
        this.y = 0.0;
        this.thickness = 0.0;
        this.areaRatio = 0.0;
        this.transferEffeciency = 0.0;
        this.shape = "";

    }

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
                , area
                , areaRatio
                , transferEffeciency);}

    /**
     * This method calculates the area of the shape.
     */
    public void getArea() {
        area = x * y;
    }

    /**
     * This method calculates the Area Ratio of the shape.
     */
    public void getAreaRatio() {
        areaRatio = area/(2 * (x + y) * thickness);
    }

    /**
     * This method is needed to sort the apertures.
     */
    public double AreaRatio() {
        return this.areaRatio;
    }

    /**
     * This method calculates the Transfer Effeciency
     */
    public void getTransferEffeciency() {
        double w;
        w = (x < y ? x : y);
        transferEffeciency = w / thickness;

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
