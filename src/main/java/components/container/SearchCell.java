package components.container;


import app.AppState;
import components.media.WrappedImageView;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Book;
import utils.PoolingToolkit;

public class SearchCell extends HBox {
    public SearchCell(Book book) {
        ImageView imageView = new ImageView();
        imageView.setImage(PoolingToolkit.getImage(book.getCover()));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(80);
        Divide space = new Divide();
        HBox.setHgrow(space, Priority.ALWAYS);
        space.setColumnPercentages(13, 87);

        VBox information = new VBox();
        // information.setStyle("-fx-background-color: red;");
        information.setSpacing(5);

        Label title = new Label(book.getTitle().trim());
        Label author = new Label(book.getAuthor().getName());

        title.setWrapText(true);
        author.setOpacity(0.8);
        author.setStyle("-fx-font-size: 12;");


        information.getChildren().add(title);
        information.getChildren().add(author);
        space.addNode(imageView, 0, 0);
        space.addNode(information, 1, 0);
        this.getChildren().add(space);

        this.setOnMouseEntered(event -> {
            this.setStyle("-fx-background-color: #d9d9d9;");
        });

        this.setOnMouseExited(event -> {
            this.setStyle("-fx-background-color: transparent;");
        });

        this.setOnMouseClicked(event -> {
            AppState.getInstance().getListener().openCanvas(book.getId());
        });
    }
}
