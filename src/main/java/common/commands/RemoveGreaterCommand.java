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

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RemoveGreaterCommand extends CommandTemplate implements CommandWithResponse {
    private StringBuilder output;

    public RemoveGreaterCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute() throws EmptyCollectionException {
        Set<Ticket> tickets = getCollectionManager().getCollection();
        output = new StringBuilder();
        if (tickets.size() == 0) {
            output.append("Collection is empty, please add ticket");
        } else {
            output.append("Removed");
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
