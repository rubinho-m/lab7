package common.commands;

import common.networkStructures.Response;

public interface CommandWithResponse extends Command {
    Response getCommandResponse();
}
