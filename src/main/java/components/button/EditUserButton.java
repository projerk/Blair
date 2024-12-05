package components.button;

import java.util.Optional;

import org.json.JSONObject;

import animation.ScaleEffect;
import app.AppState;
import components.interfaces.MouseHandler;
import components.interfaces.Receiver;
import components.interfaces.Sender;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Book;
import model.User;
import socket.SocketService;
import utils.FileHelper;
import utils.ImageUtils;
import utils.QRCodeGenerator;
import utils.StringUtils;

public class EditUserButton extends Button implements MouseHandler, Sender, Receiver {

    private User user;
    private static Image image = FileHelper.getImage("editing.png");
    private Label editStatus = new Label();

    /**
     * Constructs an EditUserButton for a specific user.
     *
     * @param user The user to be edited
     */
    public EditUserButton(User user) {
        getMessage();
        setButtonStyle();
        handleMouseEvent();
        this.user = user;
    }

    /**
     * Handles mouse events for the button, including click, mouse enter, and mouse exit.
     * Applies scale effect on hover and opens edit dialog on click.
     */
    public void handleMouseEvent() {
        setOnMouseClicked(event -> {
            openFieldBox(user);
        });

        setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(this, 0.1, 1.1, 1.1);
        });

        setOnMouseExited(event -> {
            ScaleEffect.scaleTo(this, 0.1, 1.0, 1.0);
        });
    }

    /**
     * Sets the button's visual style and graphic.
     * Calls handleMouseEvent to set up mouse interactions.
     */
    private void setButtonStyle() {
        handleMouseEvent();
        setStyle("-fx-background-color: transparent;");

        ImageView imageView = ImageUtils.getImageView(image);
        imageView.setFitWidth(30);
        setGraphic(imageView);
    }

    /**
     * Opens a dialog box for editing user details.
     * Provides input fields for all user properties and a button for editing.
     *
     * @param user The user whose details are to be edited
     */
    private void openFieldBox(User user) {
        // ... (existing implementation remains the same)
    }

    /**
     * Creates a styled text field with a prompt text and custom styling.
     *
     * @param promptText The text to display as a prompt in the text field
     * @return A styled TextField
     */
    private TextField createStyledTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setStyle("-fx-border-color: #d6d6d6; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 5;");
        return textField;
    }

    /**
     * Creates a styled text label with custom font and styling.
     *
     * @param labelText The text to display in the label
     * @return A styled Text object
     */
    private Text createStyledLabel(String labelText) {
        Text text = new Text(labelText);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        text.setStyle("-fx-fill: #333;");
        return text;
    }

    /**
     * Sends an edit user message via socket with updated user details.
     * Constructs a JSON object with the user's updated properties.
     */
    public void sendMessage() {
        JSONObject newData = new JSONObject();
        newData.put("username", user.getUsername());
        newData.put("avatar", user.getAvatar());
        newData.put("firstname", user.getFirstName());
        newData.put("lastname", user.getLastName());
        newData.put("email", user.getEmail());
        newData.put("role", user.getRole());
        newData.put("verified", true);

        JSONObject data = new JSONObject();
        data.put("user_id", user.getID());
        data.put("new_data", newData);

        SocketService.getInstance().sendMessage("edit_user", data);
    }

    /**
     * Sets up a listener for edit user response messages from the server.
     * Updates the edit status label with success or failure messages.
     */
    public void getMessage() {
        SocketService.getInstance().onMessage("edit_user_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String status = response.getString("status");
            if (status.equals("success")) {
                Platform.runLater(() -> {
                    editStatus.setText("");
                    editStatus.setStyle("-fx-text-fill: green;");
                    editStatus.setText(response.getString("message"));
                });
            }
            else {
                Platform.runLater(() -> {
                    editStatus.setText("");
                    editStatus.setStyle("-fx-text-fill: red;");
                    editStatus.setText(response.getString("message"));
                });
            }
        });
    }
}