package ru.itis.tripbook.exception;

public class UserIsNotBlockedException extends DataIsInvalidException {

    public UserIsNotBlockedException(String email) {
        super("User with email " + email + " is blocked.");
    }
}
