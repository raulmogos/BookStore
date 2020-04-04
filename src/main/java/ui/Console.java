package ui;

import controller.Controller;
import models.Book;
import models.Client;
import models.Purchase;
import models.validation.Exception;
import models.validation.ValidatorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {
    private Controller controller;

    public Console(Controller controller) {
        this.controller = controller;
    }

    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("\n");
            System.out.println("Menu:");
            System.out.println("0 - Exit\n");
            System.out.println("1 - Add Book");
            System.out.println("2 - Add Client");
            System.out.println("3 - Add Purchase - Buy a book\n");
            System.out.println("4 - List Books");
            System.out.println("5 - List Clients");
            System.out.println("6 - List Purchases\n");
            System.out.println("7 - Update Book");
            System.out.println("8 - Update Client\n");
            System.out.println("9 - Delete Book");
            System.out.println("10 - Delete Client");
            System.out.println("11 - Delete Purchase\n");
            System.out.println("12 - Filter Books by Author");
            System.out.println("13 - Filter Books by Price");
            System.out.println("14 - Filter Clients by Name\n");
            System.out.println("15 - Top 10 clients on money spent");
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
                        buyBook();
                        break;
                    case 4:
                        printBooks();
                        break;
                    case 5:
                        printClients();
                        break;
                    case 6:
                        printPurchase();
                        break;
                    case 7:
                        updateBook();
                        break;
                    case 8:
                        updateClient();
                        break;
                    case 9:
                        deleteBook();
                        break;
                    case 10:
                        deleteClient();
                        break;
                    case 11:
                        deletePurchase();
                        break;
                    case 12:
                        filterBookAuthor();
                        break;
                    case 13:
                        filterBookPrice();
                        break;
                    case 14:
                        filterClientsByName();
                        break;
                    case 15:
                        topNClientsOnMoneySpent();
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

    private void printPurchase() {
        Iterable<Purchase> purchases = controller.getAllPurchases();
        purchases.forEach(System.out::println);
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

    private void deletePurchase() {
        long id;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Purchase ID:"); id = Long.parseLong(reader.readLine());
            controller.deletePurchase(id);
            System.out.println("Purchase deleted Successfully");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void buyBook() {
        String bookID, clientID;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Book ID:");
            bookID = reader.readLine();
            System.out.println("Client ID:");
            clientID = reader.readLine();
            controller.addPurchase(Long.parseLong(bookID), Long.parseLong(clientID));
            System.out.println("Book purchase successfully registered");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void filterBookAuthor() {
        String author;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Author:");
            author = reader.readLine();
            Iterable<Book> books = controller.filterBookAuthor(author);
            books.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void filterBookPrice() {
        String min, max;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Minimum price:");
            min = reader.readLine();
            System.out.println("Maximum price:");
            max = reader.readLine();
            Iterable<Book> books = controller.filterBookPrice(Integer.parseInt(min), Integer.parseInt(max));
            books.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void filterClientsByName() {
        String name;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Client name: ");
            name = reader.readLine();
            controller.filterClientsByName(name).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void topNClientsOnMoneySpent() {
        int N = 10;
        controller.topNClientsOnMoneySpent(N).forEach(System.out::println);
    }
}
