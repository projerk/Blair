package components.button;

import java.io.File;
import java.net.Socket;

import org.json.JSONObject;

import animation.ScaleEffect;
import app.AppState;
import components.abstracts.FlexButton;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import socket.SocketService;
import utils.FileHelper;

public class BookmarkButton extends Button {
    ImageView imageView1;
    ImageView imageView2;
    static Image bookmarkImage = FileHelper.getImage("c.png");
    static Image unbookmarkImage = FileHelper.getImage("b.png");

    boolean bookmark;

    SocketService client = SocketService.getInstance();

    public boolean isBookmark() {
        return this.bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }

    public BookmarkButton(boolean bookmark) {
        this.setStyle("-fx-background-color: transparent;");

        getMessage();

        imageView1 = new ImageView(unbookmarkImage);
        imageView1.setPreserveRatio(true);
        imageView1.setFitWidth(30);

        imageView2 = new ImageView(bookmarkImage);
        imageView2.setPreserveRatio(true);
        imageView2.setFitWidth(30);

        if (!bookmark) {
            setGraphic(imageView2);
        }
        else {
            setGraphic(imageView1);
        }


        setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(this, 0.2, 1.2, 1.2);
        });

        setOnMouseExited(event -> {
            ScaleEffect.scaleTo(this, 0.2, 1.0, 1.0);
        });

        setOnMouseClicked(event -> {
            Platform.runLater(() -> {
                sendMessage();
            });
        });
    }

    public void sendMessage() {
        JSONObject data = new JSONObject();

        data.put("book_id", AppState.getInstance().getCurrentViewBookID());
        data.put("user_id", AppState.getInstance().getUser().getID());

        client.sendMessage("set_bookmark", data);
    }

    public void getMessage() {
        client.onMessage("bookmark_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String status = response.getString("status");
            if (status.equals("bookmark_success")) {
                Platform.runLater(() -> {
                    setGraphic(imageView1);

                });
            }
            else if (status.equals("unbookmark_success")) {
                Platform.runLater(() -> {
                    setGraphic(imageView2);
                });
            }
        });
    }
}
