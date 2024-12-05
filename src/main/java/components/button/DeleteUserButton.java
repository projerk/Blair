package components.button;

import animation.ScaleEffect;
import components.interfaces.MouseHandler;
import components.interfaces.Sender;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONObject;
import socket.SocketService;
import utils.FileHelper;
import utils.ImageUtils;

import java.util.Optional;

/**
 * A custom button for deleting users, implementing {@link Sender} and {@link MouseHandler}.
 * This button provides mouse interaction effects and confirms the action with an alert box.
 */
public class DeleteUserButton extends Button implements Sender, MouseHandler {
    /**
     * The socket client used to send messages to the server.
     */
    private static SocketService client = SocketService.getInstance();

    /**
     * The delete icon image displayed on the button.
     */
    private static Image image = FileHelper.getImage("delete.png");

    /**
     * The ID of the user to be deleted.
     */
    private int userID;

    /**
     * Default constructor for {@code DeleteUserButton}.
     */
    public DeleteUserButton() {
        // Default constructor
    }

    /**
     * Constructs a {@code DeleteUserButton} with the specified user ID.
     * Sets up the button's style and mouse event handlers.
     *
     * @param userID the ID of the user associated with this button
     */
    public DeleteUserButton(int userID) {
        setButtonStyle();
        handleMouseEvent();
        this.userID = userID;
    }

    /**
     * Sets the style of the button and initializes its graphic with the delete icon.
     */
    private void setButtonStyle() {
        handleMouseEvent();
        setStyle("-fx-background-color: transparent;");

        ImageView imageView = ImageUtils.getImageView(image);
        imageView.setFitWidth(30);
        setGraphic(imageView);
    }

    /**
     * Sets up mouse event handlers for the button.
     * - On click: Opens a confirmation alert box.
     * - On mouse enter: Scales the button up.
     * - On mouse exit: Scales the button back to its original size.
     */
    public void handleMouseEvent() {
        setOnMouseClicked(event -> openAlertBox());
        setOnMouseEntered(event -> ScaleEffect.scaleTo(this, 0.1, 1.1, 1.1));
        setOnMouseExited(event -> ScaleEffect.scaleTo(this, 0.1, 1.0, 1.0));
    }

    /**
     * Sends a message to the server to delete the user associated with this button.
     * The message is in JSON format and includes the user ID.
     */
    @Override
    public void sendMessage() {
        JSONObject data = new JSONObject();
        data.put("user_id", userID);
        client.sendMessage("delete_user", data);
    }

    /**
     * Opens a confirmation alert box to confirm the delete action.
     * If the user confirms, sends a delete message to the server.
     */
    private void openAlertBox() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("ALERT");
        alert.setHeaderText("Are you sure to do this?");

        ButtonType confirmButton = new ButtonType("Confirm");
        ButtonType cancelButton = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == confirmButton) {
            sendMessage();
        }
    }
}
