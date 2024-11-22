package model;

public class User extends Person {
    private String username;

    /**
     * Constructor of user.
     * 
     * @param username username
     * @param firstName first name
     * @param lastName lastname
     */
    public User(String username, String firstName, String lastName) {
        super(firstName, lastName);
        this.username = username;
    }

    public User(String username, int id, String firstName, String lastName) {
        super(id, firstName, lastName);
        this.username = username;
    }

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
