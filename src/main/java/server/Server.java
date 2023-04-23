package server;

import common.exceptions.XMLTroubleException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.collectionManagement.CollectionManager;
import server.collectionManagement.CommandExecutor;
import server.databaseManagement.DatabaseHandler;
import server.databaseManagement.DatabaseParser;
import server.databaseManagement.DatabaseManager;
import server.network.NetworkConnection;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.BindException;
import java.net.SocketException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Server {

    private static final Logger logger = LogManager.getLogger(Server.class);

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Введите путь к файлу и порт");
            System.exit(1);
        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("Отсутствует драйвер для базы данныхв");
            System.exit(1);
        }

        String login = null;
        String password = null;

        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(new FileReader("db_config.txt"));
        } catch (FileNotFoundException e) {
            logger.error("Не найден файл db_config.txt");
            System.exit(1);
        }

        try {
            login = fileScanner.nextLine().trim();
            password = fileScanner.nextLine().trim();
        } catch (NoSuchElementException e) {
            System.out.println("Unable to read username and password from db_config.txt");
            System.exit(0);
        }
        DatabaseManager databaseManager = new DatabaseManager("jdbc:postgresql://localhost:5432/studs", login, password);

        try {
            int port = Integer.parseInt(args[1]);
            logger.info("START");
            CollectionManager collectionManager = new CollectionManager();
            logger.info("COLLECTION MANAGER HAS BEEN SET");
            databaseManager.connect();
            DatabaseParser dbParser = new DatabaseParser(databaseManager.getConnection());
            DatabaseHandler dbHandler = new DatabaseHandler(databaseManager.getConnection());
            collectionManager.setCollection(dbParser.loadCollection());
//            collectionManager.printCollection();

//            TicketXMLParser xmlParser = new TicketXMLParser(args[0]);
//            collectionManager.setCollection(xmlParser.parse());
//            logger.info("XML FILE HAS BEEN PARSED");
            collectionManager.setPath(args[0]);
            CommandExecutor commandExecutor = new CommandExecutor();
            commandExecutor.setCommands(collectionManager);
            NetworkConnection networkConnection = new NetworkConnection(port, collectionManager, commandExecutor, dbParser, dbHandler);
            networkConnection.start();
        } catch (NumberFormatException e) {
            System.out.println("Порт должен быть числом");
            logger.error("PORT IS NOT INT");
            System.exit(1);
        } catch (BindException e) {
            System.out.println("Порт занят, введите другой");
            logger.error("PORT HAS ALREADY USED");
            System.exit(1);
        } catch (SocketException e) {
            System.out.println("Данный порт является системным, введите другой");
            logger.error("SYSTEM PORT ERROR");
            System.exit(1);
        } catch (XMLTroubleException e) {
            System.out.println("Some troubles with xml file, please fix it");
            logger.error("INCORRECT XML FILE");
            System.exit(1);
        }

    }
}