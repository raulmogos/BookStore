package repository.xml_repositories;

import models.BaseEntity;
import models.validation.Validator;
import repository.InMemoryRepository;

abstract public class XMLRepository<ID, T extends BaseEntity<ID>> extends InMemoryRepository<ID, T> {
    public XMLRepository(Validator<T> validator) {
        super(validator);
    }
    // todo: implement it like file repos
}
