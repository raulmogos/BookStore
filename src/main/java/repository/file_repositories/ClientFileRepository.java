package repository.file_repositories;

import models.Book;
import models.Client;
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

public class ClientFileRepository extends InMemoryRepository<Long, Client> {

    private static final String PATH = "data/txt/client/";
    private static final String DEFAULT_NAME = "clients";
    private static final String DEFAULT_TXT_FILE_EXTENSION = ".txt";

    private String path;

    public ClientFileRepository(Validator<Client> validator) {
        super(validator);
        this.path = PATH + DEFAULT_NAME + DEFAULT_TXT_FILE_EXTENSION;
        this.loadData();
    }

    public ClientFileRepository(Validator<Client> validator, String fileName) {
        super(validator);
        this.path = PATH + fileName + DEFAULT_TXT_FILE_EXTENSION;
        this.loadData();
    }

    private void loadData() {
        Path _path = Paths.get(path);
        try {
            Files.lines(_path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));
                Client client = new Client(
                        // Long id, String firstName, String lastName, int moneySpent
                        Long.parseLong(items.get(0)), // long
                        items.get(1), // string
                        items.get(2), // string
                        Integer.parseInt(items.get(3)) // int
                );
                super.save(client);
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void refreshDataInFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            bufferedWriter.write("");
            for (Client client : this.entities.values()) {
                bufferedWriter.write(client.getId() + "," + client.getFirstName() + "," + client.getLastName() + "," + client.getMoneySpent());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile(Client client) {
        Path _path = Paths.get(path);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(_path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(client.getId() + "," + client.getFirstName() + "," + client.getLastName() + "," + client.getMoneySpent());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Client> save(Client client) {
        Optional<Client> optionalClient = super.save(client);
        if (optionalClient.isPresent()) {
            return optionalClient;
        }
        saveToFile(client);
        return Optional.empty();
    }

    @Override
    public Optional<Client> delete(Long id) {
        Optional<Client> optionalClient = super.delete(id);
        refreshDataInFile();
        return Optional.empty();
    }

    @Override
    public Optional<Client> update(Client client) {
        Optional<Client> optionalClient = super.update(client);
        refreshDataInFile();
        return Optional.empty();
    }
}
