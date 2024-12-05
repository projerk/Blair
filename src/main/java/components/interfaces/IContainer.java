package components.interfaces;

import javafx.scene.Node;

/**
 * Defines a contract for containers that can add, delete, and retrieve nodes.
 */
public interface IContainer {
    /**
     * Adds a node to the container.
     *
     * @param node The node to be added to the container
     */
    void addNode(Node node);

    /**
     * Removes a node from the container at the specified index.
     *
     * @param idx The index of the node to be deleted
     */
    void deleteNode(int idx);

    /**
     * Retrieves a node from the container at the specified index.
     *
     * @param idx The index of the node to retrieve
     * @return The node at the specified index
     */
    Node getNode(int idx);
}