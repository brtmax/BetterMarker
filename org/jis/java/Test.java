package org.jis.java;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Test {

  public static void main(String[] args) {

    // Create an empty BufferedImage instance
    BufferedImage image = null;

    try {
      // Reads Dolphin png into a new file
      File imageFile = new File("res/Dolphins.png");
      // assigns new BufferedImage from Dolphin file to image
      image = ImageIO.read(imageFile);

      Controller c = new Controller();
      ScreenImage i = new BufferedScreenImage(image);

      int pixelWhite = 0;
      int pixelNonWhite = 0;

      //Get height and width of the image
      int height = image.getHeight();
      int width = image.getWidth();

      //Loop over the image pixel by pixel
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {

          //Depending on the defined case (min or max), we set the y-Value to
          //the new highest/lowest value, if it not white.
          if (!Controller.isColorWhite(x, y, image)) {
            pixelNonWhite++;
          } else if (Controller.isColorWhite(x, y, image)) {
            pixelWhite++;
          }
        }
      }
      System.out.println("Pixel count white: " + pixelWhite + ".");
      System.out.println("Pixel count non-white: " + pixelNonWhite + ".");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
