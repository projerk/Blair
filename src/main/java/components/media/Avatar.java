package components.media;

import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.PoolingToolkit;
import javafx.geometry.Insets;

public class Avatar extends VBox {
    private ImageView imageView;
    private Circle clipCircle;
    private VBox container;

    public Avatar(Image image) {
        clipCircle.setStyle("-fx-background-color: black;");
        this.imageView = new ImageView(image);
        initializeAvatar();
    }

    /**
     * 
     * @param avatar url of image
     */
    public Avatar(String avatar) {
        this.imageView = new ImageView(PoolingToolkit.getImage(avatar));
        initializeAvatar();
    }

    public Avatar(String avatar, double width, double height) {
        this.imageView = new ImageView(PoolingToolkit.getImage(avatar));
        initializeAvatar(width, height);
    }

    private void initializeAvatar(double width, double height) {
        container = new VBox();
        Image image = imageView.getImage();
        /**
        * Nếu chiều rộng của ảnh lớn hơn hoặc bằng chiều cao:
        * - Điều này có nghĩa ảnh có dạng ngang hoặc vuông.
        * - Đặt chiều rộng của `imageView` bằng giá trị `width` đã chỉ định.
        * - Chiều cao của ảnh sẽ được tự động điều chỉnh theo tỷ lệ để không bị méo
        *   vì thuộc tính `imageView.setPreserveRatio(true)` đã được bật.
        */
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

        // Ràng buộc bán kính hình tròn với kích thước container
        clipCircle.radiusProperty().bind(container.widthProperty().divide(2).subtract(10));

        clipCircle.centerXProperty().bind(container.widthProperty().divide(2));

        clipCircle.centerYProperty().bind(container.heightProperty().divide(2));
    }

    /**
     * cũng như trên những w,h mặc định là 75
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
}
