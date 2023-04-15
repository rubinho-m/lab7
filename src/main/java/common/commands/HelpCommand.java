/**
 * The HelpCommand class implements the execute method of the Command interface
 * to show help text.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package common.commands;

import common.networkStructures.Response;

public class HelpCommand extends CommandTemplate implements CommandWithResponse{
    private StringBuilder output;
    @Override
    public void execute() {
        output = new StringBuilder();
        output.append("Help on available commands: " + "\n" +
                "help: display help on available commands" + "\n" +
                "info: display collection information" + "\n" +
                "show: display all collection elements" + "\n" +
                "add: add the new element to collection" + "\n" +
                "update: update the value of collection element if its id equals entered" + "\n" +
                "remove_by_id: remove collection element according to id" + "\n" +
                "clear: clear collection" + "\n" +
                "execute_script: execute commands from the file" + "\n" +
                "exit: exit the program" + "\n" +
                "add_if_min: add the new element to collection if it is lower than min of collection" + "\n" +
                "remove_greater: remove all collection elements which are greater than entered" + "\n" +
                "history: display 10 last commands" + "\n" +
                "min_by_price: display min by price collection element" + "\n" +
                "filter_greater_than_price: display elements which have price greater than entered" + "\n" +
                "print_field_descending_venue: display all venue values in descending order");
    }

    @Override
    public Response getCommandResponse() {
        return new Response(output.toString());
    }
}
