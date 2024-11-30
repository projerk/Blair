package controller;

import components.interfaces.Listener;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

public class BookController implements Listener {

    @FXML
    private Label monthYearLabel;

    @FXML
    private GridPane calendarGrid;

    private YearMonth currentYearMonth;

    @FXML
    public void initialize() {
        // Initialize current month and year
        calendarGrid.setPadding(new Insets(10, 10, 10, 10));
        setupGridPane();
        currentYearMonth = YearMonth.now();
    }

    private void setupGridPane() {
        for (int i = 0; i < 7; i++) { 
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPercentWidth(100.0 / 7); 
            colConstraints.setFillWidth(true);
            colConstraints.setHalignment(HPos.CENTER);
            calendarGrid.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i < 7; i++) { // Maximum of 6 rows for days and 1 header row
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / 7); 
            rowConstraints.setFillHeight(true); 
            calendarGrid.getRowConstraints().add(rowConstraints);
        }
    }

    public void openCanvas(int id) {

    }

    public void closeCanvas() {

    }
}