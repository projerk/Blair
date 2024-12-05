package components.container;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import model.Book;

/**
 * A VBox container for displaying search results as a list of book cells.
 *
 * Provides methods to populate and clear the search results display.
 */
public class SearchResult extends VBox {

    /**
     * Constructs a new SearchResult container with styled background and layout.
     *
     * Initializes the container with white background, rounded corners,
     * border, drop shadow, and internal padding.
     */
    public SearchResult() {
        this.setStyle("-fx-background-color: white;\r\n" + //
                "    -fx-background-radius: 20px;\r\n" + //
                "    -fx-border-radius: 20px;\r\n" + //
                "    -fx-border-color: #d9d9d9;\r\n" + //
                "    -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 10, 0, 0, 1);\r\n" + //
                "    -fx-border-width: 1px;");
        this.setPadding(new Insets(4,4,4,10));
        this.setSpacing(3);
    }

    /**
     * Displays a list of books by creating and adding SearchCell instances.
     *
     * Clears any existing children before adding new book cells.
     *
     * @param books The list of books to display in the search results
     */
    public void display(List<Book> books) {
        this.getChildren().clear();

        for (Book book : books) {
            SearchCell cell = new SearchCell(book);
            this.getChildren().add(cell);
        }
    }

    /**
     * Removes all children from the search results container.
     *
     * Clears the display, effectively emptying the search results.
     */
    public void discard() {
        this.getChildren().clear();
    }
}