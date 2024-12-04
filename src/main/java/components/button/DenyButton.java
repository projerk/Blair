package components.button;

import animation.ScaleEffect;
import components.interfaces.MouseHandler;
import components.interfaces.Sender;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONObject;
import socket.SocketService;
import utils.FileHelper;
import utils.ImageUtils;

import java.util.Optional;

public class DenyButton extends Button implements Sender, MouseHandler {
    private static SocketService client = SocketService.getInstance();
    private static Image image = FileHelper.getImage("close.png");
    private int bookID;
    private int userID;

    public DenyButton() {

    }

    public DenyButton(int bookID, int userID) {   
        setButtonStyle();
        handleMouseEvent();
        this.bookID = bookID;
        this.userID = userID;
    }

    private void setButtonStyle() {
        handleMouseEvent();
        setStyle("-fx-background-color: transparent;");
        
        ImageView imageView = ImageUtils.getImageView(image);
        imageView.setFitWidth(30);
        setGraphic(imageView);
    }

    public void handleMouseEvent() {
        setOnMouseClicked(event -> {
            sendMessage();
            openAlertBox();
        });

        setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(this, 0.1, 1.1, 1.1);
        });

        setOnMouseExited(event -> {
            ScaleEffect.scaleTo(this, 0.1, 1.0, 1.0);
        });
    }

    // public void getMessage() {

    // }

    public void sendMessage() {
        JSONObject data = new JSONObject();
        data.put("book_id", bookID);
        data.put("user_id", userID);
        client.sendMessage("deny_book", data);
    }

    private void openAlertBox() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Success");
    
        ButtonType confirmButton = new ButtonType("OK");
        ButtonType cancelButton = new ButtonType("Cancel");
    
        alert.getButtonTypes().setAll(confirmButton, cancelButton);
    
        Optional<ButtonType> result = alert.showAndWait();
    
        if (result.isPresent() && result.get() == confirmButton) {
            // sendMessage();
        } else {

        }
    }
}
