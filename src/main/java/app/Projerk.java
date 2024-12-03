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
//import model.TrieLoader;
import socket.SocketService;
import utils.Constants;
import utils.FileHelper;
import javafx.scene.text.Font;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import utils.Constants;
import socket.SocketService;


public class Projerk extends Application {
    private ExecutorService executor;
    private static Projerk instance;
    private Stage primaryStage;
    private Screen screen = Screen.getPrimary();
    private static double screenWidth;
    private static double screenHeight;
    private Scene scene;
    Rectangle2D bounds = screen.getVisualBounds();
    public Projerk() {}

    public static Projerk getInstance() {
        return instance;
    }

    @Override
    public void start(Stage primaryStage) throws URISyntaxException {
        instance = this;
        SocketService socketService = SocketService.getInstance("http://127.0.0.1:5000"); // Connect to the server
        socketService.connect(); // Connect execute
        fontPreload(); // load font.
        screenWidth = bounds.getWidth(); // get screen width
        screenHeight = bounds.getHeight(); // get screen height
        this.primaryStage = primaryStage; 
        loadScene("LoginView.fxml", screenWidth, screenHeight); // load the login scene.
        primaryStage.show();
        // String cssPath = Paths.get("src", "main", "resources", "view", "css", "AutoCompleteSearch.css").toAbsolutePath().toString();
        // scene.getStylesheets().add("file:" + cssPath);
        
        // setMaximized(true);
    }


    /**
     * This function change the current scene, but now deprecated.
     * 
     * 
     * @param fxmlFile url of fxml file.
     * @param width width of scene.
     * @param height height of scene.
     */
    public void changeScene(String fxmlFile, double width, double height) {
        
        try {
            FXMLLoader loader = FileHelper.getLoader(fxmlFile);
            Parent root = loader.load();
            this.scene = new Scene(root, width, height);
            primaryStage.setScene(this.scene);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading FXML: " + e.getMessage());
        }
    }

    /**
     * This function change the content of root node, rather than change the whole scene.
     * 
     * @param fxmlFile url of fxml file
     */
    public void changeRoot(String fxmlFile) {
        try {
            FXMLLoader loader = FileHelper.getLoader(fxmlFile);
            Parent root = loader.load();
            this.scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading FXML: " + e.getMessage());
        }
    }


    /**
     * Initialize scene of app.
     * 
     * @param fxmlFile url of fxml file.
     * @param width screen width.
     * @param height screen height.
     */
    private void loadScene(String fxmlFile, double width, double height) {
        changeScene(fxmlFile, width, height);
    }

    public void setMaximized(boolean state) {
        primaryStage.setMaximized(state);
    }


    public Scene getScene() {
        return this.scene;
    }

    public double getScreenWidth() {
        return screenWidth;
    }

    public double getScreenHeight() {
        return screenHeight;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void fontPreload() {
        try {
            Font customFont = Font.loadFont(new FileInputStream(Constants.ACCENT_FONT_SRC), 20);
            System.out.println(customFont.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }    
    }
}
