/**
 * The FilterGreaterThanPriceCommand class implements the execute method of the Command interface
 * to show all elements which price is greater than argument.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package common.commands;

import common.networkStructures.Response;
import server.collectionManagement.CollectionManager;
import common.structureClasses.Ticket;

import java.util.Set;
import java.util.stream.Collectors;

public class FilterGreaterThanPriceCommand extends CommandTemplate implements CommandWithResponse{
    private StringBuilder output;
    public FilterGreaterThanPriceCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute() {
        Set<Ticket> tickets = getCollectionManager().getCollection();
        output = new StringBuilder();
        String result = tickets.stream()
                .filter(ticket -> ticket.getPrice() > Float.parseFloat(getArg()))
                .map(Ticket::toString)
                .collect(Collectors.joining());
        output.append(result);

    }

    @Override
    public Response getCommandResponse() {
        return new Response(output.toString());
    }
}
