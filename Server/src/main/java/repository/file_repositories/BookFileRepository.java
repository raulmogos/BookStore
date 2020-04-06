package repository.file_repositories;

import models.Book;
import models.validation.Validator;
import models.validation.ValidatorException;
import repository.InMemoryRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class BookFileRepository extends InMemoryRepository<Long, Book> {

    private static final String PATH = "data/txt/book/";
    private static final String DEFAULT_NAME = "books";
    private static final String DEFAULT_FILE_EXTENSION = ".txt";

    private String path;

    public BookFileRepository(Validator<Book> validator) {
        super(validator);
        this.path = PATH + DEFAULT_NAME + DEFAULT_FILE_EXTENSION;
        this.loadData();
    }

    public BookFileRepository(Validator<Book> validator, String fileName) {
        super(validator);
        this.path = PATH + fileName + DEFAULT_FILE_EXTENSION;
        this.loadData();
    }

    private void loadData() {
        Path _path = Paths.get(path);
        try {
            Files.lines(_path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));
                Book book = new Book(
                        Long.parseLong(items.get(0)), // long
                        items.get(1), // string
                        items.get(2), // string
                        Integer.parseInt(items.get(3)) // int
                );
                super.save(book);
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void refreshDataInFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            bufferedWriter.write("");
            super.entities.values().forEach(book -> {
                try {
                    bufferedWriter.write(book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getPrice());
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile(Book book) {
        Path _path = Paths.get(path);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(_path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getPrice());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Book> save(Book book) {
        Optional<Book> optionalBook = super.save(book);
        if (optionalBook.isPresent()) {
            return optionalBook;
        }
        saveToFile(book);
        return Optional.empty();
    }

    @Override
    public Optional<Book> delete(Long id) {
        Optional<Book> optionalBook = super.delete(id);
        refreshDataInFile();
        return Optional.empty();
    }

    @Override
    public Optional<Book> update(Book entity) {
        Optional<Book> optionalBook = super.update(entity);
        refreshDataInFile();
        return Optional.empty();
    }
}
