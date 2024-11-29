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
}