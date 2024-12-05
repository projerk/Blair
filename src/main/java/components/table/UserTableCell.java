package components.table;

import animation.ScaleEffect;
import components.abstracts.TableCell;
import components.button.DeleteUserButton;
import components.button.EditUserButton;
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
import model.User;
import utils.PoolingToolkit;

public class UserTableCell extends TableCell implements MouseHandler {
    private User user;

    /**
     * Constructs a UserTableCell for a specific user.
     *
     * @param user The user to be displayed in this table cell
     */
    public UserTableCell(User user) {
        super(15, 10, 15, 15, 17, 10, 5, 13);
        this.user = user;
        loadCellData();
        handleMouseEvent();
        this.getRowConstraints().get(0).setMaxHeight(150);
    }

    /**
     * Loads the cell data by adding various user details to the table cell.
     */
    @Override
    public void loadCellData() {
        this.addNode(createLabel(user.getUsername()), 0, 0); // isbn
        this.addNode(createImage(PoolingToolkit.getImage(user.getAvatar())), 1, 0); // cover
        this.addNode(createLabel(user.getFirstName()), 2, 0); // title
        this.addNode(createLabel(user.getLastName()), 3, 0); // genre
        this.addNode(createLabel(user.getEmail()), 4, 0); // author
        this.addNode(createLabel(user.getRole()), 5, 0); // publisher
        this.addNode(createLabel("Yes"), 6, 0); // release
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
     * Creates an HBox with action buttons for the user.
     *
     * @return An HBox containing delete and edit buttons for the user
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

        DeleteUserButton deleteButton = new DeleteUserButton(user.getID());
        EditUserButton editButton = new EditUserButton(user);

        vbox1.getChildren().add(deleteButton);
        vbox2.getChildren().add(editButton);

        HBox container = new HBox(vbox1, vbox2);
        container.setStyle("-fx-border-color: black transparent black transparent; \r\n" + //
                "    -fx-border-width: 1px 0px 1px 0px;");
        return container;
    }
}