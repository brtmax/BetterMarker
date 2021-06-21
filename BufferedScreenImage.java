import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * An implementation of the {@link ScreenImage} interface based on a {@link BufferedImage}.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class BufferedScreenImage implements ScreenImage {

  private static final Color TRANSPARENT = new Color(0, 0, 0,
      ScreenImage.TRANSPARENT_ALPHA_CHANNEL);

  private BufferedImage image;

  /**
   * Create a new buffered screen image based on a {@link BufferedImage}.
   *
   * @param image buffered image
   */
  public BufferedScreenImage(BufferedImage image) {
    this.image = toARGBImage(image);
  }

  /**
   * Create a new transparent image with given width and height.<br>
   * Transparent refers to an alpha value of zero.
   * The values of the other color channels are undefined.
   *
   * @param width  image width
   * @param height image height
   * @throws IllegalArgumentException if width or height is not positive
   */
  public BufferedScreenImage(int width, int height) {
    assertCorrectWidthAndHeight(width, height);

    this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        setColor(x, y, BufferedScreenImage.TRANSPARENT.getRGB());
      }
    }
  }

  @Override
  public void setColor(int x, int y, int color) {
    assertCorrectCoordinates(x, y);

    image.setRGB(x, y, color);
  }

  @Override
  public int getColor(int x, int y) {
    assertCorrectCoordinates(x, y);

    return image.getRGB(x, y);
  }

  @Override
  public void scaleToWidth(int width) {
    if (width <= 0) {
      throw new IllegalArgumentException("Width has to be positive.");
    }

    this.image = ImageUtils.scaleWidth(image, width);
  }

  @Override
  public void scaleToHeight(int height) {
    if (height <= 0) {
      throw new IllegalArgumentException("Height has to be positive.");
    }

    this.image = ImageUtils.scaleHeight(image, height);
  }

  @Override
  public int getWidth() {
    return image.getWidth();
  }

  @Override
  public int getHeight() {
    return image.getHeight();
  }

  @Override
  public ScreenImage copy() {
    ScreenImage copy = createEmptyImage(image.getWidth(), image.getHeight());

    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        copy.setColor(x, y, getColor(x, y));
      }
    }

    return copy;
  }

  @Override
  public void save(String path) throws IOException {
    ImageIO.write(image, "png", new File(path));
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof ScreenImage image)) {
      return false;
    }

    if (getWidth() != image.getWidth() || getHeight() != image.getHeight()) {
      return false;
    }

    for (int x = 0; x < getWidth(); x++) {
      for (int y = 0; y < getHeight(); y++) {
        if (getColor(x, y) != image.getColor(x, y)) {
          return false;
        }
      }
    }

    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(image);
  }

  /**
   * Create an empty screen image based on the current width and height.<br>
   * The color values are undefined.
   *
   * @return empty screen image
   * @throws IllegalArgumentException if width or height are not positive
   */
  private ScreenImage createEmptyImage(int width, int height) {
    assertCorrectWidthAndHeight(width, height);

    return new BufferedScreenImage(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
  }

  /**
   * Create a buffered image with image type ARGB based on a given image.<br>.
   * It is used to ensure that the internal image has type {@link BufferedImage#TYPE_INT_ARGB}.
   *
   * @param image buffered image of any type
   * @return buffered image with type ARGB
   * @see #BufferedScreenImage(BufferedImage)
   */
  private BufferedImage toARGBImage(BufferedImage image) {
    if (image.getType() == BufferedImage.TYPE_INT_ARGB) {
      return image;
    }

    BufferedImage argbImage = new BufferedImage(image.getWidth(), image.getHeight(),
        BufferedImage.TYPE_INT_ARGB);
    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        argbImage.setRGB(x, y, image.getRGB(x, y));
      }
    }

    return argbImage;
  }

  /**
   * Assert that the given coordinates are in bound of the current image.
   *
   * @param x x coordinate
   * @param y y coordinate
   * @throws IllegalArgumentException if at least one coordinate is out of bounds.
   */
  private void assertCorrectCoordinates(int x, int y) {
    if (x < 0 || x >= image.getWidth() || y < 0 || y >= image.getHeight()) {
      throw new IllegalArgumentException(
          "The provided coordinates x=%d, y=%d are out of bounds".formatted(x, y));
    }
  }

  /**
   * Assert that the given lengths are positive.
   *
   * @param width  image width
   * @param height image height
   * @throws IllegalArgumentException if at least one length is not positive
   */
  private void assertCorrectWidthAndHeight(int width, int height) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and height has to be positive.");
    }
  }
}
