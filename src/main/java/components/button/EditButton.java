package components.button;

import animation.ScaleEffect;
import components.interfaces.MouseHandler;
import components.interfaces.Receiver;
import components.interfaces.Sender;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Book;
import org.json.JSONObject;
import socket.SocketService;
import utils.FileHelper;
import utils.ImageUtils;

import java.util.Optional;

public class EditButton extends Button implements MouseHandler, Sender, Receiver {
    
    private Book book;
    private static Image image = FileHelper.getImage("editing.png");
    private Label editStatus = new Label();


    public EditButton(Book book) {
        getMessage();
        setButtonStyle();
        handleMouseEvent();
        this.book = book;
    }

    public void handleMouseEvent() {
        setOnMouseClicked(event -> {
            // sendMessage();
            openFieldBox(book);
        });

        setOnMouseEntered(event -> {
            ScaleEffect.scaleTo(this, 0.1, 1.1, 1.1);
        });

        setOnMouseExited(event -> {
            ScaleEffect.scaleTo(this, 0.1, 1.0, 1.0);
        });
    }

    private void setButtonStyle() {
        handleMouseEvent();
        setStyle("-fx-background-color: transparent;");
        
        ImageView imageView = ImageUtils.getImageView(image);
        imageView.setFitWidth(30);
        setGraphic(imageView);
    }

    
    private void openFieldBox(Book book) {
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
        isbnField.setText(book.getIsbn());

        TextField titleField = createStyledTextField("Tiêu đề");
        titleField.setText(book.getTitle());

        TextField descriptionField = createStyledTextField("Mô tả");
        descriptionField.setText(book.getDescription());

        TextField stockField = createStyledTextField("Số lượng");
        stockField.setText(Integer.toString(book.getAvailable()));

        TextField coverField = createStyledTextField("Đường dẫn ảnh bìa");
        coverField.setText(book.getCover());

        TextField authorField = createStyledTextField("Tác giả");
        authorField.setText(book.getAuthor().getName());

        TextField genreField = createStyledTextField("Thể loại");
        genreField.setText(book.getGenre());

        TextField yearField = createStyledTextField("Năm xuất bản");
        yearField.setText(book.getPublishingYear());

        TextField publisherField = createStyledTextField("Nhà xuất bản");
        publisherField.setText(book.getPublisher());

        TextField pdfField = createStyledTextField("Đường dẫn PDF");

        Button addButton = new Button("Add");
        addButton.setStyle("-fx-font-size: 20px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        addButton.setOnMouseClicked(event -> {

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


            sendMessage();
        });


        Button autofillButton = new Button("Tự điền");
        autofillButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        autofillButton.setOnAction(event -> {


            isbnField.setText("9783161484100");
            titleField.setText("Java Programming");
            descriptionField.setText("A comprehensive guide to Java programming.");
            stockField.setText("10");
            coverField.setText("https://example.com/cover.jpg");
            authorField.setText("John Doe");
            genreField.setText("Programming");
            yearField.setText("2021");
            publisherField.setText("Tech Books Publishing");
            pdfField.setText("https://example.com/book.pdf");
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
    
        // if (result.isPresent() && result.get() == ButtonType.OK) {

        // } else {
            
        // }
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

    public void sendMessage() {
        JSONObject newData = new JSONObject();
        newData.put("isbn", book.getIsbn());
        newData.put("title", book.getTitle());
        newData.put("description", book.getDescription());
        newData.put("available", book.getAvailable());
        newData.put("cover", book.getCover());
        newData.put("author", book.getAuthor().getName());
        newData.put("genre", book.getGenre());
        newData.put("publishingYear", book.getPublishingYear());
        newData.put("publisher", book.getPublisher());
        newData.put("pdf", book.getPdf());
    
        JSONObject data = new JSONObject();
        data.put("book_id", book.getId());
        data.put("new_data", newData);
    
        SocketService.getInstance().sendMessage("edit_book", data);
    }

    public void getMessage() {
        SocketService.getInstance().onMessage("edit_book_response", (Emitter.Listener) args -> {
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
