/**
 * The Command interface represents a command that can be executed on a ticket collection.
 * The interface contains methods for executing the command, getting and setting the command argument,
 * and getting and setting the ticket on which the command should be executed.
 */

package common.commands;

import common.exceptions.EmptyCollectionException;
import common.structureClasses.Ticket;

public interface Command {
    /**
     * Executes the command.
     *
     * @throws EmptyCollectionException if the ticket collection is empty and the command requires a non-empty collection.
     * @throws Exception                if there is an error executing the command.
     */
    void execute() throws Exception;

    /**
     * Gets the argument for the command.
     *
     * @return the argument for the command.
     */
    String getArg();

    /**
     * Sets the argument for the command.
     *
     * @param arg the argument for the command.
     */
    void setArg(String arg);

    /**
     * Gets the ticket on which the command should be executed.
     *
     * @return the ticket on which the command should be executed.
     */
    Ticket getTicket();

    /**
     * Sets the ticket on which the command should be executed.
     *
     * @param ticket the ticket on which the command should be executed.
     */
    void setTicket(Ticket ticket);

}
