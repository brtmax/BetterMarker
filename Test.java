import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Test {

    public static void public static void main(String[] args) {

        BufferedImage image = null;

        try {
            File imageFile = new File("res/Dolphins.png");
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.prprintStackTrace();
        }

    }
}
