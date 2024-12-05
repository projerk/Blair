package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Path;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;

public class FileHelper {

    private FileHelper() {
        // Prevent instantiation of utility class
        throw new AssertionError("This class cannot be instantiated");
    }

    /**
     * Loads an image from the specified path 
     *
     * @param path The relative path to the image file.
     * @return An {@link Image} object representing the loaded image.
     */
    public static Image getImage(String path) {
        String imagePath = getImagePath(path);
        File file = new File(imagePath);
        Image image = new Image(file.toURI().toString());
        return image;
    }

    /**
     * Constructs the absolute path to the image file.
     *
     * @param path The relative path to the image file.
     * @return The absolute path as a {@link String}.
     */
    private static String getImagePath(String path) {
        String imagePath = Paths.get("src", "main", "resources","view","images", path)
                .toAbsolutePath()
                .toString();
        return imagePath;
    }

    /**
     * Loads an FXML file from the specified path 
     *
     * @param path The relative path to the FXML file.
     * @return An {@link FXMLLoader} instance for the specified FXML file.
     * @throws IOException If there is an error loading the FXML file.
     */
    public static FXMLLoader getLoader(String path) throws IOException {
        String fxmlPath = getFXMLPath(path);
        FXMLLoader loader = new FXMLLoader(Paths.get(fxmlPath).toUri().toURL());
        return loader;
    }

    /**
     * Constructs the absolute path to the FXML file.
     *
     * @param path The relative path to the FXML file.
     * @return The absolute path as a {@link String}.
     */
    private static String getFXMLPath(String path) {
        Path fxmlPath = Paths.get("src", "main", "resources", "view", path);
        return fxmlPath.toAbsolutePath().toString();
    }
}
