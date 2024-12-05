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
 * Represents a button that handles the acceptance of a book return request.
 * When clicked, it sends a message to the server and shows a success alert.
 * Implements the MouseHandler and Sender interfaces.
 */
public class AcceptReturnButton extends Button implements Sender, MouseHandler {

    private static SocketService client = SocketService.getInstance();
    private static Image image = FileHelper.getImage("check.png");
    private int bookID;
    private int userID;

    /**
     * Default constructor for the AcceptReturnButton.
     */
    public AcceptReturnButton() {
    }

    /**
     * Constructs an AcceptReturnButton with the given book ID and user ID.
     * Sets the button style and handles mouse events.
     *
     * @param bookID The ID of the book being returned.
     * @param userID The ID of the user requesting the return.
     */
    public AcceptReturnButton(int bookID, int userID) {   
        setButtonStyle();
        handleMouseEvent();
        this.bookID = bookID;
        this.userID = userID;
    }

    /**
     * Sets the style for the button, including the button graphic (image) and background color.
     */
    private void setButtonStyle() {
        handleMouseEvent();
        setStyle("-fx-background-color: transparent;");
        
        ImageView imageView = ImageUtils.getImageView(image);
        imageView.setFitWidth(30);
        setGraphic(imageView);
    }

    /**
     * Handles mouse events for the button, including click, mouse enter, and mouse exit.
     * - On mouse click: Sends a message to the server and opens an alert box.
     * - On mouse enter: Scales the button up with an animation.
     * - On mouse exit: Scales the button back to normal size.
     */
    public void handleMouseEvent() {
        setOnMouseClicked(event -> {
            sendMessage();
            openAlertBox();
        });

        setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(this, 0.1, 1.1, 1.1);
        });

        setOnMouseExited(event -> {
            ScaleEffect.scaleTo(this, 0.1, 1.0, 1.0);
        });
    }

    /**
     * Sends a message to the server with the book ID and user ID.
     * The message type is "accept_return_book".
     */
    public void sendMessage() {
        JSONObject data = new JSONObject();
        data.put("book_id", bookID);
        data.put("user_id", userID);
        client.sendMessage("accept_return_book", data);
    }

    /**
     * Opens an alert box informing the user of the success of their action.
     * Offers the user the option to confirm or cancel the action.
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
            // sendMessage();
        } else {
            // No action on cancel
        }
    }
}
