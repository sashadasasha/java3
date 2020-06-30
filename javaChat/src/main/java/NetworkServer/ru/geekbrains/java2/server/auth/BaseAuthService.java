package NetworkServer.ru.geekbrains.java2.server.auth;

import NetworkServer.ru.geekbrains.java2.server.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService {

    private static class UserData {
        private String login;
        private String password;
        private String username;
        private int id;

        public UserData(String login, String password, String username, int id) {
            this.login = login;
            this.password = password;
            this.username = username;
            this.id = id;
        }
    }



    private static List<UserData> userData = new ArrayList<>();
    static{
        try {
            userData = getListOfUsersFromDb();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public static List<UserData> getListOfUsersFromDb () throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        List<UserData> listOfUsersFromDb = new ArrayList<>();
        try {
            conn = new DatabaseConnection().connect();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                listOfUsersFromDb.add(new UserData(rs.getString("login"), rs.getString("password"), rs.getString("name"), rs.getInt("id")));
            }
            return listOfUsersFromDb;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            assert stmt != null;
            stmt.close();
            conn.close();
        }
        return null;
    }

    @Override
    public String getUsernameByLoginAndPassword(String login, String password) {
        for (UserData userDatum : userData) {
            if (userDatum.login.equals(login) && userDatum.password.equals(password)) {
                return userDatum.username;
            }
        }
        return null;
    }

    @Override
    public int getUserIdByLoginAndPassword(String login, String password) {
        for (UserData userDatum : userData) {
            if (userDatum.login.equals(login) && userDatum.password.equals(password)) {
                return userDatum.id;
            }
        }
        return 0;
    }



    @Override
    public void start() {
        System.out.println("Сервис аутентификации запущен");
    }

    @Override
    public void stop() {
        System.out.println("Сервис аутентификации остановлен");
    }
}
