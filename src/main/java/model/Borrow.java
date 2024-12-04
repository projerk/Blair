package model;

public class Borrow {
    private User user;
    private Book book;

    public Borrow(User user, Book book) {
        this.user = user;
        this.book = book;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

}
