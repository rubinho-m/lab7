package server.databaseManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private final String url;
    private final String login;
    private final String password;
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public DatabaseManager(String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;
    }


    public  void connect() {
        try {
            connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            System.out.println("SOME TROUBLES WITH SQL");
            System.exit(1);
        }
    }


}
