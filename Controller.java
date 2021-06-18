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

}
