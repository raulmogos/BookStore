package repository.file_repositories;

import models.Client;
import models.validation.Validator;

import java.util.Arrays;
import java.util.List;

public class ClientFileRepository extends FileRepository<Long, Client> {

    public ClientFileRepository(Validator<Client> validator) {
        super(validator);
        initiate();
        this.path = PATH + DEFAULT_NAME + DEFAULT_TXT_FILE_EXTENSION;
        this.loadData();
    }

    public ClientFileRepository(Validator<Client> validator, String fileName) {
        super(validator);
        initiate();
        this.path = PATH + fileName + DEFAULT_TXT_FILE_EXTENSION;
        this.loadData();
    }

    private void initiate() {
        this.PATH = "data/txt/client/";
        this.DEFAULT_NAME = "clients";
    }

    @Override
    protected Client getEntityFromStringLine(String stringLine) {
        List<String> items = Arrays.asList(stringLine.split(","));
        return new Client(
                Long.parseLong(items.get(0)),
                items.get(1), // string - first name
                items.get(2), // string - last name
                Integer.parseInt(items.get(3)) // int money spent
        );
    }

    @Override
    protected String putEntityLineString(Client client) {
        return client.getId() + "," + client.getFirstName() + "," + client.getLastName() + "," + client.getMoneySpent();
    }
}
