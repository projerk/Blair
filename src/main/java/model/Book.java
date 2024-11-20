package model;

import database.SQLite;

public class Book {
    private int id;
    private String title;
    private String publisher;
    private String publishingYear;
    private Author author;
    private String isbn;
    private String cover;
    private String genre;
    private String description;
    private int available;
    private double rating;
    private String pdf;
    private boolean borrow;
    private int borrowID;

    public int getBorrowID() {
        return this.borrowID;
    }

    public void setBorrowID(int borrowID) {
        this.borrowID = borrowID;
    }

    public boolean isBorrow() {
        return this.borrow;
    }

    public void setBorrow(boolean borrow) {
        this.borrow = borrow;
    }

    public String getPdf() {
        return this.pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public int getAvailable() {
        return this.available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public double getRating() {
        return this.rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Book() {

    }

    public Book(int id, String title, String publisher, String publishingYear, Author author, String isbn, String genre) {
        this.id = id;
        this.title = title;
        this.publisher = publisher;
        this.publishingYear = publishingYear;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishingYear() {
        return this.publishingYear;
    }

    public void setPublishingYear(String publishingYear) {
        this.publishingYear = publishingYear;
    }

    public Author getAuthor() {
        return this.author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setAuthor(String name) {
        this.author = new Author(name);
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCover() {
        return this.cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publishingYear='" + publishingYear + '\'' +
                ", author=" + author +
                ", isbn='" + isbn + '\'' +
                ", cover='" + cover + '\'' +
                ", genre='" + genre + '\'' +
                ", description='" + description + '\'' +
                ", available=" + available +
                ", rating=" + rating +
                '}';
    }
}
