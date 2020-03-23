import controller.Controller;
import models.Book;
import models.Client;
import models.Purchase;
import models.validation.BookValidator;
import models.validation.ClientValidator;
import models.validation.PurchaseValidator;
import repository.InMemoryRepository;
import repository.Repository;
import repository.file_repositories.BookFileRepository;
import repository.file_repositories.ClientFileRepository;
import repository.file_repositories.PurchaseFileRepository;
import ui.Console;

public class Main {
    public static void main(String[] args) {
        Repository<Long, Book> bookRepository = new BookFileRepository(new BookValidator());
        Repository<Long, Client> clientRepository = new ClientFileRepository(new ClientValidator());
        Repository<String, Purchase> purchaseRepository = new PurchaseFileRepository(new PurchaseValidator());
        Controller controller = new Controller(bookRepository, clientRepository, purchaseRepository);
        Console console = new Console(controller);
        console.run();
    }
}
