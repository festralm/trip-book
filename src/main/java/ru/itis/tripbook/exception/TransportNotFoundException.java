package ru.itis.tripbook.exception;

public class TransportNotFoundException extends DataNotFoundException {
    public TransportNotFoundException(Long id) {
        super(String.format("Transport with id %d is not found", id));
    }
}
