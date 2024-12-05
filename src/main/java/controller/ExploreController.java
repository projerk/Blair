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
import components.media.WrappedImageView;
import model.User;
import app.AppState;
import app.Projerk;
import components.container.BookFrame;
import components.container.SearchResult;
import utils.SocketUtils;
import io.socket.emitter.Emitter;
import socket.SocketService;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import animation.FadeEffect;
import animation.ScaleEffect;
import model.Book;
import components.interfaces.Listener;

/**
 * Controller for the Explore view in the application.
 * Manages book displays, search functionality, and user interactions.
 */
public class ExploreController implements Listener {
    @FXML
    private HBox searchLogo;

    @FXML
    private VBox jumbotron;

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

    /**
     * Current logged-in user.
     */
    private User user;

    /**
     * Container for detailed book view.
     */
    private VBox detailBook;

    /**
     * Container for search results.
     */
    private SearchResult searchResult;

    /**
     * Socket service for server communication.
     */
    private SocketService client = SocketService.getInstance();

    /**
     * Initializes the explore controller.
     * Called automatically by JavaFX after FXML loading.
     */
    @FXML
    private void initialize() {
        initAttribute();
        preload();
        Platform.runLater(() -> {
            searchResult.discard();
            searchResult.setMaxHeight(0);
        });
    }

    /**
     * Initializes user attributes from application state.
     */
    private void initAttribute() {
        user = AppState.getInstance().getUser();
    }

    /**
     * Handles search functionality.
     * Sets up listeners for search input and socket responses.
     */
    private void searchHandle() {
        client.onMessage("search_results", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String message = response.getString("status");
            if (message.equals("success")) {
                JSONArray books = response.getJSONArray("results");

                List<Book> res = SocketUtils.parseSearch(books);

                Platform.runLater(() -> {
                    searchResult.setMaxHeight(res.size() * 90);
                    searchResult.display(res);
                });
            }
            else {
                Platform.runLater(() -> {
                    searchResult.discard();
                    searchResult.setMaxHeight(0);
                });
            }
        });

        search.textProperty().addListener((observable, oldText, newText) -> {
            if (!newText.equals(oldText) && !newText.equals("")) {
                JSONObject object = new JSONObject();
                object.put("query", newText);
                client.sendMessage("search", object);
            }
            else if (newText.equals("")) {
                searchResult.discard();
                searchResult.setMaxHeight(0);
            }
        });
    }

    /**
     * Preloads various UI components and sets up initial view.
     */
    private void preload() {
        container.setAlignment(Pos.TOP_CENTER);
        searchIconPreload();
        searchResultPreload();
        jumbotronPreload();
        loadFeatureBook();
        loadPopularBook();
        loadRecommendBook();
        loadExploreBook();
        searchHandle();
        Platform.runLater(() -> {
            container.requestFocus();
        });
    }

    /**
     * Preloads search icon for the search input.
     */
    private void searchIconPreload() {
        Image image = FileHelper.getImage("search.png");
        WrappedImageView imageView = new WrappedImageView();
        imageView.setImage(image);
        imageView.setStyle("-fx-opacity: 60%");
        searchLogo.getChildren().add(imageView);
    }

    /**
     * Preloads jumbotron with user greeting and welcome message.
     */
    private void jumbotronPreload() {
        Label greeting = new Label();
        greeting.setText("Happy Reading " + user.getFirstName());
        greeting.setStyle("-fx-font-family: 'Accent Graphic W00 Medium'; -fx-font-size: 50; -fx-text-fill: black;");

        Label introduction = new Label();
        introduction.setText("Welcome to the knowledge of humankind");
        introduction.setStyle("-fx-font-family: 'Accent Graphic W00 Medium'; -fx-font-size: 30; -fx-text-fill: black;");

        jumbotron.getChildren().add(greeting);
        jumbotron.getChildren().add(introduction);

        FadeEffect.fadeIn(greeting, 3);
        FadeEffect.fadeIn(introduction, 3);
    }

    /**
     * Loads featured book from server.
     */
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

                Platform.runLater(() -> {
                    bookDisplayPreload(book);
                });
            }
        });

        client.sendMessage("get_feature_book", null);
    }

    /**
     * Preloads book display with book image and interactions.
     *
     * @param book Book to be displayed
     */
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

        imageView.setOnMouseExited(event -> {
            ScaleEffect.scaleTo(imageView, 0.2, 1.0, 1.0);
        });

        imageView.setOnMouseClicked(event -> {
            openCanvas(book.getId());
        });
        bookDisplay.getChildren().add(imageView);
        loadBookContent(book);
    }

    /**
     * Loads book content details into the display.
     *
     * @param book Book whose content is to be displayed
     */
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
        FadeEffect.fadeIn(content, 2);
        bookDisplay.getChildren().add(content);
    }

    /**
     * Loads popular books from server.
     */
    private void loadPopularBook() {
        client.onMessage("popular_book_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String status = response.getString("status");

            if (status.equals("success")) {
                JSONArray books = response.getJSONArray("books");
                ArrayList<Book> bookList = SocketUtils.createDisplayBook(books);
                BookFrame bookFrame = new BookFrame(bookList);
                bookFrame.setAllRowsVgrow(Priority.NEVER);
                Platform.runLater(() -> {
                    popularBook.getChildren().add(bookFrame);
                });
            }
            else {
                System.out.println("Error");
            }
        });

        client.sendMessage("get_popular_book", null);
    }

    /**
     * Loads recommended books for the current user.
     */
    private void loadRecommendBook() {
        client.onMessage("recommendation_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String status = response.getString("status");

            if (status.equals("success")) {
                JSONArray books = response.getJSONArray("books");
                ArrayList<Book> bookList = SocketUtils.createDisplayBook(books);
                BookFrame bookFrame = new BookFrame(bookList);
                bookFrame.setAllRowsVgrow(Priority.NEVER);
                bookFrame.setAllColumnsHgrow(Priority.NEVER);
                Platform.runLater(() -> {
                    newArrivalBook.getChildren().add(bookFrame);
                });
            }
            else {
                System.out.println("Error");
            }
        });

        JSONObject data = new JSONObject();
        data.put("user_id", AppState.getInstance().getUser().getID());
        client.sendMessage("get_recommend_books", data);
    }

    /**
     * Loads all books for exploration.
     */
    private void loadExploreBook() {
        client.onMessage("all_books_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String status = response.getString("status");

            if (status.equals("success")) {
                JSONArray book_list = response.getJSONArray("book_list");

                for (int i = 0; i < book_list.length(); i++) {
                    JSONArray books = book_list.getJSONArray(i);
                    ArrayList<Book> book = SocketUtils.createDisplayBook(books);
                    BookFrame bookFrame = new BookFrame(book);
                    Platform.runLater(() -> {
                        exploreBook.getChildren().add(bookFrame);
                    });
                }
            }
            else {
                System.out.println("Error");
            }
        });
        client.sendMessage("get_all_books", null);
    }

    /**
     * Preloads search result container and configures its properties.
     */
    private void searchResultPreload() {
        searchResult = new SearchResult();
        searchResult.setAlignment(Pos.CENTER);
        HBox.setHgrow(searchResult, Priority.NEVER);
        VBox.setVgrow(searchResult, Priority.NEVER);

        HBox.setHgrow(container, Priority.NEVER);
        VBox.setVgrow(container, Priority.NEVER);

        container.getChildren().add(searchResult);

        search.widthProperty().addListener((observable, oldWidth, newWidth) -> {
            if (newWidth.doubleValue() > 0) {
                searchResult.setPrefWidth(Projerk.getInstance().getScreenWidth() * 0.4);
                searchResult.setMinWidth(Projerk.getInstance().getScreenWidth() * 0.35);
                searchResult.setMaxWidth(Projerk.getInstance().getScreenWidth() * 0.35);
                searchResult.setLayoutX(Projerk.getInstance().getScreenWidth() * 0.35);
                searchResult.setMinHeight(0);
                searchResult.setMaxHeight(0);
                searchResult.setTranslateY(Projerk.getInstance().getScreenHeight() * 0.10);
            }
        });

        search.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                searchResult.discard();
                searchResult.setMaxHeight(0);
            }
        });
    }

    /**
     * Opens canvas view for a specific book.
     *
     * @param id The ID of the book to be opened
     */
    @Override
    public void openCanvas(int id) {
        try {
            AppState.getInstance().setCurrentViewBookID(id);
            FXMLLoader loader = FileHelper.getLoader(Constants.CANVAS_VIEW_FILE);
            detailBook = loader.load();
            container.getChildren().add(detailBook);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the current canvas view.
     */
    @Override
    public void closeCanvas() {
        container.getChildren().remove(container.getChildren().size() - 1);
    }
}