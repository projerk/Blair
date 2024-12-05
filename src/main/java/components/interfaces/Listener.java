
package components.interfaces;

/**
 * Defines a contract for handling canvas opening and closing operations.
 */
public interface Listener {
    /**
     * Opens a canvas for a specific identifier.
     *
     * @param id The identifier of the canvas to be opened
     */
    void openCanvas(int id);

    /**
     * Closes the current canvas.
     */
    void closeCanvas();
}
