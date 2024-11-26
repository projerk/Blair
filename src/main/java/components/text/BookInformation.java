package components.text;

import components.button.BorrowButton;
import components.button.PreviewButton;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Book;

public class BookInformation extends VBox {
    private Book book;

    public BookInformation(Book book) {
        this.book = book;
        this.setSpacing(3);
        createText();
        createButton();
    }

    public void createText() {
        Label title = new Label(book.getTitle());
        title.setWrapText(true);
        title.setStyle("-fx-font-size: 40; -fx-text-fill: black; -fx-font-family: 'Accent Graphic W00 Medium';");
        Label author = new Label("Author: " + book.getAuthor().getName());
        author.setStyle("-fx-font-size: 20; -fx-text-fill: black; -fx-font-family: 'Accent Graphic W00 Medium';");
        Label genre = new Label("Genre: " + book.getGenre());
        genre.setStyle("-fx-font-size: 20; -fx-text-fill: black; -fx-font-family: 'Accent Graphic W00 Medium';");
        Label publisher = new Label("Publisher: " + book.getPublisher());
        publisher.setStyle("-fx-font-size: 20; -fx-text-fill: black; -fx-font-family: 'Accent Graphic W00 Medium';");
        Label year = new Label("Release: " + book.getPublishingYear());
        year.setStyle("-fx-font-size: 20; -fx-text-fill: black; -fx-font-family: 'Accent Graphic W00 Medium';");
        Label available = new Label("Available: " + book.getAvailable());
        available.setStyle("-fx-font-size: 20; -fx-text-fill: black; -fx-font-family: 'Accent Graphic W00 Medium';");

        // HBox test = new HBox();
        // Label text1 = new Label("The");
        // Label text2 = new Label("bes");
        // test.getChildren().add(text1);
        // test.getChildren().add(text2);
        // text2.setStyle("-fx-text-fill: green;");


        this.getChildren().add(title);
        this.getChildren().add(author);
        this.getChildren().add(genre);
        this.getChildren().add(publisher);
        this.getChildren().add(year);
        this.getChildren().add(available);
        // this.getChildren().add(test);
    }

    public void createButton() {
        HBox container = new HBox();

        container.setSpacing(10);

        boolean active;
        if (book.getAvailable() > 0) {
            active = true;
        }
        else {
            active = false;
        }

        PreviewButton previewButton = new PreviewButton(true, book.getPdf(), book.getTitle());
        BorrowButton borrowButton = new BorrowButton(active, book.isBorrow());

        container.getChildren().add(borrowButton);
        container.getChildren().add(previewButton);
        this.getChildren().add(container);
    }
}
