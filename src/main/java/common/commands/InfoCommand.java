/**
 * The InfoCommand class implements the execute method of the Command interface
 * to show information about collection.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package common.commands;

import common.networkStructures.Response;
import server.collectionManagement.CollectionManager;

public class InfoCommand extends CommandTemplate implements CommandWithResponse {
    private StringBuilder output;
    public InfoCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute() {
        try {
            output = new StringBuilder();
            CollectionManager collection = getCollectionManager();
            output.append("Type of collection: Ticket" + "\n" +
                    "creationDate: " + collection.getFirstElement().getCreationDate() + "\n" +
                    "size of collection: " + collection.getCollection().size());
        } catch (Exception e){
            output.append("Collection is empty");
        }
    }

    @Override
    public Response getCommandResponse() {
        return new Response(output.toString());
    }
}
