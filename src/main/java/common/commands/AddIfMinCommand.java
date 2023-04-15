/**
 * The AddIfMinCommand class implements the execute method of the Command interface
 * to adding the new element into the collection if it's smaller than minimum of collection.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package common.commands;

import common.networkStructures.Response;
import server.collectionManagement.CollectionManager;
import server.collectionManagement.CommandExecutor;
import common.exceptions.EmptyCollectionException;
import common.structureClasses.Ticket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class AddIfMinCommand extends CommandTemplate implements CommandWithResponse{
    public AddIfMinCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute() throws Exception {
        Ticket ticketToAdd = getTicket();
        Set<Ticket> tickets = getCollectionManager().getCollection();
        if (tickets.size() == 0){
            throw new EmptyCollectionException();
        }
        List<Ticket> sortedTickets = new ArrayList<>(tickets);
        Collections.sort(sortedTickets);
        Ticket minTicket = sortedTickets.get(0);
        if (ticketToAdd.compareTo(minTicket) < 0){
            Command addCommand = CommandExecutor.getCommandMap().get("add");
            addCommand.setTicket(ticketToAdd);
            addCommand.execute();
        }



    }

    @Override
    public Response getCommandResponse() {
        return new Response("Added");
    }
}
