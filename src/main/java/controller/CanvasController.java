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
import javafx.scene.image.Image;

public class CanvasController {
    private SocketService client = SocketService.getInstance();

    @FXML
    private Label description;

    @FXML
    private VBox bookInformation;

    @FXML
    private HBox controlBar;

    @FXML
    public void initialize() {
        loadContent();
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

    private void loadCloseButton() {
        Image newImage = FileHelper.getImage("close.png");
        closeButton = new WrappedImageView();
        closeButton.setImage(newImage);
        closeButton.setOpacity(0.8);

        controlBar.getChildren().add(closeButton);
    }
}
