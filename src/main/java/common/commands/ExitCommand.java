/**
 * The ExitCommand class implements the execute method of the Command interface
 * to exit the program.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package common.commands;


import common.networkStructures.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.network.Reader;

public class ExitCommand extends CommandTemplate implements CommandWithResponse {
    private static final Logger logger = LogManager.getLogger(ExitCommand.class);
    @Override
    public void execute() {
        logger.info("EXIT SERVER");
        System.exit(0);
    }

    @Override
    public Response getCommandResponse() {
        return null;
    }
}
