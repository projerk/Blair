package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.Constants;
import utils.FileHelper;
import utils.WrappedImageView;
import javafx.scene.control.TextField;
import model.User;
import app.AppState;
import app.Projerk;
import components.container.BookFrame;
import utils.SocketUtils;
import io.socket.emitter.Emitter;
import socket.SocketService;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import io.socket.emitter.Emitter;
import animation.ScaleEffect;
import model.Book;
import components.interfaces.Listener;


public class ExploreController implements Listener {
    @FXML
    private HBox searchLogo;

    @FXML
    private VBox jumbotron; // Displays greeting and introduction text

    @FXML
    private StackPane container;

    @FXML
    private HBox bookDisplay;

    @FXML
    private VBox popularBook;

    @FXML
    private VBox newArrivalBook;

    @FXML
    private VBox exploreBook;

    @FXML
    private TextField search;

    private User user;

    private VBox detailBook;

    private SocketService client = SocketService.getInstance();

    @FXML
    private void initialize() {
        initAttribute();
        preload();
    }

    private void initAttribute() {
        // Retrieve the currently logged-in user
        user = AppState.getInstance().getUser();
    }

    private void searchHandle() {
        
    }

    private void preload() {
        searchIconPreload();
        jumbotronPreload();
        loadFeatureBook();
        loadPopularBook();
        loadNewArrivalBook();
        loadExploreBook();
    }

    private void searchIconPreload() {
        Image image = FileHelper.getImage("search.png");
        WrappedImageView imageView = new WrappedImageView();
        imageView.setImage(image);
        imageView.setStyle("-fx-opacity: 60%");
        searchLogo.getChildren().add(imageView);
    }

    private void jumbotronPreload() {
        // Create greeting and introduction labels and style them
        Label greeting = new Label();
        greeting.setText("Happy Reading " + user.getFirstName());
        greeting.setStyle("-fx-font-family: 'Accent Graphic W00 Medium'; -fx-font-size: 50; -fx-text-fill: black;");

        Label introduction = new Label();
        introduction.setText("Welcome to the knowledge of humankind");
        introduction.setStyle("-fx-font-family: 'Accent Graphic W00 Medium'; -fx-font-size: 30; -fx-text-fill: black;");

        jumbotron.getChildren().add(greeting);
        jumbotron.getChildren().add(introduction);

       
    }

    private void loadFeatureBook() {

        Book book = new Book();

        client.onMessage("feature_book_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String status = response.getString("status");
            if (status.equals("success")) {
                book.setId(response.getInt("id"));
                book.setIsbn(response.getString("isbn"));
                book.setCover(response.getString("cover"));
                book.setTitle(response.getString("title"));
                book.setDescription(response.getString("description"));
                book.setAuthor(response.getString("author"));

                // Update the UI on the JavaFX application thread
                Platform.runLater(() -> {
                    bookDisplayPreload(book);
                });
            }
        });

        client.sendMessage("get_feature_book", null);
    }

    private void bookDisplayPreload(Book book) {
        bookDisplay.setSpacing(20);
        ImageView imageView = new ImageView();
        Image image = new Image(book.getCover(), true);
        imageView.setImage(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(200);
        imageView.setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(imageView, 0.2, 1.1, 1.1);
        });

        // Set mouse hover effects for the book cover
        imageView.setOnMouseExited(event -> {
            ScaleEffect.scaleTo(imageView, 0.2, 1.0, 1.0);
        });

        imageView.setOnMouseClicked(event -> {
            openCanvas(book.getId());
        });

        // Add the image view to the book display area
        bookDisplay.getChildren().add(imageView);
        loadBookContent(book); // Load book content below the image
    }

    private void loadBookContent(Book book) {
        VBox content = new VBox();
        content.setAlignment(Pos.TOP_LEFT);
        Label title = new Label(book.getTitle());
        Label author = new Label("By " + book.getAuthor().getName());
        Label description = new Label(book.getDescription());
        description.setWrapText(true);
        title.setStyle("-fx-font-family: 'Accent Graphic W00 Medium'; -fx-font-size:30; -fx-text-fill: black;");
        author.setStyle("-fx-font-family: 'Accent Graphic W00 Medium'; -fx-font-size: 20; -fx-text-fill: black;");
        description.setStyle("-fx-font-family: 'Accent Graphic W00 Medium'; -fx-font-size: 10; -fx-text-fill: black;");
        content.getChildren().add(title);
        content.getChildren().add(author);
        content.getChildren().add(description);
        bookDisplay.getChildren().add(content);
    }

    private void loadPopularBook() {
        client.onMessage("popular_book_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String status = response.getString("status");

            if (status.equals("success")) {
                JSONArray books = response.getJSONArray("books");
                ArrayList<Book> bookList = SocketUtils.createDisplayBook(books);
                BookFrame bookFrame = new BookFrame(bookList);
                bookFrame.setAllRowsVgrow(Priority.NEVER);
                // bookFrame.setListener(listener);
                Platform.runLater(() -> {
                    popularBook.getChildren().add(bookFrame);
                });
            }
            else {
                System.out.println("Error");
            }
        });
        // Request popular books from the server
        client.sendMessage("get_popular_book", null);
    }

    private void loadExploreBook() {

    }

    private void loadNewArrivalBook() {

        client.onMessage("new_arrival_book_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String status = response.getString("status");

            if (status.equals("success")) {
                JSONArray books = response.getJSONArray("books");
                ArrayList<Book> bookList = SocketUtils.createDisplayBook(books);
                BookFrame bookFrame = new BookFrame(bookList);
                bookFrame.setAllRowsVgrow(Priority.NEVER);
                bookFrame.setAllColumnsHgrow(Priority.NEVER);
                // if (this != null ) {bookFrame.setListener(listener);}
                Platform.runLater(() -> {
                    newArrivalBook.getChildren().add(bookFrame);
                });
            }
            else {
                System.out.println("Error");
            }
        });
        // Request new arrival books from the server
        client.sendMessage("get_new_arrival_book", null);
    }

    @Override
    public void openCanvas(int id) {
        try {
            AppState.getInstance().setCurrentViewBookID(id);
            FXMLLoader loader = FileHelper.getLoader(Constants.CANVAS_VIEW_FILE);
            detailBook = loader.load();
            // Add the detail view to the container
            container.getChildren().add(detailBook);

            
            // AppState.getInstance().setCurrentViewBookID(id);
            // detailBook.setTranslateY(Projerk.getInstance().getScreenHeight());
            // TranslateEffect.translateTo(detailBook, 0.5, 0, 0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeCanvas() {
        // Remove the last child from the container to close the detail view
        container.getChildren().remove(container.getChildren().size() - 1);
    }

}
