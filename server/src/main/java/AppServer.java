import controller.Controller;
import models.Book;
import models.Client;
import models.Purchase;
import models.validation.BookValidator;
import models.validation.ClientValidator;
import models.validation.PurchaseValidator;
import repository.Repository;
import repository.file_repositories.BookFileRepository;
import repository.file_repositories.ClientFileRepository;
import repository.file_repositories.PurchaseFileRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AppServer {
    public static void main(String[] args) {
        System.out.println("started server");

        // FILE
        Repository<Long, Book> bookRepository = new BookFileRepository(new BookValidator());
        Repository<Long, Client> clientRepository = new ClientFileRepository(new ClientValidator());
        Repository<Long, Purchase> purchaseRepository = new PurchaseFileRepository(new PurchaseValidator());

        Controller controller = new Controller(bookRepository, clientRepository, purchaseRepository);

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        ServerService service = new ServerService(executorService, controller);

        TCPServer server = new TCPServer(executorService);

        Helpers.addHandlersToServer(server, service);


        server.startServer();

        executorService.shutdown();


        System.out.println("server stopped");
    }
}
