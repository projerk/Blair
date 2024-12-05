package components.table;

import animation.ScaleEffect;
import components.abstracts.TableCell;
import components.button.DeleteBookButton;
import components.button.EditBookButton;
import components.container.Divide;
import components.interfaces.MouseHandler;
import components.media.WrappedImageView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Book;
import utils.PoolingToolkit;

public class BookTableCell extends TableCell implements MouseHandler {
    private Book book;

    /**
     * Constructs a BookTableCell for a specific book.
     *
     * @param book The book to be displayed in this table cell
     */
    public BookTableCell(Book book) {
        super(15, 10, 23, 12, 12, 10, 5, 13);
        this.book = book;
        loadCellData();
        handleMouseEvent();
        this.getRowConstraints().get(0).setMaxHeight(150);
    }

    /**
     * Loads the cell data by adding various book details to the table cell.
     */
    @Override
    public void loadCellData() {
        this.addNode(createLabel(book.getIsbn()), 0, 0); // isbn
        this.addNode(createImage(PoolingToolkit.getImage(book.getCover())), 1, 0); // cover
        this.addNode(createLabel(book.getTitle()), 2, 0); // title
        this.addNode(createLabel(book.getGenre()), 3, 0); // genre
        this.addNode(createLabel(book.getAuthor().getName()), 4, 0); // author
        this.addNode(createLabel(book.getPublisher()), 5, 0); // publisher
        this.addNode(createLabel(book.getPublishingYear()), 6, 0); // release
        this.addNode(createActionButton(), 7, 0); // action
    }

    /**
     * Creates a label VBox for a string content.
     *
     * @param content The text to be displayed in the label
     * @return A VBox containing the formatted label
     */
    public VBox createLabel(String content) {
        VBox container = new VBox();
        VBox.setVgrow(container, Priority.ALWAYS);
        HBox.setHgrow(container, Priority.ALWAYS);
        container.setAlignment(Pos.CENTER);

        Label label = new Label(content);
        label.setStyle("-fx-text-fill: black;");
        container.getChildren().add(label);
        container.setStyle("-fx-border-color: black transparent black transparent; \r\n" + //
                "    -fx-border-width: 1px 0px 1px 0px;");
        return container;
    }

    /**
     * Handles mouse events for hover effects.
     */
    public void handleMouseEvent() {
        setOnMouseEntered(event -> {
            // ScaleEffect.scaleTo(this, 0.1, 1.1, 1.1);
        });

        setOnMouseExited(event -> {
            // ScaleEffect.scaleTo(this, 0.1, 1.0, 1.0);
        });
    }

    /**
     * Creates a label VBox for an integer content.
     *
     * @param content The integer to be displayed in the label
     * @return A VBox containing the formatted label
     */
    public VBox createLabel(int content) {
        VBox container = new VBox();
        VBox.setVgrow(container, Priority.ALWAYS);
        HBox.setHgrow(container, Priority.ALWAYS);
        container.setAlignment(Pos.CENTER);

        Label label = new Label(Integer.toString(content));
        label.setStyle("-fx-text-fill: black; -fx-font-size: 20;");
        label.setWrapText(true);
        container.getChildren().add(label);
        container.setStyle("-fx-border-color: black black black black; \r\n" + //
                "    -fx-border-width: 1px 1px 1px 1px;");
        return container;
    }

    /**
     * Creates a VBox containing an image with a drop shadow effect.
     *
     * @param image The image to be displayed
     * @return A VBox containing the formatted image
     */
    public VBox createImage(Image image) {
        VBox container = new VBox();
        VBox.setVgrow(container, Priority.ALWAYS);
        HBox.setHgrow(container, Priority.ALWAYS);
        container.setPadding(new Insets(10, 10, 10, 10));
        container.setAlignment(Pos.CENTER);
        WrappedImageView imageView = new WrappedImageView();
        imageView.setImage(image);
        imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0, 0, 1);");
        container.getChildren().add(imageView);
        container.setStyle("-fx-border-color: black transparent black transparent; \r\n" + //
                "    -fx-border-width: 1px 0px 1px 0px;");
        return container;
    }

    /**
     * Creates an HBox with action buttons for the book.
     *
     * @return An HBox containing delete and edit buttons for the book
     */
    public HBox createActionButton() {
        VBox vbox1 = new VBox();
        VBox vbox2 = new VBox();
        HBox.setHgrow(vbox2, Priority.ALWAYS);
        VBox.setVgrow(vbox2, Priority.ALWAYS);
        HBox.setHgrow(vbox1, Priority.ALWAYS);
        VBox.setVgrow(vbox1, Priority.ALWAYS);

        vbox1.setAlignment(Pos.CENTER);
        vbox2.setAlignment(Pos.CENTER);

        DeleteBookButton deleteButton = new DeleteBookButton(book.getId());
        EditBookButton editButton = new EditBookButton(book);

        vbox1.getChildren().add(deleteButton);
        vbox2.getChildren().add(editButton);

        HBox container = new HBox(vbox1, vbox2);
        container.setStyle("-fx-border-color: black transparent black transparent; \r\n" + //
                "    -fx-border-width: 1px 0px 1px 0px;");
        return container;
    }
}