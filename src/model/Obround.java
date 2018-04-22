package model;

public class Obround extends Rectangle {

    public Obround() {
        super();
    }

    //Getters
    @Override
    public String getOutput() {
        return String.format("DCode: %-7.0f  \"%-9s\" \t - Size: %5.3fmm x %5.3fmm - \t Area: %6.3fmm² \t Area Ratio: %5.2f \t Transfer Effeciency: %2.1f",  (double) dCode, "Oblong",  x, y, area, areaRatio, transferEffeciency);
    }
}
