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

    public static Image getImage(String path) {
        String imagePath = getImagePath(path);
        File file = new File(imagePath);
        Image image = new Image(file.toURI().toString());
        return image;
    }

    private static String getImagePath(String path) {
        String imagePath = Paths.get("src", "main", "resources","view","images", path)
                .toAbsolutePath()
                .toString();

        return imagePath;
    }

    public static FXMLLoader getLoader(String path) throws IOException {
        String fxmlPath = getFXMLPath(path);
        FXMLLoader loader = new FXMLLoader(Paths.get(fxmlPath).toUri().toURL());
        return loader;
    }

    private static String getFXMLPath(String path) {
        Path fxmlPath = Paths.get("src", "main", "resources", "view", path);
        return fxmlPath.toAbsolutePath().toString();
    }
}
