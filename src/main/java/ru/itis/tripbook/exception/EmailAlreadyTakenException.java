package ru.itis.tripbook.exception;

public class EmailAlreadyTakenException extends Exception {
    public EmailAlreadyTakenException(String email) {
        super("Email " + email + " is already taken");
    }
}
