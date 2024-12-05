package components.field;

import animation.ScaleEffect;
import app.AppState;
import components.abstracts.FlexBox;
import components.media.WrappedImageView;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Comment;
import utils.FileHelper;
import socket.SocketService;

/**
 * A custom input field for submitting comments with a post button.
 *
 * Provides a text field for entering comments and a post button for submission.
 */
public class CommentField extends FlexBox {
    private TextField textfield;
    private SocketService client = SocketService.getInstance();

    /**
     * Constructs a new CommentField with a text input and post button.
     *
     * Sets up the layout, text field, and submit button with interactive features.
     */
    public CommentField() {
        setColumnPercentages(85, 15);
        setRowPercentages(100);
        setAllColumnsHgrow(Priority.ALWAYS);
        setAllRowsVgrow(Priority.ALWAYS);

        initializeTextField();
        initializeSubmitButton();
    }

    /**
     * Initializes the text field for comment input.
     *
     * Creates a transparent text field with a prompt text.
     */
    private void initializeTextField() {
        textfield = new TextField();
        textfield.setPromptText("Give your review...");
        textfield.setStyle("-fx-background-color: transparent;");
        this.addNode(textfield, 0, 0);
    }

    /**
     * Initializes the submit button for posting comments.
     *
     * Creates a post button with hover and click interactions.
     * Validates comment content and sends the comment when clicked.
     */
    private void initializeSubmitButton() {
        VBox container = new VBox();
        HBox.setHgrow(container, Priority.ALWAYS);
        VBox.setVgrow(container, Priority.ALWAYS);
        container.setAlignment(Pos.CENTER);
        Label postButton = new Label("Post");
        postButton.setStyle("-fx-text-fill: #0095F6;");
        container.getChildren().add(postButton);

        container.setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(container, 0.2,1.2, 1.2);
        });

        container.setOnMouseExited(event -> {
            ScaleEffect.scaleTo(container, 0.2,1.0, 1.0);
        });

        container.setOnMouseClicked(event -> {

            String content = textfield.getText();

            if (!content.equals("")) {
                sendComment(content);
            }

            textfield.clear();
            Platform.runLater(() -> {
                this.requestFocus();
            });
        });

        this.addNode(container, 1, 0);
    }

    /**
     * Sends the comment to the server via socket connection.
     *
     * Creates a new Comment object with current book and user information,
     * and sends it to the server.
     *
     * @param content The text content of the comment to be sent
     */
    private void sendComment(String content) {
        Comment comment = new Comment(AppState.getInstance().getCurrentViewBookID(), AppState.getInstance().getUser().getID(), content);
        System.out.println(AppState.getInstance().getCurrentViewBookID());
        System.out.println(AppState.getInstance().getUser().getID());

        client.sendMessage("post_comment", comment.toJSON());
    }
}