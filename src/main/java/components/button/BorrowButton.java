package components.button;

import components.abstracts.FlexButton;
import animation.ScaleEffect;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import org.json.JSONObject;
import app.AppState;


public class BorrowButton extends FlexButton {
    private boolean borrow;
    private boolean active;

    public boolean isBorrow() {
        return this.borrow;
    }

    public void setBorrow(boolean borrow) {
        this.borrow = borrow;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BorrowButton(boolean active, boolean borrow) {
        super(active);
        getMessage();
        setActive(active);
        setBorrow(borrow);
        this.setText("Borrow");



        if (!borrow && !active) {
            setDisable(true);
            this.setText("Unavailable");
        }
        else if ((borrow && active) || (borrow && !active)) {
            setText("Return");
        }

        setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(this, 0.2, 1.2, 1.2);
        });

        setOnMouseExited(event -> {
            ScaleEffect.scaleTo(this, 0.2, 1.0, 1.0);
        });

        setOnMouseClicked(event -> {
            Platform.runLater(() -> {
                setText("Requesting");
                setDisable(true);
                sendMessage();
            });
        });
    }

    public void sendMessage() {
        JSONObject data = new JSONObject();
        String status;
        if (borrow) {
            status = "return";
        }
        else {
            status = "borrow";
        }
        data.put("status", status);
        data.put("book_id", AppState.getInstance().getCurrentViewBookID());
        data.put("user_id", AppState.getInstance().getUser().getID());

        client.sendMessage("borrow_book", data);
        borrow = !borrow;
    }

    public void getMessage() {
        client.onMessage("borrow_book_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String status = response.getString("status");
            if (status.equals("borrow_success")) {
                Platform.runLater(() -> {
                    setText("Return");
                    setDisable(false);
                });
            }
            else if (status.equals("return_success")) {
                Platform.runLater(() -> {
                    setText("Borrow");
                    setDisable(false);
                });
            }
        });
    }

}
