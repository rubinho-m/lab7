/**
 * The AddCommand class implements the execute method of the Command interface
 * to adding the new element into the collection.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package common.commands;

import common.networkStructures.Response;
import server.collectionManagement.CollectionManager;
public class AddCommand extends CommandTemplate implements CommandWithResponse {
    public AddCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute() {
        getCollectionManager().addToCollection(getTicket());
    }

    @Override
    public Response getCommandResponse() {
        return new Response("Added");
    }
}
