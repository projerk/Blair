package components.abstracts;

import javafx.scene.control.Button;
import socket.SocketService;
public abstract class FlexButton extends Button {
    protected SocketService client = SocketService.getInstance();

    public FlexButton(boolean active) {
        this.setStyle("-fx-font-size: 18; -fx-text-fill: white; -fx-font-family: 'Accent Graphic W00 Medium'; -fx-background-color: black; -fx-background-radius: 20px;");
        // this.setDisable(!active);
    }
}
