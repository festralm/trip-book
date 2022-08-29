package ru.itis.tripbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.itis.tripbook.annotation.Loggable;
import ru.itis.tripbook.annotation.ResultLoggable;
import ru.itis.tripbook.dto.user.Passwords;
import ru.itis.tripbook.dto.user.UserEditForm;
import ru.itis.tripbook.exception.OldPasswordIsWrongException;
import ru.itis.tripbook.exception.UserIsBlockedException;
import ru.itis.tripbook.exception.UserIsDeletedException;
import ru.itis.tripbook.exception.UserNotFoundException;
import ru.itis.tripbook.security.UserDetailsImpl;
import ru.itis.tripbook.service.UserService;

import javax.annotation.security.PermitAll;

@RestController
public class ProfileController {
    @Autowired
    private UserService userService;

    @Loggable
    @GetMapping("/users/{id}")
    @PermitAll
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(userService.getUserById(id));
        } catch (UserIsBlockedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (UserIsDeletedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ResultLoggable
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetailsImpl user) {
        try {
            return ResponseEntity.ok().body(userService.getUserById(user.getUser().getId()));
        } catch (UserIsBlockedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (UserIsDeletedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ResultLoggable
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/profile")
    public ResponseEntity<?> editUser(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody UserEditForm userForm
    ) {
        try {
            return ResponseEntity.ok().body(
                    userService.editUser(user.getUser().getId(), userForm)
            );
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ResultLoggable
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteThis(@AuthenticationPrincipal UserDetailsImpl user) {
        try {
            return ResponseEntity.ok().body(
                    userService.deleteUserById(user.getUser().getId())
            );
        } catch (UserIsDeletedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ResultLoggable
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody Passwords passwords
            ) {
        try {
            return ResponseEntity.ok().body(
                    userService.changePassword(
                            user.getUser().getId(),
                            passwords.getOldPassword(),
                            passwords.getNewPassword()
                    )
            );
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (OldPasswordIsWrongException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
