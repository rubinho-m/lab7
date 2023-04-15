package client; /**
 * The client.Main class is the entry point of the application. It initializes the collection manager, parses the XML file
 * containing the initial collection, and sets the path of the XML file. It also sets up a command executor with the
 * collection manager and a command parser. The program then enters a loop where it reads commands from the standard
 * input using the command parser and executes them using the command executor. If an exception is thrown during
 * command execution, the exception message is printed to the console.
 */

import common.commands.ExecuteScriptCommand;
import common.dataStructures.ParsedString;
import common.exceptions.NoCommandException;
import common.exceptions.WrongCommandFormat;
import common.networkStructures.Request;
import common.structureClasses.Ticket;
import common.exceptions.XMLTroubleException;
import common.commandParsing.CommandParser;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.*;


public class Client {
    /**
     * The main method is the entry point of the application.
     *
     * @param args command line arguments. The first argument should be the path to the XML file containing the initial collection.
     * @throws IOException         if there is an error reading the XML file.
     * @throws XMLTroubleException if there is an error parsing the XML file.
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 2){
            System.out.println("Введите адрес сервера и порт");
            System.exit(1);
        }
        try {
            int port = Integer.parseInt(args[1]);
            Scanner scanner = new Scanner(System.in);
            CommandParser commandParser = new CommandParser();
            NetworkConnection networkConnection = new NetworkConnection(args[0], port);

            while (true) {
                try {
                    ParsedString<ArrayList<String>, Ticket> parsedString = commandParser.readCommand(scanner, false, false);
                    ArrayList<String> commandWithArguments = parsedString.getArray();
                    if (Objects.equals(commandWithArguments.get(0), "exit")) {
                        System.exit(0);
                    }
                    ArrayList<ParsedString<ArrayList<String>, Ticket>> firstToDoCommands = new ArrayList<>();
                    if (commandWithArguments.get(0).equals("execute_script")) {
                        try {
                            ExecuteScriptCommand executeScriptCommand = new ExecuteScriptCommand(commandWithArguments.get(1));
                            executeScriptCommand.execute();
                            ArrayList<ParsedString<ArrayList<String>, Ticket>> nextCommand = executeScriptCommand.getNextCommand();
                            firstToDoCommands = nextCommand;
                        } catch (Exception e) {
                            System.out.println("Incorrect path to script");
                        }


                    } else {
                        firstToDoCommands.add(parsedString);
                    }
                    firstToDoCommands.removeAll(Collections.singleton(null));

                    for (ParsedString<ArrayList<String>, Ticket> ps : firstToDoCommands) {
                        Request request = new Request(ps.getArray(), ps.getTicket());
                        networkConnection.connectionManage(request);
                    }
                } catch (NoCommandException | WrongCommandFormat ignored) {

                } catch (ConnectException e) {
                    System.out.println("Сервер временно недоступен");
                } catch (IOException e){
                    System.out.println("Сервер разорвал подключение");
                    System.exit(1);
                }

            }

        } catch (XMLTroubleException e) {
            System.exit(1);
        } catch (NumberFormatException e){
            System.out.println("Порт должен быть числом");
            System.exit(1);
        } catch (NoSuchElementException e){
            System.exit(0);
        } catch (UnknownHostException e){
            System.out.println("Неправильный адрес сервера");
        }


    }

}