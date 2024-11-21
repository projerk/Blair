package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;
import utils.Constants;
import java.io.IOException;

import app.AppState;

public class MainController {
    @FXML
    private VBox mainPane;

    @FXML
    private VBox compass;

    @FXML
    private VBox book;

    @FXML
    private VBox profile;

    @FXML
    private VBox setting;

    private VBox currentSelectedItem;

    private AppState appState = AppState.getInstance();

    @FXML
    private void initialize() {
        preload();
    }

    private void preload() {
        pagePreload();
    }

    private void pagePreload() {
        currentSelectedItem = compass;
        compass.setOpacity(1);
    }
}
