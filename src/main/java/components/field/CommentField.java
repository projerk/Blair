package components.field;

import animation.ScaleEffect;
import app.AppState;
import components.abstracts.FlexBox;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Comment;
import socket.SocketService;

/**
 * The CommentField class represents a user interface component for submitting comments.
 * It consists of a text field for entering the comment and a clickable "Post" button
 * that sends the comment to the server when clicked.
 *
 * <p>This class is built using a FlexBox layout to manage the positioning and responsiveness
 * of its child nodes.</p>
 *
 * @author [Your Name]
 * @version 1.0
 * @since 2024-11-30
 */
public class CommentField extends FlexBox {
    private TextField textfield;
    private SocketService client = SocketService.getInstance();

    /**
     * Constructs a new CommentField instance.
     * Initializes the text field and the submit button components and sets up the layout.
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
     * Initializes the text field where the user can enter their comment.
     * The text field is styled and added to the first column of the layout.
     */
    private void initializeTextField() {
        textfield = new TextField();
        textfield.setPromptText("Give your review...");
        textfield.setStyle("-fx-background-color: transparent;");
        this.addNode(textfield, 0, 0);
    }

    /**
     * Initializes the "Post" button for submitting the comment.
     * The button includes hover animations and a click handler for sending the comment.
     */
    private void initializeSubmitButton() {
        VBox container = new VBox();
        HBox.setHgrow(container, Priority.ALWAYS);
        VBox.setVgrow(container, Priority.ALWAYS);
        container.setAlignment(Pos.CENTER);

        Label postButton = new Label("Post");
        postButton.setStyle("-fx-text-fill: #0095F6;");
        container.getChildren().add(postButton);

        // Add hover animation effect to the "Post" button.
        container.setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(container, 0.2, 1.2, 1.2);
        });

        container.setOnMouseExited(event -> {
            ScaleEffect.scaleTo(container, 0.2, 1.0, 1.0);
        });

        // Set up the click event for submitting the comment.
        container.setOnMouseClicked(event -> {
            String content = textfield.getText();

            if (!content.equals("")) {
                sendComment(content);
            }

            textfield.clear();

            // Refocus on the text field after submitting the comment.
            Platform.runLater(() -> {
                this.requestFocus();
            });
        });

        this.addNode(container, 1, 0);
    }

    /**
     * Sends the user's comment to the server.
     *
     * @param content The content of the comment to be sent.
     */
    private void sendComment(String content) {
        Comment comment = new Comment(
            AppState.getInstance().getCurrentViewBookID(),
            AppState.getInstance().getUser().getID(),
            content
        );

        System.out.println(AppState.getInstance().getCurrentViewBookID());
        System.out.println(AppState.getInstance().getUser().getID());

        client.sendMessage("post_comment", comment.toJSON());
    }
}
