package components.container;

import components.abstracts.FlexBox;
import model.GuestComment;
import components.media.Avatar; 
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

/**
 * The CommentView class is a custom JavaFX container that displays a guest comment.
 * It is designed to show the user's avatar, username, and their comment content.
 * 
 * <p>The layout is managed using a FlexBox, with two columns:
 * one for the user's avatar and the other for their username and comment text.</p>
 *
 * <p>This class dynamically adjusts the column widths based on the parent container's width,
 * ensuring responsiveness.</p>
 *
 * @author [Your Name]
 * @version 1.0
 * @since 2024-11-30
 */
public class CommentView extends FlexBox {
    private GuestComment guestComment;

    /**
     * Constructs a new CommentView instance for a given guest comment.
     *
     * @param guestComment The {@link GuestComment} object containing the user's avatar, 
     *                     username, and comment content.
     */
    public CommentView(GuestComment guestComment) {
        this.guestComment = guestComment;
        setColumnPercentages(20, 80);
        setRowPercentages(100);

        // Dynamically adjust column widths based on the container's width.
        this.widthProperty().addListener((observable, oldWidth, newWidth) -> {
            this.getColumnConstraints().get(0).setMaxWidth(0.2 * newWidth.doubleValue());
            this.getColumnConstraints().get(0).setMinWidth(0.2 * newWidth.doubleValue());
            this.getColumnConstraints().get(0).setPrefWidth(0.2 * newWidth.doubleValue());

            this.getColumnConstraints().get(1).setMaxWidth(0.8 * newWidth.doubleValue());
            this.getColumnConstraints().get(1).setMinWidth(0.8 * newWidth.doubleValue());
            this.getColumnConstraints().get(1).setPrefWidth(0.8 * newWidth.doubleValue());
        });

        // Add avatar and comment content to the FlexBox.
        Avatar avatar = new Avatar(guestComment.getAvatar());
        this.addNode(avatar, 0, 0);
        this.addNode(createCommentString(guestComment.getGuestname(), guestComment.getContent()), 1, 0);
    }

    /**
     * Creates a VBox containing the username and the comment content.
     * 
     * @param username The name of the user who made the comment.
     * @param content The content of the comment.
     * @return A VBox containing the username as a bold label and the comment content.
     */
    private VBox createCommentString(String username, String content) {
        VBox container = new VBox();
        container.setSpacing(5);
        container.setAlignment(Pos.TOP_LEFT);
        container.setPadding(new Insets(10, 0, 0, 0));

        // Create and style the username label.
        Label usernameText = new Label("@" + username);
        usernameText.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");

        // Create and style the content label.
        Label contentText = new Label(content);
        contentText.setWrapText(true);
        contentText.prefWidthProperty().bind(container.widthProperty());
        contentText.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");

        // Add the labels to the container.
        container.getChildren().add(usernameText);
        container.getChildren().add(contentText);

        return container;
    }   
}
