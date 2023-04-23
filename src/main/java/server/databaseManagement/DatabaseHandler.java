package server.databaseManagement;

import common.structureClasses.Address;
import common.structureClasses.Ticket;
import common.structureClasses.Venue;

import java.sql.*;

public class DatabaseHandler {
    Connection connection;

    public DatabaseHandler(Connection connection) {
        this.connection = connection;
    }

    public synchronized int register(String login, String password) throws SQLException {
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

    public synchronized int auth(String login, String password) throws SQLException {
        final String AUTH_REQUEST = "SELECT * FROM users WHERE login = ?;";
        try (PreparedStatement authStatement = connection.prepareStatement(AUTH_REQUEST)) {

            authStatement.setString(1, login);

            ResultSet resultSet = authStatement.executeQuery();

            if (resultSet.next()) {
                String returnedLogin = resultSet.getString("login");
                String returnedPassword = resultSet.getString("password");
                if (returnedPassword.equals(password)) {
                    return 2; // успешно
                }
                return 3; // пароли не совпадают
            }

            return 4; // нет такого пользователя в бд
        }

    }

    public synchronized void deleteTickets(String user) throws SQLException {
        final String DELETE_REQUEST = "DELETE FROM tickets WHERE user_id = (SELECT id FROM users WHERE login = ?);";
        try (PreparedStatement deleteStatement = connection.prepareStatement(DELETE_REQUEST)) {
            deleteStatement.setString(1, user);
            deleteStatement.executeUpdate();
        }

    }

    public synchronized void removeTicket(String user, int id) throws SQLException {
        final String REMOVE_REQUEST = "DELETE FROM tickets WHERE user_id = (SELECT id FROM users WHERE login = ?) AND id = ?;";
        try (PreparedStatement removeStatement = connection.prepareStatement(REMOVE_REQUEST)) {
            removeStatement.setString(1, user);
            removeStatement.setInt(2, id);
            removeStatement.executeUpdate();
        }
    }
    public synchronized int getUserId (String user) throws SQLException {
        final String USER_REQUEST = "SELECT * FROM users WHERE login = ?;";
        try (PreparedStatement userStatement = connection.prepareStatement(USER_REQUEST)) {
            userStatement.setString(1, user);
            ResultSet resultSet = userStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        }

    }

    public synchronized int addTicket(Ticket ticket) throws SQLException {
        final String ADD_REQUEST = "INSERT INTO tickets (localdate, name, x_coord, y_coord, price, comment, refundable, ticket_type, venue, user_id)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;";
        int user_id = getUserId(ticket.getUser());
        Integer venue_id = null;
        if (ticket.getVenue() != null){
            venue_id = addVenue(ticket.getVenue());
        }
        try (PreparedStatement addStatement = connection.prepareStatement(ADD_REQUEST)) {
            addStatement.setDate(1, Date.valueOf(ticket.getCreationDate()));
            addStatement.setString(2, ticket.getName());
            addStatement.setFloat(3, ticket.getCoordinates().getX());
            addStatement.setInt(4, ticket.getCoordinates().getY());
            addStatement.setFloat(5, (float) ticket.getPrice());
            addStatement.setString(6, ticket.getComment());
            addStatement.setBoolean(7, ticket.isRefundable());
            if (ticket.getType() != null) {
                addStatement.setString(8, String.valueOf(ticket.getType()));
            } else {
                addStatement.setNull(8, Types.NULL);
            }
            if (venue_id != null) {
                addStatement.setInt(9, venue_id);
            } else {
                addStatement.setNull(9, Types.NULL);
            }
            addStatement.setInt(10, user_id);
            ResultSet resultSet = addStatement.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt(1);
            }


        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public synchronized int addVenue(Venue venue) throws SQLException {
        final String ADD_REQUEST = "INSERT INTO venues (name, capacity, venue_type, address) VALUES (?, ?, ?, ?);";
        Integer address_id = null;
        if (venue.getAddress() != null) {
            address_id = addAddress(venue.getAddress());
        }
        try (PreparedStatement addStatement = connection.prepareStatement(ADD_REQUEST)) {
            addStatement.setString(1, venue.getName());
            addStatement.setLong(2, venue.getCapacity());
            addStatement.setString(3, String.valueOf(venue.getType()));
            if (address_id != null) {
                addStatement.setInt(4, address_id);
            } else {
                addStatement.setNull(4, Types.NULL);
            }
            ResultSet resultSet = addStatement.executeQuery();
            return resultSet.getInt(1);
        }
    }

    public synchronized int addAddress(Address address) throws SQLException {
        final String ADD_REQUEST = "INSERT INTO adresses (street) VALUES (?) RETURNING id;";
        try (PreparedStatement addStatement = connection.prepareStatement(ADD_REQUEST)) {
            addStatement.setString(1, address.getStreet());
            ResultSet resultSet = addStatement.executeQuery();
            return resultSet.getInt(1);
        }
    }
}
