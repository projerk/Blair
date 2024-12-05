package components.abstracts;

import components.interfaces.IFlexContainer;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

/**
 * Abstract class that extends GridPane to provide additional functionality for flexible layouts.
 * This class implements the IFlexContainer interface and adds methods for managing column and row constraints,
 * as well as dynamically adding, removing, and retrieving nodes within the grid.
 */
public abstract class FlexBox extends GridPane implements IFlexContainer {

    // Constructor to initialize the FlexBox.
    public FlexBox() {
        // Initialize the grid if necessary.
    }

    /**
     * Sets the percentage width for each column in the GridPane.
     *
     * @param percentages An array of doubles representing the percentage width for each column.
     */
    public void setColumnPercentages(double... percentages) {
        this.getColumnConstraints().clear();
        for (double percentage : percentages) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(percentage);
            this.getColumnConstraints().add(column);
        }
    }

    /**
     * Sets equal percentage width for all columns in the GridPane.
     *
     * @param columnCount The number of columns in the GridPane.
     */
    public void setBalanceColumnPercentage(int columnCount) {
        double each = (100 * 1.0) / columnCount;
        this.getColumnConstraints().clear();
        for (int i = 0; i < columnCount; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(each);
            this.getColumnConstraints().add(column);
        }
    }

    /**
     * Sets equal percentage height for all rows in the GridPane.
     *
     * @param rowCount The number of rows in the GridPane.
     */
    public void setBalanceRowPercentage(int rowCount) {
        double each = (100 * 1.0) / rowCount;
        this.getRowConstraints().clear();
        for (int i = 0; i < rowCount; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(each);
            this.getRowConstraints().add(row);
        }
    }

    /**
     * Sets the percentage height for each row in the GridPane.
     *
     * @param percentages An array of doubles representing the percentage height for each row.
     */
    public void setRowPercentages(double... percentages) {
        this.getRowConstraints().clear();
        for (double percentage : percentages) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(percentage);
            this.getRowConstraints().add(row);
        }
    }

    /**
     * Gets the total number of rows in the GridPane based on the row constraints.
     *
     * @return The number of rows in the GridPane.
     */
    public int getRowSize() {
        return this.getRowConstraints().size();
    }

    /**
     * Gets the total number of columns in the GridPane based on the column constraints.
     *
     * @return The number of columns in the GridPane.
     */
    public int getColumnSize() {
        return this.getColumnConstraints().size();
    }

    /**
     * Adds a node to the specified cell in the GridPane.
     *
     * @param node The node to be added to the GridPane.
     * @param columnIndex The column index where the node should be placed.
     * @param rowIndex The row index where the node should be placed.
     */
    public void addNode(Node node, int columnIndex, int rowIndex) {
        this.add(node, columnIndex, rowIndex);
    }

    /**
     * Deletes the node at the specified cell in the GridPane.
     *
     * @param columnIndex The column index of the node to be deleted.
     * @param rowIndex The row index of the node to be deleted.
     */
    public void deleteNode(int columnIndex, int rowIndex) {
        Node node = getNode(columnIndex, rowIndex);
        if (node != null) {
            this.getChildren().remove(node);
        }
    }

    /**
     * Retrieves the node at the specified cell in the GridPane.
     *
     * @param columnIndex The column index of the cell to check.
     * @param rowIndex The row index of the cell to check.
     * @return The node at the specified cell, or null if no node exists.
     */
    public Node getNode(int columnIndex, int rowIndex) {
        for (Node node : this.getChildren()) {
            if (GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == columnIndex &&
                    GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == rowIndex) {
                return node;
            }
        }
        return null;
    }

    /**
     * Sets the horizontal growth priority for a specified column.
     *
     * @param columnIndex The index of the column.
     * @param priority The horizontal growth priority (e.g., Priority.ALWAYS, Priority.SOMETIMES).
     */
    public void setColumnHgrow(int columnIndex, Priority priority) {
        if (columnIndex >= 0 && columnIndex < this.getColumnConstraints().size()) {
            this.getColumnConstraints().get(columnIndex).setHgrow(priority);
        }
    }

    /**
     * Sets the vertical growth priority for a specified row.
     *
     * @param rowIndex The index of the row.
     * @param priority The vertical growth priority (e.g., Priority.ALWAYS, Priority.SOMETIMES).
     */
    public void setRowVgrow(int rowIndex, Priority priority) {
        if (rowIndex >= 0 && rowIndex < this.getRowConstraints().size()) {
            this.getRowConstraints().get(rowIndex).setVgrow(priority);
        }
    }

    /**
     * Sets the horizontal growth priority for all columns.
     *
     * @param priority The horizontal growth priority (e.g., Priority.ALWAYS, Priority.SOMETIMES).
     */
    public void setAllColumnsHgrow(Priority priority) {
        for (ColumnConstraints column : this.getColumnConstraints()) {
            column.setHgrow(priority);
        }
    }

    /**
     * Sets the vertical growth priority for all rows.
     *
     * @param priority The vertical growth priority (e.g., Priority.ALWAYS, Priority.SOMETIMES).
     */
    public void setAllRowsVgrow(Priority priority) {
        for (RowConstraints row : this.getRowConstraints()) {
            row.setVgrow(priority);
        }
    }
}
