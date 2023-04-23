package server.databaseManagement;

import common.structureClasses.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class DatabaseParser {
    Connection connection;

    public DatabaseParser(Connection connection) {
        this.connection = connection;
    }

    public Set<Ticket> loadCollection() throws SQLException {
        final String LOAD_REQUEST = "SELECT t.id, t.localdate, t.name, t.x_coord, t.y_coord, t.price, t.comment," +
                " t.refundable, t.ticket_type, v.id, v.name, v.capacity, v.venue_type, a.id, a.street, u.login\n" +
                "FROM tickets t LEFT JOIN venues v ON t.venue = v.id\n" +
                "LEFT JOIN adresses a ON v.address = a.id\n" +
                "INNER JOIN users u ON t.user_id = u.id;";
        Set<Ticket> tickets = new HashSet<>();
        try (PreparedStatement loadStatement = connection.prepareStatement(LOAD_REQUEST)) {
            ResultSet resultSet = loadStatement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                LocalDate creationDate = LocalDate.parse(resultSet.getDate(2).toString());
                String name = resultSet.getString(3);
                Coordinates coordinates = new Coordinates();
                float x = resultSet.getFloat(4);
                int y = resultSet.getInt(5);
                coordinates.setX(x);
                coordinates.setY(y);
                int price = resultSet.getInt(6);
                String comment = resultSet.getString(7);
                boolean refundable = resultSet.getBoolean(8);
                String user = resultSet.getString(16);
                String type_ticket = resultSet.getString(9);
                TicketType ticketType = null;
                if (type_ticket != null){
                    ticketType = TicketType.valueOf(type_ticket);
                }


                Venue venue = null;
                int venue_id = resultSet.getInt(10);
                if (!resultSet.wasNull()){
                    venue = new Venue();
                    venue.setId(venue_id);
                    venue.setName(resultSet.getString(11));
                    venue.setCapacity(resultSet.getLong(12));
                    String type_venue = resultSet.getString(13);
                    VenueType venueType = null;
                    if (type_venue != null) {
                        venueType = VenueType.valueOf(type_venue);
                    }
                    venue.setType(venueType);
                    Address address = null;
                    int address_id = resultSet.getInt(14);
                    if (!resultSet.wasNull()){
                        address = new Address();
                        String street = resultSet.getString(15);
                        address.setStreet(street);
                    }
                    venue.setAddress(address);
                }
                Ticket ticket = new Ticket();
                ticket.setId(id);
                ticket.setCreationDate(creationDate);
                ticket.setName(name);
                ticket.setCoordinates(coordinates);
                ticket.setPrice(price);
                ticket.setComment(comment);
                ticket.setRefundable(refundable);
                ticket.setType(ticketType);
                ticket.setUser(user);
                ticket.setVenue(venue);
                tickets.add(ticket);
            }


        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return tickets;
    }
}
