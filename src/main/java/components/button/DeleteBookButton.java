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
 * A custom button for deleting books, implementing {@link Sender} and {@link MouseHandler}.
 * This button provides mouse interaction effects and confirms the action with an alert box.
 */
public class DeleteBookButton extends Button implements Sender, MouseHandler {
    /**
     * The socket client used to send messages to the server.
     */
    private static SocketService client = SocketService.getInstance();

    /**
     * The delete icon image displayed on the button.
     */
    private static Image image = FileHelper.getImage("delete.png");

    /**
     * The ID of the book to be deleted.
     */
    private int bookID;

    /**
     * Default constructor for {@code DeleteBookButton}.
     */
    public DeleteBookButton() {
        // Default constructor
    }

    /**
     * Constructs a {@code DeleteBookButton} with the specified book ID.
     * Sets up the button's style and mouse event handlers.
     *
     * @param bookID the ID of the book associated with this button
     */
    public DeleteBookButton(int bookID) {
        setButtonStyle();
        handleMouseEvent();
        this.bookID = bookID;
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
     * Sends a message to the server to delete the book associated with this button.
     * The message is in JSON format and includes the book ID.
     */
    @Override
    public void sendMessage() {
        JSONObject data = new JSONObject();
        data.put("book_id", bookID);
        client.sendMessage("delete_book", data);
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
