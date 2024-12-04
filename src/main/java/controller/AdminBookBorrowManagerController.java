package controller;

import animation.ScaleEffect;
import api.GoogleBooksAPI;
import components.table.BorrowTableCell;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Book;
import model.Borrow;
import org.json.JSONObject;
import socket.SocketService;
import utils.SocketUtils;

import java.util.List;
import java.util.Optional;

public class AdminBookBorrowManagerController {
    
    @FXML
    private VBox borrowContainer;

    @FXML
    private Button newBorrow;

    @FXML
    private Button previousPage;

    @FXML
    private Button nextPage;

    // @FXML
    // private Button searchButton;

    @FXML
    private Button refreshPage;

    private int currentPage = 1;

    private Label editStatus = new Label();


    private SocketService client = SocketService.getInstance();

    @FXML
    private void initialize() {
        initializeButton();
        getPopUpMessage();
        getMessage();
        sendMessage();
    }

    private void initializeButton() {
        newBorrow.setOnMouseClicked(event -> {
            openFieldBox();
        });

        newBorrow.setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(newBorrow, 0.1, 1.1, 1.1);
        });

        newBorrow.setOnMouseExited(event -> {
            ScaleEffect.scaleTo(newBorrow, 0.1, 1.0, 1.0);
        });

        previousPage.setOnMouseClicked(event -> {
            if (currentPage > 1) {
                currentPage--;
                sendMessage();
            }
        });

        previousPage.setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(previousPage, 0.1, 1.1, 1.1);
        });

        previousPage.setOnMouseExited(event -> {
            ScaleEffect.scaleTo(previousPage, 0.1, 1.0, 1.0);
        });

        nextPage.setOnMouseClicked(event -> {
            if (currentPage < 8) {
                currentPage++;
                sendMessage();
            }
        });

        nextPage.setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(nextPage, 0.1, 1.1, 1.1);
        });

        nextPage.setOnMouseExited(event -> {
            ScaleEffect.scaleTo(nextPage, 0.1, 1.0, 1.0);
        });

        refreshPage.setOnMouseClicked(event -> {
            sendMessage();
        });

        refreshPage.setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(refreshPage, 0.1, 1.1, 1.1);
        });

        refreshPage.setOnMouseExited(event -> {
            ScaleEffect.scaleTo(refreshPage, 0.1, 1.0, 1.0);
        });

        

    }

    private void getMessage() {
        client.onMessage("borrow_table_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String message = response.getString("status");
            if (message.equals("success")) {
                Platform.runLater(() -> {
                    borrowContainer.getChildren().clear();
                });
                List<Borrow> borrows = SocketUtils.parseBorrowTableCell(response.getJSONArray("borrow_list"));

                for (Borrow borrow : borrows) {
                    Platform.runLater(() -> {
                        borrowContainer.getChildren().add(new BorrowTableCell(borrow));
                    });
                }
            }
            else {

            }
        });
    }

    private void sendMessage() {
        JSONObject data = new JSONObject();
        data.put("page", currentPage);
        client.sendMessage("get_borrow_table", data);
    }

    private void openFieldBox() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Nhập thông tin");
        alert.setHeaderText("Vui lòng nhập thông tin sách:");
        alert.setContentText(null);
    
        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setStyle("-fx-background-color: #f9f9f9; -fx-border-radius: 10; -fx-background-radius: 10;");
    
        TextField isbnField = createStyledTextField("ISBN");

        TextField titleField = createStyledTextField("Tiêu đề");

        TextField descriptionField = createStyledTextField("Mô tả");

        TextField stockField = createStyledTextField("Số lượng");

        TextField coverField = createStyledTextField("Đường dẫn ảnh bìa");

        TextField authorField = createStyledTextField("Tác giả");

        TextField genreField = createStyledTextField("Thể loại");

        TextField yearField = createStyledTextField("Năm xuất bản");

        TextField publisherField = createStyledTextField("Nhà xuất bản");

        TextField pdfField = createStyledTextField("Đường dẫn PDF");

        Button addButton = new Button("Add");
        addButton.setStyle("-fx-font-size: 20px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        addButton.setOnMouseClicked(event -> {
            Book book = new Book();

            book.setIsbn(isbnField.getText());
            book.setTitle(titleField.getText());
            book.setDescription(descriptionField.getText());
            book.setAvailable(Integer.valueOf(stockField.getText()));
            book.setCover(coverField.getText());
            book.setAuthor(authorField.getText());
            book.setGenre(genreField.getText());
            book.setPublishingYear(yearField.getText());
            book.setPublisher(publisherField.getText());
            book.setPdf(pdfField.getText());


            sendPopUpMessage(book);
        });


        Button autofillButton = new Button("Tự điền");
        autofillButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        autofillButton.setOnMouseClicked(event -> {
            Platform.runLater(() -> {
                autofillButton.setDisable(true);
            });

            Task<Book> bookSearchTask = GoogleBooksAPI.createBookSearchTask(isbnField.getText());

            bookSearchTask.setOnSucceeded(e -> {
                Book resBook = bookSearchTask.getValue();
                Platform.runLater(() -> {
                    titleField.setText(resBook.getTitle());
                    descriptionField.setText(resBook.getDescription());
                    coverField.setText(resBook.getCover());
                    authorField.setText(resBook.getAuthor().getName());
                    genreField.setText(resBook.getGenre());
                    yearField.setText(resBook.getPublishingYear());
                    publisherField.setText(resBook.getPublisher());
                    autofillButton.setDisable(false);
                });
            });
    
            bookSearchTask.setOnFailed(e -> {
                editStatus.setStyle("-fx-text-fill: red;");
                editStatus.setText(bookSearchTask.getException().getMessage());
            });

            Thread thread = new Thread(bookSearchTask);
            thread.setDaemon(true); 
            thread.start();
        });
    
        gridPane.add(createStyledLabel("ISBN:"), 0, 0);
        gridPane.add(isbnField, 1, 0);
        gridPane.add(autofillButton, 2, 0); 
    
        gridPane.add(createStyledLabel("Tiêu đề:"), 0, 1);
        gridPane.add(titleField, 1, 1);
    
        gridPane.add(createStyledLabel("Mô tả:"), 0, 2);
        gridPane.add(descriptionField, 1, 2);
    
        gridPane.add(createStyledLabel("Số lượng:"), 0, 3);
        gridPane.add(stockField, 1, 3);
    
        gridPane.add(createStyledLabel("Ảnh bìa:"), 0, 4);
        gridPane.add(coverField, 1, 4);
    
        gridPane.add(createStyledLabel("Tác giả:"), 0, 5);
        gridPane.add(authorField, 1, 5);
    
        gridPane.add(createStyledLabel("Thể loại:"), 0, 6);
        gridPane.add(genreField, 1, 6);
    
        gridPane.add(createStyledLabel("Năm xuất bản:"), 0, 7);
        gridPane.add(yearField, 1, 7);
    
        gridPane.add(createStyledLabel("Nhà xuất bản:"), 0, 8);
        gridPane.add(publisherField, 1, 8);
    
        gridPane.add(createStyledLabel("File PDF:"), 0, 9);
        gridPane.add(pdfField, 1, 9);

        gridPane.add(addButton, 0, 10);

        gridPane.add(editStatus, 0, 11);
    
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setContent(gridPane);
    
        dialogPane.setStyle("-fx-border-color: #d6d6d6; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 20;");
    
        alert.getButtonTypes().addAll(ButtonType.CANCEL);
    
        Optional<ButtonType> result = alert.showAndWait();
    }
    
    private TextField createStyledTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setStyle("-fx-border-color: #d6d6d6; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 5;");
        return textField;
    }
    
    private Text createStyledLabel(String labelText) {
        Text text = new Text(labelText);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        text.setStyle("-fx-fill: #333;");
        return text;
    }

    public void sendPopUpMessage(Book book) {
        JSONObject data = new JSONObject();
        data.put("isbn", book.getIsbn());
        data.put("title", book.getTitle());
        data.put("description", book.getDescription());
        data.put("available", book.getAvailable());
        data.put("cover", book.getCover());
        data.put("author", book.getAuthor().getName());
        data.put("genre", book.getGenre());
        data.put("publishingYear", book.getPublishingYear());
        data.put("publisher", book.getPublisher());
        data.put("pdf", book.getPdf());        
    
        SocketService.getInstance().sendMessage("add_borrow", data);
    }

    public void getPopUpMessage() {
        SocketService.getInstance().onMessage("add_borrow_response", (Emitter.Listener) args -> {
            JSONObject response = (JSONObject) args[0];
            String status = response.getString("status");
            if (status.equals("success")) {
                Platform.runLater(() -> {
                    editStatus.setText("");
                    editStatus.setStyle("-fx-text-fill: green;");
                    editStatus.setText(response.getString("message"));
                });
            }
            else {
                Platform.runLater(() -> {
                    editStatus.setText("");
                    editStatus.setStyle("-fx-text-fill: red;");
                    editStatus.setText(response.getString("message"));
                });
            }
        });
    }
}
