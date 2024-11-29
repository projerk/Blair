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
import animation.ScaleEffect;
import app.AppState;

public class BookItem extends VBox {
    private WrappedImageView bookCover;
    private Book book;
    private Listener listener;
    private static AppState appState = AppState.getInstance();

    public BookItem(Book book) {
        listener = appState.getListener();
        this.book = book;
        setVgrow(this, Priority.NEVER);
        VBox titleContainer = new VBox();
        titleContainer.setAlignment(Pos.CENTER);
        Label title = new Label(book.getTitle());
        title.setStyle("-fx-text-fill: black; -fx-font-family: 'Accent Graphic W00 Medium';");
        titleContainer.getChildren().add(title);
        Image image = new Image(book.getCover(), true);
        bookCover = new WrappedImageView();
        bookCover.setImage(image);
        this.getChildren().add(bookCover);
        this.getChildren().add(titleContainer);
        this.setAlignment(Pos.BOTTOM_CENTER);
        handleMouseEvent();
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

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

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
