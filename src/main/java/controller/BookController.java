package controller;
import components.interfaces.Listener;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

public class BookController implements Listener {
    
    // @FXML
    // private Button prevButton;

    // @FXML
    // private Button nextButton;

    @FXML
    private Label monthYearLabel;

    @FXML
    private GridPane calendarGrid;

    private YearMonth currentYearMonth;

    @FXML
    public void initialize() {
        // Initialize current month and year
        calendarGrid.setPadding(new Insets(10,10 ,10,10));
        setupGridPane();
        currentYearMonth = YearMonth.now();
        refreshCalendar();
    }

    private void setupGridPane() {
        for (int i = 0; i < 7; i++) { 
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPercentWidth(100.0 / 7); 
            colConstraints.setFillWidth(true);
            colConstraints.setHalignment(HPos.CENTER);
            calendarGrid.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i < 7; i++) { //  Maximum of 6 rows for days and 1 header row
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / 7); 
            rowConstraints.setFillHeight(true); 
            calendarGrid.getRowConstraints().add(rowConstraints);
        }
    }

    private void refreshCalendar() {
        // Update month and year label
        monthYearLabel.setText(currentYearMonth.getMonth()
                .getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + currentYearMonth.getYear());

        // Clear existing calendar content
        calendarGrid.getChildren().clear();

        // Add day headers
        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < daysOfWeek.length; i++) {
            Label dayLabel = new Label(daysOfWeek[i]);
            dayLabel.setStyle("-fx-font-weight: bold;");
            calendarGrid.add(dayLabel, i, 0);
        }

        // Populate calendar days
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


    public void openCanvas(int id) {

    }

    public void closeCanvas() {

    }
}
