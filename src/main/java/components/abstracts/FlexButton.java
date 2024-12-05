package components.abstracts;

import javafx.scene.control.Button;
import socket.SocketService;

/**
 * An abstract class that extends the JavaFX {@link Button} to provide a customizable button
 * with a predefined style and a reference to a {@link SocketService} client.
 */
public abstract class FlexButton extends Button {

    /**
     * A singleton instance of {@link SocketService} for handling socket communication.
     */
    protected SocketService client = SocketService.getInstance();

    /**
     * Constructs a new {@code FlexButton} with a predefined style.
     * The button's activation state can be set via the {@code active} parameter.
     *
     * @param active whether the button should be active (enabled) or not (disabled)
     */
    public FlexButton(boolean active) {
        this.setStyle("-fx-font-size: 18; -fx-text-fill: white; -fx-font-family: 'Accent Graphic W00 Medium'; " +
                "-fx-background-color: black; -fx-background-radius: 20px;");
        // this.setDisable(!active);
    }

    // Uncomment and implement these methods as needed in subclasses:

    /**
     * Defines the behavior for receiving a message. Subclasses must implement this method
     * to handle the logic for receiving messages through the button.
     */
    // public abstract void getMessage();

    /**
     * Defines the behavior for sending a message. Subclasses must implement this method
     * to handle the logic for sending messages through the button.
     */
    // public abstract void sendMessage();
}
