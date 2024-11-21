package utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WrappedImageView extends ImageView {

    public WrappedImageView() {
        setPreserveRatio(true);
    }

    @Override
    public double minWidth(double height) {
        return 1;
    }

    @Override
    public double prefWidth(double height) {
        Image image = getImage();
        if (image == null) {
            return minWidth(height);
        }
        return image.getWidth();
    }

    @Override
    public double maxWidth(double height) {
        return 16384;
    }

    @Override
    public double minHeight(double width) {
        return 1;
    }

    @Override
    public double prefHeight(double width) {
        Image image = getImage();
        if (image == null) {
            return minHeight(width);
        }
        return image.getHeight();
    }

    @Override
    public double maxHeight(double width) {
        return 16384;
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public void resize(double width, double height) {
        setFitWidth(width);
        setFitHeight(height);
    }
}

