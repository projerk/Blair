package utils;

import app.AppState;
import javafx.scene.image.Image;

import java.util.concurrent.ConcurrentHashMap;

/**
 * A utility class for managing an image cache in the application.
 * This class provides a method to retrieve images from a cache,
 * ensuring that images are loaded only once and reused thereafter.
 */
public class PoolingToolkit {
    private static AppState appState = AppState.getInstance();

    public static Image getImage(String url) {
        if (appState.getPooling() == null) {
            appState.setPooling(new ConcurrentHashMap<>());
        }

        synchronized (appState.getPooling()) {
            if (appState.getPooling().containsKey(url)) {
                return appState.getPooling().get(url);
            } else {
                try {
                    Image image = new Image(url, true); 
                    appState.getPooling().put(url, image);
                    return image;
                } catch (Exception e) {
                    System.err.println("Error loading image from URL: " + url);
                    return null;
                }
            }
        }
    }
}
