package ru.itis.tripbook.exception;

public class CarIsNotBlockedException extends DataIsInvalidException {
    public CarIsNotBlockedException(Long id) {
        super(String.format("Transport with id %d is already not blocked", id));
    }
}
