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
 * A custom button for denying book borrow requests, implementing {@link Sender} and {@link MouseHandler}.
 * This button provides mouse interaction effects and sends a message when clicked.
 */
public class DenyBorrowButton extends Button implements Sender, MouseHandler {

    /**
     * The socket client used to send messages to the server.
     */
    private static SocketService client = SocketService.getInstance();

    /**
     * The deny icon image displayed on the button.
     */
    private static Image image = FileHelper.getImage("close.png");

    /**
     * The ID of the book involved in the borrow request.
     */
    private int bookID;

    /**
     * The ID of the user who made the borrow request.
     */
    private int userID;

    /**
     * Default constructor for {@code DenyBorrowButton}.
     */
    public DenyBorrowButton() {
        // Default constructor
    }

    /**
     * Constructs a {@code DenyBorrowButton} with the specified book ID and user ID.
     * Sets up the button's style and mouse event handlers.
     *
     * @param bookID the ID of the book associated with this button
     * @param userID the ID of the user associated with this button
     */
    public DenyBorrowButton(int bookID, int userID) {
        setButtonStyle();
        handleMouseEvent();
        this.bookID = bookID;
        this.userID = userID;
    }

    /**
     * Sets the style of the button and initializes its graphic with the deny icon.
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
     * - On click: Sends a message and opens an informational alert box.
     * - On mouse enter: Scales the button up.
     * - On mouse exit: Scales the button back to its original size.
     */
    public void handleMouseEvent() {
        setOnMouseClicked(event -> {
            sendMessage();
            openAlertBox();
        });

        setOnMouseEntered(event -> ScaleEffect.scaleTo(this, 0.1, 1.1, 1.1));
        setOnMouseExited(event -> ScaleEffect.scaleTo(this, 0.1, 1.0, 1.0));
    }

    /**
     * Sends a message to the server to deny the borrow request for the specified book and user.
     * The message is in JSON format and includes the book ID and user ID.
     */
    @Override
    public void sendMessage() {
        JSONObject data = new JSONObject();
        data.put("book_id", bookID);
        data.put("user_id", userID);
        client.sendMessage("deny_borrow_book", data);
    }

    /**
     * Opens an informational alert box to confirm the denial action.
     */
    private void openAlertBox() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Success");

        ButtonType confirmButton = new ButtonType("OK");
        ButtonType cancelButton = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == confirmButton) {
            // Action confirmed
        } else {
            // Action canceled
        }
    }
}
