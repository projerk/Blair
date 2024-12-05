package components.button;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import socket.SocketService;
import utils.FileHelper;
import utils.Hasher;
import utils.PoolingToolkit;
import utils.QRCodeGenerator;

import org.json.JSONObject;

import utils.StringUtils;

import animation.ScaleEffect;
import app.AppState;
import components.interfaces.MouseHandler;
import components.interfaces.Receiver;
import components.interfaces.Sender;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * A custom JavaFX Button for generating and displaying QR codes for book borrowing.
 *
 * This button interacts with a socket service to retrieve book borrowing status
 * and generates a QR code for the transaction if successful.
 *
 */
public class QRCodeButton extends Button implements Sender, Receiver, MouseHandler {
    /** Static image resource for the QR code button. */
    private static Image image = FileHelper.getImage("qr-code.png");

    /** Alert dialog for successful QR code generation. */
    private static Alert alert = new Alert(Alert.AlertType.NONE);

    /** Alert dialog for warning when no book is selected for borrowing. */
    private static Alert redalert = new Alert(Alert.AlertType.WARNING);

    /** Button type for closing alerts. */
    private static ButtonType closeButton = new ButtonType("Close");

    static {
        alert.setTitle("QRCode");
        alert.setHeaderText("Transaction Code: " + Hasher.generateTransactionCode(AppState.getInstance().getUser().getID(), AppState.getInstance().getCurrentViewBookID()));
        alert.getButtonTypes().add(closeButton);
        redalert.setTitle("Warning");
        redalert.setHeaderText("Please borrow a book to get QRCode");
    }

    /**
     * Constructs a new QRCodeButton with default styling and image.
     *
     * Initializes message handling, mouse events, and sets a transparent background
     * with a QR code icon.
     */
    public QRCodeButton() {
        getMessage();
        handleMouseEvent();

        this.setStyle("-fx-background-color: transparent;");
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(30);
        this.setGraphic(imageView);
    }

    /**
     * Handles mouse interaction events for the button.
     *
     * Provides visual feedback through scaling effects when the mouse
     * enters or exits the button. Triggers message sending on click.
     */
    @Override
    public void handleMouseEvent() {
        setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(this, 0.1, 1.1, 1.1);
        });

        setOnMouseExited(event -> {
            ScaleEffect.scaleTo(this, 0.1, 1.0, 1.0);
        });

        setOnMouseClicked(event -> {
            sendMessage();
        });
    }

    /**
     * Sends a request to check the borrowing state of a book.
     *
     * Constructs a JSON object with the current user's ID and the viewed book's ID,
     * and sends it via socket service to retrieve borrowing status.
     */
    @Override
    public void sendMessage() {
        JSONObject data = new JSONObject();

        data.put("book_id", AppState.getInstance().getCurrentViewBookID());
        data.put("user_id", AppState.getInstance().getUser().getID());
        SocketService.getInstance().sendMessage("get_borrow_state", data);
    }

    /**
     * Listens for and handles the response from the borrow state request.
     *
     * If the borrow state is successful, generates and displays a QR code.
     * If unsuccessful, shows a warning alert.
     *
     * Runs UI updates on the JavaFX Application Thread to ensure thread safety.
     */
    @Override
    public void getMessage() {
        SocketService.getInstance().onMessage("borrow_state_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String status = response.getString("status");
            if (status.equals("success")) {
                Platform.runLater(() -> {
                    ImageView imageView = new ImageView(QRCodeGenerator.getQRCodeImage(StringUtils.getQRCodeURL(response.getInt("code"))));
                    imageView.setFitWidth(300);
                    imageView.setPreserveRatio(true);
                    alert.getDialogPane().setContent(imageView);

                    alert.show();
                });
            }
            else {
                Platform.runLater(() -> {
                    redalert.show();
                });
            }
        });
    }
}