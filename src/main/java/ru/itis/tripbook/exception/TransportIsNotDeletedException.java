package ru.itis.tripbook.exception;

public class TransportIsNotDeletedException extends DataIsInvalidException {
    public TransportIsNotDeletedException(Long id) {
        super(String.format("Transport with id %d is already not deleted", id));
    }
}
