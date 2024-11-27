package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.layout.Priority;

public class LoginController {

    private double width;
    private VBox fronttrace;
    private VBox content;
    private VBox midtrace;
    private VBox form;

    @FXML
    private VBox backtrace;

    @FXML
    private StackPane pane;

    @FXML
    public void initialize() {
        fronttrace = new VBox();
        midtrace = new VBox();
        Insets insets = new Insets(50, 50, 50, 50);
        midtrace.setPadding(insets);
        fronttrace.setAlignment(javafx.geometry.Pos.CENTER);
        fronttrace.setStyle("-fx-background-color: black;");
        content = new VBox();
        form = new VBox();
        VBox.setVgrow(form, Priority.ALWAYS);
        VBox.setVgrow(content, Priority.ALWAYS);
        fronttrace.getChildren().add(content);
        midtrace.getChildren().add(form);
        backtrace.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            width = newWidth.doubleValue();
        });
    }
}
