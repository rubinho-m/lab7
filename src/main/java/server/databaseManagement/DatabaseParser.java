package server.databaseManagement;

import common.structureClasses.Ticket;

import java.sql.Connection;
import java.util.Set;

public class DatabaseParser {
    Connection connection;

    public DatabaseParser(Connection connection) {
        this.connection = connection;
    }

    public Set<Ticket> parse(){
        return null;
    }
}
