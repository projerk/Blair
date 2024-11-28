package controller;

import animation.ScaleEffect;
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
import javafx.scene.paint.Paint;
import model.User;
import org.json.JSONObject;
import socket.SocketService;
import utils.FileHelper;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import animation.FadeEffect;
import animation.TranslateEffect;

import animation.FadeEffect;
import animation.TranslateEffect;


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

    private Label signinNotification;

    private Label signupNotification;

    private boolean trace = true;

    private Button signinButton;

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
            form.getChildren().setAll(createSignupPage());
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

        button.setOnMouseClicked(event -> {
            if (trace) {
                TranslateEffect.translateTo(fronttrace, 1, width / 4, 0);
                TranslateEffect.translateTo(midtrace, 1, midtrace.getLayoutX() - width / 2, 0);
                FadeEffect.fadeOutInAndChangeContent(form, 1, createSigninPage());
                FadeEffect.fadeOutInAndChangeContent(content, 1, createLeftContent());
                trace = false;
            } else {
                TranslateEffect.translateTo(fronttrace, 1, fronttrace.getLayoutX() - width / 2, 0);
                TranslateEffect.translateTo(midtrace, 1, width / 4, 0);
                FadeEffect.fadeOutInAndChangeContent(content, 1, createRightContent());
                FadeEffect.fadeOutInAndChangeContent(form, 1, createSignupPage());
                trace = true;
            }
        });

        Insets insets = new Insets(20, 0, 20, 0);
        contentContainer.setPadding(insets);
        buttonContainer.getChildren().add(button);
        contentContainer.getChildren().addAll(text, imageView);
        container.getChildren().addAll(contentContainer, buttonContainer);

        return container;
    }

    private VBox createLeftContent() {
        VBox container = new VBox();
        container.setSpacing(50);
        VBox contentContainer = new VBox();
        contentContainer.setAlignment(Pos.CENTER);
        contentContainer.setSpacing(30);
        VBox buttonContainer = new VBox();
        buttonContainer.setAlignment(Pos.CENTER);
        VBox.setVgrow(buttonContainer, Priority.ALWAYS);
        VBox.setVgrow(contentContainer, Priority.ALWAYS);
        Label text = new Label("Explore thousands of books and comics");
        text.setStyle("-fx-text-fill: white; -fx-font-size: 30px; -fx-font-family: 'Accent Graphic W00 Medium';");

        Image image = FileHelper.getImage("a1.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(width / 2 - width / 15);

        Button button = new Button("Sign up");
        button.setStyle("-fx-background-color: transparent; -fx-border-width: 1; -fx-border-color: white; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-family: 'Accent Graphic W00 Medium';");

        button.setOnMouseClicked(event -> {
            if (trace) {
                TranslateEffect.translateTo(fronttrace, 1, width / 4, 0);
                TranslateEffect.translateTo(midtrace, 1, midtrace.getLayoutX() - width / 2, 0);
                FadeEffect.fadeOutInAndChangeContent(form, 1, createSigninPage());
                FadeEffect.fadeOutInAndChangeContent(content, 1, createLeftContent());
                trace = false;
            } else {
                TranslateEffect.translateTo(fronttrace, 1, fronttrace.getLayoutX() - width / 2, 0);
                TranslateEffect.translateTo(midtrace, 1, width / 4, 0);
                FadeEffect.fadeOutInAndChangeContent(content, 1, createRightContent());
                FadeEffect.fadeOutInAndChangeContent(form, 1, createSignupPage());
                trace = true;
            }
        });

        Insets insets = new Insets(20, 0, 20, 0);
        contentContainer.setPadding(insets);
        buttonContainer.getChildren().add(button);
        contentContainer.getChildren().add(text);
        contentContainer.getChildren().add(imageView);
        container.getChildren().add(contentContainer);
        container.getChildren().add(buttonContainer);
        return container;
    }

    private VBox createSignupPage() {
        VBox form = new VBox();
        signinNotification = new Label();
        signinNotification.setStyle("-fx-text-fill: black; -fx-font-size: 20px; -fx-font-family: 'Accent Graphic W00 Medium';");
        form.setSpacing(width / 20);
        VBox.setVgrow(form, Priority.ALWAYS);

        VBox dataContainer = new VBox();
        VBox buttonContainer = new VBox();
        buttonContainer.setAlignment(Pos.CENTER);
        dataContainer.setAlignment(Pos.CENTER);
        dataContainer.setSpacing(width / 40);

        signupNotification = new Label();
        signupNotification.setStyle("-fx-text-fill: black; -fx-font-size: 20px; -fx-font-family: 'Accent Graphic W00 Medium';");

        form.setStyle("-fx-background-color: white; -fx-background-radius: 20px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 10, 0, 0, 1);");
        Insets insets = new Insets(20, 20, 20, 20);
        form.setPadding(insets);

        VBox titleContainer = new VBox();
        titleContainer.setAlignment(Pos.CENTER);
        Label title = new Label("Sign up");
        title.setStyle("-fx-text-fill: black; -fx-font-size: 30px; -fx-font-family: 'Accent Graphic W00 Medium';");
        titleContainer.getChildren().add(title);

        HBox nameForm = new HBox();
        VBox dataForm = new VBox();
        dataForm.setAlignment(Pos.CENTER);
        dataForm.setSpacing(width / 50);

        HBox.setHgrow(nameForm, Priority.ALWAYS);
        nameForm.setAlignment(Pos.CENTER);
        nameForm.setSpacing(30);

        TextField firstname = new TextField();
        TextField lastname = new TextField();
        TextField username = new TextField();
        PasswordField password = new PasswordField();
        PasswordField confirmPassword = new PasswordField();

        firstname.setMaxWidth(Double.MAX_VALUE);
        lastname.setMaxWidth(Double.MAX_VALUE);

        firstname.setPromptText("First Name");
        lastname.setPromptText("Last name");
        username.setPromptText("Username");
        password.setPromptText("Password");
        confirmPassword.setPromptText("Confirm Password");

        firstname.setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: #d9d9d9; -fx-font-familly: 'Accent Graphic W00 Medium'; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 10, 0, 0, 1); -fx-min-height: 60px; -fx-background-radius: 30px; -fx-border-radius: 30px;");
        lastname.setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: #d9d9d9; -fx-font-familly: 'Accent Graphic W00 Medium'; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 10, 0, 0, 1); -fx-min-height: 60px; -fx-background-radius: 30px; -fx-border-radius: 30px;");
        username.setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: #d9d9d9; -fx-font-familly: 'Accent Graphic W00 Medium'; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 10, 0, 0, 1); -fx-min-height: 60px; -fx-background-radius: 30px; -fx-border-radius: 30px;");
        password.setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: #d9d9d9; -fx-font-familly: 'Accent Graphic W00 Medium'; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 10, 0, 0, 1); -fx-min-height: 60px; -fx-background-radius: 30px; -fx-border-radius: 30px;");
        confirmPassword.setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: #d9d9d9; -fx-font-familly: 'Accent Graphic W00 Medium'; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 10, 0, 0, 1); -fx-min-height: 60px; -fx-background-radius: 30px; -fx-border-radius: 30px;");

        firstname.setPrefWidth(width / 4);
        lastname.setPrefWidth(width / 4);

        Button signupButton = new Button();
        signupButton.setText("Sign Up");
        signupButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-family: 'Accent Graphic W00 Medium'; -fx-background-radius: 20px;");

        nameForm.getChildren().add(firstname);
        nameForm.getChildren().add(lastname);
        dataForm.getChildren().add(username);
        dataForm.getChildren().add(password);
        dataForm.getChildren().add(confirmPassword);
        dataForm.getChildren().add(signupNotification);
        buttonContainer.getChildren().add(signupButton);

        dataContainer.getChildren().add(nameForm);
        dataContainer.getChildren().add(dataForm);

        form.getChildren().add(titleContainer);
        form.getChildren().add(dataContainer);
        form.getChildren().add(buttonContainer);

        signupButton.setOnMouseClicked(event -> {
            signupButton.setText("Requesting...");
            signupButton.setDisable(true);
            String un = username.getText();
            String pw = password.getText();
            String cpw = confirmPassword.getText();
            String fn = firstname.getText();
            String ln = lastname.getText();

            handleRegisterAuthentication(fn, ln, un, pw, cpw);
        });

        return form;
    }

    private VBox createSigninPage() {
        VBox form = new VBox();
        form.setSpacing(width / 20);
        VBox.setVgrow(form, Priority.ALWAYS);

        VBox dataContainer = new VBox();
        VBox buttonContainer = new VBox();
        buttonContainer.setAlignment(Pos.CENTER);
        dataContainer.setAlignment(Pos.CENTER);
        dataContainer.setSpacing(width / 40);

        signinNotification = new Label();
        signinNotification.setStyle("-fx-text-fill: black; -fx-font-size: 20px; -fx-font-family: 'Accent Graphic W00 Medium';");

        form.setStyle("-fx-background-color: white; -fx-background-radius: 20px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 10, 0, 0, 1);");
        Insets insets = new Insets(20, 20, 20, 20);
        form.setPadding(insets);

        VBox titleContainer = new VBox();
        titleContainer.setAlignment(Pos.CENTER);
        Label title = new Label("Sign in");
        title.setStyle("-fx-text-fill: black; -fx-font-size: 30px; -fx-font-family: 'Accent Graphic W00 Medium';");
        titleContainer.getChildren().add(title);

        VBox dataForm = new VBox();
        dataForm.setAlignment(Pos.CENTER);
        dataForm.setSpacing(width / 50);

        TextField username = new TextField();
        PasswordField password = new PasswordField();

        username.setPromptText("Username");
        password.setPromptText("Password");

        username.setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: #d9d9d9; -fx-font-familly: 'Accent Graphic W00 Medium'; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 10, 0, 0, 1); -fx-min-height: 60px; -fx-background-radius: 30px; -fx-border-radius: 30px;");
        password.setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: #d9d9d9; -fx-font-familly: 'Accent Graphic W00 Medium'; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 10, 0, 0, 1); -fx-min-height: 60px; -fx-background-radius: 30px; -fx-border-radius: 30px;");

        signinButton = new Button("Sign In");
        signinButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-family: 'Accent Graphic W00 Medium'; -fx-background-radius: 20px;");

        dataForm.getChildren().add(username);
        dataForm.getChildren().add(password);
        buttonContainer.getChildren().add(signinButton);
        dataContainer.getChildren().add(dataForm);
        dataContainer.getChildren().add(signinNotification);

        form.getChildren().add(titleContainer);
        form.getChildren().add(dataContainer);
        form.getChildren().add(buttonContainer);

        signinButton.setOnMouseClicked(event -> {
            signinButton.setText("Requesting...");
            signinButton.setDisable(true);
            String un = username.getText();
            String pw = password.getText();

            handleLoginAuthentication(un, pw);
        });

        return form;
    }

    private VBox createSigninPage() {
        VBox form = new VBox();
        form.setSpacing(width / 20);
        VBox.setVgrow(form, Priority.ALWAYS);

        VBox dataContainer = new VBox();
        VBox buttonContainer = new VBox();
        buttonContainer.setAlignment(Pos.CENTER);
        dataContainer.setAlignment(Pos.CENTER);
        dataContainer.setSpacing(width / 40);

        signinNotification = new Label();
        signinNotification.setStyle("-fx-text-fill: black; -fx-font-size: 20px; -fx-font-family: 'Accent Graphic W00 Medium';");

        form.setStyle("-fx-background-color: white; -fx-background-radius: 20px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 10, 0, 0, 1);");
        Insets insets = new Insets(20, 20, 20, 20);
        form.setPadding(insets);

        VBox titleContainer = new VBox();
        titleContainer.setAlignment(Pos.CENTER);
        Label title = new Label("Sign in");
        title.setStyle("-fx-text-fill: black; -fx-font-size: 30px; -fx-font-family: 'Accent Graphic W00 Medium';");
        titleContainer.getChildren().add(title);

        VBox dataForm = new VBox();
        dataForm.setAlignment(Pos.CENTER);
        dataForm.setSpacing(width / 50);

        TextField username = new TextField();
        PasswordField password = new PasswordField();

        username.setPromptText("Username");
        password.setPromptText("Password");

        username.setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: #d9d9d9; -fx-font-familly: 'Accent Graphic W00 Medium'; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 10, 0, 0, 1); -fx-min-height: 60px; -fx-background-radius: 30px; -fx-border-radius: 30px;");
        password.setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: #d9d9d9; -fx-font-familly: 'Accent Graphic W00 Medium'; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 10, 0, 0, 1); -fx-min-height: 60px; -fx-background-radius: 30px; -fx-border-radius: 30px;");

        signinButton = new Button("Sign In");
        signinButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-family: 'Accent Graphic W00 Medium'; -fx-background-radius: 20px;");

        dataForm.getChildren().add(username);
        dataForm.getChildren().add(password);
        buttonContainer.getChildren().add(signinButton);
        dataContainer.getChildren().add(dataForm);
        dataContainer.getChildren().add(signinNotification);

        form.getChildren().add(titleContainer);
        form.getChildren().add(dataContainer);
        form.getChildren().add(buttonContainer);

        return form;
    }

}
