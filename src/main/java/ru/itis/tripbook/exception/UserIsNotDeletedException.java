package ru.itis.tripbook.exception;

public class UserIsNotDeletedException extends DataIsInvalidException {
    public UserIsNotDeletedException(String email) {

        super("User with email " + email + " is not deleted.");
    }
}
