package ru.itis.tripbook.exception;

public class UserIsNotAdminException extends DataIsInvalidException {
    public UserIsNotAdminException(String email) {
        super("User with email " + email + " is not admin");
    }
}
