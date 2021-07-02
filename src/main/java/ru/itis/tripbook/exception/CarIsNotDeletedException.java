package ru.itis.tripbook.exception;

public class CarIsNotDeletedException extends DataIsInvalidException {
    public CarIsNotDeletedException(Long id) {
        super(String.format("Transport with id %d is already not deleted", id));
    }
}
