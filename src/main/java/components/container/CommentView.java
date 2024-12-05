package components.container;

import components.abstracts.FlexBox;
import components.media.Avatar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.GuestComment;

/**
 * A flexible container for displaying a single guest comment.
 *
 * Renders a comment with an avatar and text content, using a two-column
 * layout to separate the user's avatar from their comment details.
 */
public class CommentView extends FlexBox {
    /** The guest comment model containing comment details. */
    private GuestComment guestComment;

    /**
     * Constructs a CommentView for a specific guest comment.
     *
     * Creates a flexible layout with an avatar and comment text.
     * Dynamically adjusts column widths based on the container's width.
     *
     * @param guestComment The GuestComment object to be displayed
     */
    public CommentView(GuestComment guestComment) {
        this.guestComment = guestComment;
        setColumnPercentages(20, 80);
        setRowPercentages(100);
        // setAllColumnsHgrow(Priority.ALWAYS);
        // setAllRowsVgrow(Priority.ALWAYS);
        this.widthProperty().addListener((observable, oldWidth, newWidth) -> {
            this.getColumnConstraints().get(0).setMaxWidth(0.2*newWidth.doubleValue());
            this.getColumnConstraints().get(0).setMinWidth(0.2*newWidth.doubleValue());
            this.getColumnConstraints().get(0).setPrefWidth(0.2*newWidth.doubleValue());

            this.getColumnConstraints().get(1).setMaxWidth(0.8*newWidth.doubleValue());
            this.getColumnConstraints().get(1).setMinWidth(0.8*newWidth.doubleValue());
            this.getColumnConstraints().get(1).setPrefWidth(0.8*newWidth.doubleValue());
        });
        // HBox.setHgrow(this, Priority.ALWAYS);

        Avatar avatar = new Avatar(guestComment.getAvatar());
        this.addNode(avatar, 0, 0);
        this.addNode(createCommentString(guestComment.getGuestname(), guestComment.getContent()), 1, 0);
    }

    /**
     * Creates a vertical box containing the username and comment content.
     *
     * Generates a VBox with styled labels for the username and comment text.
     * Configures text wrapping and styling for better readability.
     *
     * @param username The name of the user who posted the comment
     * @param content The text content of the comment
     * @return A VBox containing formatted username and comment labels
     */
    private VBox createCommentString(String username, String content) {
        VBox container = new VBox();
        container.setSpacing(5);
        container.setAlignment(Pos.TOP_LEFT);
        container.setPadding(new Insets(10, 0, 0, 0));
        // container.setStyle("-fx-background-color: blue;");

        Label usernameText = new Label("@" + username);
        Label contentText = new Label(content);

        contentText.setWrapText(true);
        contentText.prefWidthProperty().bind(container.widthProperty());

        usernameText.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");
        contentText.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");

        container.getChildren().add(usernameText);
        container.getChildren().add(contentText);

        return container;
    }
}