package ru.itis.tripbook.exception;

public class TransportIsDeletedException extends DataIsInvalidException{
    public TransportIsDeletedException(Long id) {
        super(String.format("Transport with id %d is blocked", id));
    }
}
