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
    } catch (IOException e) {
      e.printStackTrace();
    }
    // Calls testPixelCount method in Controller
    ModuleLayer.Controller.testPixelCount(image);
  }
}
