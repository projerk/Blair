package api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.concurrent.Task;
import model.Book;

/**
 * GoogleBooksAPI is a utility class for interacting with the Google Books API.
 * It provides methods for searching books by ISBN and returning information about the book in a {@link Book} object.
 */
public class GoogleBooksAPI {

    // Google Books API URL and key
    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes";
    private static final String KEY = "&key=AIzaSyBhMm6LPXdhV-GHY4zzBmd9ZbCCoxQRbsc";

    /**
     * Searches for a book by its ISBN and returns the book information as a {@link Book} object.
     *
     * @param isbn ISBN of the book to search for.
     * @return A {@link Book} object containing information about the book.
     * @throws Exception If there is an error while calling the API.
     */
    public static Book searchBookByISBN(String isbn) throws Exception {
        String query = API_URL + "?q=isbn:" + isbn + KEY;

        // Create HttpClient and HttpRequest
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(query))
                .header("Accept", "application/json")
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int responseCode = response.statusCode();
        if (responseCode != 200) {
            throw new Exception("HTTP Error: " + responseCode);
        }

        // Parse the response
        return parseBookInfo(response.body());
    }

    /**
     * Parses the JSON response from the Google Books API and creates a {@link Book} object.
     *
     * @param jsonResponse The JSON response string from the Google Books API.
     * @return A {@link Book} object containing information about the book.
     */
    private static Book parseBookInfo(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        if (!jsonObject.has("items")) {
            System.out.println("Không tìm thấy sách với ISBN này.");
            return null; // Return null if no book is found.
        }

        JSONObject volumeInfo = jsonObject.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo");

        // Extract book details from the JSON object
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

        // Create a new Book object and set its properties
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(authors);
        book.setGenre(categories);
        book.setDescription(description);
        book.setCover(thumbnail);
        book.setPublishingYear(publishedDate);
        book.setPublisher(publisher);

        return book;
    }

    /**
     * Converts a {@link JSONArray} of authors or categories into a comma-separated string.
     *
     * @param jsonArray The JSON array of authors or categories.
     * @return A string containing all elements of the array separated by commas.
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
     * Creates a {@link Task} to search for a book by its ISBN asynchronously.
     *
     * @param isbn The ISBN of the book to search for.
     * @return A {@link Task} object that performs the book search.
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
     * Main method to demonstrate searching for a book by ISBN and printing the results.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        String isbn = "9780134685991"; // ISBN of the book to search for

        Task<Book> bookSearchTask = createBookSearchTask(isbn);

        // Set actions for when the task is completed successfully
        bookSearchTask.setOnSucceeded(event -> {
            Book book = bookSearchTask.getValue();
            if (book != null) {
                System.out.println("\nThông tin sách:");
                System.out.println(book);
            } else {
                System.out.println("Không tìm thấy sách.");
            }
        });

        // Set actions for when the task fails
        bookSearchTask.setOnFailed(event -> {
            System.err.println("Lỗi: " + bookSearchTask.getException().getMessage());
        });

        // Start the task on a new thread
        Thread thread = new Thread(bookSearchTask);
        thread.setDaemon(true); // Ensure the thread terminates when the program ends
        thread.start();
    }
}
