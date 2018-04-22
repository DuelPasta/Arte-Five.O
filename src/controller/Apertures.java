package controller;


import model.Rectangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Apertures {

    private ArrayList<Rectangle> rectangles = new ArrayList<>();

    public void setRectangles(ArrayList<Rectangle> rectangles) {

        this.rectangles = rectangles;

    }

    public void sortList() {
  Collections.sort(rectangles, new Comparator<Rectangle>() {
      @Override
      public int compare(Rectangle o1, Rectangle o2) {
          return Double.compare(o1.compareAreaRatio(), o2.compareAreaRatio());
      }
  });

  for (Rectangle rect : rectangles) {
      System.out.println(rect.getOutput());
  }


    }

}
