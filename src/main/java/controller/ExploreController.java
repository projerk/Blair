package controller;

import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import socket.SocketService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import io.socket.emitter.Emitter;

public class ExploreController {
    @FXML
    private HBox bookDisplay; // HBox to display books
    @FXML
    private VBox jumbotron; // VBox for the jumbotron UI

    private SocketService client = SocketService.getInstance();

    @FXML
    private void initialize() {
        preload();
    }

    private void preload() {
        jumbotronPreload();
        loadFeatureBook();
    }

    private void jumbotronPreload() {
        Label greeting = new Label("Welcome to the Book Explorer!");
        greeting.setStyle("-fx-font-family: 'Accent Graphic W00 Medium'; -fx-font-size: 50; -fx-text-fill: black;");
        jumbotron.getChildren().add(greeting);
    }

    private void loadFeatureBook() {
        client.onMessage("feature_book_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            
            // Check if the response has the "status" key
            if (response.has("status")) {
                try {
                    String status = response.getString("status");
    
                    if (status.equals("success")) {
                        // Proceed to parse the books
                        if (response.has("books")) {
                            JSONArray books = response.getJSONArray("books");
                            for (int i = 0; i < books.length(); i++) {
                                JSONObject bookJson = books.getJSONObject(i);
                                String cover = bookJson.getString("cover");
                                String title = bookJson.getString("title");
                                String author = bookJson.getString("author");
    
                                Platform.runLater(() -> {
                                    bookDisplayPreload(cover, title, author);
                                });
                            }
                        } else {
                            System.out.println("No books found in the response");
                        }
                    } else {
                        System.out.println("Status was not successful: " + status);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Error parsing JSON response: " + e.getMessage());
                }
            } else {
                System.out.println("Response does not contain 'status' key");
            }
        });
    
        client.sendMessage("get_feature_book", null);
    }

    private void bookDisplayPreload(String cover, String title, String author) {
        ImageView imageView = new ImageView(new Image(cover, true));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(200);
        imageView.setOnMouseClicked(event -> {
            // Logic to handle book click can be added here
            System.out.println("Clicked on: " + title + " by " + author);
        });

        // Add image view to the book display area
        bookDisplay.getChildren().add(imageView);
    }
}