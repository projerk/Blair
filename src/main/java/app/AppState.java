package app;

import components.interfaces.Listener;
import model.User;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import javafx.scene.image.Image;

public class AppState {
    private static AppState instance = new AppState();
    private User currentUser;
    private Listener listener;
    private int currentViewBookID;
    private Map<String, Image> pooling = new ConcurrentHashMap<>();

    private AppState() { }

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
