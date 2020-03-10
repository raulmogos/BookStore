package Controller;

import Models.Book;
import Models.Client;
import Models.Validation.Exception;
import Models.Validation.ValidatorException;
import Repository.Repository;

public class Controller {
    private Repository<Long, Book> books;
    private Repository<Long, Client> clients;
    private static Long bookID = 1L;
    private static Long clientID = 1L;

    public Controller(Repository<Long, Book> books, Repository<Long, Client> clients) {
        this.books = books;
        this.clients = clients;
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

    public void addBook(String title, String author, int price) throws ValidatorException {
        Book book = new Book(Controller.generateBookId(), title, author, price);
        books.save(book);
    }

    public void addClient(String firstName, String lastName, int moneySpent) throws ValidatorException {
        Client client = new Client(Controller.generateClientId(), firstName, lastName, moneySpent);
        clients.save(client);
    }

    public void addClient(String firstName, String lastName) throws ValidatorException {
        Client client = new Client(Controller.generateClientId(), firstName, lastName, 0 );
        clients.save(client);
    }

    public Iterable<Book> getAllBooks() {
        return books.findAll();
    }

    public Iterable<Client> getAllClients() {
        return clients.findAll();
    }

    public void updateBook(Long ID, String title, String author, int price) throws Exception, ValidatorException {
        if (!books.findOne(ID).isPresent()) {
            throw new Exception("Book ID not found");
        }
        Book book = books.findOne(ID).get();
        if (!title.equals("")) {
            book.setTitle(title);
        }
        if (!author.equals("")) {
            book.setAuthor(title);
        }
        if (price >= 0) {
            book.setPrice(price);
        }
        books.update(book);
    }

    public void deleteBook(Long ID) throws Exception {
        if (!books.findOne(ID).isPresent()) {
            throw new Exception("Book ID not found");
        }
        books.delete(ID);
    }

     public void updateClient(Long id, String firstName, String lastName, int moneySpent) throws Exception, ValidatorException {
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

     public void deleteClient(Long id) throws Exception {
         if (!clients.findOne(id).isPresent()) {
             throw new Exception("Client ID not found");
         }
         clients.delete(id);
     }
}
