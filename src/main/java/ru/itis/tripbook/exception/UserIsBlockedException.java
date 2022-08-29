package ru.itis.tripbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserIsBlockedException extends DataIsInvalidException {
    public UserIsBlockedException(String email) {
        super("User with email " + email + " is blocked.");
    }
}
