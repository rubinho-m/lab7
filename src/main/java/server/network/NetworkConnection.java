package server.network;

import common.dataStructures.ParsedString;
import common.networkStructures.Request;
import common.networkStructures.Response;
import common.structureClasses.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.Server;
import server.collectionManagement.CollectionManager;
import server.collectionManagement.CommandExecutor;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public class NetworkConnection {
    private final int BUFFER_SIZE = 1024 * 1024;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private int port;
    private CollectionManager collectionManager;
    private CommandExecutor commandExecutor;
    private Request request;
    private Response response;
    private static final Logger logger = LogManager.getLogger(NetworkConnection.class);

    public NetworkConnection(int port, CollectionManager collectionManager, CommandExecutor commandExecutor) throws IOException {
        this.port = port;
        this.collectionManager = collectionManager;
        this.commandExecutor = commandExecutor;

    }

    public void start() throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        logger.info("SERVER STARTED ON PORT " + port);
        Reader reader = new Reader(serverSocket, commandExecutor);
        reader.read();


    }


}
