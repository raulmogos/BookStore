package Controller;

import Models.Book;
import Models.Validation.BookValidator;
import Models.Validation.ValidatorException;
import Repository.InMemoryRepository;

public class Controller {
    private InMemoryRepository<Long, Book> books;
    private static Long bookID = 1L;

    public Controller() {
        books = new InMemoryRepository<>(new BookValidator());
    }

    private static Long getBookID() {
        Long id = bookID;
        bookID ++;
        return id;
    }

    public void addBook(String title, String author, int price) throws ValidatorException {
        Book book = new Book(Controller.getBookID(), title, author, price);
        books.save(book);
    }

    public Iterable<Book> getAllBooks() {
        return books.findAll();
    }
}
