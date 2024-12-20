package utils;

import java.util.ArrayList;
import javafx.util.Pair;
import model.Borrow;
import model.User;
import model.Borrow;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import model.Book;
import model.GuestComment;

public class SocketUtils {
    private SocketUtils() {

    }

    /**
     * Create an arraylist of Book.
     *
     * @param object the json object get from server
     * @return arraylist contain information for display.
     */
    public static ArrayList<Book> createDisplayBook(JSONArray books) {
        ArrayList<Book> list = new ArrayList<>();

        for (int i = 0; i < books.length(); i++) {
            JSONObject book = books.getJSONObject(i);

            // Read book fields
            int id = book.getInt("id");
            String cover = book.getString("cover");
            String title = book.getString("title");

            Book resBook = new Book();
            resBook.setId(id);
            resBook.setCover(cover);
            resBook.setTitle(title);

            list.add(resBook);
        }

        return list;
    }

    /**
     * Parses a JSON object to create a Book object.
     *
     * @param object the JSON object containing detailed book information
     * @return a Book object populated with data from the JSON object
     */
    public static Book parseBook(JSONObject object) {
        Book book = new Book();
        book.setId(object.getInt("id"));
        book.setTitle(object.getString("title"));
        book.setAuthor(object.getString("author"));
        book.setGenre(object.getString("genre"));
        book.setCover(object.getString("cover"));
        book.setIsbn(object.getString("isbn"));
        book.setRating(object.getDouble("rating"));
        book.setAvailable(object.getInt("available"));
        book.setPublisher(object.getString("publisher"));
        book.setPublishingYear(object.getString("year"));
        book.setDescription(object.getString("description"));
        book.setPdf(object.getString("pdf"));
        book.setBorrow(object.getBoolean("borrow"));
        book.setBorrowID(object.getInt("borrow_id"));;
        // book.setPublisher(object.getString("publisher"));
        // book.setPublishingYear(object.getString("year"));

        return book;
    }

    /**
     * Parses a JSON object to create a GuestComment object.
     *
     * @param object the JSON object containing guest comment information
     * @return a GuestComment object populated with data from the JSON object
     */
    public static GuestComment parseComment(JSONObject object) {
        int book_id = object.getInt("book_id");
        int user_id = object.getInt("user_id");
        String content = object.getString("content");
        String guestname = object.getString("username");
        String avatar = object.getString("avatar");

        GuestComment guestComment = new GuestComment(book_id, user_id, content, guestname, avatar, "today");

        return guestComment;
    }

    /**
     * Parses a JSONArray of recent book finishes and returns a list of pairs
     * containing the finish day and the number of books finished.
     *
     * @param arr The JSONArray containing recent book finish data. 
     * @return A list of {@link Pair} objects, each containing a String (day)
     *         and an Integer (number of books finished).
     */
    public static List<Pair<String, Integer>> parseRecentBookFinish(JSONArray arr) {
        List<Pair<String, Integer>> result = new ArrayList<>();

        if (arr != null) {
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);

                String day = obj.getString("day");
                int bookFinish = obj.getInt("bookfinish");

                result.add(new Pair<String, Integer>(day, bookFinish));
            }
        }

        return result;
    }


    /**
     * Parses a JSONArray of books and returns a list of Book objects.
     *
     * @param books The JSONArray containing book data.
     * @return A list of {@link Book} objects populated with the parsed data.
     */
    public static List<Book> parseBookTableCell(JSONArray books) {
        List<Book> res = new ArrayList<>();

        for (int i = 0; i < books.length(); i++) {
            JSONObject object = books.getJSONObject(i);
            Book book = new Book();
            book.setId(object.getInt("id"));
            book.setCover(object.getString("cover"));
            book.setAuthor(object.getString("author"));
            book.setTitle(object.getString("title"));
            book.setGenre(object.getString("genre"));
            book.setPublishingYear(object.getString("year"));
            book.setIsbn(object.getString("isbn"));
            book.setPublisher(object.getString("publisher"));


            res.add(book);
        }

        return res;
    }


    /**
     * Parses a JSONArray of search results and returns a list of Book objects.
     *
     * @param books The JSONArray containing search result data.
     * @return A list of {@link Book} objects populated with the parsed data.
     */
    public static List<Book> parseSearch(JSONArray books) {
        ArrayList<Book> res = new ArrayList<>();

        for (int i = 0; i < books.length(); i++) {
            JSONObject object = books.getJSONObject(i);
            Book book = new Book();
            book.setId(object.getInt("id"));
            book.setCover(object.getString("cover"));
            book.setAuthor(object.getString("author"));
            book.setTitle(object.getString("title"));
            book.setGenre(object.getString("genre"));

            res.add(book);
        }

        return res;
    }


    /**
     * Parses a JSONArray of borrow transactions and returns a list of Borrow objects.
     *
     * @param borrows The JSONArray containing borrow data.
     * @return A list of {@link Borrow} objects populated with user and book information.
     */
    public static List<Borrow> parseBorrowTableCell(JSONArray borrows) {
        List<Borrow> res = new ArrayList<>();

        for (int i = 0; i < borrows.length(); i++) {
            JSONObject object = borrows.getJSONObject(i);
            Book book = new Book();
            User user = new User();
            book.setId(object.getInt("id"));
            book.setCover(object.getString("cover"));
            book.setAuthor(object.getString("author"));
            book.setTitle(object.getString("title"));
            book.setGenre(object.getString("genre"));
            book.setIsbn(object.getString("isbn"));
            user.setID(object.getInt("user_id"));
            user.setUsername(object.getString("username"));
            Borrow borrow = new Borrow(user, book);
            res.add(borrow);
        }

        return res;
    }
}