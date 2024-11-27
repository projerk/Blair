package components.abstracts;

import components.interfaces.IFlexContainer;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public abstract class FlexBox extends GridPane implements IFlexContainer {
    // private GridPane container;

    public FlexBox() {
        // container = new GridPane();
    }

    /**
     * Set the column percentage widths for the GridPane.
     *
     * @param percentages An array of doubles where each value represents
     *                    the percentage width for a column.
     */
    public void setColumnPercentages(double... percentages) {
        this.getColumnConstraints().clear();
        for (double percentage : percentages) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(percentage);
            this.getColumnConstraints().add(column);
        }
    }

    public void setBalanceColumnPercentage(int columnCount) {
        double each = (100 * 1.0) / columnCount;
        this.getColumnConstraints().clear();
        for (int i = 0; i < columnCount; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(each);
            this.getColumnConstraints().add(column);
        }
    }

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
     * Set the row percentage heights for the GridPane.
     *
     * @param percentages An array of doubles where each value represents
     *                    the percentage height for a row.
     */
    public void setRowPercentages(double... percentages) {
        this.getRowConstraints().clear();
        for (double percentage : percentages) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(percentage);
            this.getRowConstraints().add(row);
        }
    }

    // public GridPane getContainer() {
    //     return container;
    // }

    public int getRowSize() {
        return this.getRowConstraints().size();
    }

    public int getColumnSize() {
        return this.getColumnConstraints().size();
    }

    /**
     * Adds a Node to the specified cell in the GridPane.
     *
     * @param node the Node to be added
     * @param x    the column index
     * @param y    the row index
     */
    public void addNode(Node node, int columnIndex, int rowIndex) {
        this.add(node, columnIndex, rowIndex);
    }

    /**
     * Deletes the Node at the specified cell in the GridPane.
     *
     * @param x the column index
     * @param y the row index
     */
    public void deleteNode(int columnIndex, int rowIndex) {
        Node node = getNode(columnIndex, rowIndex);
        if (node != null) {
            this.getChildren().remove(node);
        }
    }

    /**
     * Retrieves the Node at the specified cell in the GridPane.
     *
     * @param x the column index
     * @param y the row index
     * @return the Node at the specified cell, or null if no Node exists there
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
     * @param columnIndex the index of the column
     * @param priority    the growth priority (e.g., Priority.ALWAYS, Priority.SOMETIMES)
     */
    public void setColumnHgrow(int columnIndex, Priority priority) {
        if (columnIndex >= 0 && columnIndex < this.getColumnConstraints().size()) {
            this.getColumnConstraints().get(columnIndex).setHgrow(priority);
        }
    }

    /**
     * Sets the vertical growth priority for a specified row.
     *
     * @param rowIndex the index of the row
     * @param priority the growth priority (e.g., Priority.ALWAYS, Priority.SOMETIMES)
     */
    public void setRowVgrow(int rowIndex, Priority priority) {
        if (rowIndex >= 0 && rowIndex < this.getRowConstraints().size()) {
            this.getRowConstraints().get(rowIndex).setVgrow(priority);
        }
    }

    /**
     * Sets the horizontal growth priority for all columns.
     *
     * @param priority the growth priority (e.g., Priority.ALWAYS, Priority.SOMETIMES)
     */
    public void setAllColumnsHgrow(Priority priority) {
        for (ColumnConstraints column : this.getColumnConstraints()) {
            column.setHgrow(priority);
        }
    }

    /**
     * Sets the vertical growth priority for all rows.
     *
     * @param priority the growth priority (e.g., Priority.ALWAYS, Priority.SOMETIMES)
     */
    public void setAllRowsVgrow(Priority priority) {
        for (RowConstraints row : this.getRowConstraints()) {
            row.setVgrow(priority);
        }
    }
}
