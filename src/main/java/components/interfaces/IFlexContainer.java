

package components.interfaces;

import javafx.scene.Node;

/**
 * Defines a contract for flexible containers that can add, delete, and retrieve nodes 
 * using two-dimensional coordinates.
 */
public interface IFlexContainer {
    /**
     * Adds a node to the container at specified x and y coordinates.
     *
     * @param node The node to be added to the container
     * @param x The x-coordinate for node placement
     * @param y The y-coordinate for node placement
     */
    void addNode(Node node, int x, int y);

    /**
     * Removes a node from the container at the specified x and y coordinates.
     *
     * @param x The x-coordinate of the node to be deleted
     * @param y The y-coordinate of the node to be deleted
     */
    void deleteNode(int x, int y);

    /**
     * Retrieves a node from the container at the specified x and y coordinates.
     *
     * @param x The x-coordinate of the node to retrieve
     * @param y The y-coordinate of the node to retrieve
     * @return The node at the specified coordinates
     */
    Node getNode(int x, int y);
}