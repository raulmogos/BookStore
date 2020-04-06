package repository;
import models.BaseEntity;
import java.io.Serializable;

/**
 * Created by radu.
 */
public interface SortingRepository<ID extends Serializable,
        T extends BaseEntity<ID>>
        extends Repository<ID, T> {

    Iterable<T> findAll(Sort sort);

    //TODO: insert sorting-related code here
}