package api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.concurrent.Task;

public class GoogleBooksAPI {

    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes";

    /**
     * Tìm kiếm sách qua ISBN và trả về thông tin sách.
     * 
     * @param isbn ISBN của sách.
     * @return Thông tin sách dưới dạng chuỗi JSON.
     * @throws Exception Nếu có lỗi khi gọi API.
     */
    public static String searchBookByISBN(String isbn) throws Exception {
        String query = API_URL + "?q=isbn:" + isbn;
        HttpURLConnection connection = (HttpURLConnection) new URL(query).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new Exception("HTTP Error: " + responseCode);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return parseBookInfo(response.toString());
    }

    /**
     * Phân tích thông tin sách từ JSON trả về.
     * 
     * @param jsonResponse Chuỗi JSON từ Google Books API.
     * @return Thông tin sách ở định dạng chuỗi JSON.
     */
    private static String parseBookInfo(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        if (!jsonObject.has("items")) {
            return "Không tìm thấy sách với ISBN này.";
        }

        JSONObject volumeInfo = jsonObject.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo");

        String title = volumeInfo.optString("title", "No Title");
        JSONArray authorsArray = volumeInfo.optJSONArray("authors");
        String authors = authorsArray != null ? String.join(", ", authorsArray.toList().toArray(new String[0])) : "No Authors";
        String publishedDate = volumeInfo.optString("publishedDate", "No Date");
        String publisher = volumeInfo.optString("publisher", "No Publisher");
        int pageCount = volumeInfo.optInt("pageCount", 0);
        JSONArray categoriesArray = volumeInfo.optJSONArray("categories");
        String categories = categoriesArray != null ? String.join(", ", categoriesArray.toList().toArray(new String[0])) : "No Categories";
        String description = volumeInfo.optString("description", "No Description");
        JSONObject imageLinks = volumeInfo.optJSONObject("imageLinks");
        String thumbnail = imageLinks != null ? imageLinks.optString("thumbnail", "No Thumbnail") : "No Thumbnail";

        // Kết hợp thông tin sách
        return String.format("""
                {
                    "title": "%s",
                    "authors": "%s",
                    "publishedDate": "%s",
                    "publisher": "%s",
                    "pageCount": %d,
                    "categories": "%s",
                    "description": "%s",
                    "thumbnail": "%s"
                }
                """, title, authors, publishedDate, publisher, pageCount, categories, description, thumbnail);
    }

    /**
     * Tạo một Task để tìm kiếm sách qua ISBN.
     * 
     * @param isbn ISBN của sách.
     * @return Task thực hiện tìm kiếm.
     */
    public static Task<String> createBookSearchTask(String isbn) {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                return searchBookByISBN(isbn);
            }
        };
    }

    public static void main(String[] args) {
        String isbn = "9780134685991"; 

        Task<String> bookSearchTask = createBookSearchTask(isbn);

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