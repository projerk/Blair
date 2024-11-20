package model;

public class Author extends Person {
    private String bio;
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public Author(String name) {
        super();
        this.name = name;
    }
}
