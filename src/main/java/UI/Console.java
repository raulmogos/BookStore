package UI;

import Controller.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {
    private Controller controller;

    public Console() {
        controller = new Controller();
    }

    public void runConsole() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("Menu:");
            System.out.println("1 - Add Book");
            System.out.println("2 - Add Client");
            System.out.println("3 - Show Books");
            System.out.println("4 - Show Clients");
            System.out.println("5 - Exit");
            try {
                String choice = reader.readLine();
                int intChoice = Integer.parseInt(choice);
                switch (intChoice) {
                    case 1:
                        addBook();
                        break;
                    case 2:
                        addClient();
                        break;
                    case 3:
                        printBooks();
                        break;
                    case 4:
                        printClients();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid choice");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addBook() {

    }

    public void addClient() {

    }

    public void printBooks() {

    }

    public void printClients() {

    }
}
