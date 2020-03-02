package Repository;

import Models.BaseEntity;
import Models.Validation.ValidatorException;

import java.util.Optional;

public class InMemoryRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {
    @Override
    public Optional<T> findOne(ID id) {
        return Optional.empty();
    }

    @Override
    public Iterable<T> findAll() {
        return null;
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        return Optional.empty();
    }

    @Override
    public Optional<T> delete(ID id) {
        return Optional.empty();
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        return Optional.empty();
    }
}
