import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Test {

  public static void main(String[] args) {

    BufferedImage image = null;

    try {
      File imageFile = new File("res/Dolphins.png");
      image = ImageIO.read(imageFile);
    } catch (IOException e) {
      e.prprintStackTrace();
    }

    Controller.testPixelCount(image);
  }
}
