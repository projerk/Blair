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
import utils.StringUtils;

/**
 * A custom HBox component representing a search result cell for a book.
 *
 * This cell displays book information including cover image, title, and author,
 * and provides interactive features like hover effects and click events.
 */
public class SearchCell extends HBox {

    /**
     * Constructs a new SearchCell for a given book.
     *
     * Creates a cell with the book's cover image, title, and author.
     * Adds mouse event handlers for hover and click interactions.
     *
     * @param book The Book object containing information to be displayed
     */
    public SearchCell(Book book) {
        // Create and configure book cover image
        ImageView imageView = new ImageView();
        imageView.setImage(PoolingToolkit.getImage(book.getCover()));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(80);

        // Create a Divide space for layout
        Divide space = new Divide();
        HBox.setHgrow(space, Priority.ALWAYS);
        space.setColumnPercentages(13, 87);

        // Create a VBox to hold book information
        VBox information = new VBox();
        information.setSpacing(5);

        // Create labels for book title and author
        Label title = new Label(book.getTitle().trim());
        Label author = new Label(book.getAuthor().getName());

        // Configure label styles
        title.setWrapText(true);
        author.setOpacity(0.8);
        author.setStyle("-fx-font-size: 12;");

        // Add labels to information VBox
        information.getChildren().add(title);
        information.getChildren().add(author);

        // Add components to the Divide space
        space.addNode(imageView, 0, 0);
        space.addNode(information, 1, 0);
        this.getChildren().add(space);

        // Add hover effect for visual feedback
        this.setOnMouseEntered(event -> {
            this.setStyle("-fx-background-color: #d9d9d9;");
        });

        // Remove hover effect when mouse exits
        this.setOnMouseExited(event -> {
            this.setStyle("-fx-background-color: transparent;");
        });

        // Handle click event to open book canvas
        this.setOnMouseClicked(event -> {
            AppState.getInstance().getListener().openCanvas(book.getId());
        });
    }
}