/**
 * CommandTemplate is a class that serves as a template for other command classes.
 * It contains fields and methods that can be used by subclasses to perform their functionality.
 */

package common.commands;

import server.collectionManagement.CollectionManager;
import common.structureClasses.Ticket;

public class CommandTemplate {
    private CollectionManager collectionManager;
    private String arg;
    private Ticket ticket;

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

    public CommandTemplate(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public CommandTemplate() {

    }


    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

}
