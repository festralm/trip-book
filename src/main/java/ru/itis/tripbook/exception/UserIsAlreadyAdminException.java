package ru.itis.tripbook.exception;

public class UserIsAlreadyAdminException extends DataIsInvalidException {
    public UserIsAlreadyAdminException(String email) {
        super("User with email " + email + " is already admin");
    }
}
