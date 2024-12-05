package app;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import components.interfaces.Listener;
import javafx.scene.image.Image;
import model.User;

/**
 * Singleton class to manage the application's state.
 * <p>
 * The AppState class holds the current user, listener, the ID of the book currently being viewed,
 * and a cache (pooling) of images. It provides access to and modification of these values
 * throughout the application's lifecycle.
 * </p>
 */
public class AppState {

    /**
     * The single instance of the AppState class.
     */
    private static AppState instance = new AppState();

    /**
     * The current user of the application.
     */
    private User currentUser;

    /**
     * The listener for application events.
     */
    private Listener listener;

    /**
     * The ID of the book currently being viewed in the application.
     */
    private int currentViewBookID;

    /**
     * A thread-safe map for caching images, keyed by a string identifier.
     */
    private Map<String, Image> pooling = new ConcurrentHashMap<>();

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private AppState() { }

    /**
     * Returns the single instance of the AppState class.
     *
     * @return The AppState instance.
     */
    public static AppState getInstance() {
        return instance;
    }

    /**
     * Sets the current user of the application.
     *
     * @param user The user to set as the current user.
     */
    public void setUser(User user) {
        this.currentUser = user;
    }

    /**
     * Returns the current user of the application.
     *
     * @return The current user.
     */
    public User getUser() {
        return currentUser;
    }

    /**
     * Sets the listener for application events.
     *
     * @param listener The listener to set.
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Returns the current listener for application events.
     *
     * @return The current listener.
     */
    public Listener getListener() {
        return listener;
    }

    /**
     * Returns the ID of the book currently being viewed.
     *
     * @return The current book ID.
     */
    public int getCurrentViewBookID() {
        return currentViewBookID;
    }

    /**
     * Sets the ID of the book to be viewed in the application.
     *
     * @param currentViewBookID The book ID to set.
     */
    public void setCurrentViewBookID(int currentViewBookID) {
        this.currentViewBookID = currentViewBookID;
    }

    /**
     * Returns the pooling (cache) of images.
     *
     * @return The map of cached images.
     */
    public Map<String, Image> getPooling() {
        return pooling;
    }

    /**
     * Sets the pooling (cache) of images with a new map.
     *
     * @param pooling The new map of cached images.
     */
    public void setPooling(Map<String, Image> pooling) {
        this.pooling = pooling;
    }
}
