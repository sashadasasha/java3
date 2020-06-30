package NetworkClientServer.ru.geekbrains.java2.client.command;

import java.io.Serializable;

public class AuthCommand implements Serializable {

    private final String login;
    private final String password;

    private String username;
    private int id;

    public AuthCommand(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(int id) {
        this.id = id;
    }
}
