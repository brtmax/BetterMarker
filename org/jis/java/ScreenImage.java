package org.jis.java;
import java.awt.Image;
import java.io.IOException;

/**
 * The {@link ScreenImage} interface represents a mutable ARGB image with additional methods
 * compared to the default {@link Image} implementation.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public interface ScreenImage {

  /**
   * Set a pixel transparent by setting the alpha channel to zero.
   */
  int TRANSPARENT_ALPHA_CHANNEL = 0;

  /**
   * Set the color of the given pixel located at x and y.
   *
   * @param x     x coordinate
   * @param y     y coordinate
   * @param color ARGB color value with 8 bits per channel
   * @throws IllegalArgumentException if the coordinates are out of bounds
   */
  void setColor(int x, int y, int color);

  /**
   * Get the color of the given pixel located at x and y.
   *
   * @param x x coordinate
   * @param y y coordinate
   * @return pixel color as ARGB value with 8 bits per channel
   * @throws IllegalArgumentException if the coordinates are out of bounds
   */
  int getColor(int x, int y);

  /**
   * Scale the image to the given width.<br>
   * The scaling keeps the original width-height ratio.
   *
   * @param width new image width
   * @throws IllegalArgumentException if the width is negative or zero
   */
  void scaleToWidth(int width);

  /**
   * Scale the image to the given height.<br>
   * The scaling keeps the original width-height ratio.
   *
   * @param height new image height
   * @throws IllegalArgumentException if the height is negative or zero
   */
  void scaleToHeight(int height);

  /**
   * Get the width of the image.
   *
   * @return image width
   */
  int getWidth();

  /**
   * Get the height of the image.
   *
   * @return image height
   */
  int getHeight();

  /**
   * Return a copy of the current image.
   *
   * @return copied image
   */
  ScreenImage copy();

  /**
   * Save the image to a png file.
   * If the file already exists, it will be overwritten.
   *
   * @param path path of the image, including file name and png extension
   * @throws IOException if saving the image fails
   */
  void save(String path) throws IOException;
}
