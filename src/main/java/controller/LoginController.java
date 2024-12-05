package controller;

import animation.ScaleEffect;
import app.AppState;
import app.Projerk;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
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
    private Button signupButton;

    private SocketService client = SocketService.getInstance();

    private Projerk app = Projerk.getInstance();

    /**
     * Initializes the login controller and sets up the UI components.
     * Configures event listeners and layout properties for the login/signup interface.
     */
    @FXML
    public void initialize() {
        handleLoginAuthentication();
        handleRegisterAuthentication();
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

    /**
     * Creates the left-side content for the login/signup interface.
     *
     * @return A VBox containing the left-side content with an image, text, and sign-up button
     */
    private VBox createLeftContent() {
        System.out.println("Fuck");
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

        button.setOnMouseEntered(event -> ScaleEffect.scaleTo(button, 0.2, 1.2, 1.2));
        button.setOnMouseExited(event -> ScaleEffect.scaleTo(button, 0.2, 1.0, 1.0));
        button.setOnMouseClicked(event -> {
            if (trace) {
                TranslateEffect.translateTo(fronttrace, 1, width / 4, 0);
                TranslateEffect.translateTo(midtrace, 1, midtrace.getLayoutX() - width / 2, 0);
                FadeEffect.fadeOutInAndChangeContent(content, 1, createLeftContent());
                FadeEffect.fadeOutInAndChangeContent(form, 1, createSigninPage());
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

    /**
     * Creates the right-side content for the login/signup interface.
     *
     * @return A VBox containing the right-side content with an image, text, and sign-in button
     */
    private VBox createRightContent() {
        System.out.println("Fuck Bitch");
        VBox container = new VBox();
        container.setSpacing(50);
        VBox contentContainer = new VBox();
        contentContainer.setSpacing(30);
        contentContainer.setAlignment(Pos.CENTER);
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

        button.setOnMouseEntered(event -> ScaleEffect.scaleTo(button, 0.2, 1.2, 1.2));
        button.setOnMouseExited(event -> ScaleEffect.scaleTo(button, 0.2, 1.0, 1.0));
        button.setOnMouseClicked(event -> {
            if (trace) {
                TranslateEffect.translateTo(fronttrace, 1, width / 4, 0);
                TranslateEffect.translateTo(midtrace, 1, midtrace.getLayoutX() - width / 2, 0);
                FadeEffect.fadeOutInAndChangeContent(content, 1, createLeftContent());
                FadeEffect.fadeOutInAndChangeContent(form, 1, createSigninPage());
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

    /**
     * Creates the signup page form with input fields for user registration.
     *
     * @return A VBox containing the signup form with firstname, lastname, username, password fields
     */
    private VBox createSignupPage() {
        System.out.println("Fuck Man");
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
        Insets insets = new Insets(20,20, 20, 20);
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
        lastname.setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: #d9d9d9; -fx-font-familly: 'Accent Graphic W00 Medium'; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 10, 0, 0, 1);  -fx-min-height: 60px; -fx-background-radius: 30px; -fx-border-radius: 30px;");
        username.setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: #d9d9d9; -fx-font-familly: 'Accent Graphic W00 Medium'; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 10, 0, 0, 1);  -fx-min-height: 60px; -fx-background-radius: 30px; -fx-border-radius: 30px;");
        password.setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: #d9d9d9; -fx-font-familly: 'Accent Graphic W00 Medium'; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 10, 0, 0, 1);  -fx-min-height: 60px; -fx-background-radius: 30px; -fx-border-radius: 30px;");
        confirmPassword.setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: #d9d9d9; -fx-font-familly: 'Accent Graphic W00 Medium'; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 10, 0, 0, 1);  -fx-min-height: 60px; -fx-background-radius: 30px; -fx-border-radius: 30px;");

        firstname.setPrefWidth(width / 4);
        lastname.setPrefWidth(width / 4);

        signupButton = new Button();
        signupButton.setText("Sign Up");
        signupButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-family: 'Accent Graphic W00 Medium'; -fx-background-radius: 20px;");
        signupButton.setOnMouseEntered(event -> ScaleEffect.scaleTo(signupButton, 0.2, 1.2, 1.2));
        signupButton.setOnMouseExited(event -> ScaleEffect.scaleTo(signupButton, 0.2, 1.0, 1.0));

        signupButton.setOnMouseClicked(event -> {
            signupButton.setText("Requesting...");
            signupButton.setDisable(true);
            String un = username.getText();
            String pw = password.getText();
            String cpw = confirmPassword.getText();
            String fn = firstname.getText();
            String ln = lastname.getText();

            sendRegister(fn, ln, un, pw, cpw);
        });

        nameForm.getChildren().add(firstname);
        nameForm.getChildren().add(lastname);
        dataForm.getChildren().add(username);
        dataForm.getChildren().add(password);
        dataForm.getChildren().add(confirmPassword);
        dataForm.getChildren().add(signupNotification);
        buttonContainer.getChildren().add(signupButton);
        // TextField firstname = new TextField();
        // TextField lastname = new TextField();s

        dataContainer.getChildren().add(nameForm);
        dataContainer.getChildren().add(dataForm);
        form.getChildren().add(titleContainer);
        form.getChildren().add(dataContainer);
        form.getChildren().add(buttonContainer);
        // form.getChildren().add(dataForm);
        return form;
    }

    /**
     * Creates the signin page form with input fields for user login.
     *
     * @return A VBox containing the signin form with username and password fields
     */
    private VBox createSigninPage() {
        // System.out.println("Fuck Man");
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
        Insets insets = new Insets(20,20, 20, 20);
        form.setPadding(insets);

        VBox titleContainer = new VBox();
        titleContainer.setAlignment(Pos.CENTER);
        Label title = new Label("Sign in");
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
        lastname.setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: #d9d9d9; -fx-font-familly: 'Accent Graphic W00 Medium'; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 10, 0, 0, 1);  -fx-min-height: 60px; -fx-background-radius: 30px; -fx-border-radius: 30px;");
        username.setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: #d9d9d9; -fx-font-familly: 'Accent Graphic W00 Medium'; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 10, 0, 0, 1);  -fx-min-height: 60px; -fx-background-radius: 30px; -fx-border-radius: 30px;");
        password.setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: #d9d9d9; -fx-font-familly: 'Accent Graphic W00 Medium'; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 10, 0, 0, 1);  -fx-min-height: 60px; -fx-background-radius: 30px; -fx-border-radius: 30px;");
        confirmPassword.setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: #d9d9d9; -fx-font-familly: 'Accent Graphic W00 Medium'; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 10, 0, 0, 1);  -fx-min-height: 60px; -fx-background-radius: 30px; -fx-border-radius: 30px;");

        firstname.setPrefWidth(width / 4);
        lastname.setPrefWidth(width / 4);

        signinButton = new Button();
        signinButton.setText("Sign In");
        signinButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-family: 'Accent Graphic W00 Medium'; -fx-background-radius: 20px;");
        signinButton.setOnMouseEntered(event -> ScaleEffect.scaleTo(signinButton, 0.2, 1.2, 1.2));
        signinButton.setOnMouseExited(event -> ScaleEffect.scaleTo(signinButton, 0.2, 1.0, 1.0));

        signinButton.setOnMouseClicked(event -> {
            signinButton.setText("Requesting...");
            signinButton.setDisable(true);
            String un = username.getText();
            String pw = password.getText();

            sendLogin(un, pw);
        });

        nameForm.getChildren().add(firstname);
        nameForm.getChildren().add(lastname);
        dataForm.getChildren().add(username);
        dataForm.getChildren().add(password);
        // dataForm.getChildren().add(confirmPassword);
        buttonContainer.getChildren().add(signinButton);
        // dataContainer.getChildren().add(nameForm);
        dataContainer.getChildren().add(dataForm);
        dataContainer.getChildren().add(signinNotification);
        form.getChildren().add(titleContainer);
        form.getChildren().add(dataContainer);
        form.getChildren().add(buttonContainer);
        return form;
    }

    /**
     * Sets up the event listener for handling user registration responses from the server.
     * Updates the UI with success or error messages based on the server's response.
     */
    private void handleRegisterAuthentication() {
        // if (!pw.equals(cpw)) {
        //     return;
        // }

        client.onMessage("register_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String message = response.getString("status");
            System.out.println(message);
            if (message.equals("success")) {
                Platform.runLater(() -> {
                    signupNotification.setText(response.getString("message"));
                    signupNotification.setTextFill(Paint.valueOf("green"));

                    signupButton.setDisable(false);
                    signupButton.setText("Sign up");
                });
            }
            else {
                Platform.runLater(() -> {
                    signupNotification.setText(response.getString("message"));
                    signupNotification.setTextFill(Paint.valueOf("red"));

                    signupButton.setDisable(false);
                    signupButton.setText("Sign up");
                });
            }
        });
    }

    /**
     * Sends a registration request to the server with user details.
     *
     * @param fn  First name of the user
     * @param ln  Last name of the user
     * @param un  Username chosen by the user
     * @param pw  Password for the user account
     * @param cpw Confirmation of the password
     */
    public void sendRegister(String fn, String ln, String un, String pw, String cpw) {
        JSONObject registerData = new JSONObject();
        registerData.put("username", un);
        registerData.put("password", pw);
        registerData.put("firstname", fn);
        registerData.put("lastname", ln);
        client.sendMessage("register", registerData);
    }

    /**
     * Sets up the event listener for handling user login responses from the server.
     * Navigates to the appropriate view (admin or main) based on the server's response.
     */
    private void handleLoginAuthentication() {
        client.onMessage("login_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String message = response.getString("status");
            if (message.equals("success")) {
                if (response.getString("role").equals("admin")) {
                    Platform.runLater(() -> {
                        app.changeRoot("AdminView.fxml");

                    });
                }
                else {
                    initialAppState(response.getInt("id"), response.getString("username"), response.getString("firstname"), response.getString("lastname"));
                    Platform.runLater(() -> {
                        app.changeRoot("MainView.fxml");
                    });
                }
            }
            else {
                Platform.runLater(() -> {
                    signinNotification.setText(response.getString("message"));
                    signinNotification.setTextFill(Paint.valueOf("red"));
                    signinButton.setDisable(false);
                    signinButton.setText("Sign in");
                });
            }
        });
    }

    /**
     * Sends a login request to the server with user credentials.
     *
     * @param username Username of the user attempting to log in
     * @param password Password for the user account
     */
    public void sendLogin(String username, String password) {
        JSONObject loginData = new JSONObject();
        loginData.put("username", username);
        loginData.put("password", password);
        client.sendMessage("login", loginData);
    }

    /**
     * Initializes the application state with the logged-in user's information.
     *
     * @param id        User's unique identifier
     * @param username  User's username
     * @param firstname User's first name
     * @param lastname  User's last name
     */
    private void initialAppState(int id, String username, String firstname, String lastname) {
        AppState appState = AppState.getInstance();
        User user = new User();
        user.setID(id);
        user.setUsername(username);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        appState.setUser(user);
    }
}