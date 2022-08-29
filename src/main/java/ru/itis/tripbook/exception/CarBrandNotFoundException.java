package ru.itis.tripbook.exception;

public class CarBrandNotFoundException extends DataNotFoundException {
    public CarBrandNotFoundException(Long id) {
        super(String.format("Car brand %d not found", id));
    }
}
