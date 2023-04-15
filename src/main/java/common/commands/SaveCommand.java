/**
 * The SaveCommand class implements the execute method of the Command interface
 * to save the collection into file.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package common.commands;

import common.exceptions.XMLTroubleException;
import common.networkStructures.Response;
import server.collectionManagement.CollectionManager;
import server.xml.TicketXMLWriter;

import java.io.IOException;

public class SaveCommand extends CommandTemplate implements CommandWithResponse {

    public SaveCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute() {
        try {
            TicketXMLWriter writer = new TicketXMLWriter(getCollectionManager().getPath());
            writer.save(getCollectionManager());
        } catch (XMLTroubleException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response getCommandResponse() {
        return null;
    }
}
