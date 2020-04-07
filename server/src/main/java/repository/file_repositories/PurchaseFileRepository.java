package repository.file_repositories;

import models.Purchase;
import models.validation.Validator;
import repository.InMemoryRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PurchaseFileRepository extends InMemoryRepository<Long, Purchase> {

    private static final String PATH = "data/txt/purchase/";
    private static final String DEFAULT_NAME = "purchases";
    private static final String DEFAULT_TXT_FILE_EXTENSION = ".txt";

    private String path;

    public PurchaseFileRepository(Validator<Purchase> validator) {
        super(validator);
        this.path = PATH + DEFAULT_NAME + DEFAULT_TXT_FILE_EXTENSION;
        this.loadData();
    }

    public PurchaseFileRepository(Validator<Purchase> validator, String fileName) {
        super(validator);
        this.path = PATH + fileName + DEFAULT_TXT_FILE_EXTENSION;
        this.loadData();
    }

    private void loadData() {
        Path _path = Paths.get(path);
        try {
            Files.lines(_path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));
                Purchase purchase = new Purchase(
                        // Purchase(Long bookId, Long clientId, LocalDateTime lastModifiedDateTime)
                        Long.parseLong(items.get(0)), // long
                        Long.parseLong(items.get(1)), // long
                        Long.parseLong(items.get(2)),  // long
                        LocalDate.parse(items.get(3)) // LocalDateTime
                );
                super.save(purchase);
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void refreshDataInFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            bufferedWriter.write("");
            for (Purchase purchase : this.entities.values()) {
                bufferedWriter.write(purchase.getId() + "," + purchase.getBookId() + "," + purchase.getClientId() + "," + purchase.getLastModifiedDate());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile(Purchase purchase) {
        Path _path = Paths.get(path);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(_path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(purchase.getId() + "," + purchase.getBookId() + "," + purchase.getClientId() + "," + purchase.getLastModifiedDate());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Purchase> save(Purchase purchase) {
        Optional<Purchase> optionalPurchase = super.save(purchase);
        if (optionalPurchase.isPresent()) {
            return optionalPurchase;
        }
        saveToFile(purchase);
        return Optional.empty();
    }

    @Override
    public Optional<Purchase> delete(Long id) {
        Optional<Purchase> optionalPurchase = super.delete(id);
        refreshDataInFile();
        return Optional.empty();
    }

    @Override
    public Optional<Purchase> update(Purchase purchase) {
        Optional<Purchase> optionalPurchase = super.update(purchase);
        refreshDataInFile();
        return Optional.empty();
    }
}
