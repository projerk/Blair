package controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.layout.Priority;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import utils.FileHelper;

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
        Insets insets = new Insets(50,50, 50, 50);
        midtrace.setPadding(insets);
        fronttrace.setAlignment(Pos.CENTER);
        fronttrace.setStyle("-fx-background-color: black;");
        content = new VBox();
        form = new VBox();
        VBox.setVgrow(form, Priority.ALWAYS);
        HBox.setHgrow(form, Priority.ALWAYS);
        VBox.setVgrow(content, Priority.ALWAYS);
        HBox.setHgrow(content, Priority.ALWAYS);
        fronttrace.getChildren().add(content);
        midtrace.getChildren().add(form);
        backtrace.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            width = newWidth.doubleValue();
            if (!pane.getChildren().contains(fronttrace) && !pane.getChildren().contains(midtrace)) {
                pane.getChildren().add(midtrace);
                pane.getChildren().add(fronttrace);
            }

            VBox.setVgrow(midtrace, Priority.ALWAYS);
            HBox.setHgrow(midtrace, Priority.NEVER);
            // midtrace.setStyle("-fx-background-color: red");
            midtrace.setMaxWidth(width / 2);
            midtrace.setPrefWidth(width / 2);
            midtrace.setTranslateX(width / 4);

            VBox.setVgrow(fronttrace, Priority.ALWAYS);
            HBox.setHgrow(fronttrace, Priority.NEVER);
            fronttrace.setMaxWidth(width / 2);
            fronttrace.setPrefWidth(width / 2);
            fronttrace.setTranslateX(-width / 4);

            // Update content initially
            content.getChildren().setAll(createRightContent());
        });
    }

    private VBox createRightContent() {
        VBox container = new VBox();
        container.setSpacing(50);
        VBox contentContainer = new VBox();
        contentContainer.setAlignment(Pos.CENTER);
        contentContainer.setSpacing(30);
        VBox buttonContainer = new VBox();
        buttonContainer.setAlignment(Pos.CENTER);
        VBox.setVgrow(buttonContainer, Priority.ALWAYS);
        VBox.setVgrow(contentContainer, Priority.ALWAYS);

        Label text = new Label("Enjoy our collection of magazines and novels");
        text.setStyle("-fx-text-fill: white; -fx-font-size: 30px; -fx-font-family: 'Accent Graphic W00 Medium';");

        Image image = FileHelper.getImage("a2.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(width / 2 - width / 15);

        Button button = new Button("Sign In");
        button.setStyle("-fx-background-color: transparent; -fx-border-width: 1; -fx-border-color: white; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-family: 'Accent Graphic W00 Medium';");

        Insets insets = new Insets(20, 0, 20, 0);
        contentContainer.setPadding(insets);
        buttonContainer.getChildren().add(button);
        contentContainer.getChildren().addAll(text, imageView);
        container.getChildren().addAll(contentContainer, buttonContainer);

        return container;
    }
}
