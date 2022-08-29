package ru.itis.tripbook.exception;

public class CarIsBlockedException extends DataIsInvalidException {
    public CarIsBlockedException(Long id) {
        super(String.format("Transport with id %d is blocked", id));
    }
}
