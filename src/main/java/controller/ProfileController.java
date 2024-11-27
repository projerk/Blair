package controller;

import components.interfaces.Listener;
import javafx.fxml.FXML;
import io.socket.emitter.Emitter;
import org.json.JSONArray;
import org.json.JSONObject;
import socket.SocketService;
import javafx.application.Platform;
import app.AppState;
import components.media.Avatar;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class ProfileController implements Listener{
    @FXML
    private VBox avatarContainer;

    @FXML
    private VBox informationContainer;

    @FXML
    private void initialize() {
        SocketService.getInstance().onMessage("user_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String status = response.getString("status");
            if (status.equals("success")) {
                Platform.runLater(() -> {
                    avatarPreload(response.getString("avatar"));
                    bioPreload(response.getString("firstname"), response.getString("lastname"), response.getString("username"));
                    bookReadPreload(response.getInt("readbook"), response.getInt("totalbook"));
                    recentPreload(response.getJSONArray("recent"));
                    lastPreload(response.getInt("last"));
                });
            }
        });
        //avatarPreload(null);
        JSONObject data = new JSONObject();
        data.put("user_id", AppState.getInstance().getUser().getID());
        SocketService.getInstance().sendMessage("get_user", data);
    }

    private void avatarPreload(String avatarString) {
        Avatar avatar = new Avatar(avatarString, 228, 228);
        avatarContainer.getChildren().add(avatar);
    }

    private void bioPreload(String firstname, String lastname, String username) {
        VBox container = new VBox();
        container.setSpacing(10);
        Label nameLabel = new Label(lastname + " " + firstname);
        nameLabel.setStyle("-fx-font-size: 30; -fx-text-fill: black;");
        Label usernameLabel = new Label("@" + username);
        usernameLabel.setStyle("-fx-font-size: 15; -fx-text-fill: black; -fx-opacity: 0.6;");
        Label tierLabel = new Label("Blair Novice");
        tierLabel.setStyle("-fx-font-size: 15; -fx-text-fill: black;");
        Label creditLabel = new Label("Credit: 100");
        creditLabel.setStyle("-fx-font-size: 15; -fx-text-fill: black;");

        container.getChildren().add(nameLabel);
        container.getChildren().add(usernameLabel);
        container.getChildren().add(tierLabel);
        container.getChildren().add(creditLabel);

        informationContainer.getChildren().add(container);

    }


    @Override
    public void openCanvas(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'openCanvas'");
    }

    @Override
    public void closeCanvas() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'closeCanvas'");
    }

}
