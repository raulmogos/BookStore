package Models.Validation;

public interface Validator<T> {
    void validate(T object) throws ValidatorException;
}
