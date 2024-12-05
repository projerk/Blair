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

public class PDFEngine extends PDFView {
    public PDFEngine() {

    }

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
