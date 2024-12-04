package api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import model.Book;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.concurrent.Task;

public class GoogleBooksAPI {

    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes";
    private static final String KEY = "&key=AIzaSyBhMm6LPXdhV-GHY4zzBmd9ZbCCoxQRbsc";


    /**
     * Tìm kiếm sách qua ISBN và trả về thông tin sách.
     * 
     * @param isbn ISBN của sách.
     * @return Thông tin sách dưới dạng chuỗi JSON.
     * @throws Exception Nếu có lỗi khi gọi API.
     */
    public static Book searchBookByISBN(String isbn) throws Exception {
        String query = API_URL + "?q=isbn:" + isbn + KEY;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(query))
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int responseCode = response.statusCode();
        if (responseCode != 200) {
            throw new Exception("HTTP Error: " + responseCode);
        }

        return parseBookInfo(response.body());
    }

    /**
     * Phân tích thông tin sách từ JSON trả về.
     * 
     * @param jsonResponse Chuỗi JSON từ Google Books API.
     * @return Thông tin sách ở định dạng chuỗi JSON.
     */
    private static Book parseBookInfo(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        if (!jsonObject.has("items")) {
            System.out.println("Không tìm thấy sách với ISBN này.");
            return null; // Nếu không tìm thấy sách, trả về null.
        }

        JSONObject volumeInfo = jsonObject.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo");

        String title = volumeInfo.optString("title", "No Title");
        JSONArray authorsArray = volumeInfo.optJSONArray("authors");
        String authors = authorsArray != null ? convertJSONArrayToString(authorsArray) : "No Authors";
        String publishedDate = volumeInfo.optString("publishedDate", "No Date");
        String publisher = volumeInfo.optString("publisher", "No Publisher");
        int pageCount = volumeInfo.optInt("pageCount", 0);
        JSONArray categoriesArray = volumeInfo.optJSONArray("categories");
        String categories = categoriesArray != null ? convertJSONArrayToString(categoriesArray) : "No Categories";
        String description = volumeInfo.optString("description", "No Description");
        JSONObject imageLinks = volumeInfo.optJSONObject("imageLinks");
        String thumbnail = imageLinks != null ? imageLinks.optString("thumbnail", "No Thumbnail") : "No Thumbnail";

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(authors);
        book.setGenre(categories);
        book.setDescription(description);
        book.setCover(thumbnail);
        book.setPublishingYear(publishedDate); // Thay vì "2000"
        book.setPublisher(publisher);

        // Tạo đối tượng Book và trả về
        return book;
    }

    /**
     * Chuyển đổi JSONArray thành String.
     *
     * @param jsonArray Mảng JSON.
     * @return Chuỗi kết hợp các phần tử trong mảng.
     */
    private static String convertJSONArrayToString(JSONArray jsonArray) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < jsonArray.length(); i++) {
            stringBuilder.append(jsonArray.getString(i));
            if (i < jsonArray.length() - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Tạo một Task để tìm kiếm sách qua ISBN.
     * 
     * @param isbn ISBN của sách.
     * @return Task thực hiện tìm kiếm.
     */
    public static Task<Book> createBookSearchTask(String isbn) {
        return new Task<>() {
            @Override
            protected Book call() throws Exception {
                return searchBookByISBN(isbn);
            }
        };
    }

    /**
     * git add.
     * 
     * @param args hello.
     */
    public static void main(String[] args) {
        String isbn = "9780134685991";

        Task<Book> bookSearchTask = createBookSearchTask(isbn);

        bookSearchTask.setOnSucceeded(event -> {
            System.out.println("\nThông tin sách:");
            System.out.println(bookSearchTask.getValue());
        });

        bookSearchTask.setOnFailed(event -> {
            System.err.println("Lỗi: " + bookSearchTask.getException().getMessage());
        });

        Thread thread = new Thread(bookSearchTask);
        thread.setDaemon(true); // Đảm bảo thread dừng khi chương trình kết thúc
        thread.start();
    }
}