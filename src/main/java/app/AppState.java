package app;

import components.interfaces.Listener;
import model.User;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import javafx.scene.image.Image;

public class AppState {
    private static AppState instance = new AppState(); // Singleton instance
    private User currentUser; // current user, save after login success.
    private Listener listener; // Listener, this is used to communicate between Controller.
    private int currentViewBookID; // current book id at CanvasView
    private Map<String, Image> pooling = new ConcurrentHashMap<>();
    // image cache, store java.scene.image Object to accelerate and reduce memory use.

    private AppState() { 
        // private constructor prevent initialize.
    }

    public static AppState getInstance() {
        return instance;
    }

    public Map<String, Image> getPooling() {
        return pooling;
    }

    public void setPooling(Map<String, Image> pooling) {
        this.pooling = pooling;
    }
    
    public void setUser(User user) {
        this.currentUser = user;
    }

    public User getUser() {
        return currentUser;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public Listener getListener() {
        return listener;
    }

    public int getCurrentViewBookID() {
        return currentViewBookID;
    }

    public void setCurrentViewBookID(int currentViewBookID) {
        this.currentViewBookID = currentViewBookID;
    }
}
