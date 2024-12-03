package controller;

import animation.ScaleEffect;
import app.AppState;
import app.Projerk;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utils.FileHelper;
import utils.WrappedImageView;

import java.io.IOException;
import java.net.http.WebSocket.Listener;

public class AdminController {
    @FXML
    private ImageView logoImage;

    @FXML 
    private VBox logoContainer;

    @FXML
    private VBox mainPane;

    @FXML
    private void initialize() {
        preload();
    }

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
    private VBox sectionContainer;

    private VBox currentSelectedItem;

    private AppState appState = AppState.getInstance();

    private void preload() {
        logoPreload();
        sectionPreload();
        adminDashboardPreload();
        pagePreload();
    }

    private void pagePreload() {
        currentSelectedItem = compass;
        compass.setOpacity(1);
        // compass.setScaleX(1.1);
        // compass.setScaleY(1.1);
    }

    private void logoPreload() {
        Image logo = FileHelper.getImage("paper.png");
        WrappedImageView imageView = new WrappedImageView();
        imageView.setImage(logo);
        logoContainer.getChildren().add(imageView);
    }

    private void sectionPreload() {
        compass.setOpacity(0.4);
        book.setOpacity(0.4);
        profile.setOpacity(0.4);
        setting.setOpacity(0.4);

        Image homeImage = FileHelper.getImage("rcompass.png");
        Image bookImage = FileHelper.getImage("rbook.png");
        Image profileImage = FileHelper.getImage("ruser.png");
        Image settingImage = FileHelper.getImage("rsetting.png");
        Image borrowImage = FileHelper.getImage("agenda.png");

        WrappedImageView homeImageView = new WrappedImageView();
        WrappedImageView bookImageView = new WrappedImageView();
        WrappedImageView profileImageView = new WrappedImageView();
        WrappedImageView settingImageView = new WrappedImageView();
        WrappedImageView borrowImageView = new WrappedImageView();

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
                loadView("ProfileView.fxml");
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

        homeImageView.setImage(homeImage);
        bookImageView.setImage(bookImage);
        profileImageView.setImage(profileImage);
        settingImageView.setImage(settingImage);
        borrowImageView.setImage(borrowImage);

        compass.getChildren().add(homeImageView);
        book.getChildren().add(bookImageView);
        profile.getChildren().add(profileImageView);
        setting.getChildren().add(settingImageView);
        borrow.getChildren().add(borrowImageView);
    }

    private void adminDashboardPreload() {
        try {
            FXMLLoader loader = FileHelper.getLoader("AdminDashboardView.fxml");
            StackPane content = loader.load();
            if (loader.getController() instanceof Listener) {
                appState.setListener(loader.getController());
            }
            // appState.setListener(loader.getController());
            mainPane.getChildren().setAll(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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