package components.button;

import org.json.JSONObject;

import animation.ScaleEffect;
import app.AppState;
import components.interfaces.Sender;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import socket.SocketService;
import utils.FileHelper;

public class StarButton extends Button implements Sender {

    private static Image image1 = FileHelper.getImage("blackstar.png");
    private static Image image2 = FileHelper.getImage("colorstar.png");

    private ImageView imageView1;
    private ImageView imageView2;

    private int number;
    private boolean active;

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isActive() {
        return this.active;
    }


    public void setActive(boolean active) {
        this.setStyle("-fx-background-color: transparent;");
        this.active = active;
        if (active) {
            setGraphic(imageView2);
        }
        else {
            setGraphic(imageView1);
        }
    }

    public StarButton(boolean active) {
        imageView1 = new ImageView(image1);
        imageView2 = new ImageView(image2);

        imageView1.setPreserveRatio(true);
        imageView2.setPreserveRatio(true);

        imageView1.setFitWidth(27);
        imageView2.setFitWidth(30);


        setActive(active);

        setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(this, 0.1, 1.1, 1.1);
        });

        setOnMouseExited(event -> {
            ScaleEffect.scaleTo(this, 0.1, 1.0, 1.0);
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
        data.put("rating", number);
        SocketService.getInstance().sendMessage("set_rating", data);
    }
}
