import Controller.Controller;
import Models.Book;
import Models.Client;
import Models.Purchase;
import Models.Validation.BookValidator;
import Models.Validation.ClientValidator;
import Models.Validation.PurchaseValidator;
import Repository.InMemoryRepository;
import Repository.Repository;
import UI.Console;

public class Main {
    public static void main(String[] args) {
        Repository<Long, Book> bookRepository = new InMemoryRepository<>(new BookValidator());
        Repository<Long, Client> clientRepository = new InMemoryRepository<>(new ClientValidator());
        Repository<String, Purchase> purchaseRepository = new InMemoryRepository<>(new PurchaseValidator());
        Controller controller = new Controller(bookRepository, clientRepository, purchaseRepository);
        Console console = new Console(controller);
        console.runConsole();
    }
}
