package components.button;

import org.json.JSONObject;

import animation.ScaleEffect;
import app.AppState;
import components.interfaces.Sender;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import socket.SocketService;
import utils.FileHelper;

/**
 * A custom JavaFX Button representing a star rating mechanism.
 *
 * This button allows users to set and visualize ratings for books by
 * toggling between an inactive (black) and active (colored) star image.
 */
public class StarButton extends Button implements Sender {

    /** Image representing an inactive (black) star. */
    private static Image image1 = FileHelper.getImage("blackstar.png");

    /** Image representing an active (colored) star. */
    private static Image image2 = FileHelper.getImage("colorstar.png");

    /** ImageView for the inactive star state. */
    private ImageView imageView1;

    /** ImageView for the active star state. */
    private ImageView imageView2;

    /** The numeric value of the star rating. */
    private int number;

    /** Flag indicating whether the star is in an active (selected) state. */
    private boolean active;

    /**
     * Retrieves the current numeric rating of the star button.
     *
     * @return The numeric rating value
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * Sets the numeric rating for the star button.
     *
     * @param number The rating value to be set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Checks if the star button is currently in an active state.
     *
     * @return {@code true} if the star is active, {@code false} otherwise
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Sets the active state of the star button and updates its visual representation.
     *
     * Changes the button's graphic between black and colored star images based
     * on the active state. Sets a transparent background for the button.
     *
     * @param active {@code true} to activate the star, {@code false} to deactivate
     */
    public void setActive(boolean active) {
        this.setStyle("-fx-background-color: transparent;");
        this.active = active;
        if (active) {
            setGraphic(imageView2);
        }
        else {
            setGraphic(imageView1);
        }
    }

    /**
     * Constructs a new StarButton with specified initial active state.
     *
     * Initializes star images, sets up mouse interaction effects, and
     * configures the initial visual state of the button.
     *
     * @param active Initial active state of the star button
     */
    public StarButton(boolean active) {
        imageView1 = new ImageView(image1);
        imageView2 = new ImageView(image2);

        imageView1.setPreserveRatio(true);
        imageView2.setPreserveRatio(true);

        imageView1.setFitWidth(27);
        imageView2.setFitWidth(30);

        setActive(active);

        setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(this, 0.1, 1.1, 1.1);
        });

        setOnMouseExited(event -> {
            ScaleEffect.scaleTo(this, 0.1, 1.0, 1.0);
        });

        setOnMouseClicked(event -> {
            Platform.runLater(() -> {
                sendMessage();
            });
        });
    }

    /**
     * Sends a rating message via socket service.
     *
     * Constructs a JSON object with the current book ID, user ID,
     * and rating, then sends it through the socket service.
     * Runs on the JavaFX Application Thread to ensure thread safety.
     */
    public void sendMessage() {
        JSONObject data = new JSONObject();

        data.put("book_id", AppState.getInstance().getCurrentViewBookID());
        data.put("user_id", AppState.getInstance().getUser().getID());
        data.put("rating", number);
        SocketService.getInstance().sendMessage("set_rating", data);
    }
}