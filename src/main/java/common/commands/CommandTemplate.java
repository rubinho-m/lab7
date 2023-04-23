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
    private volatile CollectionManager collectionManager;
    private volatile DatabaseHandler dbHandler;
    private volatile DatabaseParser dbParser;
    private volatile String arg;
    private volatile Ticket ticket;
    private volatile String user;

    public synchronized DatabaseParser getDbParser() {
        return dbParser;
    }

    public synchronized void setDbParser(DatabaseParser dbParser) {
        this.dbParser = dbParser;
    }

    public synchronized DatabaseHandler getDbHandler() {
        return dbHandler;
    }

    public synchronized void setDbHandler(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public synchronized String getUser() {
        return user;
    }

    public synchronized void setUser(String user) {
        this.user = user;
    }

    public synchronized String getArg() {
        return arg;
    }

    public synchronized void setArg(String arg) {
        this.arg = arg;
    }

    public synchronized Ticket getTicket() {
        return ticket;
    }


    public synchronized void setTicket(Ticket ticket) {
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


    public synchronized CollectionManager getCollectionManager() {
        return collectionManager;
    }

}
