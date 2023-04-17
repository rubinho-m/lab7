package server.network;

import common.commandParsing.CommandParser;
import common.dataStructures.ParsedString;
import common.exceptions.NoCommandException;
import common.exceptions.WrongCommandFormat;
import common.networkStructures.Request;
import common.networkStructures.Response;
import common.structureClasses.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.collectionManagement.CommandExecutor;
import sun.misc.SignalHandler;
import sun.misc.Signal;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Reader {
    private ServerSocket serverSocket;
    private CommandExecutor commandExecutor;
    private boolean ctrlPressed = false;
    private Set<String> serverCommands = new HashSet<>() {{
        add("save");
        add("exit");
    }};
    private static final Logger logger = LogManager.getLogger(Reader.class);

    public Reader(ServerSocket serverSocket, CommandExecutor commandExecutor) {
        this.serverSocket = serverSocket;
        this.commandExecutor = commandExecutor;
    }

    public void read() throws Exception {
//        serverSocket.setSoTimeout(1000);
        ExecutorService executorService = Executors.newCachedThreadPool();


        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
//                System.out.println(clientSocket.getPort() + " " + clientSocket.getLocalPort());

                logger.info("GOT CLIENT SOCKET");
                logger.info("New client connected: " + clientSocket.getInetAddress().getHostAddress());
                logger.info("READY TO READ");

                executorService.submit(() -> {
                    try (InputStream inputStream = clientSocket.getInputStream();
                         OutputStream outputStream = clientSocket.getOutputStream();
                         ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {

                        while (true) {
                            Request request = (Request) objectInputStream.readObject();
                            ArrayList<String> commandWithArguments = request.getCommandWithArguments();

                            if (commandWithArguments.get(0).equals("reg") | commandWithArguments.get(0).equals("auth")){
                                ArrayList<String> userData = request.getUserData();
                                String type;
                                if (commandWithArguments.get(0).equals("reg")){
                                    type = "Регистрация";
                                } else {
                                    type = "Авторизация";
                                }
                                Writer writer = new Writer(outputStream);
                                Response response = new Response(type + " прошла успешно");
                                writer.write(response);
                                System.out.println(userData.get(0) + " " + userData.get(1));
                            } else {
                                Ticket ticket = (Ticket) request.getTicket();
                                ArrayList<String> userData = request.getUserData();
                                System.out.println(userData.get(0) + " " + userData.get(1));
                                ParsedString<ArrayList<String>, Ticket> parsedString = new ParsedString<>(commandWithArguments, ticket);
                                logger.info("REQUEST HAS BEEN PARSED");

                                Handler handler = new Handler(commandExecutor, outputStream, false);
                                handler.handleCommand(parsedString);
                            }
                        }
                    } catch (Exception e) {
                        logger.info("Client disconnected");
                        try {
                            clientSocket.close();
                            logger.info("CLIENT CONNECTION CLOSED");
                        } catch (IOException ex) {
                            logger.error("Failed to close client socket: " + ex.getMessage());
                        }
                    }
                });

            } catch (Exception ignored){

            }



        }
    }


}
