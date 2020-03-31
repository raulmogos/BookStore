package repository.file_repositories;

import models.Purchase;
import models.validation.Validator;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class PurchaseFileRepository extends FileRepository<Long, Purchase> {
    public PurchaseFileRepository(Validator<Purchase> validator) {
        super(validator);
        initiate();
        this.path = PATH + DEFAULT_NAME + DEFAULT_TXT_FILE_EXTENSION;
        this.loadData();
    }

    public PurchaseFileRepository(Validator<Purchase> validator, String fileName) {
        super(validator);
        initiate();
        this.path = PATH + fileName + DEFAULT_TXT_FILE_EXTENSION;
        this.loadData();
    }

    private void initiate() {
        this.PATH = "data/txt/purchase/";
        this.DEFAULT_NAME = "purchases";
    }

    @Override
    protected Purchase getEntityFromStringLine(String stringLine) {
        List<String> items = Arrays.asList(stringLine.split(","));
        return new Purchase(
                Long.parseLong(items.get(0)), // long
                Long.parseLong(items.get(1)), // long
                Long.parseLong(items.get(2)), // long
                LocalDate.parse(items.get(3)) // LocalDate
        );
    }

    @Override
    protected String putEntityLineString(Purchase purchase) {
        return purchase.getId() + "," + purchase.getBookId() + "," + purchase.getClientId() + "," + purchase.getLastModifiedDate();
    }
}
