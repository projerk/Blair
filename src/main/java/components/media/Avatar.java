package components.media;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import utils.PoolingToolkit;

/**
 * A custom VBox implementation for creating circular avatar images.
 *
 * Provides multiple constructors to create avatars from different image sources
 * with flexible sizing and clipping options.
 */
public class Avatar extends VBox {
    private ImageView imageView;
    private Circle clipCircle;
    private VBox container;

    /**
     * Constructs an Avatar with a given JavaFX Image.
     *
     * @param image The Image to be used for the avatar
     */
    public Avatar(Image image) {
        clipCircle.setStyle("-fx-background-color: black;");
        this.imageView = new ImageView(image);
        initializeAvatar();
    }

    /**
     * Constructs an Avatar using an image path from PoolingToolkit.
     *
     * @param avatar The path to the avatar image
     */
    public Avatar(String avatar) {
        this.imageView = new ImageView(PoolingToolkit.getImage(avatar));
        initializeAvatar();
    }

    /**
     * Constructs an Avatar with a specified image path and custom dimensions.
     *
     * @param avatar The path to the avatar image
     * @param width The desired width of the avatar
     * @param height The desired height of the avatar
     */
    public Avatar(String avatar, double width, double height) {
        this.imageView = new ImageView(PoolingToolkit.getImage(avatar));
        initializeAvatar(width, height);
    }

    /**
     * Initializes the avatar with custom width and height.
     *
     * Configures the image view, creates a circular clip, and sets up
     * layout properties based on the provided dimensions.
     *
     * @param width The desired width of the avatar
     * @param height The desired height of the avatar
     */
    private void initializeAvatar(double width, double height) {
        container = new VBox();
        Image image = imageView.getImage();
        if (image.widthProperty().doubleValue() >= image.heightProperty().doubleValue()) {
            imageView.setFitWidth(width);
        }
        else {
            imageView.setFitHeight(height);
        }

        imageView.setPreserveRatio(true);
        clipCircle = new Circle();
        clipCircle.setCenterX(width / 2);
        clipCircle.setCenterY(height / 2);

        imageView.setClip(clipCircle);

        setPadding(new Insets(5,1, 5, 1));
        container.getChildren().add(imageView);

        getChildren().add(container);

        clipCircle.radiusProperty().bind(container.widthProperty().divide(2).subtract(10));

        clipCircle.centerXProperty().bind(container.widthProperty().divide(2));

        clipCircle.centerYProperty().bind(container.heightProperty().divide(2));
    }

    /**
     * Initializes the avatar with default dimensions.
     *
     * Configures the image view, creates a circular clip, and sets up
     * layout properties with standard sizing of 75 pixels.
     */
    private void initializeAvatar() {
        container = new VBox();
        Image image = imageView.getImage();
        if (image.widthProperty().doubleValue() >= image.heightProperty().doubleValue()) {
            double ratio = image.widthProperty().doubleValue() / image.heightProperty().doubleValue();
            System.out.println(ratio);
            imageView.setFitWidth(75);
        }
        else {
            imageView.setFitHeight(75);
        }

        imageView.setPreserveRatio(true);
        clipCircle = new Circle();
        clipCircle.setCenterX(75 / 2);
        clipCircle.setCenterY(75 / 2);

        imageView.setClip(clipCircle);

        setPadding(new Insets(5,1, 5, 1));
        container.getChildren().add(imageView);

        getChildren().add(container);

        clipCircle.radiusProperty().bind(container.widthProperty().divide(2).subtract(10));

        clipCircle.centerXProperty().bind(container.widthProperty().divide(2));

        clipCircle.centerYProperty().bind(container.heightProperty().divide(2));
    }

    /**
     * Updates the clip circle's width based on the container's width.
     *
     * Adjusts the radius and center position of the circular clip.
     *
     * @param width The new width of the container
     */
    private void updateClipCircleWidth(double width) {
        double radius = Math.min(width / 2, clipCircle.getRadius());
        clipCircle.setRadius(radius);
        clipCircle.setCenterX(width / 2);
    }

    /**
     * Updates the clip circle's height based on the container's height.
     *
     * Adjusts the radius and center position of the circular clip.
     *
     * @param height The new height of the container
     */
    private void updateClipCircleHeight(double height) {
        double radius = Math.min(height / 2, clipCircle.getRadius());
        clipCircle.setRadius(radius);
        clipCircle.setCenterY(height / 2);
    }
}