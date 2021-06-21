package ru.itis.tripbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.itis.tripbook.dto.UserDto;
import ru.itis.tripbook.exception.UserIsBlockedException;
import ru.itis.tripbook.exception.UserIsDeletedException;
import ru.itis.tripbook.exception.UserNotFoundException;
import ru.itis.tripbook.model.User;
import ru.itis.tripbook.security.UserDetailsImpl;
import ru.itis.tripbook.service.UserService;

import javax.annotation.security.PermitAll;

@RestController
public class ProfileController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);
    @Autowired
    private UserService userService;

    @GetMapping("/users/{id}")
    @PermitAll
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserDto userDto = null;
        try {
            userDto = userService.getUserById(id);
        } catch (UserIsBlockedException e) {
            //todo
        } catch (UserIsDeletedException e) {
            //todo
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User is not found", HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(userDto);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetailsImpl user) {
        UserDto userDto = null;
        try {
            userDto = userService.getUserById(user.getUser().getId());
        } catch (UserIsBlockedException e) {
            LOGGER.info("User is blocked");
            var myBody = new MyResponseBody(MyStatus.USER_IS_BLOCKED);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        } catch (UserIsDeletedException e) {
            LOGGER.info("User is deleted");
            var myBody = new MyResponseBody(MyStatus.USER_IS_DELETED);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        } catch (UserNotFoundException e) {
            LOGGER.info("User is not found");
            var myBody = new MyResponseBody(MyStatus.USER_IS_NOT_FOUND);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        }
        LOGGER.info("Returning status 200(OK) and userDto {} with {} cars", userDto);
        return ResponseEntity.ok(userDto);
    }
}
