package components.container;

import components.abstracts.FlexBox;
import model.GuestComment;
import components.media.Avatar; 
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class CommentView extends FlexBox {
    private GuestComment guestComment;

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
