package utils;

import components.media.WrappedImageView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageUtils {
    public static ImageView getImageView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        return imageView;
    }
    
    public static WrappedImageView getFlexImageView(Image image) {
        WrappedImageView imageView = new WrappedImageView();
        imageView.setImage(image);
        return imageView;
    }


}