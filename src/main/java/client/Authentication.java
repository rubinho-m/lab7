package client;

import common.networkStructures.Request;

import java.io.Console;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Authentication {
    private String login;
    private String password;
    private Scanner scanner = new Scanner(System.in);
    private String type;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void chooseFunction() {
        System.out.println("Введите 0 для регистрации, 1 для авторизации");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 0) {
                System.out.println("Регистрация:");
                type = "reg";
            } else if (choice == 1) {
                System.out.println("Авторизация:");
                type = "auth";
            } else {
                chooseFunction();
            }
            readUserData(scanner);
        } catch (Exception e) {
            chooseFunction();
        }


    }

    public void readPassword(Scanner scanner) {
        Console console = System.console();

        if (console != null) {
            password = String.valueOf(console.readPassword());
        } else {
            password = scanner.nextLine();
        }
    }

    public void readLogin(Scanner scanner){
        login = scanner.nextLine();
    }

    public void readUserData(Scanner scanner) {
        System.out.println("Введите логин");
        readLogin(scanner);
        System.out.println("Введите пароль");
        readPassword(scanner);
    }

    public Request getRequest(){
        ArrayList<String> commandWithArgs = new ArrayList<>();
        ArrayList<String> userData = new ArrayList<>();
        commandWithArgs.add(type);
        userData.add(login);
        userData.add(password);
        return new Request(commandWithArgs, userData);
    }

}
