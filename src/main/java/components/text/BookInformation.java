package components.text;

import org.json.JSONObject;
import com.google.zxing.qrcode.encoder.QRCode;
import app.AppState;
import components.button.BookmarkButton;
import components.button.BorrowButton;
import components.button.PreviewButton;
import components.button.QRCodeButton;
import components.container.RatingField;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Book;
import socket.SocketService;

/**
 * This class represents a detailed view of a book, including its information, user rating, and control buttons for borrowing, previewing, bookmarking, and viewing the QR code.
 */
public class BookInformation extends VBox {
    private Book book;
    private RatingField ratingField;

    /**
     * Constructs a BookInformation object with the provided book details.
     *
     * @param book the book to display information for.
     */
    public BookInformation(Book book) {
        this.book = book;
        this.setSpacing(3);
        createText();
        createButton();
    }

    /**
     * Creates and adds labels to the UI displaying the book's information such as title, author, genre, publisher, release year, rating, and availability.
     */
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
        Label rating = new Label("Rating: " + String.format("%.3f", book.getRating()));
        rating.setStyle("-fx-font-size: 20; -fx-text-fill: black; -fx-font-family: 'Accent Graphic W00 Medium';");
        Label available = new Label("Available: " + book.getAvailable());
        available.setStyle("-fx-font-size: 20; -fx-text-fill: black; -fx-font-family: 'Accent Graphic W00 Medium';");

        this.getChildren().add(title);
        this.getChildren().add(author);
        this.getChildren().add(genre);
        this.getChildren().add(rating);
        this.getChildren().add(publisher);
        this.getChildren().add(year);
        this.getChildren().add(available);
    }

    /**
     * Creates and adds buttons to the UI, including buttons for rating, borrowing, previewing, bookmarking, and generating a QR code.
     */
    public void createButton() {

        ratingField = new RatingField(0);
        getUserRating();

        VBox container = new VBox();
        container.setSpacing(5);
        HBox functionButton = new HBox();

        functionButton.setSpacing(10);

        boolean active;
        if (book.getAvailable() > 0) {
            active = true;
        }
        else {
            active = false;
        }

        PreviewButton previewButton = new PreviewButton(true, book.getPdf(), book.getTitle());
        BorrowButton borrowButton = new BorrowButton(active, book.isBorrow());
        BookmarkButton bookmarkButton = new BookmarkButton(book.isBookmark());
        QRCodeButton qr = new QRCodeButton();

        functionButton.getChildren().add(borrowButton);
        functionButton.getChildren().add(previewButton);
        functionButton.getChildren().add(bookmarkButton);
        functionButton.getChildren().add(qr);

        container.getChildren().add(ratingField);
        container.getChildren().add(functionButton);

        this.getChildren().add(container);
    }

    /**
     * Fetches the current rating of the book from the server and updates the rating field in the UI.
     */
    public void getUserRating() {
        SocketService.getInstance().onMessage("user_rating_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String status = response.getString("status");
            if (status.equals("success")) {
                Platform.runLater(() -> {
                    ratingField.setRating(response.getInt("rating"));
                });
            }
        });

        JSONObject object = new JSONObject();
        object.put("user_id", AppState.getInstance().getUser().getID());
        object.put("book_id", AppState.getInstance().getCurrentViewBookID());
        SocketService.getInstance().sendMessage("get_user_rating", object);
    }
}
