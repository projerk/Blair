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


public class QRCodeButton extends Button implements Sender, Receiver, MouseHandler {
    private static Image image = FileHelper.getImage("qr-code.png");
    private static Alert alert = new Alert(Alert.AlertType.NONE);
    private static Alert redalert = new Alert(Alert.AlertType.WARNING);
    private static ButtonType closeButton = new ButtonType("Close");


    static {
        alert.setTitle("QRCode");
        alert.setHeaderText("Transaction Code: " + Hasher.generateTransactionCode(AppState.getInstance().getUser().getID(), AppState.getInstance().getCurrentViewBookID()));
        alert.getButtonTypes().add(closeButton);
        redalert.setTitle("Warning");
        redalert.setHeaderText("Please borrow a book to get QRCode");
    }


    public QRCodeButton() {
        getMessage();
        handleMouseEvent();


        this.setStyle("-fx-background-color: transparent;");
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(30);
        this.setGraphic(imageView);

    }

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

    public void sendMessage() {
        JSONObject data = new JSONObject();

        data.put("book_id", AppState.getInstance().getCurrentViewBookID());
        data.put("user_id", AppState.getInstance().getUser().getID());
        SocketService.getInstance().sendMessage("get_borrow_state", data);
    }

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
