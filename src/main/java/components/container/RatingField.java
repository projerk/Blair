package components.container;

import org.json.JSONObject;

import components.button.StarButton;
import components.interfaces.Receiver;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import socket.SocketService;

/**
 * A custom JavaFX HBox component that represents a rating field with star buttons.
 * Implements the Receiver interface for handling rating-related socket messages.
 */
/**
 * A custom JavaFX HBox component that represents a rating field with star buttons.
 * Implements the Receiver interface for handling rating-related socket messages.
 */
public class RatingField extends HBox implements Receiver {

    /**
     * Constructs a RatingField with a specified initial rating.
     *
     * Creates an HBox with centered-left alignment and populates it with star buttons.
     * Active (filled) and inactive (empty) star buttons are created based on the rating.
     *
     * @param rating The initial rating value (between 1 and 5)
     */
    /**
     * Constructs a RatingField with a specified initial rating.
     *
     * Creates an HBox with centered-left alignment and populates it with star buttons.
     * Active (filled) and inactive (empty) star buttons are created based on the rating.
     *
     * @param rating The initial rating value (between 1 and 5)
     */
    public RatingField(int rating) {
        this.setAlignment(Pos.CENTER_LEFT);
        getMessage();
        setSpacing(3);
        for (int i = 1; i <= rating; i++) {
            StarButton starButton = new StarButton(true);
            starButton.setNumber(i);
            getChildren().add(starButton);
        }

        for (int i = rating + 1; i <= 5; i++) {
            StarButton starButton = new StarButton(false);
            starButton.setNumber(i);
            getChildren().add(starButton);
        }
    }

    /**
     * Sets the rating by updating the active state of star buttons.
     *
     * Activates (fills) star buttons up to the specified rating
     * and deactivates (empties) star buttons beyond the rating.
     *
     * @param rating The new rating value (between 1 and 5)
     */
    /**
     * Sets the rating by updating the active state of star buttons.
     *
     * Activates (fills) star buttons up to the specified rating
     * and deactivates (empties) star buttons beyond the rating.
     *
     * @param rating The new rating value (between 1 and 5)
     */
    public void setRating(int rating) {
        for (int i = 1; i <= rating; i++) {
            StarButton starButton = (StarButton)getChildren().get(i - 1);
            starButton.setActive(true);
        }

        for (int i = rating + 1; i <= 5; i++) {
            StarButton starButton = (StarButton)getChildren().get(i - 1);
            starButton.setActive(false);
        }
    }

    /**
     * Sets up a socket message listener for rating responses.
     *
     * Listens for 'rating_response' events and updates the rating
     * on the JavaFX application thread if the response status is 'rating_success'.
     */
    /**
     * Sets up a socket message listener for rating responses.
     *
     * Listens for 'rating_response' events and updates the rating
     * on the JavaFX application thread if the response status is 'rating_success'.
     */
    public void getMessage() {
        SocketService.getInstance().onMessage("rating_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String status = response.getString("status");
            if (status.equals("rating_success")) {
                Platform.runLater(() -> {
                    setRating(response.getInt("rating"));
                });
            }
        });
    }
}