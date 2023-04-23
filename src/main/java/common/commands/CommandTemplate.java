/**
 * CommandTemplate is a class that serves as a template for other command classes.
 * It contains fields and methods that can be used by subclasses to perform their functionality.
 */

package common.commands;

import server.collectionManagement.CollectionManager;
import common.structureClasses.Ticket;
import server.databaseManagement.DatabaseHandler;
import server.databaseManagement.DatabaseParser;

public class CommandTemplate {
    private CollectionManager collectionManager;
    private DatabaseHandler dbHandler;
    private DatabaseParser dbParser;
    private String arg;
    private Ticket ticket;
    private String user;

    public DatabaseParser getDbParser() {
        return dbParser;
    }

    public void setDbParser(DatabaseParser dbParser) {
        this.dbParser = dbParser;
    }

    public DatabaseHandler getDbHandler() {
        return dbHandler;
    }

    public void setDbHandler(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    public Ticket getTicket() {
        return ticket;
    }


    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public CommandTemplate(CollectionManager collectionManager, DatabaseHandler dbHandler) {
        this.collectionManager = collectionManager;
        this.dbHandler = dbHandler;
    }
    public CommandTemplate(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public CommandTemplate() {

    }


    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

}
