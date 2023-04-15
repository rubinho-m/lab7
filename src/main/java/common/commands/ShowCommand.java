/**
 * The ShowCommand class implements the execute method of the Command interface
 * to show the item in collection.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package common.commands;

import common.networkStructures.Response;
import server.collectionManagement.CollectionManager;
import common.exceptions.EmptyCollectionException;

public class ShowCommand extends CommandTemplate implements CommandWithResponse{
    private StringBuilder output;
    private StringBuilder outputCollection;
    public ShowCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute() throws EmptyCollectionException {
        if (getCollectionManager().getCollection().size() == 0){
            output = new StringBuilder();
            output.append("Collection is empty, please add ticket");
        } else {
            output = getCollectionManager().printCollection();
        }
    }

    @Override
    public Response getCommandResponse() {
        return new Response(output.toString());
    }
}
