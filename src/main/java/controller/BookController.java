package controller;
import components.container.BookFrame;
import components.interfaces.Listener;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Book;
import socket.SocketService;
import utils.Constants;
import utils.FileHelper;
import utils.SocketUtils;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import app.AppState;

public class BookController implements Listener {

    SocketService client = SocketService.getInstance();

    @FXML
    private Label monthYearLabel;

    @FXML
    private StackPane container;

    @FXML
    private GridPane calendarGrid;

    private YearMonth currentYearMonth;

    private VBox detailBook;

    @FXML
    private VBox bookmark;

    @FXML
    private VBox borrow;

    /**
     * Initializes the controller by setting up calendar, loading borrowed and bookmarked books.
     */
    @FXML
    public void initialize() {
        loadBorrowBook();
        loadBookmarkBook();

        calendarGrid.setPadding(new Insets(10,10 ,10,10));
        setupGridPane();
        currentYearMonth = YearMonth.now();

        refreshCalendar();
    }

    /**
     * Configures column and row constraints for the calendar grid.
     */
    private void setupGridPane() {
        for (int i = 0; i < 7; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPercentWidth(100.0 / 7);
            colConstraints.setFillWidth(true);
            colConstraints.setHalignment(HPos.CENTER);
            calendarGrid.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i < 7; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / 7);
            rowConstraints.setFillHeight(true);
            calendarGrid.getRowConstraints().add(rowConstraints);
        }
    }

    /**
     * Refreshes the calendar display with current month and days.
     */
    private void refreshCalendar() {
        monthYearLabel.setText(currentYearMonth.getMonth()
                .getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + currentYearMonth.getYear());

        calendarGrid.getChildren().clear();

        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < daysOfWeek.length; i++) {
            Label dayLabel = new Label(daysOfWeek[i]);
            dayLabel.setStyle("-fx-font-weight: bold;");
            calendarGrid.add(dayLabel, i, 0);
        }

        LocalDate firstOfMonth = currentYearMonth.atDay(1);
        int totalDays = currentYearMonth.lengthOfMonth();
        int startDayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7;

        int row = 1;
        int col = startDayOfWeek;

        for (int day = 1; day <= totalDays; day++) {
            int currentDay = day;
            VBox dayButton = new VBox();
            dayButton.setAlignment(Pos.CENTER);
            dayButton.setStyle("-fx-border-width: 1px; -fx-background-color: #fafafa; -fx-border-color: #d9d9d9; -fx-background-radius: 5px; -fx-border-radius: 5px;");
            dayButton.setPadding(new Insets(10, 0, 10, 0));
            Label dayText = new Label(String.valueOf(day));
            dayButton.getChildren().add(dayText);

            calendarGrid.add(dayButton, col, row);

            col++;
            if (col > 6) {
                col = 0;
                row++;
            }
        }
    }

    /**
     * Loads and displays the user's borrowed books.
     * Retrieves book list from server and populates borrow VBox.
     */
    public void loadBorrowBook() {
        client.onMessage("borrow_book_list_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String status = response.getString("status");

            if (status.equals("success")) {
                JSONArray books = response.getJSONArray("books");
                ArrayList<Book> bookList = SocketUtils.createDisplayBook(books);
                BookFrame bookFrame = new BookFrame(bookList);
                bookFrame.setAllRowsVgrow(Priority.NEVER);
                bookFrame.setAllColumnsHgrow(Priority.NEVER);
                Platform.runLater(() -> {
                    borrow.getChildren().add(bookFrame);
                });
            }
            else {
                System.out.println("Error");
            }
        });

        JSONObject object = new JSONObject();
        object.put("user_id", AppState.getInstance().getUser().getID());
        client.sendMessage("get_borrow_book_list", object);
    }

    /**
     * Loads and displays the user's bookmarked books.
     * Retrieves book list from server and populates bookmark VBox.
     */
    public void loadBookmarkBook() {
        client.onMessage("bookmark_list_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String status = response.getString("status");

            if (status.equals("success")) {
                JSONArray books = response.getJSONArray("books");
                ArrayList<Book> bookList = SocketUtils.createDisplayBook(books);
                BookFrame bookFrame = new BookFrame(bookList);
                bookFrame.setAllRowsVgrow(Priority.NEVER);
                bookFrame.setAllColumnsHgrow(Priority.NEVER);
                Platform.runLater(() -> {
                    bookmark.getChildren().add(bookFrame);
                });
            }
            else {
                System.out.println("Error");
            }
        });

        JSONObject object = new JSONObject();
        object.put("user_id", AppState.getInstance().getUser().getID());
        client.sendMessage("get_bookmark_list", object);
    }

    /**
     * Opens a canvas view for a specific book.
     *
     * @param id The ID of the book to view
     */
    public void openCanvas(int id) {
        try {
            AppState.getInstance().setCurrentViewBookID(id);
            FXMLLoader loader = FileHelper.getLoader(Constants.CANVAS_VIEW_FILE);
            detailBook = loader.load();
            container.getChildren().add(detailBook);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the current canvas view.
     */
    public void closeCanvas() {
        container.getChildren().remove(container.getChildren().size() - 1);
    }
}