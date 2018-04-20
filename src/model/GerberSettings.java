package model;

public class GerberSettings {

    private String units;

    public GerberSettings() {
        this.units = "mm";
    }

    public void setUnit(String units)   {this.units = units;}
    public String getUnit()             {return units;}
}
