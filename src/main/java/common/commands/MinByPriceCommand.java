/**
 * The MinByPriceCommand class implements the execute method of the Command interface
 * to print the ticket which price is minimum.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package common.commands;

import common.exceptions.EmptyCollectionException;
import common.networkStructures.Response;
import common.structureClasses.Ticket;
import server.collectionManagement.CollectionManager;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MinByPriceCommand extends CommandTemplate implements CommandWithResponse{
    private StringBuilder output;
    public MinByPriceCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute() throws EmptyCollectionException {
        Set<Ticket> tickets = getCollectionManager().getCollection();
        output = new StringBuilder();
        if (tickets.size() == 0){
            output.append("Collection is empty, please add ticket");
        }
        Map<Double, Ticket> ticketMap = tickets.stream()
                .collect(Collectors.toMap(Ticket::getPrice, Function.identity()));
        Double minPrice = Collections.min(ticketMap.keySet());
        Ticket ticketToPrint = ticketMap.get(minPrice);
        output.append(ticketToPrint);


    }

    @Override
    public Response getCommandResponse() {
        return new Response(output.toString());
    }
}
