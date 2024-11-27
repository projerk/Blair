package model;

public class GuestComment extends Comment {
    private String guestname;
    private String avatar;
    private String datetime;

    public GuestComment(int book_id, int user_id, String content, String guestname, String avatar, String datetime) {
        super(book_id, user_id, content);
        this.guestname = guestname;
        this.avatar = avatar;
        this.datetime = datetime;
    }

    public String getGuestname() {
        return this.guestname;
    }

    public void setGuestname(String guestname) {
        this.guestname = guestname;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDatetime() {
        return this.datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
