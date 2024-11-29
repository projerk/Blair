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

public class PDFDownloaderAPI {
    private final String pdfUrl;
    private final String saveDir;

    public PDFDownloader(String pdfUrl, String saveDir) {
        this.pdfUrl = pdfUrl;
        this.saveDir = saveDir;
    }

    public Task<Void> createDownloadTask() {
        return new Task<>() {
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

                            while ((bytesRead = in.read(buffer)) != -1) {
                                out.write(buffer, 0, bytesRead);
                                totalBytesRead += bytesRead;

                                System.out.println("Tải xuống: " + totalBytesRead + " bytes");
                            }
                        }
                    } else {
                        System.out.println("Lỗi: Mã phản hồi HTTP " + response.statusCode());
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                return null;
            }
        };
    }
}
