package ru.itis.tripbook.logger;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class NotFoundExceptionHandler {
    public static void handle(String className, Exception ex) {
        var LOGGER = LoggerFactory.getLogger(className);
        LOGGER.error(ex.getMessage());
    }
}
