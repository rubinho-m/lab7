/**
 * The AddCommand class implements the execute method of the Command interface
 * to adding the new element into the collection.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package common.commands;

import common.networkStructures.Response;
import server.collectionManagement.CollectionManager;
import server.databaseManagement.DatabaseHandler;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AddCommand extends CommandTemplate implements CommandWithResponse {
    public AddCommand(CollectionManager collectionManager, DatabaseHandler dbHandler) {
        super(collectionManager, dbHandler);
    }

    @Override
    public void execute() {
//        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
//        lock.writeLock().lock();
        getCollectionManager().addToCollection(getTicket());
//        lock.writeLock().unlock();
    }

    @Override
    public Response getCommandResponse() {
        return new Response("Added");
    }
}
