package components.button;

import org.json.JSONObject;

import animation.ScaleEffect;
import app.AppState;
import components.abstracts.FlexButton;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.scene.control.Button;
import socket.SocketService;

/**
 * Represents a button that allows the user to borrow or return a book.
 * The button's text and state change based on the current borrow status.
 * When clicked, it sends a request to the server to borrow or return the book.
 */
public class BorrowButton extends FlexButton {

    private boolean borrow;
    private boolean active;

    /**
     * Gets the current borrow status.
     *
     * @return true if the book is currently borrowed, false otherwise.
     */
    public boolean isBorrow() {
        return this.borrow;
    }

    /**
     * Sets the borrow status.
     *
     * @param borrow The borrow status to set.
     */
    public void setBorrow(boolean borrow) {
        this.borrow = borrow;
    }

    /**
     * Gets the current active status.
     *
     * @return true if the button is active (enabled), false otherwise.
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Sets the active status.
     *
     * @param active The active status to set.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Constructs a BorrowButton with the specified initial states for active and borrow status.
     * The button's text and disabled state are set based on the borrow and active status.
     *
     * @param active The initial active state of the button.
     * @param borrow The initial borrow state of the book.
     */
    public BorrowButton(boolean active, boolean borrow) {
        super(active);
        getMessage();
        setActive(active);
        setBorrow(borrow);
        this.setText("Borrow");

        // Disable button and set text to "Unavailable" if neither borrow nor active is true
        if (!borrow && !active) {
            setDisable(true);
            this.setText("Unavailable");
        }
        // Set text to "Return" if the book is borrowed, regardless of the active state
        else if ((borrow && active) || (borrow && !active)) {
            setText("Return");
        }

        // Handle mouse events for scaling effect
        setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(this, 0.2, 1.2, 1.2);
        });

        setOnMouseExited(event -> {
            ScaleEffect.scaleTo(this, 0.2, 1.0, 1.0);
        });

        // Handle button click event to send borrow/return request
        setOnMouseClicked(event -> {
            Platform.runLater(() -> {
                setText("Requesting");
                setDisable(true);
                sendMessage();
            });
        });
    }

    /**
     * Sends a message to the server to request to borrow or return the book.
     * The status is determined based on the current borrow state.
     */
    public void sendMessage() {
        JSONObject data = new JSONObject();
        String status;
        if (borrow) {
            status = "return";
        } else {
            status = "borrow";
        }
        data.put("status", status);
        data.put("book_id", AppState.getInstance().getCurrentViewBookID());
        data.put("user_id", AppState.getInstance().getUser().getID());

        client.sendMessage("borrow_book", data);
        borrow = !borrow; // Toggle the borrow state
    }

    /**
     * Listens for the server's response regarding the borrow/return action.
     * Updates the button text and state based on whether the action was successful.
     */
    public void getMessage() {
        client.onMessage("borrow_book_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String status = response.getString("status");
            if (status.equals("borrow_success")) {
                Platform.runLater(() -> {
                    setText("Return");
                    setDisable(false);
                });
            } else if (status.equals("return_success")) {
                Platform.runLater(() -> {
                    setText("Borrow");
                    setDisable(false);
                });
            } else {
                Platform.runLater(() -> {
                    setText("Requested");
                });
            }
        });
    }
}
