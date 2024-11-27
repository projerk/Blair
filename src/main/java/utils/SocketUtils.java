package utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

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

    public static GuestComment parseComment(JSONObject object) {
        int book_id = object.getInt("book_id");
        int user_id = object.getInt("user_id");
        String content = object.getString("content");
        String guestname = object.getString("username");
        String avatar = object.getString("avatar");

        GuestComment guestComment = new GuestComment(book_id, user_id, content, guestname, avatar, "today");

        return guestComment;
    }
}
