/**
 * The PrintFieldDescendingCommand class implements the execute method of the Command interface
 * to show collection in sort of venues.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package common.commands;

import common.exceptions.EmptyCollectionException;
import common.networkStructures.Response;
import common.structureClasses.Ticket;
import common.structureClasses.Venue;
import server.collectionManagement.CollectionManager;

import java.util.*;
import java.util.stream.Collectors;

public class PrintFieldDescendingVenueCommand extends CommandTemplate implements CommandWithResponse{
    private StringBuilder output;
    private boolean flag;
    public PrintFieldDescendingVenueCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute(String user) throws EmptyCollectionException {
        try {

            output = new StringBuilder();
            flag = true;
            if (getCollectionManager().getCollection().isEmpty()) {
                output.append("Collection is empty, please add ticket");
            }
            List<Venue> venues = getCollectionManager().getCollection().stream()
                    .map(Ticket::getVenue)
                    .filter(Objects::nonNull)
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());

            venues.forEach(venue -> {
                output.append(venue);
                flag = false;
            });



            if (flag) {
                output.append("No tickets have venues");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Response getCommandResponse() {
        System.out.println(output.toString());
        return new Response(output.toString());
    }
}
