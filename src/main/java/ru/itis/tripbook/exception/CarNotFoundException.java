package ru.itis.tripbook.exception;

public class CarNotFoundException extends DataNotFoundException {
    public CarNotFoundException(Long id) {
        super(String.format("Transport with id %d is not found", id));
    }
}
