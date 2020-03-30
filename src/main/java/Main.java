import controller.Controller;
import models.Book;
import models.Client;
import models.Purchase;
import models.validation.BookValidator;
import models.validation.ClientValidator;
import models.validation.PurchaseValidator;
import repository.InMemoryRepository;
import repository.Repository;
import repository.database.DatabaseBookRepository;
import repository.file_repositories.BookFileRepository;
import repository.file_repositories.ClientFileRepository;
import repository.file_repositories.PurchaseFileRepository;
import repository.xml_repositories.BookXMLRepository;
import repository.xml_repositories.ClientXMLRepository;
import repository.xml_repositories.PurchaseXMLRepository;
import ui.Console;

public class Main {
    public static void main(String[] args) {
//        // XML
//        Repository<Long, Book> bookRepository = new BookXMLRepository(new BookValidator());
//        Repository<Long, Client> clientRepository = new ClientXMLRepository(new ClientValidator());
//        Repository<Long, Purchase> purchaseRepository = new PurchaseXMLRepository(new PurchaseValidator());

        // FILE
        Repository<Long, Book> bookRepository = new BookFileRepository(new BookValidator());
        Repository<Long, Client> clientRepository = new ClientFileRepository(new ClientValidator());
        Repository<Long, Purchase> purchaseRepository = new PurchaseFileRepository(new PurchaseValidator());

        // todo: @Dragos pls rename all Database Repositories into: 'EntityName'DatabaseRepository.java (just a convention name)
        // ex: DatabaseBookRepository into ->  BookDatabaseRepository
        // it's better to start different and makes life a lot easier

        Controller controller = new Controller(bookRepository, clientRepository, purchaseRepository);
        Console console = new Console(controller);
        console.run();
    }
}
