package ru.itis.tripbook.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.itis.tripbook.dto.UserDto;
import ru.itis.tripbook.exception.UserIsBlockedException;
import ru.itis.tripbook.exception.UserIsDeletedException;
import ru.itis.tripbook.exception.UserNotFoundException;
import ru.itis.tripbook.model.User;
import ru.itis.tripbook.response.MyResponse;
import ru.itis.tripbook.security.UserDetailsImpl;
import ru.itis.tripbook.service.UserService;

import javax.annotation.security.PermitAll;

@RestController
public class ProfileController {
    @Autowired
    private UserService userService;

    @GetMapping("/users/{id}")
    @PermitAll
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserDto userDto = null;
        try {
            userDto = userService.getUserByIdForUser(id);
        } catch (UserIsBlockedException e) {
            return ResponseEntity
                    .status(HttpStatus.GONE)
                    .body(MyResponse.USER_BLOCKED);
        } catch (UserIsDeletedException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(MyResponse.USER_DELETED);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User is not found", HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(userDto);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public ResponseEntity<User> getProfilePage(@AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(user.getUser());
    }
}
