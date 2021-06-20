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

    public static point LOWER_LEFT_INCREASED;
    public static point LOWER_RIGHT_INCREASED;
    public static point UPPER_LEFT_INCREASED;
    public static point UPPER_RIGHT_INCREASED;

    public static int highlightRectangleUpperY;
    public static int hightlightRectangleLowerY;

    public static int textRectangleUpperY;
    public static int textRectangleLowerY;

    public static int highestNonWhite;
    public static int lowestNonWhite;

    public static ScreenImage image;
    public static BufferdImage background;

    public static int highlightedRectangleHeight;

    public static highlight(ScreenImage i) {

        background = convertScreenImageToBufferedImage(i);

        //TODO
        //Set old values to the rectangle entered by the user
        /*
        UPPER_LEFT_OLD =
        UPPER_RIGHT_OLD =
        LOWER_LEFT_OLD =
        LOWER_RIGHT_OLD =
        */

        //Increases the y-values, basically enlarging the search area
        LOWER_LEFT_INCREASED = setLocation(LOWER_LEFT_OLD.x(), getIncreasedYValue(LOWER_LEFT_OLD.y(), image));
        LOWER_RIGHT_INCREASED = setLocation(LOWER_RIGHT_OLD.x(), getIncreasedYValue(LOWER_RIGHT_OLD.y(), image));
        UPPER_LEFT_INCREASED = setLocation(UPPER_LEFT_OLD.x(), getIncreasedYValue(UPPER_LEFT_OLD.y(), image));
        UPPER_RIGHT_INCREASED = setLocation(UPPER_RIGHT_OLD.x(), getIncreasedYValue(UPPER_RIGHT_OLD.y(), image));

        setYCoordinate(background, "Min");
        setYCoordinate(background, "Max");

        calculateHalfpoint(textRectangleLowerY, textRectangleUpperY);

    }

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
    public void setYCoordinate(ScreenImage image, String position) {

        //Initialize default highest and lowest values
        textRectangleUpperY = 0;
        textRectangleLowerY = 100000;

        //Create a copy of the image to work on
        ScreenImage toReturn = image.copy();


        //Loop over the image pixel by pixel
        // x-coordinate is left as is
        // y-coordinate is "fitted", there are a few pixels added to it.
        for (int x = UPPER_LEFT_OLD.x(); x < UPPER_RIGHT_OLD; y++) {
            for (int y = UPPER_LEFT_INCREASED; y < LOWER_LEFT_INCREASED; x++) {

                //Depending on the defined case (min or max), we set the y-Value to
                //the new highest/lowest value, if it not white.
                if (position.equals("Min") && (y < textRectangleLowerY) && !isColorWhite(x, y, toReturn)) {
                    textRectangleLowerY = y;
                } else if (position.equals("Max") && (y > textRectangleUpperY) && !isColorWhite(x, y, toReturn)) {
                    textRectangleUpperY = y;
                }
            }
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
    public int getIncreaseYValue(int y, BufferdImage image) {
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
    public void calculateHalfpoint(int lower, int upper) {

        int difference = upper - lower;
        if (difference == 0) {
            //TODO, ERROR
            return;
        }

            halfwayPoint = lower + (difference / 2);
    }

    public void createFittedRectangle() {
        int heightRectangle = UPPER_LEFT.y() - LOWER_LEFT.y();
        int heighToBeAdded = heightRectangle / 2;

        int halfwaypoint = calculateHalfpoint(UPPER_LEFT.y(), LOWER_LEFT.y());

        int upper = getYCoordinate(image, "Max");
        int lower = getYCoordinate(image, "Min");

        LOWER_LEFT_FITTED.setLocation(LOWER_LEFT_OLD.x(), lower);
        LOWER_RIGHT_FITTED.setLocation(LOWER_RIGHT_OLD, lower)
        UPPER_LEFT_FITTED.setLocation(UPPER_LEFT_OLD, upper);
        UPPER_RIGHT_FITTED.setLocation(UPPER_RIGHT_OLD, upper);
    }

    /**
    * This method takes the updated rectangles coordinates, loops over import junit.framework.TestCase;
    * and highlights the are in (currently only) neon yellow.
    */
    public void highlightRectangle() {

        for (int x = UPPER_LEFT_FITTED.x(); x < UPPER_RIGHT_FITTED; y++) {
            for (int y = UPPER_LEFT_FITTED; y < LOWER_LEFT_FITTED; x++) {
                //Black for testing, will be changed later
                image.setColor(COLOR.BLACK);
    }
}
