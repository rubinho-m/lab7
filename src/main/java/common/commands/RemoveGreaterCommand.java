/**
 * The RemoveGreaterCommand class implements the execute method of the Command interface
 * to remove all elements in collection which are bigger than argument.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package common.commands;

import common.exceptions.EmptyCollectionException;
import common.networkStructures.Response;
import common.structureClasses.Ticket;
import server.collectionManagement.CollectionManager;
import server.databaseManagement.DatabaseHandler;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class RemoveGreaterCommand extends CommandTemplate implements CommandWithResponse {
    private StringBuilder output;

    public RemoveGreaterCommand(CollectionManager collectionManager, DatabaseHandler dbHandler) {
        super(collectionManager, dbHandler);
    }

    @Override
    public void execute(String user) throws EmptyCollectionException, SQLException {
        Set<Ticket> tickets = getCollectionManager().getCollection();
        output = new StringBuilder();
        if (tickets.size() == 0) {
            output.append("Collection is empty, please add ticket");
        } else {
            output.append("Removed");
        }
        for (Ticket ticket: tickets){
            if (ticket.compareTo(getTicket()) > 0){
                getDbHandler().removeTicket(user, (int) ticket.getId());
            }
        }
        getCollectionManager().setCollection(tickets.stream().
                filter(ticket -> ticket.compareTo(getTicket()) <= 0).
                collect(Collectors.toSet()));

    }

    @Override
    public Response getCommandResponse() {
        return new Response(output.toString());
    }
}
