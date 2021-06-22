package org.jis.java;
import java.awt.*;
import java.awt.image.BufferedImage;

/*
* This is the controller class of the BetterMarker program
* It handles all calculations and util methods.
* @author Max Bretschneider
* @author Philipp Richter
* @version 0.0.1-SNAPSHOT
*/

public class Controller {

    //OLD == USER GIVEN
    public static Point LOWER_LEFT_OLD;
    public static Point LOWER_RIGHT_OLD;
    public static Point UPPER_LEFT_OLD;
    public static Point UPPER_RIGHT_OLD;

    //FITTED RECTANGLE TO BE DRAWN
    public static Point LOWER_LEFT_FITTED;
    public static Point LOWER_RIGHT_FITTED;
    public static Point UPPER_LEFT_FITTED;
    public static Point UPPER_RIGHT_FITTED;

    //INCREASED == LARGER SEARCH AREA
    public static Point LOWER_LEFT_INCREASED;
    public static Point LOWER_RIGHT_INCREASED;
    public static Point UPPER_LEFT_INCREASED;
    public static Point UPPER_RIGHT_INCREASED;

    public static int textRectangleUpperY;
    public static int textRectangleLowerY;

    public static BufferedImage image;
    public static BufferedImage background;

    public static int highlightedRectangleHeight;

    public static int middle;

    public static void highlight(ScreenImage i) {

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
        LOWER_LEFT_INCREASED.setLocation(LOWER_LEFT_OLD.getX(), getIncreasedYValue((int) LOWER_LEFT_OLD.getY(), background));
        LOWER_RIGHT_INCREASED.setLocation(LOWER_RIGHT_OLD.getX(), getIncreasedYValue((int) LOWER_RIGHT_OLD.getY(), background));
        UPPER_LEFT_INCREASED.setLocation(UPPER_LEFT_OLD.getX(), getIncreasedYValue((int) UPPER_LEFT_OLD.getY(), background));
        UPPER_RIGHT_INCREASED.setLocation(UPPER_RIGHT_OLD.getX(), getIncreasedYValue((int) UPPER_RIGHT_OLD.getY(), background));

        //Sets highest and lowest text pixel y-Value
        setTextRectangleYCoordinate(background, "Min");
        setTextRectangleYCoordinate(background, "Max");

        //Calculates the halfway point right between the top of the letters and the bottom
        calculateMiddle(textRectangleLowerY, textRectangleUpperY);

        //Calcualtes height of actual drawn rectangle, usually a fixed height in most applications
        highlightedRectangleHeight = (int) (LOWER_LEFT_OLD.getY() - UPPER_LEFT_OLD.getY());

        //Sets final highlighted rectangle corner coordinates
        UPPER_LEFT_FITTED.setLocation(UPPER_LEFT_OLD.getX(), middle - highlightedRectangleHeight / 2);
        LOWER_LEFT_FITTED.setLocation(LOWER_LEFT_OLD.getX(), middle + highlightedRectangleHeight / 2);
        UPPER_RIGHT_FITTED.setLocation(UPPER_RIGHT_OLD.getX(), middle - highlightedRectangleHeight / 2);
        LOWER_RIGHT_FITTED.setLocation(LOWER_RIGHT_OLD.getX(), middle + highlightedRectangleHeight / 2);

        highlightRectangle();
    }

    /**
     * This method is currently just for testing. DynamicFitting searches above and below
     * the middle point of the user-given rectangle and stops when it finds an all-white line.
     * This way we can fit the rectangle more accurately.
     */
    public static void dynamicFitting() {
        //Given the user input rectangle, we can calculate the middle point of it.
        calculateMiddle((int) UPPER_LEFT_OLD.getY(), (int) LOWER_LEFT_OLD.getY());

        //TODO
    }

    /**
     * This method converts a screenImage instance to a BufferedImage instance
     *
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
     * This method copies a BufferedImage
     *
     * @param source image to be copied
     * @return returns a copy of the given image
     */
    public static BufferedImage copyImage(BufferedImage source) {
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
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
    public static void setTextRectangleYCoordinate(BufferedImage image, String position) {

        //Initialize default highest and lowest values
        textRectangleUpperY = 0;
        textRectangleLowerY = Integer.MAX_VALUE;

        //Loop over the image pixel by pixel
        // x-coordinate is left as is
        // y-coordinate is "fitted", there are a few pixels added to it.
        for (int x = (int) UPPER_LEFT_OLD.getX(); x < UPPER_RIGHT_OLD.getX(); x++) {
            for (int y = (int) UPPER_LEFT_INCREASED.getY(); y < LOWER_LEFT_INCREASED.getY(); y++) {

                //Depending on the defined case (min or max), we set the y-Value to
                //the new highest/lowest value, if it is not white.
                if (position.equals("Min") && (y < textRectangleLowerY) && !isColorWhite(x, y, image)) {
                    textRectangleLowerY = y;
                } else if (position.equals("Max") && (y > textRectangleUpperY) && !isColorWhite(x, y, image)) {
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
     *
     * @param x,     x-Coordinate on the image
     * @param y,     y-Coordinate on the image
     * @param image, image that is passed.
     */
    public static boolean isColorWhite(int x, int y, BufferedImage image) {

        Color color = new Color(image.getRGB(x, y));
        return (color.equals(Color.WHITE));
    }

    /**
     * This method increases the y-value based on the images height.
     *
     * @param y,     y-coordinate
     * @param image, !background! image, not the rectangle that
     *               the user chose but the whole image.
     * @return int, increased y-value
     */
    public static int getIncreasedYValue(int y, BufferedImage image) {
        //100 is a test value, the distance between the point of an
        //i and the line is about 10px in an image that is 1200px high
        return y + (image.getHeight() / 100);
    }

    /**
     * This method calculates the halfway point between the lower and
     * the upper y-coordinates.
     *
     * @param upper, highest qualifying y-value.
     * @param lower, lowest qualifying y-value.
     * @return int, halfway point between these two values
     */
    public static void calculateMiddle(int lower, int upper) {

        int difference = upper - lower;
        if (difference == 0) {
            //TODO, ERROR
            return;
        }

        middle = lower + (difference / 2);
    }

    public void createFittedRectangle() {
        int heightRectangle = (int) (UPPER_LEFT_OLD.getY() - LOWER_LEFT_OLD.getY());
        int heighToBeAdded = heightRectangle / 2;

        int upper = textRectangleUpperY;
        int lower = textRectangleLowerY;

        LOWER_LEFT_FITTED.setLocation(LOWER_LEFT_OLD.getX(), lower);
        LOWER_RIGHT_FITTED.setLocation(LOWER_RIGHT_OLD.getX(), lower);
        UPPER_LEFT_FITTED.setLocation(UPPER_LEFT_OLD.getX(), upper);
        UPPER_RIGHT_FITTED.setLocation(UPPER_RIGHT_OLD.getX(), upper);
    }

    /**
     * This method takes the updated rectangles coordinates, loops over import junit.framework.TestCase;
     * and highlights the are in (currently only) neon yellow.
     */
    public static void highlightRectangle() {

        for (int x = (int) UPPER_LEFT_FITTED.getX(); x < UPPER_RIGHT_FITTED.getX(); x++) {
            for (int y = (int) UPPER_LEFT_FITTED.getY(); y < LOWER_LEFT_FITTED.getY(); y++) {
                //Black for testing, will be changed later
                image.setRGB(x, y, Color.BLACK.getRGB());
            }
        }
    }
}

