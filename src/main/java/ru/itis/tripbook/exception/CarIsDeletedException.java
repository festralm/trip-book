package ru.itis.tripbook.exception;

public class CarIsDeletedException extends DataIsInvalidException{
    public CarIsDeletedException(Long id) {
        super(String.format("Transport with id %d is deleted", id));
    }
}
