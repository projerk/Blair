package components.media;

import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import com.dlsc.pdfviewfx.PDFBoxDocument;
import com.dlsc.pdfviewfx.PDFView;
import javafx.scene.layout.StackPane;

/**
 * A specialized PDF view engine for loading PDF documents from URLs.
 *
 * Extends PDFView to provide convenient URL-based PDF loading capabilities.
 */
public class PDFEngine extends PDFView {
    /**
     * Constructs an empty PDFEngine instance.
     */
    public PDFEngine() {

    }

    /**
     * Loads a PDF document from the specified URL.
     *
     * Establishes an HTTP connection, retrieves the input stream,
     * and loads the PDF document using PDFBoxDocument.
     *
     * @param url The URL of the PDF document to load
     * @throws Exception If there are issues opening the connection or loading the PDF
     */
    public final void load(URL url) throws Exception {
        Objects.requireNonNull(url, "url can not be null");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);

        InputStream inputStream = connection.getInputStream();

        this.load(() -> {
            return new PDFBoxDocument(inputStream);
        });
    }
}