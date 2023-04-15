/**
 * The ExecuteScriptCommand class implements the execute method of the Command interface
 * to execute the user's script with commands.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package common.commands;

import common.networkStructures.Response;
import server.collectionManagement.CollectionManager;
import server.collectionManagement.CommandExecutor;
import common.dataStructures.ParsedString;
import common.exceptions.ScriptRecursionException;
import common.commandParsing.CommandParser;
import common.structureClasses.Ticket;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class ExecuteScriptCommand{
    private static Set<String> paths = new HashSet<>();

    private String arg;
    private ArrayList<ParsedString<ArrayList<String>, Ticket>> allParsedStrings = new ArrayList<>();
    private ParsedString<ArrayList<String>, Ticket> parsedString;

    public ExecuteScriptCommand(String arg) {
        this.arg = arg;
    }

    public void execute() throws ScriptRecursionException {
        try {

            File file = new File(arg);
            Scanner scanner = new Scanner(file);
            CommandParser commandParser = new CommandParser();
            if (paths.contains(arg)) {
                throw new ScriptRecursionException();
            }
            paths.add(arg);
            while (true) {
                try {
                    parsedString = commandParser.readCommand(scanner, true, false);
                    if (Objects.equals(parsedString.getArray().get(0), "execute_script")){
                        if (paths.contains(parsedString.getArray().get(1))){
                            System.out.println("Looping scripts... Please fix it");
                            throw new ScriptRecursionException();
                        }
                        ExecuteScriptCommand scriptCommand = new ExecuteScriptCommand(parsedString.getArray().get(1));
                        scriptCommand.execute();
                        allParsedStrings.addAll(scriptCommand.getNextCommand());
                    } else {
                        allParsedStrings.add(parsedString);
                    }
                } catch (Exception e) {

                    break;
                }
            }
            paths.clear();
        } catch (FileNotFoundException e) {
            System.out.println("Incorrect path to script");
        }catch (ScriptRecursionException e){
            System.out.println("Looping scripts... Please fix it");
        }
        catch (Exception e) {
            System.out.println("Some troubles with your script. Please fix it");
        }

    }

    public ArrayList<ParsedString<ArrayList<String>, Ticket>> getNextCommand(){
        return allParsedStrings;
    }
}
