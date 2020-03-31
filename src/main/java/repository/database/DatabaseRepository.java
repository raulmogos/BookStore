package repository.database;


import models.BaseEntity;
import models.validation.ValidatorException;
import repository.Repository;

import java.util.Optional;

abstract public class DatabaseRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {
    @Override
    public Optional findOne(Object o) {
        return Optional.empty();
    }

    @Override
    public Iterable findAll() {
        return null;
    }

    @Override
    public Optional save(BaseEntity entity) throws ValidatorException {
        return Optional.empty();
    }

    @Override
    public Optional delete(Object o) {
        return Optional.empty();
    }

    @Override
    public Optional update(BaseEntity entity) throws ValidatorException {
        return Optional.empty();
    }
}
