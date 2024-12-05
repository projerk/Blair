package model;

public class User extends Person {
    private String username;
    private String email;
    private String avatar;
    private Boolean verified;
    private String role;
    private String password;

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getVerified() {
        return this.verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

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
