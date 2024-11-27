package components.interfaces;

import javafx.scene.Node;

public interface IContainer {
    void addNode(Node node);
    void deleteNode(int idx);
    Node getNode(int idx);
}
