package controller;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

import animation.ScaleEffect;
import api.GoogleBooksAPI;
import components.table.BookTableCell;
import components.table.BorrowTableCell;
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
import model.Borrow;

/**
 * Controller for managing book borrow actions in the admin interface.
 */
public class AdminBookBorrowManagerController {

    @FXML
    private VBox borrowContainer;

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
     * Initializes the controller, sets up button actions, and sends initial messages.
     */
    @FXML
    private void initialize() {
        initializeButton();
        getMessage();
        sendMessage();
    }

    /**
     * Initializes button actions including navigation, page refresh, and search.
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
     * Listens for borrow table data from the server and updates the UI with borrow entries.
     */
    private void getMessage() {
        client.onMessage("borrow_table_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String message = response.getString("status");
            if (message.equals("success")) {
                Platform.runLater(() -> {
                    borrowContainer.getChildren().clear();
                });
                List<Borrow> borrows = SocketUtils.parseBorrowTableCell(response.getJSONArray("borrow_list"));

                for (Borrow borrow : borrows) {
                    Platform.runLater(() -> {
                        borrowContainer.getChildren().add(new BorrowTableCell(borrow));
                    });
                }
            }
        });
    }

    /**
     * Sends a request to the server to fetch the borrow table for the current page.
     */
    private void sendMessage() {
        JSONObject data = new JSONObject();
        data.put("page", currentPage);
        client.sendMessage("get_borrow_table", data);
    }
}
