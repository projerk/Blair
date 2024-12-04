package components.container;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import model.Book;

public class SearchResult extends VBox {
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

    public void display(List<Book> books) {
        this.getChildren().clear();

        for (Book book : books) {
            SearchCell cell = new SearchCell(book);
            this.getChildren().add(cell);
        }
    }

    public void discard() {
        this.getChildren().clear();
    }
}
