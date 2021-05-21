package ru.itis.tripbook.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.itis.tripbook.dto.UserAdminDto;
import ru.itis.tripbook.dto.UserSignInForm;
import ru.itis.tripbook.exception.*;
import ru.itis.tripbook.security.UserDetailsImpl;
import ru.itis.tripbook.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserAdminDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getUsersForAdmin());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUserByIdForAdmin(id));
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User is not found", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/users/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.deleteUserById(id));
        } catch (UserIsDeletedException e) {
            return new ResponseEntity<>("User is already deleted", HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User is not found", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/users/restore/{id}")
    public ResponseEntity<?> restoreUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.restoreUserById(id));
        } catch (UserIsNotDeletedException e) {
            return new ResponseEntity<>("User is not deleted", HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User is not found", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/users/ban/{id}")
    public ResponseEntity<?> blockUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.blockUserById(id));
        } catch (UserIsBlockedException e) {
            return new ResponseEntity<>("User is already blocked", HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User is not found", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/users/unban/{id}")
    public ResponseEntity<?> unblockUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.unblockUserById(id));
        } catch (UserIsNotBlockedException e) {
            return new ResponseEntity<>("User is not blocked", HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User is not found", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/make-admin/{id}")
    public ResponseEntity<?> makeAdminById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.makeAdminById(id));
        } catch (UserIsAlreadyAdminException e) {
            return new ResponseEntity<>("User is already admin", HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User is not found", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/undo-admin/{id}")
    public ResponseEntity<?> undoAdminById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.undoAdminById(id));
        } catch (UserIsNotAdminException e) {
            return new ResponseEntity<>("User is not admin", HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User is not found", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<?>> findUsers(@RequestBody UserAdminDto user) {
            return ResponseEntity.ok().body(userService.findUsers(user));
    }
}
