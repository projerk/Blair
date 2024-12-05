package components.button;

import java.io.File;
import java.net.Socket;

import org.json.JSONObject;

import animation.ScaleEffect;
import app.AppState;
import components.abstracts.FlexButton;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import socket.SocketService;
import utils.FileHelper;

/**
 * Represents a button that allows the user to toggle a bookmark for a book.
 * When clicked, it sends a request to the server to bookmark or unbookmark the current book.
 * The button's graphic changes based on the current bookmark state.
 */
public class BookmarkButton extends Button {
    private ImageView imageView1;
    private ImageView imageView2;
    private static Image bookmarkImage = FileHelper.getImage("c.png");
    private static Image unbookmarkImage = FileHelper.getImage("b.png");

    private boolean bookmark;

    private SocketService client = SocketService.getInstance();

    /**
     * Gets the current bookmark status.
     *
     * @return true if the book is bookmarked, false otherwise.
     */
    public boolean isBookmark() {
        return this.bookmark;
    }

    /**
     * Sets the bookmark status.
     *
     * @param bookmark The bookmark status to set.
     */
    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }

    /**
     * Constructs a BookmarkButton with the specified initial bookmark state.
     * The button's graphic changes based on whether the book is bookmarked or not.
     *
     * @param bookmark The initial bookmark state.
     */
    public BookmarkButton(boolean bookmark) {
        this.setStyle("-fx-background-color: transparent;");

        getMessage();

        imageView1 = new ImageView(unbookmarkImage);
        imageView1.setPreserveRatio(true);
        imageView1.setFitWidth(30);

        imageView2 = new ImageView(bookmarkImage);
        imageView2.setPreserveRatio(true);
        imageView2.setFitWidth(30);

        // Set the graphic based on the bookmark state
        if (!bookmark) {
            setGraphic(imageView2);
        } else {
            setGraphic(imageView1);
        }

        // Handle mouse events for scaling effect
        setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(this, 0.2, 1.2, 1.2);
        });

        setOnMouseExited(event -> {
            ScaleEffect.scaleTo(this, 0.2, 1.0, 1.0);
        });

        // Handle button click event to send bookmark/unbookmark request
        setOnMouseClicked(event -> {
            Platform.runLater(this::sendMessage);
        });
    }

    /**
     * Sends a message to the server to toggle the bookmark status for the current book.
     * The book ID and user ID are sent as part of the request.
     */
    public void sendMessage() {
        JSONObject data = new JSONObject();

        data.put("book_id", AppState.getInstance().getCurrentViewBookID());
        data.put("user_id", AppState.getInstance().getUser().getID());

        client.sendMessage("set_bookmark", data);
    }

    /**
     * Listens for the response from the server regarding the bookmark action.
     * Updates the button graphic based on whether the bookmark operation was successful.
     */
    public void getMessage() {
        client.onMessage("bookmark_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String status = response.getString("status");

            // Update the button graphic based on the server response
            if (status.equals("bookmark_success")) {
                Platform.runLater(() -> {
                    setGraphic(imageView1);
                });
            } else if (status.equals("unbookmark_success")) {
                Platform.runLater(() -> {
                    setGraphic(imageView2);
                });
            }
        });
    }
}
