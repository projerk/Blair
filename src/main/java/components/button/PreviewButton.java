package components.button;

import animation.ScaleEffect;
import components.abstracts.FlexButton;
import api.PDFDownloader;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class PreviewButton extends FlexButton {

    private String PDF_URL;
    private static final String SAVE_DIR = "pdf/";
    private String name;

    public PreviewButton(boolean active, String pdf, String name) {
        super(active);
        setText("PDF");
        this.name = name;
        PDF_URL = pdf;
        setOnMouseEntered(event -> ScaleEffect.scaleTo(this, 0.2, 1.2, 1.2));
        setOnMouseExited(event -> ScaleEffect.scaleTo(this, 0.2, 1.0, 1.0));

        setOnMouseClicked(event -> startDownload());
    }

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
}
