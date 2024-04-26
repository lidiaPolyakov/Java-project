package il.ac.shenkar.includes;

public class User {

    private String username;
    private String password;
    private Integer id;

    public User() {}

    /* Constructor for creating a new User object with an ID, username, and password. */
    public User(Integer id, String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
        this.setId(id);
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
