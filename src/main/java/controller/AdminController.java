package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;
import utils.Constants;
import java.io.File;
import java.io.IOException;
import java.net.http.WebSocket.Listener;
import java.nio.file.Paths;

import animation.ScaleEffect;
import app.AppState;
import app.Projerk;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import utils.FileHelper;
import components.media.WrappedImageView;;

/**
 * Controller class for the admin dashboard interface.
 * Manages navigation, section loading, and UI interactions for the admin panel.
 */
public class AdminController {
    @FXML
    private ImageView logoImage;

    @FXML
    private VBox logoContainer;

    @FXML
    private VBox mainPane;

    @FXML
    private VBox compass;

    @FXML
    private VBox book;

    @FXML
    private VBox profile;

    @FXML
    private VBox setting;

    @FXML
    private VBox borrow;

    @FXML
    private VBox returns;

    @FXML
    private VBox sectionContainer;

    /**
     * Tracks the currently selected navigation item.
     */
    private VBox currentSelectedItem;

    /**
     * Singleton instance of application state.
     */
    private AppState appState = AppState.getInstance();

    /**
     * Initializes the controller, called automatically by JavaFX after FXML loading.
     * Performs initial setup of various UI components.
     */
    @FXML
    private void initialize() {
        preload();
    }

    /**
     * Performs comprehensive preloading of various UI components.
     * Includes logo, section navigation, dashboard, and initial page setup.
     */
    private void preload() {
        logoPreload();
        sectionPreload();
        adminDashboardPreload();
        pagePreload();
    }

    /**
     * Sets the initial selected page to the compass/dashboard section.
     */
    private void pagePreload() {
        currentSelectedItem = compass;
        compass.setOpacity(1);
    }

    /**
     * Loads and sets up the application logo in the logo container.
     */
    private void logoPreload() {
        Image logo = FileHelper.getImage("paper.png");
        WrappedImageView imageView = new WrappedImageView();
        imageView.setImage(logo);
        logoContainer.getChildren().add(imageView);
    }

    /**
     * Configures navigation sections with images, opacity, and mouse event handlers.
     * Sets up interactive behavior for dashboard navigation items.
     */
    private void sectionPreload() {
        compass.setOpacity(0.4);
        book.setOpacity(0.4);
        profile.setOpacity(0.4);
        setting.setOpacity(0.4);
        borrow.setOpacity(0.4);
        returns.setOpacity(0.4);

        Image homeImage = FileHelper.getImage("dashboard.png");
        Image bookImage = FileHelper.getImage("rbook.png");
        Image profileImage = FileHelper.getImage("ruser.png");
        Image settingImage = FileHelper.getImage("rsetting.png");
        Image borrowImage = FileHelper.getImage("agenda.png");
        Image returnImage = FileHelper.getImage("return.png");

        WrappedImageView homeImageView = new WrappedImageView();
        WrappedImageView bookImageView = new WrappedImageView();
        WrappedImageView profileImageView = new WrappedImageView();
        WrappedImageView settingImageView = new WrappedImageView();
        WrappedImageView borrowImageView = new WrappedImageView();
        WrappedImageView returnImageView = new WrappedImageView();

        compass.setOnMouseEntered(event -> {
            compass.setOpacity(1);
            ScaleEffect.scaleTo(compass, 0.2, 1.2, 1.2);
        });

        compass.setOnMouseExited(event -> {
            if (currentSelectedItem == compass) {
                ScaleEffect.scaleTo(compass, 0.2, 1.1, 1.1);
            }
            else {
                ScaleEffect.scaleTo(compass, 0.2, 1.0, 1.0);
                compass.setOpacity(0.4);
            }
        });

        compass.setOnMouseClicked(event -> {

            if (currentSelectedItem != compass) {
                loadView("AdminDashboardView.fxml");
                currentSelectedItem.setOpacity(0.4);
                compass.setOpacity(1);
                currentSelectedItem = compass;
            }
        });

        book.setOnMouseEntered(event -> {
            book.setOpacity(1);
            ScaleEffect.scaleTo(book, 0.2, 1.2, 1.2);
        });

        book.setOnMouseExited(event -> {
            if (currentSelectedItem == book) {
                ScaleEffect.scaleTo(book, 0.2, 1.1, 1.1);
            }
            else {
                ScaleEffect.scaleTo(book, 0.2, 1.0, 1.0);
                book.setOpacity(0.4);
            }
        });

        book.setOnMouseClicked(event -> {
            if (currentSelectedItem != book) {
                loadView("AdminBookManagerView.fxml");
                currentSelectedItem.setOpacity(0.4);
                book.setOpacity(1);
                currentSelectedItem = book;
            }
        });

        borrow.setOnMouseEntered(event -> {
            borrow.setOpacity(1);
            ScaleEffect.scaleTo(borrow, 0.2, 1.2, 1.2);
        });

        borrow.setOnMouseExited(event -> {
            if (currentSelectedItem == borrow) {
                ScaleEffect.scaleTo(borrow, 0.2, 1.1, 1.1);
            }
            else {
                ScaleEffect.scaleTo(borrow, 0.2, 1.0, 1.0);
                borrow.setOpacity(0.4);
            }
        });

        borrow.setOnMouseClicked(event -> {
            if (currentSelectedItem != borrow) {
                loadView("AdminBookBorrowManagerView.fxml");
                currentSelectedItem.setOpacity(0.4);
                borrow.setOpacity(1);
                currentSelectedItem = borrow;
            }
        });

        profile.setOnMouseEntered(event -> {
            profile.setOpacity(1);
            ScaleEffect.scaleTo(profile, 0.2, 1.2, 1.2);
        });

        profile.setOnMouseExited(event -> {
            if (currentSelectedItem == profile) {
                ScaleEffect.scaleTo(profile, 0.2, 1.1, 1.1);
            }
            else {
                ScaleEffect.scaleTo(profile, 0.2, 1.0, 1.0);
                profile.setOpacity(0.4);
            }
        });

        profile.setOnMouseClicked(event -> {
            if (currentSelectedItem != profile) {
                loadView("AdminUserManagerView.fxml");
                currentSelectedItem.setOpacity(0.4);
                profile.setOpacity(1);
                currentSelectedItem = profile;
            }
        });

        setting.setOnMouseEntered(event -> {
            setting.setOpacity(1);
            ScaleEffect.scaleTo(setting, 0.2, 1.2, 1.2);
        });

        setting.setOnMouseExited(event -> {
            if (currentSelectedItem == setting) {
                ScaleEffect.scaleTo(setting, 0.2, 1.1, 1.1);
            }
            else {
                ScaleEffect.scaleTo(setting, 0.2, 1.0, 1.0);
                setting.setOpacity(0.4);
            }
        });

        setting.setOnMouseClicked(event -> {
            currentSelectedItem.setOpacity(0.4);
            setting.setOpacity(1);
            currentSelectedItem = setting;

            Projerk.getInstance().changeRoot("LoginView.fxml");
        });

        returns.setOnMouseEntered(event -> {
            returns.setOpacity(1);
            ScaleEffect.scaleTo(returns, 0.2, 1.2, 1.2);
        });

        returns.setOnMouseExited(event -> {
            if (currentSelectedItem == returns) {
                ScaleEffect.scaleTo(returns, 0.2, 1.1, 1.1);
            }
            else {
                ScaleEffect.scaleTo(returns, 0.2, 1.0, 1.0);
                returns.setOpacity(0.4);
            }
        });

        returns.setOnMouseClicked(event -> {
            if (currentSelectedItem != returns) {
                loadView("AdminBookReturnManagerView.fxml");
                currentSelectedItem.setOpacity(0.4);
                returns.setOpacity(1);
                currentSelectedItem = returns;
            }
        });

        homeImageView.setImage(homeImage);
        bookImageView.setImage(bookImage);
        profileImageView.setImage(profileImage);
        settingImageView.setImage(settingImage);
        borrowImageView.setImage(borrowImage);
        returnImageView.setImage(returnImage);

        compass.getChildren().add(homeImageView);
        book.getChildren().add(bookImageView);
        profile.getChildren().add(profileImageView);
        setting.getChildren().add(settingImageView);
        borrow.getChildren().add(borrowImageView);
        returns.getChildren().add(returnImageView);
    }

    /**
     * Loads the initial admin dashboard view when the application starts.
     * Sets up the main content pane with the dashboard FXML.
     */
    private void adminDashboardPreload() {
        try {
            FXMLLoader loader = FileHelper.getLoader("AdminDashboardView.fxml");
            StackPane content = loader.load();
            if (loader.getController() instanceof Listener) {
                appState.setListener(loader.getController());
            }
            mainPane.getChildren().setAll(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a specific view into the main content pane.
     *
     * @param path The filename of the FXML view to be loaded
     */
    private void loadView(String path) {
        try {
            FXMLLoader loader = FileHelper.getLoader(path);
            StackPane content = loader.load();
            if (loader.getController() instanceof Listener) {
                appState.setListener(loader.getController());
            }
            mainPane.getChildren().setAll(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}