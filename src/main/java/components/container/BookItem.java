package components.container;

import components.interfaces.IContainer;
import components.media.WrappedImageView;
import components.text.BookTitle;
import controller.ExploreController;
import components.interfaces.Listener;
import animation.FadeEffect;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Book;
import utils.PoolingToolkit;
import animation.ScaleEffect;
import app.AppState;

/**
 * A visual container representing a single book item in a library or book collection.
 *
 * Displays a book's cover image and title with interactive mouse events,
 * allowing users to view book details or open a book's canvas when clicked.
 */
public class BookItem extends VBox {
    /** Image view component for displaying the book's cover. */
    private WrappedImageView bookCover;

    /** The Book model representing the book's data. */
    private Book book;

    /** Listener for handling book-related interactions. */
    private Listener listener;

    /** Singleton application state instance. */
    private static AppState appState = AppState.getInstance();

    /**
     * Constructs a BookItem with a specific book's information.
     *
     * Creates a vertical layout containing the book's cover image and title.
     * Sets up visual styling and prepares mouse event handling.
     *
     * @param book The Book object containing book details to be displayed
     */
    public BookItem(Book book) {
        listener = appState.getListener();
        this.book = book;
        setVgrow(this, Priority.NEVER);
        VBox titleContainer = new VBox();
        titleContainer.setAlignment(Pos.CENTER);
        Label title = new Label(book.getTitle());
        title.setStyle("-fx-text-fill: black; -fx-font-family: 'Accent Graphic W00 Medium';");
        titleContainer.getChildren().add(title);
        Image image = PoolingToolkit.getImage(book.getCover());
        bookCover = new WrappedImageView();
        bookCover.setImage(image);
        this.getChildren().add(bookCover);
        this.getChildren().add(titleContainer);
        this.setAlignment(Pos.BOTTOM_CENTER);
        handleMouseEvent();
    }

    /**
     * Retrieves the Book object associated with this BookItem.
     *
     * @return The Book instance representing the book's data
     */
    public Book getBook() {
        return this.book;
    }

    /**
     * Sets the Book object for this BookItem.
     *
     * @param book The Book instance to be associated with this BookItem
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Configures mouse interaction events for the BookItem.
     *
     * Handles mouse enter and exit events with scaling animation.
     * Triggers book canvas opening when clicked, if a listener is available.
     */
    public void handleMouseEvent() {
        this.setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(this, 0.2, 1.2, 1.2);
        });

        this.setOnMouseExited(event -> {
            ScaleEffect.scaleTo(this, 0.2, 1.0, 1.0);
        });

        this.setOnMouseClicked(event -> {
            if (listener != null) {
                listener.openCanvas(book.getId());
            }
            else {
                System.out.println("Listener is null");
            }
        });
    }

    /**
     * Sets the listener for book-related interactions.
     *
     * @param listener The Listener to handle book interaction events
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }
}