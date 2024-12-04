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
import components.container.SearchResult;
import utils.SocketUtils;
import io.socket.emitter.Emitter;
import socket.SocketService;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import io.socket.emitter.Emitter;
import animation.FadeEffect;
import animation.ScaleEffect;
import animation.TranslateEffect;
import model.Book;
import components.interfaces.Listener;


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

    private User user;

    private VBox detailBook;

    private SearchResult searchResult;

    private SocketService client = SocketService.getInstance();

    /**
     * Initializes the controller after its root element has been processed.
     * This method sets up initial attributes and preloads necessary data.
     */
    @FXML
    private void initialize() {
        initAttribute();
        preload();
    }

    private void initAttribute() {
        user = AppState.getInstance().getUser();
    }

    
    /**
     * Preloads various UI elements and data for the explore view.
     */
    private void preload() {
        searchIconPreload();
        jumbotronPreload();
        loadFeatureBook();
        loadPopularBook();
        loadNewArrivalBook();
        loadExploreBook();
    }

    /**
     * Preloads the search icon into the search logo area.
     * @author: MothMalone(nam)
     */
    private void searchIconPreload() {
        Image image = FileHelper.getImage("search.png");
        WrappedImageView imageView = new WrappedImageView();
        imageView.setImage(image);
        imageView.setStyle("-fx-opacity: 60%");
        searchLogo.getChildren().add(imageView);
    }
    /**
     * Preloads greeting messages into the jumbotron.
     * @author: MothMalone(nam)
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
     * Loads the featured book from the server and updates the display.
     * @author: MothMalone(nam)
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
     * Preloads the display of a book in the UI.
     * 
     * @param book The book to display.
     * @author: MothMalone(nam)
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
     * Loads the content for a specific book and adds it to the display.
     * 
     * @param book The book whose content is to be loaded.
     * @author: MothMalone(nam) + duon9(duong)
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
     * Loads popular books from the server and updates the display.
     * @author: MothMalone(nam)
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
                // bookFrame.setListener(listener);
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

                // ArrayList<Book> bookList = SocketUtils.createDisplayBook(books);
                // BookFrame bookFrame = new BookFrame(bookList);
                // // bookFrame.setAllRowsVgrow(Priority.NEVER);
                // // bookFrame.setAllColumnsHgrow(Priority.NEVER);
                // Platform.runLater(() -> {
                //     newArrivalBook.getChildren().add(bookFrame);
                // });
            }
            else {
                System.out.println("Error");
            }
        });

        // JSONObject data = new JSONObject();
        // data.put("user_id", AppState.getInstance().getUser().getID());
        client.sendMessage("get_all_books", null);
    }

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
            if (newValue) {
                // if (newValue != oldValue) {
                //     JSONObject object = new JSONObject();
                //     object.put("query", search.getText());
                //     client.sendMessage("search", object);
                // }
            }
            else {
                searchResult.discard();
                searchResult.setMaxHeight(0);
            }
        });

        // searchResult.discard();   
        // searchResult.setMaxHeight(0);
        // Platform.runLater(() -> {
        //     container.requestFocus();
        // });
    }


    /**
     * Loads newly arrived books from the server and updates the display.
     * @author: MothMalone(nam)
     */
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

        client.sendMessage("get_new_arrival_book", null);
    }

    /**
     * Opens the detailed canvas view for a specified book.
     * 
     * @param id The ID of the book to display.
     * @author: MothMalone(nam)
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
     * @author: MothMalone(nam)
     */
    @Override
    public void closeCanvas() {
        container.getChildren().remove(container.getChildren().size() - 1);
    }

}
