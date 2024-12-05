package controller;

import animation.ScaleEffect;
import components.table.ReturnTableCell;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Borrow;
import org.json.JSONObject;
import socket.SocketService;
import utils.SocketUtils;

import java.util.List;

/**
 * Controller class for managing book returns in the admin interface.
 * Handles return table pagination, message handling, and socket communication.
 */
public class AdminBookReturnManagerController {
    @FXML
    private VBox returnContainer;

    @FXML
    private Button previousPage;

    @FXML
    private Button nextPage;

    @FXML
    private Button refreshPage;

    /**
     * Current page number for pagination.
     */
    private int currentPage = 1;

    /**
     * Socket service instance for communication.
     */
    private SocketService client = SocketService.getInstance();

    @FXML
    private TextField searchfield;

    @FXML
    private Button search;

    @FXML
    private ComboBox<String> searchOption;

    /**
     * Initializes the controller by setting up buttons,
     * message listeners, and initial data retrieval.
     * This method is automatically called by JavaFX after FXML loading.
     */
    @FXML
    private void initialize() {
        initializeButton();
        getMessage();
        sendMessage();
    }

    /**
     * Configures navigation and interaction buttons with event listeners.
     * Sets up mouse enter/exit scaling effects for previous, next, and refresh buttons.
     */
    private void initializeButton() {
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
     * Handles incoming socket messages for return table data.
     * Processes successful responses by clearing and populating the return container
     * with retrieved borrow records.
     */
    private void getMessage() {
        client.onMessage("return_table_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String message = response.getString("status");
            if (message.equals("success")) {
                Platform.runLater(() -> {
                    returnContainer.getChildren().clear();
                });
                List<Borrow> borrows = SocketUtils.parseBorrowTableCell(response.getJSONArray("return_list"));

                for (Borrow borrow : borrows) {
                    Platform.runLater(() -> {
                        returnContainer.getChildren().add(new ReturnTableCell(borrow));
                    });
                }
            }
            else {

            }
        });
    }

    /**
     * Sends a request to retrieve return table data for the current page.
     * Constructs a JSON object with the current page number and sends it via socket.
     */
    private void sendMessage() {
        JSONObject data = new JSONObject();
        data.put("page", currentPage);
        client.sendMessage("get_return_table", data);
    }
}