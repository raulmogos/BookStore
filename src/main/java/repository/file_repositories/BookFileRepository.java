package repository.file_repositories;


import models.Book;
import models.validation.Validator;

import java.util.Arrays;
import java.util.List;

public class BookFileRepository extends FileRepository<Long, Book> {

    public BookFileRepository(Validator<Book> validator) {
        super(validator);
        initiate();
        this.path = PATH + DEFAULT_NAME + DEFAULT_TXT_FILE_EXTENSION;
        this.loadData();
    }

    public BookFileRepository(Validator<Book> validator, String fileName) {
        super(validator);
        initiate();
        this.path = PATH + fileName + DEFAULT_TXT_FILE_EXTENSION;
        this.loadData();
    }

    private void initiate() {
        this.PATH = "data/txt/book/";
        this.DEFAULT_NAME = "books";
    }

    @Override
    protected Book getEntityFromStringLine(String stringLine) {
        List<String> items = Arrays.asList(stringLine.split(","));
        return new Book(
                Long.parseLong(items.get(0)), // long
                items.get(1), // string
                items.get(2), // string
                Integer.parseInt(items.get(3)) // int
        );
    }

    @Override
    protected String putEntityLineString(Book book) {
        return book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getPrice();
    }
}
