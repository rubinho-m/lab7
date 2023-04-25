/**
 * The CommandExecutor class is responsible for executing commands from the user input, either from the console or from a script file.
 * It stores the registered commands in a HashMap, and executes the command based on the input from the user.
 */

package server.collectionManagement;

import common.commands.*;
import common.dataStructures.ParsedString;
import common.exceptions.WrongScriptException;
import common.networkStructures.Response;
import common.structureClasses.Ticket;
import server.databaseManagement.DatabaseHandler;
import server.databaseManagement.DatabaseParser;

import java.net.Socket;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Thread.sleep;

public class CommandExecutor {
    private static final HashMap<String, CommandWithResponse> commandMap = new HashMap<>();
    private DatabaseHandler dbHandler;
    private DatabaseParser dbParser;
    private Set<String> paths = new HashSet<>();
    private int BUFFER_SIZE = 512 * 512;
//    private volatile String user;

    public DatabaseParser getDbParser() {
        return dbParser;
    }

    public void setDbParser(DatabaseParser dbParser) {
        this.dbParser = dbParser;
    }

//    public synchronized void setUser(String user) {
//        this.user = user;
//    }

    ReadWriteLock lock = new ReentrantReadWriteLock();

    public Set<String> getPaths() {
        return paths;
    }

    public void setDbHandler(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public void clearPaths() {
        paths.clear();
    }

    public void addToPaths(String path) {
        paths.add(path);
    }

    public static HashMap<String, CommandWithResponse> getCommandMap() {
        return commandMap;
    }

    private final Set<String> collectionCommands = new HashSet<>() {{
        add("add");
        add("update");
        add("add_if_min");
        add("remove_greater");
    }};
    private final Set<String> userCommands = new HashSet<>() {{
       add("add");
       add("remove_by_id");
       add("clear");
       add("add_if_min");
       add("update");
       add("remove_greater");
    }};

    private final Set<String> writeLockCommands = new HashSet<>() {{
        add("add");
        add("update");
        add("remove_greater");
        add("remove_by_id");
        add("add_if_min");
        add("clear");


    }};

    private final Set<String> readLockCommands = new HashSet<>() {{
        add("info");
        add("show");
        add("history");
        add("help");
        add("min_by_price");
        add("print_field_descending_venue");
        add("filter_greater_than_price");


    }};

    public void setCommands(CollectionManager collectionManager) {
        commandMap.put("save", new SaveCommand(collectionManager));
        commandMap.put("add", new AddCommand(collectionManager, dbHandler));
        commandMap.put("history", new HistoryCommand());
        commandMap.put("show", new ShowCommand(collectionManager));
        commandMap.put("info", new InfoCommand(collectionManager));
        commandMap.put("remove_by_id", new RemoveByIdCommand(collectionManager, dbHandler));
        commandMap.put("clear", new ClearCommand(collectionManager, dbHandler));
        commandMap.put("exit", new ExitCommand());
        commandMap.put("add_if_min", new AddIfMinCommand(collectionManager, dbHandler));
        commandMap.put("help", new HelpCommand());
        commandMap.put("update", new UpdateCommand(collectionManager, dbHandler));
        commandMap.put("remove_greater", new RemoveGreaterCommand(collectionManager, dbHandler));
        commandMap.put("min_by_price", new MinByPriceCommand(collectionManager));
        commandMap.put("filter_greater_than_price", new FilterGreaterThanPriceCommand(collectionManager));
        commandMap.put("print_field_descending_venue", new PrintFieldDescendingVenueCommand(collectionManager));
//        commandMap.put("execute_script", new ExecuteScriptCommand(collectionManager));
    }

    public Response execute(ParsedString<ArrayList<String>, Ticket> parsedString, String user) throws Exception {
        try {
            ArrayList<String> commandWithArgs = parsedString.getArray();
            CommandWithResponse command = commandMap.get(commandWithArgs.get(0));
            if (writeLockCommands.contains(commandWithArgs.get(0))) {
                lock.writeLock().lock();
            } else if (readLockCommands.contains(commandWithArgs.get(0))) {
                lock.readLock().lock();
            }
            if (command == null) {
                throw new IllegalStateException("No command registered for " + commandWithArgs.get(0));
            }
            String arg = null; // для команд с аргументом
            if (commandWithArgs.size() > 1) {
                arg = commandWithArgs.get(1);
            }
            Ticket inputTicket = parsedString.getTicket(); // для команд с коллекцией


            command.setArg(arg);
            command.setDbHandler(dbHandler);
            command.setDbParser(dbParser);
            command.setTicket(inputTicket);
            HistoryManager.addToHistory(commandWithArgs.get(0));
            command.execute(user);


            try {
                Response response = command.getCommandResponse();
                String output = response.getOutput();
                byte[] outputBytes = output.getBytes();
                if (outputBytes.length > BUFFER_SIZE) {
                    String newOutput = new String(outputBytes, 0, BUFFER_SIZE);
                    response.setOutput(newOutput);
                }
                return response;
            } finally {
                if (writeLockCommands.contains(commandWithArgs.get(0))) {
                    lock.writeLock().unlock();
                } else if (readLockCommands.contains(commandWithArgs.get(0))) {
                    lock.readLock().unlock();
                }
            }

        } catch (WrongScriptException e) {
            System.out.println(e.getMessage());
            return new Response("Wrong script");
        }
    }
}
