/**
 * The UpdateCommand class implements the execute method of the Command interface
 * to update the element in collection according to its id.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package common.commands;

import common.exceptions.EmptyCollectionException;
import common.networkStructures.Response;
import common.structureClasses.Ticket;
import server.collectionManagement.CollectionManager;

import java.util.Objects;
import java.util.Set;

public class UpdateCommand extends CommandTemplate implements CommandWithResponse{
    private String output = null;
    public UpdateCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute() throws EmptyCollectionException {
        CollectionManager collectionManager = getCollectionManager();
        Set<Ticket> tickets = collectionManager.getCollection();
        if (tickets.size() == 0){
            output = "Collection is empty";
        }
        for (Ticket ticketToUpdate: tickets){
            if (ticketToUpdate.getId() == Integer.parseInt(getArg())){
                tickets.remove(ticketToUpdate);
                break;
            }
        }
        Ticket newTicket = getTicket();
        Long tmpId = Ticket.getLastId();
        Ticket.setLastId((long) Integer.parseInt(getArg()) - 1);
        collectionManager.addToCollection(newTicket);
        Ticket.setLastId(tmpId);
    }

    @Override
    public Response getCommandResponse() {
        return new Response(Objects.requireNonNullElse(output, "Element has been updated"));
    }
}
