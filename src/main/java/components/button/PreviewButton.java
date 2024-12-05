package components.button;

import animation.ScaleEffect;
import components.abstracts.FlexButton;
import components.media.PDFEngine;
import api.PDFDownloader;
import app.AppState;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import utils.Hasher;
import com.dlsc.pdfviewfx.PDFView;

import java.net.URI;

import com.dansoftware.pdfdisplayer.PDFDisplayer;

public class PreviewButton extends FlexButton {

    private String PDF_URL;
    private static final String SAVE_DIR = "pdf/";
    private String name;
    private static PDFEngine displayer = new PDFEngine();

    private static Alert alert = new Alert(Alert.AlertType.NONE);
    private static ButtonType closeButton = new ButtonType("Close");

    static {
        alert.setTitle("PDF VIEWER");
        alert.getButtonTypes().add(closeButton);
    }

    /**
     * Constructs a PreviewButton for opening and downloading PDF files.
     *
     * @param active Determines if the button is initially active
     * @param pdf The URL of the PDF file to be previewed
     * @param name The name to be used for saving the PDF file
     */
    public PreviewButton(boolean active, String pdf, String name) {
        super(active);
        setText("PDF");
        this.name = name;
        PDF_URL = pdf;
        setOnMouseEntered(event -> ScaleEffect.scaleTo(this, 0.2, 1.2, 1.2));
        setOnMouseExited(event -> ScaleEffect.scaleTo(this, 0.2, 1.0, 1.0));

        setOnMouseClicked(event -> {
            openPDF();
        });
    }

    /**
     * Initiates the download of the PDF file.
     * Disables the button during download and updates its text to show progress.
     * Creates a download task that runs on a separate thread.
     */
    private void startDownload() {
        setDisable(true);
        setText("Downloading...");

        PDFDownloader downloader = new PDFDownloader(PDF_URL, SAVE_DIR + name + ".pdf");
        Task<Void> downloadTask = downloader.createDownloadTask();

        downloadTask.setOnSucceeded(event -> {
            Platform.runLater(() -> {
                setDisable(false);
                setText("Finished!");
            });
        });

        downloadTask.setOnFailed(event -> {
            Platform.runLater(() -> {
                setDisable(false);
                setText("Error!");
            });
        });

        new Thread(downloadTask).start();
    }

    /**
     * Opens the PDF file in a viewer.
     * Loads the PDF asynchronously on a separate thread.
     * Creates an alert dialog to display the PDF content.
     * Updates button state and text during the loading process.
     */
    private void openPDF() {
        Platform.runLater(() -> {
            setDisable(true);
            setText("Loading...");
            displayer = new PDFEngine();
        });

        Task<Void> loadPdfTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    displayer.load(new URI(PDF_URL).toURL());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void succeeded() {
                Platform.runLater(() -> {
                    setDisable(false);
                    setText("PDF");
                    StackPane pane = new StackPane(displayer);
                    pane.setPrefWidth(1275);
                    pane.setPrefHeight(720);
                    alert.getDialogPane().setContent(pane);
                    alert.show();
                });
            }

            @Override
            protected void failed() {
                Platform.runLater(() -> {
                    System.out.println("Error loading PDF.");
                });
            }
        };

        Thread thread = new Thread(loadPdfTask);
        thread.setDaemon(true);
        thread.start();
    }
}