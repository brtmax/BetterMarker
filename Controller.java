import java.awt.Color;
import java.awt.ScreenImage;
import java.awt.BufferedImage;
/*
* This is the controller class of the BetterMarker program
* It handles all calculations and util methods.
* @author Max Bretschneider
* @author Philipp Richter
* @version 0.0.1-SNAPSHOT
*/

public static Class Controller {


    /**
    * This method converts a screenImage instance to a BufferedImage instance
    * @param screenImage image that needs to be converted
    * @return returns the converted image as BufferdImage type
    */
    public static BufferedImage convertScreenImageToBufferedImage(ScreenImage screenImage) {

            //Creates a new BufferedImage instance, based on the screenImages
            //height and width.
            BufferedImage output = new BufferedImage(
                    screenImage.getWidth(),
                    screenImage.getHeight(),
                    BufferedImage.TYPE_INT_ARGB
            );
            //Loops over the screenImage and sets the color of the
            // (i,j)th pixel in the new BufferedImage to that of
            // the (i,j)th pixel of the screenImage.
            for (int i = 0; i < screenImage.getWidth(); i++) {
                for (int j = 0; j < screenImage.getHeight(); j++) {
                    output.setRGB(i, j, screenImage.getColor(i, j));
                }
            }
            //Returns converted image
            return output;
    }

    /**
    * Calculate the difference between two colors in the RGBA color space using the euclidean norm.
    *
    * @param a color a
    * @param b color b
    * @return euclidean distance between two colors in the RGBA color space
    */
    public double colorDistance(Color a, Color b) {

        double diffRed = Math.abs(a.getRed() - b.getRed());
        double diffGreen = Math.abs(a.getGreen() - b.getGreen());
        double diffBlue = Math.abs(a.getBlue() - b.getBlue());

        return Math.sqrt((diffRed * diffRed) + (diffGreen * diffGreen) + (diffBlue * diffBlue));
    }

    /**
    * This method finds the highest point in the given image that is still
    * a part of the text.
    * It does it by looping over the image and returning the highest or lowestY
    * value that is not white.
    */
    public int getYCoordinate(ScreenImage image, String position) {

        //Initialize default highest and lowest values
        int highestY = 0;
        int lowestY = 100000;

        //Create a copy of the image to work on
        ScreenImage toReturn = image.copy();

        //Get height and width of the image
        int height = toReturn.getHeight();
        int width = toReturn.getWidth();

        //Loop over the image pixel by pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                //Checks what is asked for, i.e. highest or lowest value
                if (position.equals("Min") && y < lowestY && ) {
                    lowestY = y;
                } else if (position.equals("Max") && y > highestY) {
                    highestY = y;
                }
            }
        }

        //Returns the specified value
        if (position.equals("Min")) {
            return lowestY;
        } else {
            return highestY;
        }
    }


        public boolean isColorWhite(int x, int y, ScreenImage image) {
            Color white = new Color();
            Color color = new Color (image.getColor(x, y));
            ScreenImage.
            return
        }
}
