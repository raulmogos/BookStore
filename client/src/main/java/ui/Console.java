package ui;

import client.ClientService;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Console {

    private ClientService clientService;

    public Console(ClientService clientService) {
        this.clientService = clientService;
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
                        addPurchase();
                        break;
                    case 4:
                        printBooks();
                        break;
                    case 5:
                        printClients();
                        break;
                    case 6:
                        printPurchases();
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
                        filterBooksAuthor();
                        break;
                    case 13:
                        filterBooksPrice();
                        break;
                    case 14:
                        filterClientsName();
                        break;
                    case 15:
                        filterTopClients();
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
            System.out.println("Book Title:");
            title = reader.readLine();
            System.out.println("Book Author:");
            author = reader.readLine();
            System.out.println("Book Price:");
            price = reader.readLine();
            Future<String> out = this.clientService.addBook(title, author, Integer.parseInt(price));
            System.out.println(out.get());
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void addClient() {
        String firstname, lastname;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("First Name:");
            firstname = reader.readLine();
            System.out.println("Last Name:");
            lastname = reader.readLine();
            Future<String> out = this.clientService.addClient(firstname, lastname);
            System.out.println(out.get());
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void addPurchase() {
        String bookID, clientID;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Book ID:");
            bookID = reader.readLine();
            System.out.println("Client ID:");
            clientID = reader.readLine();
            Future<String> out = this.clientService.addPurchase(Long.parseLong(bookID), Long.parseLong(clientID));
            System.out.println(out.get());
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void printBooks() {
        try {
            Future<String> out = this.clientService.getAllBooks();
            JSONObject jsonObject = (JSONObject) JSONValue.parse(out.get());
            Iterable<String> books = (Iterable<String>) jsonObject.get("data");
            books.forEach(System.out::println);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void printClients() {
        try {
            Future<String> out = this.clientService.getAllClients();
            JSONObject jsonObject = (JSONObject) JSONValue.parse(out.get());
            Iterable<String> clients = (Iterable<String>) jsonObject.get("data");
            clients.forEach(System.out::println);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void printPurchases() {
        try {
            Future<String> out = this.clientService.getAllPurchases();
            JSONObject jsonObject = (JSONObject) JSONValue.parse(out.get());
            Iterable<String> purchases = (Iterable<String>) jsonObject.get("data");
            purchases.forEach(System.out::println);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void updateBook() {
        String ID, title, author, price;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Book ID:");
            ID = reader.readLine();
            System.out.println("New Book Title (Blank if unchanged):");
            title = reader.readLine();
            System.out.println("New Book Author (Blank if unchanged):");
            author = reader.readLine();
            System.out.println("New Book Price (Blank if unchanged):");
            price = reader.readLine();
            Future<String> out = this.clientService.updateBook(Long.parseLong(ID), title, author, Integer.parseInt(price));
            System.out.println(out.get());
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void updateClient() {
        String ID, firstname, lastname, spendings;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Client ID:");
            ID = reader.readLine();
            System.out.println("New First Name (Blank if unchanged):");
            firstname = reader.readLine();
            System.out.println("New Last Name (Blank if unchanged):");
            lastname = reader.readLine();
            System.out.println("New Spendings (Blank if unchanged):");
            spendings = reader.readLine();
            Future<String> out = this.clientService.updateClient(Long.parseLong(ID), firstname, lastname, Integer.parseInt(spendings));
            System.out.println(out.get());
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void deleteBook() {
        String ID;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Book ID:");
            ID = reader.readLine();
            Future<String> out = this.clientService.deleteBook(Long.parseLong(ID));
            System.out.println(out.get());
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void deleteClient() {
        String ID;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Client ID:");
            ID = reader.readLine();
            Future<String> out = this.clientService.deleteClient(Long.parseLong(ID));
            System.out.println(out.get());
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void deletePurchase() {
        String ID;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Purchase ID:");
            ID = reader.readLine();
            Future<String> out = this.clientService.deletePurchase(Long.parseLong(ID));
            System.out.println(out.get());
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void filterBooksAuthor() {
        String author;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Book Author:");
            author = reader.readLine();
            Future<String> out = this.clientService.filterBooksAuthor(author);
            JSONObject jsonObject = (JSONObject) JSONValue.parse(out.get());
            Iterable<String> books = (Iterable<String>) jsonObject.get("data");
            books.forEach(System.out::println);
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void filterBooksPrice() {
        String minPrice, maxPrice;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Minimum Price:");
            minPrice = reader.readLine();
            System.out.println("Maximum Price:");
            maxPrice = reader.readLine();
            Future<String> out = this.clientService.filterBooksPrice(Integer.parseInt(minPrice), Integer.parseInt(maxPrice));
            JSONObject jsonObject = (JSONObject) JSONValue.parse(out.get());
            Iterable<String> books = (Iterable<String>) jsonObject.get("data");
            books.forEach(System.out::println);
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void filterClientsName() {
        String name;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Client Name:");
            name = reader.readLine();
            Future<String> out = this.clientService.filterClientsName(name);
            JSONObject jsonObject = (JSONObject) JSONValue.parse(out.get());
            Iterable<String> clients = (Iterable<String>) jsonObject.get("data");
            clients.forEach(System.out::println);
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void filterTopClients() {
        try {
            Future<String> out = this.clientService.filterTopClients();
            JSONObject jsonObject = (JSONObject) JSONValue.parse(out.get());
            Iterable<String> clients = (Iterable<String>) jsonObject.get("data");
            clients.forEach(System.out::println);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
