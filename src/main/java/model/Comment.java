package model;

import org.json.JSONObject;

public class Comment {
    private int book_id;
    private int user_id;
    private String content;

    public Comment(int book_id, int user_id, String content) {
        this.book_id = book_id;
        this.user_id = user_id;
        this.content = content;
    }

    public int getBook_id() {
        return this.book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("book_id", this.book_id);
        json.put("user_id", this.user_id);
        json.put("content", this.content);
        return json;
    }

}
