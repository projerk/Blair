package components.media;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * An extended ImageView that provides more flexible resizing and layout behavior.
 *
 * Overrides default sizing methods to improve image view responsiveness
 * and adaptability in different layout contexts.
 */
public class WrappedImageView extends ImageView {

    /**
     * Constructs an empty WrappedImageView with preserve ratio enabled.
     */
    public WrappedImageView() {
        setPreserveRatio(true);
    }

    /**
     * Constructs a WrappedImageView with a specified image and preserve ratio enabled.
     *
     * @param image The image to be displayed in the view
     */
    public WrappedImageView(Image image) {
        setImage(image);
        setPreserveRatio(true);
    }

    /**
     * Returns the minimum width for the image view.
     *
     * @param height The height of the image view (not used in this implementation)
     * @return The minimum width of 1 pixel
     */
    @Override
    public double minWidth(double height) {
        return 1;
    }

    /**
     * Calculates the preferred width based on the image's actual width.
     *
     * @param height The height of the image view (not used in this implementation)
     * @return The image's width, or minimum width if no image is set
     */
    @Override
    public double prefWidth(double height) {
        Image image = getImage();
        if (image == null) {
            return minWidth(height);
        }
        return image.getWidth();
    }

    /**
     * Returns the maximum width for the image view.
     *
     * @param height The height of the image view (not used in this implementation)
     * @return A very large maximum width of 16384 pixels
     */
    @Override
    public double maxWidth(double height) {
        return 16384;
    }

    /**
     * Returns the minimum height for the image view.
     *
     * @param width The width of the image view (not used in this implementation)
     * @return The minimum height of 1 pixel
     */
    @Override
    public double minHeight(double width) {
        return 1;
    }

    /**
     * Calculates the preferred height based on the image's actual height.
     *
     * @param width The width of the image view (not used in this implementation)
     * @return The image's height, or minimum height if no image is set
     */
    @Override
    public double prefHeight(double width) {
        Image image = getImage();
        if (image == null) {
            return minHeight(width);
        }
        return image.getHeight();
    }

    /**
     * Returns the maximum height for the image view.
     *
     * @param width The width of the image view (not used in this implementation)
     * @return A very large maximum height of 16384 pixels
     */
    @Override
    public double maxHeight(double width) {
        return 16384;
    }

    /**
     * Indicates that this image view is resizable.
     *
     * @return Always returns true
     */
    @Override
    public boolean isResizable() {
        return true;
    }

    /**
     * Resizes the image view to the specified width and height.
     *
     * Sets the fit width and fit height to the provided dimensions.
     *
     * @param width The new width for the image view
     * @param height The new height for the image view
     */
    @Override
    public void resize(double width, double height) {
        setFitWidth(width);
        setFitHeight(height);
    }
}