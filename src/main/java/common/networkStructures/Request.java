package common.networkStructures;

import common.structureClasses.Ticket;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;

public class Request implements Serializable {
    private ArrayList<String> commandWithArguments;
    private Serializable ticket;
    private InetAddress host;

    public ArrayList<String> getCommandWithArguments() {
        return commandWithArguments;
    }

    public void setCommandWithArguments(ArrayList<String> commandWithArguments) {
        this.commandWithArguments = commandWithArguments;
    }

    public Serializable getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Request(ArrayList<String> commandWithArguments, Serializable ticket, InetAddress host) {
        this.commandWithArguments = commandWithArguments;
        this.ticket = ticket;
        this.host = host;
    }
    public Request(ArrayList<String> commandWithArguments, Serializable ticket) {
        this.commandWithArguments = commandWithArguments;
        this.ticket = ticket;
    }

    public InetAddress getHost() {
        return host;
    }
}
