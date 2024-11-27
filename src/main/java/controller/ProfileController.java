package controller;

import components.interfaces.Listener;
import javafx.fxml.FXML;
import io.socket.emitter.Emitter;
import org.json.JSONArray;
import org.json.JSONObject;
import socket.SocketService;
import javafx.application.Platform;
import app.AppState;
public class ProfileController implements Listener{

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
