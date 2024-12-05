package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import javafx.stage.Screen;
import java.util.concurrent.ExecutorService;
import javafx.geometry.Rectangle2D;
import socket.SocketService;
import utils.Constants;
import javafx.scene.text.Font;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import utils.Constants;
import socket.SocketService;

/**
 * The main application class for the Projerk application.
 * <p>
 * This class is responsible for initializing the JavaFX application,
 * connecting to the socket service, preloading the custom font, and managing scene transitions.
 * It follows the Singleton pattern to ensure a single instance of the application.
 * </p>
 */
public class Projerk extends Application {
    /**
     * Executor service for managing background tasks.
     */
    private ExecutorService executor;

    /**
     * The single instance of the Projerk application (Singleton pattern).
     */
    private static Projerk instance;

    /**
     * The primary stage of the application.
     */
    private Stage primaryStage;

    /**
     * The screen used for obtaining display dimensions.
     */
    private Screen screen = Screen.getPrimary();

    /**
     * The width of the screen.
     */
    private static double screenWidth;

    /**
     * The height of the screen.
     */
    private static double screenHeight;

    /**
     * The current scene of the application.
     */
    private Scene scene;

    /**
     * Rectangle representing the bounds of the screen.
     */
    Rectangle2D bounds = screen.getVisualBounds();

    /**
     * Default constructor for Projerk.
     */
    public Projerk() {}

    /**
     * Gets the instance of the Projerk application (Singleton pattern).
     *
     * @return The Projerk instance.
     */
    public static Projerk getInstance() {
        return instance;
    }

    /**
     * Starts the JavaFX application by setting up the socket connection,
     * preloading the font, and loading the initial scene.
     *
     * @param primaryStage The primary stage for the JavaFX application.
     * @throws URISyntaxException If the URI for loading resources is invalid.
     */
    @Override
    public void start(Stage primaryStage) throws URISyntaxException {
        SocketService socketService = SocketService.getInstance(Constants.URL);
        socketService.connect();
        fontPreload();
        instance = this;
        screenWidth = bounds.getWidth();
        screenHeight = bounds.getHeight();
        System.out.println(screenWidth);
        System.out.println(screenHeight);
        this.primaryStage = primaryStage;
        loadScene("LoginView.fxml", screenWidth, screenHeight);
        primaryStage.show();

        // Load and apply custom CSS
        String cssPath = Paths.get("src", "main", "resources", "view", "css", "AutoCompleteSearch.css").toAbsolutePath().toString();
        scene.getStylesheets().add("file:" + cssPath);
    }

    /**
     * Changes the current scene of the application to a new FXML view.
     *
     * @param fxmlFile The path to the FXML file to load.
     * @param width The width of the new scene.
     * @param height The height of the new scene.
     */
    public void changeScene(String fxmlFile, double width, double height) {
        try {
            String fxmlPath = Paths.get("src", "main", "resources", "view", fxmlFile).toAbsolutePath().toString();
            FXMLLoader loader = new FXMLLoader(Paths.get(fxmlPath).toUri().toURL());
            Parent root = loader.load();
            this.scene = new Scene(root, width, height);
            primaryStage.setScene(this.scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading FXML: " + e.getMessage());
        }
    }

    /**
     * Changes the root of the current scene to a new FXML view.
     *
     * @param fxmlFile The path to the FXML file to load.
     */
    public void changeRoot(String fxmlFile) {
        try {
            String fxmlPath = Paths.get("src", "main", "resources", "view", fxmlFile).toAbsolutePath().toString();
            FXMLLoader loader = new FXMLLoader(Paths.get(fxmlPath).toUri().toURL());
            Parent root = loader.load();
            this.scene.setRoot(root);
            this.scene.getWindow().setOnShown(event -> {
                System.out.println(scene.widthProperty().get());
                System.out.println(scene.heightProperty().get());
            });
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading FXML: " + e.getMessage());
        }
    }

    /**
     * Loads an initial scene with the given FXML file and dimensions.
     *
     * @param fxmlFile The FXML file to load.
     * @param width The width of the initial scene.
     * @param height The height of the initial scene.
     */
    private void loadScene(String fxmlFile, double width, double height) {
        changeScene(fxmlFile, width, height);
    }

    /**
     * Maximizes or restores the window based on the provided state.
     *
     * @param state True to maximize the window, false to restore it.
     */
    public void setMaximized(boolean state) {
        primaryStage.setMaximized(state);
    }

    /**
     * Returns the current scene of the application.
     *
     * @return The current scene.
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Returns the width of the screen.
     *
     * @return The screen width.
     */
    public double getScreenWidth() {
        return screenWidth;
    }

    /**
     * Returns the height of the screen.
     *
     * @return The screen height.
     */
    public double getScreenHeight() {
        return screenHeight;
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Preloads the custom font for the application.
     * If the font cannot be found, it prints an error message.
     */
    private void fontPreload() {
        try {
            Font customFont = Font.loadFont(new FileInputStream(Constants.ACCENT_FONT_SRC), 20);
            System.out.println(customFont.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
