/**
 * The HistoryCommand class implements the execute method of the Command interface
 * to show history of input commands.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package common.commands;

import common.exceptions.EmptyHistoryException;
import common.networkStructures.Response;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryCommand extends CommandTemplate implements CommandWithResponse {
    private StringBuilder output;
    public HistoryCommand() {

    }

    @Override
    public void execute() throws EmptyHistoryException {
        List<String> history = HistoryManager.getHistoryCommands();
        if (history.size() == 0){
            throw new EmptyHistoryException();
        }
        output = new StringBuilder();
        List<String> historyToPrint = history.stream()
                .limit(Math.min(10, history.size()))
                .collect(Collectors.toList());
        Collections.reverse(historyToPrint);
        historyToPrint.forEach(output::append);

    }

    @Override
    public Response getCommandResponse() {
        return new Response(output.toString());
    }
}
