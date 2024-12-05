package api;

import javafx.concurrent.Task;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;

/**
 * Class for downloading a PDF file from a specified URL and saving it to a directory.
 * This class handles the process of creating an HTTP request to download the file
 * and saving it locally in the specified directory.
 * <p>
 * The download is performed in a background thread to prevent blocking the main UI thread.
 * </p>
 */
public class PDFDownloader {

    /**
     * URL of the PDF file to be downloaded.
     */
    private final String pdfUrl;

    /**
     * Directory where the PDF file will be saved.
     */
    private final String saveDir;

    /**
     * Constructs a PDFDownloader object with the provided URL and save directory.
     *
     * @param pdfUrl  The URL of the PDF file to be downloaded.
     * @param saveDir The directory where the downloaded PDF will be saved.
     */
    public PDFDownloader(String pdfUrl, String saveDir) {
        this.pdfUrl = pdfUrl;
        this.saveDir = saveDir;
    }

    /**
     * Creates a Task to download the PDF file asynchronously. The task fetches the PDF
     * from the given URL and writes it to the specified directory.
     * <p>
     * The download process runs on a separate thread to avoid blocking the main UI.
     * </p>
     *
     * @return A Task that downloads the PDF file.
     */
    public Task<Void> createDownloadTask() {
        return new Task<>() {
            /**
             * The method that performs the file download.
             * It makes an HTTP request to fetch the file and writes it to the disk.
             *
             * @return null after the task completes.
             * @throws Exception If an error occurs during the download process.
             */
            @Override
            protected Void call() throws Exception {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(pdfUrl))
                        .build();

                try {
                    HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

                    if (response.statusCode() == 200) {
                        try (InputStream in = response.body();
                             OutputStream out = new FileOutputStream(Paths.get(saveDir).toFile())) {

                            byte[] buffer = new byte[1024];
                            int bytesRead;
                            long totalBytesRead = 0;

                            // Reads the file in chunks and writes it to disk
                            while ((bytesRead = in.read(buffer)) != -1) {
                                out.write(buffer, 0, bytesRead);
                                totalBytesRead += bytesRead;

                                // Optionally prints progress
                                System.out.println("Tải xuống: " + totalBytesRead + " bytes");
                            }
                        }
                    } else {
                        System.out.println("Lỗi: Mã phản hồi HTTP " + response.statusCode());
                    }
                } catch (Exception e) {
                    // Print any errors during the download process
                    System.out.println("Error: " + e.getMessage());
                }
                return null;
            }
        };
    }
}
