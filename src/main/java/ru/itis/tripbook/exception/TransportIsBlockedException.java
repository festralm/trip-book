package ru.itis.tripbook.exception;

public class TransportIsBlockedException extends DataIsInvalidException {
    public TransportIsBlockedException(Long id) {
        super(String.format("Transport with id %d is blocked", id));
    }
}
