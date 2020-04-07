import controller.Controller;
import models.Book;
import models.Client;
import models.Purchase;
import models.validation.BookValidator;
import models.validation.ClientValidator;
import models.validation.PurchaseValidator;
import repository.InMemoryRepository;
import repository.Repository;
import repository.database.BookDatabaseRepository;
import repository.database.ClientDatabaseRepository;
import repository.database.PurchaseDatabaseRepository;
import repository.file_repositories.BookFileRepository;
import repository.file_repositories.ClientFileRepository;
import repository.file_repositories.PurchaseFileRepository;
import ui.Console;

public class Main {
    public static void main(String[] args) {
        //// XML
        //Repository<Long, Book> bookRepository = new BookXMLRepository(new BookValidator());
        //Repository<Long, Client> clientRepository = new ClientXMLRepository(new ClientValidator());
        //Repository<Long, Purchase> purchaseRepository = new PurchaseXMLRepository(new PurchaseValidator());

        //// FILE
        Repository<Long, Book> bookRepository = new BookFileRepository(new BookValidator());
        Repository<Long, Client> clientRepository = new ClientFileRepository(new ClientValidator());
        Repository<Long, Purchase> purchaseRepository = new PurchaseFileRepository(new PurchaseValidator());

        //// Database
//        Repository<Long, Book> bookRepository = new BookDatabaseRepository();
//        Repository<Long, Client> clientRepository = new ClientDatabaseRepository();
//        Repository<Long, Purchase> purchaseRepository = new PurchaseDatabaseRepository();

        Controller controller = new Controller(bookRepository, clientRepository, purchaseRepository);
        Console console = new Console(controller);
        console.run();
    }
}
