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

public class DeleteButton extends Button implements Sender, MouseHandler {
    private static SocketService client = SocketService.getInstance();
    private static Image image = FileHelper.getImage("delete.png");
    private int bookID;

    public DeleteButton() {

    }

    public DeleteButton(int bookID) {   
        setButtonStyle();
        handleMouseEvent();
        this.bookID = bookID;
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
        client.sendMessage("delete_book", data);
    }

    private void openAlertBox() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("ALERT");
        alert.setHeaderText("Are you sure to do this ?");
    
        ButtonType confirmButton = new ButtonType("Confirm");
        ButtonType cancelButton = new ButtonType("Cancel");
    
        alert.getButtonTypes().setAll(confirmButton, cancelButton);
    
        Optional<ButtonType> result = alert.showAndWait();
    
        if (result.isPresent() && result.get() == confirmButton) {
            sendMessage();
        } else {

        }
    }
}
