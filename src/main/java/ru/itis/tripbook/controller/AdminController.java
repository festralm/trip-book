package ru.itis.tripbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.tripbook.annotation.Loggable;
import ru.itis.tripbook.dto.UserAdminForm;
import ru.itis.tripbook.exception.*;
import ru.itis.tripbook.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @Loggable
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(userService.getUserByIdForAdmin(id));
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Loggable
    @PostMapping("/users/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(userService.deleteUserById(id));
        } catch (UserIsDeletedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Loggable
    @PostMapping("/users/restore/{id}")
    public ResponseEntity<?> restoreUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(userService.restoreUserById(id));
        } catch (UserIsNotDeletedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Loggable
    @PostMapping("/users/ban/{id}")
    public ResponseEntity<?> blockUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.blockUserById(id));
        } catch (UserIsBlockedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Loggable
    @PostMapping("/users/unban/{id}")
    public ResponseEntity<?> unblockUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.unblockUserById(id));
        } catch (UserIsNotBlockedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Loggable
    @PostMapping("/make-admin/{id}")
    public ResponseEntity<?> makeAdminById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.makeAdminById(id));
        } catch (UserIsAlreadyAdminException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Loggable
    @PostMapping("/undo-admin/{id}")
    public ResponseEntity<?> undoAdminById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.undoAdminById(id));
        } catch (UserIsNotAdminException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Loggable
    @PostMapping("/search")
    public ResponseEntity<List<?>> findUsers(@RequestBody UserAdminForm user) {
        return ResponseEntity.ok().body(userService.findUsers(user));
    }
}
