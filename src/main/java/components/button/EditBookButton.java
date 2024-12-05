package components.button;

import animation.ScaleEffect;
import components.interfaces.MouseHandler;
import components.interfaces.Receiver;
import components.interfaces.Sender;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Book;
import org.json.JSONObject;
import socket.SocketService;
import utils.FileHelper;
import utils.ImageUtils;

import java.util.Optional;

public class EditBookButton extends Button implements MouseHandler, Sender, Receiver {

    private Book book;
    private static Image image = FileHelper.getImage("editing.png");
    private Label editStatus = new Label();

    /**
     * Constructs an EditBookButton for a specific book.
     *
     * @param book The book to be edited
     */
    public EditBookButton(Book book) {
        getMessage();
        setButtonStyle();
        handleMouseEvent();
        this.book = book;
    }

    /**
     * Handles mouse events for the button, including click, mouse enter, and mouse exit.
     * Applies scale effect on hover and opens edit dialog on click.
     */
    public void handleMouseEvent() {
        setOnMouseClicked(event -> {
            openFieldBox(book);
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
     * Opens a dialog box for editing book details.
     * Provides input fields for all book properties and buttons for editing and autofilling.
     *
     * @param book The book whose details are to be edited
     */
    private void openFieldBox(Book book) {
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
     * Sends an edit book message via socket with updated book details.
     * Constructs a JSON object with the book's updated properties.
     */
    public void sendMessage() {
        JSONObject newData = new JSONObject();
        newData.put("isbn", book.getIsbn());
        newData.put("title", book.getTitle());
        newData.put("description", book.getDescription());
        newData.put("available", book.getAvailable());
        newData.put("cover", book.getCover());
        newData.put("author", book.getAuthor().getName());
        newData.put("genre", book.getGenre());
        newData.put("publishingYear", book.getPublishingYear());
        newData.put("publisher", book.getPublisher());
        newData.put("pdf", book.getPdf());

        JSONObject data = new JSONObject();
        data.put("book_id", book.getId());
        data.put("new_data", newData);

        SocketService.getInstance().sendMessage("edit_book", data);
    }

    /**
     * Sets up a listener for edit book response messages from the server.
     * Updates the edit status label with success or failure messages.
     */
    public void getMessage() {
        SocketService.getInstance().onMessage("edit_book_response", (Emitter.Listener) args -> {
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