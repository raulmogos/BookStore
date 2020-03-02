package UI;

import Controller.Controller;
import Models.Book;
import Models.Validation.ValidatorException;

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
            System.out.println("\n");
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

    private void addBook() {
        String title, author, price;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("Book title:");
            title = reader.readLine();
            System.out.println("Book author:");
            author = reader.readLine();
            System.out.println("Book price:");
            price = reader.readLine();

            controller.addBook(title, author, Integer.parseInt(price));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ValidatorException error) {
            System.out.println(error.getMessage());
        }
    }

    private void addClient() {

    }

    private void printBooks() {
        Iterable<Book> books = controller.getAllBooks();
        for (Book book : books) {
            System.out.println(book.toString());
        }
    }

    private void printClients() {

    }
}
