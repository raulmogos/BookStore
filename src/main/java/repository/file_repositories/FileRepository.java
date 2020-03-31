package repository.file_repositories;

import models.BaseEntity;
import models.validation.Validator;
import repository.InMemoryRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public abstract class FileRepository<ID, T extends BaseEntity<ID>> extends InMemoryRepository<ID, T> {

    protected String PATH;
    protected String DEFAULT_NAME;
    protected String DEFAULT_TXT_FILE_EXTENSION = ".txt";

    protected String path;

    public FileRepository(Validator<T> validator) {
        super(validator);
    }

    /**
     * Call this in your child constructor to get all data from the file.
     */
    protected void loadData() {
        Path _path = Paths.get(path);
        try {
            Files.lines(_path).forEach(line -> {
                T entity = this.getEntityFromStringLine(line);
                super.save(entity);
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    protected void refreshDataInFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            bufferedWriter.write("");
            super.entities.values().forEach(entity -> {
                try {
                    bufferedWriter.write(this.putEntityLineString(entity));
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile(T entity) {
        Path _path = Paths.get(path);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(_path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(this.putEntityLineString(entity));
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<T> save(T entity) {
        Optional<T> optionalIDBaseEntityEntity = super.save(entity);
        if (optionalIDBaseEntityEntity.isPresent()) {
            return optionalIDBaseEntityEntity;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    @Override
    public Optional<T> delete(ID id) {
        Optional<T> optionalIDBaseEntity = super.delete(id);
        refreshDataInFile();
        return Optional.empty();
    }

    @Override
    public Optional<T> update(T entity) {
        Optional<T> optionalIDBaseEntity = super.update(entity);
        refreshDataInFile();
        return Optional.empty();
    }

    /**
     * Parses a a string line and gets information from it and returns
     *  the new object created.
     *
     * @param stringLine: a string that needs ti be parsed
     * @return an entity with .
     */
    protected abstract T getEntityFromStringLine(String stringLine);

    /**
     * Converts an entity to its inline representation in a txt file.
     *
     * @param entity: the entity
     * @return a String.
     */
    protected abstract String putEntityLineString(T entity);
}
