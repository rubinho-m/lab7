package server.databaseManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler {
    Connection connection;

    public DatabaseHandler(Connection connection) {
        this.connection = connection;
    }

    public int register(String login, String password) throws SQLException {
        final String REG_REQUEST = "SELECT * FROM users WHERE login = ?";


        try (PreparedStatement regStatement = connection.prepareStatement(REG_REQUEST)) {

            regStatement.setString(1, login);

            ResultSet resultSet = regStatement.executeQuery();

            if (resultSet.next()) {
                return 1; // пользователь уже есть
            }
            final String ADD_REQUEST = "INSERT INTO users (login, password) VALUES (?, ?);";
            try (PreparedStatement addStatement = connection.prepareStatement(ADD_REQUEST)) {
                addStatement.setString(1, login);
                addStatement.setString(2, password);
                addStatement.executeUpdate();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return 0; // успешно
        }
    }

    public int auth(String login, String password) throws SQLException {
        final String AUTH_REQUEST = "SELECT * FROM users WHERE login = ?;";
        try (PreparedStatement authStatement = connection.prepareStatement(AUTH_REQUEST)) {

            authStatement.setString(1, login);

            ResultSet resultSet = authStatement.executeQuery();

            if (resultSet.next()) {
                String returnedLogin = resultSet.getString("login");
                String returnedPassword = resultSet.getString("password");
                if (returnedPassword.equals(password)){
                    return 2; // успешно
                }
                return 3; // пароли не совпадают
            }

            return 4; // нет такого пользователя в бд
        }

    }

}
