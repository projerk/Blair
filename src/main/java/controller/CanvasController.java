package controller;

import java.util.List;

import org.json.JSONObject;
import com.dansoftware.pdfdisplayer.PDFDisplayer;
import animation.ScaleEffect;
import components.container.CommentView;
import components.field.CommentField;
import components.interfaces.Listener;
import components.media.WrappedImageView;
import components.text.BookInformation;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Book;
import model.GuestComment;
import utils.FileHelper;
import utils.PoolingToolkit;
import app.AppState;
import socket.SocketService;

public class CanvasController {
    private Listener listener = AppState.getInstance().getListener();

    @FXML
    private VBox canvas;

    @FXML
    private HBox controlBar;

    @FXML
    private VBox bookContainer;

    @FXML
    private VBox commentContainer;

    @FXML
    private VBox commentFieldContainer;

    @FXML
    private VBox commentBox;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private VBox bookCover;

    @FXML
    private VBox bookInformation;

    @FXML
    private Label description;

    private SocketService client = SocketService.getInstance();

    private WrappedImageView closeButton;

    @FXML
    public void initialize() {
        loadContent();
        // loadBookContainer();
        loadCommentBox();
        closeButtonPreload();
        loadCommentField();
        scrollpane.setHbarPolicy(ScrollBarPolicy.NEVER);
        commentBox.setSpacing(7);
        commentContainer.widthProperty().addListener((observable, oldWidth, newWidth) -> {
            if (newWidth.doubleValue() >= 0) {
                commentBox.setMaxWidth(newWidth.doubleValue() - 10);
                commentBox.setPrefWidth(newWidth.doubleValue() - 10);
                commentBox.setMinWidth(newWidth.doubleValue() - 10);
            }
        });
        // commentBox.widthProperty().bind(commentContainer.widthProperty());
        // commentContainer.widthProperty().addListener((observable, oldWidth, newWidth) -> {
        //     commentBox.setPrefWidth(newWidth.doubleValue() - 100);
        // });
    }

    public void closeButtonPreload() {

        Image image = FileHelper.getImage("close.png");
        closeButton = new WrappedImageView();
        closeButton.setImage(image);
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

    private void loadCommentField() {
        CommentField commentfield = new CommentField();
        commentFieldContainer.getChildren().add(commentfield);
    }

    private void loadContent() {
        client.onMessage("book_detail_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String status = response.getString("status");

            if (status.equals("success")) {
                Book book = utils.SocketUtils.parseBook(response);
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
}
