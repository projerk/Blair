package components.abstracts;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * Abstract class representing a cell in a table, extending FlexBox to support flexible layout.
 * This class allows setting column and row percentages and loading node styles.
 */
public abstract class TableCell extends FlexBox {

    /**
     * Constructor for creating a TableCell with specified column percentage widths.
     * Sets row percentages to 100%.
     *
     * @param percentage An array of doubles representing the percentage width for each column.
     */
    public TableCell(double... percentage) {
        this.setColumnPercentages(percentage);
        this.setRowPercentages(100);
        loadNode();
        // loadCellData();
    }

    /**
     * Loads all child nodes of the TableCell and applies styles to each node.
     */
    public void loadNode() {
        for (Node node : this.getChildren()) {
            loadStyle(node);
        }
    }

    /**
     * Applies border style to the specified node.
     * The style adds a black border around the node.
     *
     * @param node The node to which the style will be applied.
     */
    public void loadStyle(Node node) {
        node.setStyle("-fx-border-color: black black black black; \r\n" + //
                "    -fx-border-width: 1px 1px 1px 1px;");
    }

    /**
     * Abstract method that must be implemented by subclasses to load data into the cell.
     */
    public abstract void loadCellData();
}
