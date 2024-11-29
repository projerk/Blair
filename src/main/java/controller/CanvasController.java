package controller;

import app.AppState;
import components.media.WrappedImageView;
import components.text.BookInformation;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Book;
import org.json.JSONObject;
import socket.SocketService;
import utils.FileHelper;
import utils.PoolingToolkit;
import components.interfaces.Listener;
import animation.ScaleEffect;
import javafx.scene.image.Image;
import model.GuestComment;
import components.container.CommentView;

public class CanvasController {
     private Listener listener = AppState.getInstance().getListener();

    private SocketService client = SocketService.getInstance();

    @FXML
    private Label description;

    @FXML
    private VBox bookInformation;

    @FXML
    private VBox commentBox;

    @FXML
    private HBox controlBar;

    @FXML
    public void initialize() {
        loadContent();
        loadCommentBox();
        loadCloseButton();
    }

    @FXML
    private VBox bookCover;

    private WrappedImageView closeButton;

    private void loadContent() {
        client.onMessage("book_detail_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String status = response.getString("status");

            if (status.equals("success")) {
                Book book = utils.SocketUtils.parseBook(response);
                // System.out.println(book.toString());
                Platform.runLater(() -> {
                    description.setText(book.getDescription());
                    loadBookContainer(book);
                });
            }
        });

        JSONObject data = new JSONObject();
        data.put("book_id", AppState.getInstance().getCurrentViewBookID());
        data.put("user_id", AppState.getInstance().getUser().getID());
        client.sendMessage("get_book_detail", data);
    }

    private void loadBookContainer(Book book) {
        WrappedImageView imageView = new WrappedImageView(PoolingToolkit.getImage(book.getCover()));

        BookInformation bookInfo = new BookInformation(book);

        bookInformation.getChildren().add(bookInfo);
        bookCover.getChildren().add(imageView);
    }

    private void loadCommentBox() {
        client.onMessage("get_comment_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String message = response.getString("message");
            if (message.equals("comment")) {
                GuestComment guestComment = utils.SocketUtils.parseComment(response);
                Platform.runLater(() -> {
                    CommentView commentView = new CommentView(guestComment);
                    commentBox.widthProperty().addListener((observable, oldWidth, newWidth) -> {
                        if (newWidth.doubleValue() >= 0) {
                            commentView.setMaxWidth(newWidth.doubleValue());
                            commentView.setPrefWidth(newWidth.doubleValue());
                            commentView.setMinWidth(newWidth.doubleValue());
                        }
                    });
                    commentBox.getChildren().add(commentView);
                });
            }
            else {
                System.out.println(message);
                System.out.println(response.getString("error"));
            }
        });

        JSONObject object = new JSONObject();
        object.put("book_id", AppState.getInstance().getCurrentViewBookID());
        client.sendMessage("get_all_comment", object);
    }

    private void loadCloseButton() {
        Image newImage = FileHelper.getImage("close.png");
        closeButton = new WrappedImageView();
        closeButton.setImage(newImage);
        closeButton.setOpacity(0.8);
        
        closeButton.setOnMouseClicked(event -> {
            listener.closeCanvas();
        });

        closeButton.setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(closeButton, 0.1, 1.2, 1.2);
        });

        closeButton.setOnMouseExited(event -> {
            ScaleEffect.scaleTo(closeButton, 0.1, 1.0, 1.0);
        });

        controlBar.getChildren().add(closeButton);
    }
}
