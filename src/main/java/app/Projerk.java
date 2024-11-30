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
        // TrieLoader.loadTrie();
        // primaryStage.setTitle("Liberro Client");
        // primaryStage.setHeight(800);
        // primaryStage.setWidth(600);
        // System.out.println(System.getProperty("java.class.path"));
        SocketService socketService = SocketService.getInstance("http://127.0.0.1:5000");
        socketService.connect();
        fontPreload();
        instance = this;
        screenWidth = bounds.getWidth();
        screenHeight = bounds.getHeight();
        System.out.println(screenWidth);
        System.out.println(screenHeight);
        this.primaryStage = primaryStage;
        // loadScene("LoginView.fxml", screenWidth, screenHeight);
        loadScene("LoginView.fxml", screenWidth, screenHeight);
        primaryStage.show();
        String cssPath = Paths.get("src", "main", "resources", "view", "css", "AutoCompleteSearch.css").toAbsolutePath().toString();
        scene.getStylesheets().add("file:" + cssPath);
        
        // setMaximized(true);
    }

    public void changeScene(String fxmlFile, double width, double height) {
        
        try {
            String fxmlPath = Paths.get("src", "main", "resources", "view", fxmlFile).toAbsolutePath().toString();
            FXMLLoader loader = new FXMLLoader(Paths.get(fxmlPath).toUri().toURL());
            Parent root = loader.load();
            this.scene = new Scene(root, width, height);
            primaryStage.setScene(this.scene);
            // setMaximized(true);
            // primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading FXML: " + e.getMessage());
        }
    }

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

    private void loadScene(String fxmlFile, double width, double height) {
        changeScene(fxmlFile, width, height);
    }

    public void setMaximized(boolean state) {
        primaryStage.setMaximized(state);
    }

    @Override
    public void stop() {
        shutdownExecutor();
    }

    private void shutdownExecutor() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow(); 
            try {
                if (!executor.awaitTermination(60, java.util.concurrent.TimeUnit.SECONDS)) {
                    System.out.println("Executor chưa dừng. Thực hiện shutdownNow...");
                    executor.shutdownNow();
                    if (!executor.awaitTermination(60, java.util.concurrent.TimeUnit.SECONDS)) {
                        System.err.println("Executor không thể dừng đúng cách");
                    }
                }
            } catch (InterruptedException ex) {
                System.err.println("Ngắt quãng khi chờ Executor dừng");
                executor.shutdownNow();
                Thread.currentThread().interrupt(); 
            }
        }
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
