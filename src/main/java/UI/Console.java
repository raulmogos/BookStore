package UI;

import Controller.Controller;
import Models.Book;
import Models.Client;
import Models.Validation.BookValidator;
import Models.Validation.ClientValidator;
import Models.Validation.Exception;
import Models.Validation.ValidatorException;
import Repository.InMemoryRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {
    private Controller controller;

    public Console(Controller controller) {
        this.controller = controller;
    }

    public void runConsole() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("\n");
            System.out.println("Menu:");
            System.out.println("0 - Exit");
            System.out.println("1 - Add Book");
            System.out.println("2 - Add Client");
            System.out.println("3 - Show Books");
            System.out.println("4 - Show Clients");
            System.out.println("5 - Update Book");
            System.out.println("6 - Update Client");
            System.out.println("7 - Delete Book");
            System.out.println("8 - Delete Client");
            try {
                String choice = reader.readLine();
                int intChoice = Integer.parseInt(choice);
                switch (intChoice) {
                    case 0:
                        return;
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
                        updateBook();
                        break;
                    case 6:
                        updateClient();
                        break;
                    case 7:
                        deleteBook();
                        break;
                    case 8:
                        deleteClient();
                        break;
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
        String firstName, lastName;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Client first name:");
            firstName = reader.readLine();
            System.out.println("Client last name:");
            lastName = reader.readLine();
            controller.addClient(firstName, lastName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ValidatorException error) {
            System.out.println(error.getMessage());
        }
    }

    private void printBooks() {
        Iterable<Book> books = controller.getAllBooks();
        books.forEach(System.out::println);
    }

    private void printClients() {
        Iterable<Client> clients = controller.getAllClients();
        clients.forEach(System.out::println);
    }

    private void updateBook() {
        String ID, title, author, price;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("Book ID:");
            ID = reader.readLine();
            System.out.println("New Book Title (blank if unchanged):");
            title = reader.readLine();
            System.out.println("New Book Author (blank if unchanged):");
            author = reader.readLine();
            System.out.println("New Book Price (-1 if unchanged):");
            price = reader.readLine();

            controller.updateBook(Long.parseLong(ID), title, author, Integer.parseInt(price));
            System.out.println("Book Updated Successfully");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception | ValidatorException exception) {
            System.out.println(exception.getMessage());
        }
    }
    private void updateClient() {
        String id, firstName, lastName, moneySpent;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Client ID:"); id = reader.readLine();
            System.out.println("New client first name (blank if unchanged): "); firstName = reader.readLine();
            System.out.println("New client last name (blank if unchanged):"); lastName = reader.readLine();
            System.out.println("New money spent (-1 if unchanged): "); moneySpent = reader.readLine();
            controller.updateClient(Long.parseLong(id), firstName, lastName, Integer.parseInt(moneySpent));
            System.out.println("Client Updated Successfully");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception | ValidatorException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void deleteBook() {
        String ID;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Book ID:");
            ID = reader.readLine();

            controller.deleteBook(Long.parseLong(ID));
            System.out.println("Book Deleted Successfully");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void deleteClient() {
        String id;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Client ID:"); id = reader.readLine();
            controller.deleteClient(Long.parseLong(id));
            System.out.println("Client deleted Successfully");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
