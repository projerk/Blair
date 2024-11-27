package components.interfaces;

import javafx.scene.Node;

public interface IFlexContainer {
    void addNode(Node node, int x, int y);
    void deleteNode(int x, int y);
    Node getNode(int x, int y);
}
