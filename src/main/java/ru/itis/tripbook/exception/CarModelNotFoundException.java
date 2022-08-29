package ru.itis.tripbook.exception;

public class CarModelNotFoundException extends DataNotFoundException {
    public CarModelNotFoundException(Long id) {
        super(String.format("Car model %d not found", id));
    }
}
