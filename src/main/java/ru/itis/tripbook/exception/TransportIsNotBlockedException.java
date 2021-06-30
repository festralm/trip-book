package ru.itis.tripbook.exception;

public class TransportIsNotBlockedException extends DataIsInvalidException {
    public TransportIsNotBlockedException(Long id) {
        super(String.format("Transport with id %d is already not blocked", id));
    }
}
