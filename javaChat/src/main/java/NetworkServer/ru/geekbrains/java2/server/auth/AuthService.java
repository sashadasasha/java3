package NetworkServer.ru.geekbrains.java2.server.auth;

import java.sql.SQLException;

public interface AuthService {

    String getUsernameByLoginAndPassword(String login, String password);
    int getUserIdByLoginAndPassword(String login, String password);

    void start() throws SQLException;
    void stop();

}
