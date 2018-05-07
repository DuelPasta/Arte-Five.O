package model;

public class GerberSettings {

    private int precision;
    private String unit;

    public GerberSettings(int precision, String unit) {
        this.precision = precision;
        this.unit = unit;
    }

    public int getPrecision() {
        return precision;
    }

    public String getUnit() {
        return unit;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
