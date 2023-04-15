/**
 * The RemoveByIdCommand class implements the execute method of the Command interface
 * to remove the element according to its id.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package common.commands;

import common.exceptions.EmptyCollectionException;
import common.networkStructures.Response;
import common.structureClasses.Ticket;
import server.collectionManagement.CollectionManager;

public class RemoveByIdCommand extends CommandTemplate implements CommandWithResponse{
    private String output;
    public RemoveByIdCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute() throws EmptyCollectionException {
        CollectionManager collectionManager = getCollectionManager();
        if (collectionManager.getCollection().size() == 0){
            output = "Collection is empty, please add ticket";
        } else {
            output = "Removed";
        }
        for (Ticket ticketToRemove : collectionManager.getCollection()){
            if (ticketToRemove.getId() == Integer.parseInt(getArg())){
                collectionManager.getCollection().remove(ticketToRemove);
                break;
            }
        }
    }

    @Override
    public Response getCommandResponse() {
        return new Response(output);
    }
}
