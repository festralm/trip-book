package ru.itis.tripbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserIsDeletedException extends DataIsInvalidException {
    public UserIsDeletedException(String email) {
        super("User with email " + email + " is deleted.");
    }
}
