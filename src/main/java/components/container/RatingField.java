package components.container;

import org.json.JSONObject;

import components.button.StarButton;
import components.interfaces.Receiver;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import socket.SocketService;

public class RatingField extends HBox implements Receiver {

    public RatingField(int rating) {
        this.setAlignment(Pos.CENTER_LEFT);
        getMessage();
        setSpacing(3);
        for (int i = 1; i <= rating; i++) {
            StarButton starButton = new StarButton(true);
            starButton.setNumber(i);
            getChildren().add(starButton);
        }

        for (int i = rating + 1; i <= 5; i++) {
            StarButton starButton = new StarButton(false);
            starButton.setNumber(i);
            getChildren().add(starButton);
        }
    }

    public void setRating(int rating) {
        for (int i = 1; i <= rating; i++) {
            StarButton starButton = (StarButton)getChildren().get(i - 1);
            starButton.setActive(true);
        }

        for (int i = rating + 1; i <= 5; i++) {
            StarButton starButton = (StarButton)getChildren().get(i - 1);
            starButton.setActive(false);
        }
    }

    public void getMessage() {
        SocketService.getInstance().onMessage("rating_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String status = response.getString("status");
            if (status.equals("rating_success")) {
                Platform.runLater(() -> {
                    setRating(response.getInt("rating"));
                });
            }
        });
    }

}
