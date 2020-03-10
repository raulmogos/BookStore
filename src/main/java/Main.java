import Controller.Controller;
import Models.Book;
import Models.Client;
import Models.Validation.BookValidator;
import Models.Validation.ClientValidator;
import Repository.InMemoryRepository;
import Repository.Repository;
import UI.Console;

public class Main {
    public static void main(String[] args) {
        Repository<Long, Book> bookRepository = new InMemoryRepository<>(new BookValidator());
        Repository<Long, Client> clientRepository = new InMemoryRepository<>(new ClientValidator());
        Controller controller = new Controller(bookRepository, clientRepository);
        Console console = new Console(controller);
        console.runConsole();
    }
}
