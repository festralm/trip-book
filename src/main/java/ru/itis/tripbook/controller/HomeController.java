package ru.itis.tripbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tripbook.annotation.Loggable;
import ru.itis.tripbook.annotation.ResultLoggable;
import ru.itis.tripbook.exception.JwtAuthenticationException;
import ru.itis.tripbook.exception.UserIsBlockedException;
import ru.itis.tripbook.exception.UserIsDeletedException;
import ru.itis.tripbook.exception.UserNotFoundException;
import ru.itis.tripbook.security.UserDetailsImpl;
import ru.itis.tripbook.service.UserService;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping("/")
public class HomeController {
    @Autowired
    private UserService userService;

    @ResultLoggable
    @PermitAll
    @GetMapping
    public ResponseEntity<?> getDefault(@AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            try {
                return ResponseEntity.ok().body(
                        userService.getUserById(user.getUser().getId())
                );
            } catch (UserIsBlockedException e) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } catch (UserIsDeletedException e) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } catch (UserNotFoundException e) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
    }
}
