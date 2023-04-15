/**
 * The ClearCommand class implements the execute method of the Command interface
 * to delete all elements from the collection.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package common.commands;

import common.networkStructures.Response;
import server.collectionManagement.CollectionManager;

public class ClearCommand extends CommandTemplate implements CommandWithResponse{
    private StringBuilder output;
    public ClearCommand(CollectionManager collectionManager){
        super(collectionManager);
    }
    @Override
    public void execute() {
        getCollectionManager().getCollection().clear();
        output = new StringBuilder();
        output.append("Now collection is empty");
    }

    @Override
    public Response getCommandResponse() {
        return new Response(output.toString());
    }
}
