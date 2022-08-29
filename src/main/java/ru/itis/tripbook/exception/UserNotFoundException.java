package ru.itis.tripbook.exception;

public class UserNotFoundException extends DataNotFoundException{
    public UserNotFoundException(Long id) {
        super("User with id " + id + " is not found.");
    }
}
