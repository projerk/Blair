package components.abstracts;

import javafx.scene.Node;

public abstract class TableCell extends FlexBox {
    public TableCell(double... percentage) {
        this.setColumnPercentages(percentage);
        this.setRowPercentages(100);
        loadNode();
        // loadCellData();
    }

    public void loadNode() {
        for (Node node : this.getChildren()) {
            loadStyle(node);
        }
    }

    public void loadStyle(Node node) {
        node.setStyle("-fx-border-color: black black black black; \r\n" + //
                        "    -fx-border-width: 1px 1px 1px 1px;");
    }

    public abstract void loadCellData();
}
