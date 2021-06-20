import java.awt.Color;
import java.awt.ScreenImage;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/*
* This is the controller class of the BetterMarker program
* It handles all calculations and util methods.
* @author Max Bretschneider
* @author Philipp Richter
* @version 0.0.1-SNAPSHOT
*/

public static Class Controller {

    public static point LOWER_LEFT_OLD;
    public static point LOWER_RIGHT_OLD;
    public static point UPPER_LEFT_OLD;
    public static point UPPER_RIGHT_OLD;

    public static point LOWER_LEFT_FITTED;
    public static point LOWER_RIGHT_FITTED;
    public static point UPPER_LEFT_FITTED;
    public static point UPPER_RIGHT_FITTED;

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

                //Depending on the defined case (min or max), we set the y-Value to
                //the new highest/lowest value, if it not white.
                if (position.equals("Min") && (y < lowestY) && !isColorWhite(x, y, toReturn)) {
                    lowestY = y;
                } else if (position.equals("Max") && (y > highestY) && !isColorWhite(x, y, toReturn)) {
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


    /**
    * This method compares the color of the given coordinate on the image
    * to the white color, and returns true if it is white and false if it's
    * anything else. That way we don't need to define a color distance, at least
    * for black and white images.
    * @param int x, x-Coordinate on the image
    * @param int y, y-Coordiante on the image
    * @param ScreenImage image, image that is passed.
    */
    public boolean isColorWhite(int x, int y, ScreenImage image) {

        Color color = new Color (image.getColor(x, y));
        return (color == Color.WHITE);
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

    /**
    * This method increases the y-value based on the images height.
    * @param int y, y-coordinate
    * @param BufferedImage image, !background! image, not the rectangle that
    * the user chose but the whole image.
    * @return int, increased y-value
    */
    public int increaseYValue(int y, BufferdImage image) {
        //100 is a test value, the distance between the point of an
        //i and the line is about 10px in an image that is 1200px high
        return y + (image.getHeight() / 100);
    }

    /**
    * This method calculates the halfway point between the lower and
    * the upper y-coordinates.
    * @param int upper, highest qualifying y-value.
    * @param int lower, lowest qualifying y-value.
    * @return int, halfway point between these two values
    */
    public int calculateHalfpoint(int lower, int upper) {

        int difference = upper - lower;
        if (difference == 0) {
            //TODO, ERROR
            return;
        }
            half = difference / 2;

        return lower + difference;
    }

    public void fitRectangle() {
        int heightRectangle = UPPER_LEFT.y() - LOWER_LEFT.y();
        int heighToBeAdded = heightRectangle / 2;

        int halfwaypoint = calculateHalfpoint(UPPER_LEFT.y(), LOWER_LEFT.y());
        


    }
}
