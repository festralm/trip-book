package ru.itis.tripbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.tripbook.dto.UserAdminForm;
import ru.itis.tripbook.exception.*;
import ru.itis.tripbook.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;


    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            var userForAdmin = userService.getUserByIdForAdmin(id);
            LOGGER.info("Returning UserAdminDto {}", userForAdmin);
            return ResponseEntity.ok(userForAdmin);
        } catch (UserNotFoundException e) {
            LOGGER.info("User is not found");
            var myBody = new MyResponseBody(MyStatus.USER_IS_NOT_FOUND);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        }
    }

    @PostMapping("/users/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            LOGGER.info("Trying to delete user with id {}", id);
            var userAdminDto = userService.deleteUserById(id);
            LOGGER.info("Returning status 200(OK) and {}", userAdminDto);
            return ResponseEntity.ok(userAdminDto);
        } catch (UserIsDeletedException e) {
            LOGGER.info("User is already deleted");
            var myBody = new MyResponseBody(MyStatus.USER_IS_ALREADY_DELETED);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        } catch (UserNotFoundException e) {
            LOGGER.info("User is not found");
            var myBody = new MyResponseBody(MyStatus.USER_IS_NOT_FOUND);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        }
    }

    @PostMapping("/users/restore/{id}")
    public ResponseEntity<?> restoreUserById(@PathVariable Long id) {
        try {
            LOGGER.info("Trying to restore user with id {}", id);
            var userAdminDto = userService.restoreUserById(id);
            LOGGER.info("Returning status 200(OK) and {}", userAdminDto);
            return ResponseEntity.ok(userAdminDto);
        } catch (UserIsNotDeletedException e) {
            LOGGER.info("User is already not deleted");
            var myBody = new MyResponseBody(MyStatus.USER_IS_ALREADY_NOT_DELETED);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        } catch (UserNotFoundException e) {
            LOGGER.info("User is not found");
            var myBody = new MyResponseBody(MyStatus.USER_IS_NOT_FOUND);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        }
    }

    @PostMapping("/users/ban/{id}")
    public ResponseEntity<?> blockUserById(@PathVariable Long id) {
        try {
            LOGGER.info("Trying to ban user with id {}", id);
            var userAdminDto = userService.blockUserById(id);
            LOGGER.info("Returning status 200(OK) and {}", userAdminDto);
            return ResponseEntity.ok(userAdminDto);
        } catch (UserIsBlockedException e) {
            LOGGER.info("User is already blocked");
            var myBody = new MyResponseBody(MyStatus.USER_IS_ALREADY_BLOCKED);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        } catch (UserNotFoundException e) {
            LOGGER.info("User is not found");
            var myBody = new MyResponseBody(MyStatus.USER_IS_NOT_FOUND);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        }
    }

    @PostMapping("/users/unban/{id}")
    public ResponseEntity<?> unblockUserById(@PathVariable Long id) {
        try {
            LOGGER.info("Trying to unban user with id {}", id);
            var userAdminDto = userService.unblockUserById(id);
            LOGGER.info("Returning status 200(OK) and {}", userAdminDto);
            return ResponseEntity.ok(userAdminDto);
        } catch (UserIsNotBlockedException e) {
            LOGGER.info("User is already not blocked");
            var myBody = new MyResponseBody(MyStatus.USER_IS_ALREADY_NOT_BLOCKED);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        } catch (UserNotFoundException e) {
            LOGGER.info("User is not found");
            var myBody = new MyResponseBody(MyStatus.USER_IS_NOT_FOUND);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        }
    }

    @PostMapping("/make-admin/{id}")
    public ResponseEntity<?> makeAdminById(@PathVariable Long id) {
        try {
            LOGGER.info("Trying to make user with id {} admin", id);
            var userAdminDto = userService.makeAdminById(id);
            LOGGER.info("Returning status 200(OK) and {}", userAdminDto);
            return ResponseEntity.ok(userAdminDto);
        } catch (UserIsAlreadyAdminException e) {
            LOGGER.info("User is already admin");
            var myBody = new MyResponseBody(MyStatus.USER_IS_ALREADY_ADMIN);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        } catch (UserNotFoundException e) {
            LOGGER.info("User is not found");
            var myBody = new MyResponseBody(MyStatus.USER_IS_NOT_FOUND);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        }
    }

    @PostMapping("/undo-admin/{id}")
    public ResponseEntity<?> undoAdminById(@PathVariable Long id) {
        try {
            LOGGER.info("Trying to undo user with id {} admin", id);
            var userAdminDto = userService.undoAdminById(id);
            LOGGER.info("Returning status 200(OK) and {}", userAdminDto);
            return ResponseEntity.ok(userAdminDto);
        } catch (UserIsNotAdminException e) {
            LOGGER.info("User is already not admin");
            var myBody = new MyResponseBody(MyStatus.USER_IS_ALREADY_NOT_ADMIN);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        } catch (UserNotFoundException e) {
            LOGGER.info("User is not found");
            var myBody = new MyResponseBody(MyStatus.USER_IS_NOT_FOUND);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<?>> findUsers(@RequestBody UserAdminForm user) {
        LOGGER.info("Got UserAdminSearchDto {}", user.toString());
        var usersList = userService.findUsers(user);
        LOGGER.info("Returning status 200(OK) and List of UserAdminDto");
        return ResponseEntity.ok().body(usersList);
    }
}
