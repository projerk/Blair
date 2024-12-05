package controller;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;

import animation.ScaleEffect;
import api.GoogleBooksAPI;
import components.table.BookTableCell;
import components.table.UserTableCell;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Book;
import socket.SocketService;
import utils.SocketUtils;
import model.User;

public class AdminUserManagerController {
    @FXML
    private VBox userContainer;

    @FXML
    private Button newUser;

    @FXML
    private Button previousPage;

    @FXML
    private Button nextPage;

    @FXML
    private Button refreshPage;

    private int currentPage = 1;

    private Label editStatus = new Label();

    @FXML
    private TextField searchfield;

    @FXML
    private Button search;

    @FXML
    private ComboBox<String> searchOption;

    private SocketService client = SocketService.getInstance();

    /**
     * Initializes the controller by setting up buttons and socket message handlers.
     */
    @FXML
    private void initialize() {
        initializeButton();
        getPopUpMessage();
        getMessage();
        sendMessage();
    }

    /**
     * Configures event handlers for navigation and action buttons.
     * Adds hover and click effects to buttons.
     */
    private void initializeButton() {
        newUser.setOnMouseClicked(event -> {
            openFieldBox();
        });

        newUser.setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(newUser, 0.1, 1.1, 1.1);
        });

        newUser.setOnMouseExited(event -> {
            ScaleEffect.scaleTo(newUser, 0.1, 1.0, 1.0);
        });

        previousPage.setOnMouseClicked(event -> {
            if (currentPage > 1) {
                currentPage--;
                sendMessage();
            }
        });

        previousPage.setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(previousPage, 0.1, 1.1, 1.1);
        });

        previousPage.setOnMouseExited(event -> {
            ScaleEffect.scaleTo(previousPage, 0.1, 1.0, 1.0);
        });

        nextPage.setOnMouseClicked(event -> {
            if (currentPage < 8) {
                currentPage++;
                sendMessage();
            }
        });

        nextPage.setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(nextPage, 0.1, 1.1, 1.1);
        });

        nextPage.setOnMouseExited(event -> {
            ScaleEffect.scaleTo(nextPage, 0.1, 1.0, 1.0);
        });

        refreshPage.setOnMouseClicked(event -> {
            sendMessage();
        });

        refreshPage.setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(refreshPage, 0.1, 1.1, 1.1);
        });

        refreshPage.setOnMouseExited(event -> {
            ScaleEffect.scaleTo(refreshPage, 0.1, 1.0, 1.0);
        });
    }

    /**
     * Sets up a socket listener for receiving user table responses.
     * Clears the user container and populates it with user table cells upon successful response.
     */
    private void getMessage() {
        client.onMessage("user_table_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String message = response.getString("status");
            if (message.equals("success")) {
                Platform.runLater(() -> {
                    userContainer.getChildren().clear();
                });
                List<User> users = SocketUtils.parseUserTableCell(response.getJSONArray("user_list"));

                for (User user : users) {
                    Platform.runLater(() -> {
                        userContainer.getChildren().add(new UserTableCell(user));
                    });
                }
            }
            else {

            }
        });
    }

    /**
     * Sends a request to retrieve user table data for the current page.
     */
    private void sendMessage() {
        JSONObject data = new JSONObject();
        data.put("page", currentPage);
        client.sendMessage("get_user_table", data);
    }

    /**
     * Opens a dialog box for creating a new user with input fields.
     * Allows input of user details such as username, avatar, name, email, role, and password.
     */
    private void openFieldBox() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Enter information");
        alert.setHeaderText("Please enter information:");
        alert.setContentText(null);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setStyle("-fx-background-color: #f9f9f9; -fx-border-radius: 10; -fx-background-radius: 10;");

        TextField usernameField = createStyledTextField("Username");
        TextField avatarField = createStyledTextField("Avatar");
        TextField firstnameField = createStyledTextField("Firstname");
        TextField lastnameField = createStyledTextField("Lastname");
        TextField emailField = createStyledTextField("Email");
        TextField roleField = createStyledTextField("Role");
        TextField passwordField = createStyledTextField("Password");

        Button addButton = new Button("Add");
        addButton.setStyle("-fx-font-size: 20px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        addButton.setOnMouseClicked(event -> {
            User user = new User();

            user.setUsername(usernameField.getText());
            user.setAvatar(avatarField.getText());
            user.setFirstName(firstnameField.getText());
            user.setLastName(lastnameField.getText());
            user.setEmail(emailField.getText());
            user.setRole(roleField.getText());
            user.setVerified(true);
            user.setPassword(passwordField.getText());
            sendPopUpMessage(user);
        });

        gridPane.add(createStyledLabel("Username:"), 0, 0);
        gridPane.add(usernameField, 1, 0);

        gridPane.add(createStyledLabel("Avatar:"), 0, 1);
        gridPane.add(avatarField, 1, 1);

        gridPane.add(createStyledLabel("Firstname:"), 0, 2);
        gridPane.add(firstnameField, 1, 2);

        gridPane.add(createStyledLabel("Lastname:"), 0, 3);
        gridPane.add(lastnameField, 1, 3);

        gridPane.add(createStyledLabel("Email:"), 0, 4);
        gridPane.add(emailField, 1, 4);

        gridPane.add(createStyledLabel("Role:"), 0, 5);
        gridPane.add(roleField, 1, 5);

        gridPane.add(createStyledLabel("Password:"), 0, 6);
        gridPane.add(passwordField, 1, 6);

        gridPane.add(addButton, 0, 7);

        gridPane.add(editStatus, 0, 8);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setContent(gridPane);

        dialogPane.setStyle("-fx-border-color: #d6d6d6; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 20;");

        alert.getButtonTypes().addAll(ButtonType.CANCEL);

        Optional<ButtonType> result = alert.showAndWait();
    }

    /**
     * Creates a styled text field with a prompt text and custom styling.
     *
     * @param promptText The text to display as a prompt in the text field
     * @return A styled TextField
     */
    private TextField createStyledTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setStyle("-fx-border-color: #d6d6d6; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 5;");
        return textField;
    }

    /**
     * Creates a styled text label with bold font and custom color.
     *
     * @param labelText The text to display in the label
     * @return A styled Text object
     */
    private Text createStyledLabel(String labelText) {
        Text text = new Text(labelText);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        text.setStyle("-fx-fill: #333;");
        return text;
    }

    /**
     * Sends a socket message to add a new user with the provided user details.
     *
     * @param user The User object containing details to be sent
     */
    public void sendPopUpMessage(User user) {
        JSONObject data = new JSONObject();
        data.put("username", user.getUsername());
        data.put("avatar", user.getAvatar());
        data.put("firstname", user.getFirstName());
        data.put("lastname", user.getLastName());
        data.put("email", user.getEmail());
        data.put("role", user.getRole());
        data.put("verified", true);
        data.put("password", user.getPassword());

        SocketService.getInstance().sendMessage("add_user", data);
    }

    /**
     * Sets up a socket listener for the response to adding a new user.
     * Updates the edit status label with success or error message.
     */
    public void getPopUpMessage() {
        SocketService.getInstance().onMessage("add_user_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String status = response.getString("status");
            if (status.equals("success")) {
                Platform.runLater(() -> {
                    editStatus.setText("");
                    editStatus.setStyle("-fx-text-fill: green;");
                    editStatus.setText(response.getString("message"));
                });
            }
            else {
                Platform.runLater(() -> {
                    editStatus.setText("");
                    editStatus.setStyle("-fx-text-fill: red;");
                    editStatus.setText(response.getString("message"));
                });
            }
        });
    }
}