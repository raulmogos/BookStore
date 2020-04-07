package controller;

import models.Book;
import models.Client;
import models.Purchase;
import models.validation.Exception;
import repository.Repository;
import common.CommonController;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Controller implements CommonController {
    private Repository<Long, Book> books;
    private Repository<Long, Client> clients;
    private Repository<Long, Purchase> purchases;
    private static Long bookID = 1L;
    private static Long clientID = 1L;
    private static Long purchaseID = 1L;

    public Controller(Repository<Long, Book> books, Repository<Long, Client> clients, Repository<Long, Purchase> purchases) {
        this.books = books;
        this.clients = clients;
        this.purchases = purchases;
        this.initializeIdGenerators();
    }

    private void initializeIdGenerators() {
        // when we load all the data we need to get the largest id for each entity
        Optional<Book> bookMaxId = Lists.newArrayList(books.findAll()).stream()
                .max(Comparator.comparing(Book::getId));
        Optional<Client> clientMaxId = Lists.newArrayList(clients.findAll()).stream()
                .max(Comparator.comparing(Client::getId));
        Optional<Purchase> purchaseMaxId = Lists.newArrayList(purchases.findAll()).stream()
                .max(Comparator.comparing(Purchase::getId));
        //bookID = bookMaxId.isPresent() ? bookMaxId.get().getId() + 1: 1L;  ->  old version
        bookID = bookMaxId.map(book -> book.getId() + 1).orElse(1L);  // functional version
        clientID = clientMaxId.map(client -> client.getId() + 1).orElse(1L);
        purchaseID = purchaseMaxId.map(purchase -> purchase.getId() + 1).orElse(1L);
    }

    private static Long generateBookId() {
        Long id = bookID;
        bookID ++;
        return id;
    }

    private static Long generateClientId() {
        Long id = clientID;
        clientID ++;
        return id;
    }

    private static Long generatePurchaseId() {
        Long id = purchaseID;
        purchaseID ++;
        return id;
    }

    public void addBook(String title, String author, int price) {
        Book book = new Book(Controller.generateBookId(), title, author, price);
        books.save(book);
    }

    public void addClient(String firstName, String lastName, int moneySpent) {
        Client client = new Client(Controller.generateClientId(), firstName, lastName, moneySpent);
        clients.save(client);
    }

    public void addClient(String firstName, String lastName) {
        Client client = new Client(Controller.generateClientId(), firstName, lastName, 0 );
        clients.save(client);
    }

    public Iterable<Book> getAllBooks() {
        return books.findAll();
    }

    public Iterable<Client> getAllClients() {
        return clients.findAll();
    }

    public Iterable<Purchase> getAllPurchases() {
        return purchases.findAll();
    }

    public void updateBook(Long ID, String title, String author, int price) {
        if (!books.findOne(ID).isPresent()) {
            throw new Exception("Book ID not found");
        }
        Book book = books.findOne(ID).get();
        if (!title.equals("")) {
            book.setTitle(title);
        }
        if (!author.equals("")) {
            book.setAuthor(author);
        }
        if (price >= 0) {
            book.setPrice(price);
        }
        books.update(book);
    }

    public void deleteBook(Long id) {
        if (!books.findOne(id).isPresent()) {
            throw new Exception("Book ID not found");
        }
        // delete all purchases in which is that book
        Lists.newArrayList(purchases.findAll()).stream()
                .filter(purchase -> purchase.getBookId().equals(id))
                .forEach(purchase -> purchases.delete(purchase.getId()));
        books.delete(id);
    }

     public void updateClient(Long id, String firstName, String lastName, int moneySpent) {
         if (!clients.findOne(id).isPresent()) {
             throw new Exception("Client ID not found");
         }
         Client client = clients.findOne(id).get();
         if (!firstName.equals("")) {
             client.setFirstName(firstName);
         }
         if (!lastName.equals("")) {
             client.setLastName(lastName);
         }
         if (moneySpent != -1) {
             client.setMoneySpent(moneySpent);
         }
         clients.update(client);
     }

     public void deleteClient(Long id) {
         if (!clients.findOne(id).isPresent()) {
             throw new Exception("Client ID not found");
         }
         // delete all purchases made by that client
         Lists.newArrayList(purchases.findAll()).stream()
                 .filter(purchase -> purchase.getClientId().equals(id))
                 .forEach(purchase -> purchases.delete(purchase.getId()));
         clients.delete(id);
     }

     public void addPurchase(Long bookID, Long clientID) {
        if (!books.findOne(bookID).isPresent()) {
            throw new Exception("Book ID not found");
        }
        if (!clients.findOne(clientID).isPresent()) {
            throw new Exception("Client ID not found");
        }
        // update moneySpent for the client
        Client client = clients.findOne(clientID).get();
        int newMoneySpent = client.getMoneySpent() + books.findOne(bookID).get().getPrice();
        client.setMoneySpent(newMoneySpent);
        clients.update(client);
        // add purchase
        Purchase purchase = new Purchase(generatePurchaseId(), bookID, clientID);
        purchases.save(purchase);
     }

     public void deletePurchase(Long id) {
        if (!purchases.findOne(id).isPresent()) {
            throw new Exception("Purchase ID not found");
        }
        purchases.delete(id);
     }

     public Iterable<Book> filterBookAuthor(String author) {
        Iterable<Book> bookList = books.findAll();
        List<Book> list = new ArrayList<>();
        bookList.forEach(list::add);
        return list.stream()
                 .filter(book -> book.getAuthor().equals(author))
                 .collect(Collectors.toList());
     }

     public Iterable<Book> filterBookPrice(int min, int max) {
        Iterable<Book> bookList = books.findAll();
        List<Book> list = new ArrayList<>();
        bookList.forEach(list::add);
        return list.stream()
                .filter(book -> book.getPrice() >= min && book.getPrice() <= max)
                .collect(Collectors.toList());
     }

     public Iterable<Client> filterClientsByName(String name) {
        return Lists.newArrayList(clients.findAll()).stream()
                .filter(client -> client.getLastName().contains(name) || client.getFirstName().contains(name))
                .collect(Collectors.toList());
     }

     public Iterable<Client> topNClientsOnMoneySpent(int n) {
        return Lists.newArrayList(clients.findAll())
                .stream()
                .sorted((Client client1, Client client2) -> - client1.getMoneySpent() + client2.getMoneySpent())
                .limit(n)
                .collect(Collectors.toList());
     }
}
