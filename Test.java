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

        /**
        * This test loops over the Dolphins image and
        * prints out the count of white pixels and of none
        * white pixels.
        *
        */
        public void testPixelCount(BufferedImage image) {
            Controller c = new Controller();
            ScreenImage i = new BufferedScreenImage(image);

            int pixelWhite = 0;
            int pixelNonWhite = 0;

            ScreenImage toReturn = i.copy();

            //Get height and width of the image
            int height = toReturn.getHeight();
            int width = toReturn.getWidth();

            //Loop over the image pixel by pixel
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    //Depending on the defined case (min or max), we set the y-Value to
                    //the new highest/lowest value, if it not white.
                    if (!isColorWhite(x, y, toReturn)) {
                        pixelNonWhite++;
                    } else if (isColorWhite(x, y, toReturn)) {
                        pixelWhite++;
                    }
                }
            }
            System.out.println("Pixel count white: " + pixelWhite + ".");
            System.out.println("Pixel count non-white: " + pixelNonWhite + ".");
        }

    }
}
