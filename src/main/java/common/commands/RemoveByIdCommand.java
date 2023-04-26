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
import server.databaseManagement.DatabaseHandler;

import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RemoveByIdCommand extends CommandTemplate implements CommandWithResponse{
    private String output;
    public RemoveByIdCommand(CollectionManager collectionManager, DatabaseHandler dbHandler) {
        super(collectionManager, dbHandler);
    }

    @Override
    public void execute(String user) throws EmptyCollectionException, SQLException {
        CollectionManager collectionManager = getCollectionManager();
        if (collectionManager.getCollection().size() == 0){
            output = "Collection is empty, please add ticket";
        } else {
            output = "Removed";
        }
        for (Ticket ticketToRemove : collectionManager.getCollection()){
            if (ticketToRemove.getId() == Integer.parseInt(getArg())){
                int condition = getDbHandler().removeTicket(user, (int) ticketToRemove.getId());
                if (condition == 0) {
                    collectionManager.getCollection().remove(ticketToRemove);
                    break;
                } else {
                    output = "Нет прав для удаления";
                }

            }
        }
    }

    @Override
    public Response getCommandResponse() {
        return new Response(output);
    }
}
