package controller;

import org.json.JSONArray;
import org.json.JSONObject;

import app.AppState;
import components.interfaces.Listener;
import components.chart.ChartUtils;
import components.media.Avatar;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import socket.SocketService;
import utils.SocketUtils;

import java.util.List;

public class ProfileController implements Listener {

    @FXML
    private VBox avatarContainer;

    @FXML
    private VBox informationContainer;

    @FXML
    private VBox bookRead;

    @FXML
    private VBox recent;

    @FXML
    private VBox last;

    /**
     * Initializes the profile controller by setting up socket message listeners
     * and requesting user data from the server.
     */
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
        JSONObject data = new JSONObject();
        data.put("user_id", AppState.getInstance().getUser().getID());
        SocketService.getInstance().sendMessage("get_user", data);
    }

    /**
     * Loads and displays the user's avatar.
     *
     * @param avatarString Base64 encoded string representing the avatar image
     */
    private void avatarPreload(String avatarString) {
        Avatar avatar = new Avatar(avatarString, 228, 228);
        avatarContainer.getChildren().add(avatar);
    }

    /**
     * Populates user biographical information.
     *
     * @param firstname User's first name
     * @param lastname User's last name
     * @param username User's username
     */
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

    /**
     * Creates and displays a pie chart showing books read.
     *
     * @param readBook Number of books read
     * @param totalBook Total number of books
     */
    public void bookReadPreload(int readBook, int totalBook) {
        PieChart chart = ChartUtils.createPieChart("Read Book");
        chart.setStyle("-fx-background-color: transparent;");
        ChartUtils.addPieData(chart, "read" + "(" + readBook + ")", readBook);
        ChartUtils.addPieData(chart, "total" + "(" + totalBook + ")", totalBook);
        bookRead.getChildren().add(chart);
    }

    /**
     * Creates and displays a bar chart of recent book finishes.
     *
     * @param arr JSON array containing recent book finish data
     */
    public void recentPreload(JSONArray arr) {
        BarChart chart = ChartUtils.createBarChart("Streak", "Day", "Book");
        List<Pair<String, Integer>> data = SocketUtils.parseRecentBookFinish(arr);

        for (Pair<String, Integer> point : data) {
            ChartUtils.addBarData(chart, null, point.getKey(), point.getValue());
        }
        recent.getChildren().add(chart);
    }

    /**
     * Displays the number of days since last activity.
     *
     * @param day Number of days since last activity
     */
    public void lastPreload(int day) {
        String text = day > 1 ? "days" : "day";

        HBox container = new HBox();
        container.setSpacing(4);
        container.setAlignment(Pos.CENTER);
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        Label dayNumber = new Label(Integer.toString(day));
        dayNumber.setStyle("-fx-font-family: 'Accent Graphic W00 Medium';\r\n" + //
                "    -fx-font-size: 50;\r\n" + //
                "    -fx-text-fill: black;");
        Label dayText = new Label(text);

        box.getChildren().add(dayText);
        container.getChildren().add(dayNumber);
        container.getChildren().add(box);

        last.getChildren().add(container);
    }

    /**
     * Opens a canvas with the specified ID.
     *
     * @param id Identifier for the canvas to be opened
     */
    public void openCanvas(int id) {

    }

    /**
     * Closes the currently open canvas.
     */
    public void closeCanvas() {

    }
}