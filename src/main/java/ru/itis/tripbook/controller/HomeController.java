package ru.itis.tripbook.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tripbook.dto.UserAdminDto;
import ru.itis.tripbook.exception.UserIsBlockedException;
import ru.itis.tripbook.exception.UserIsDeletedException;
import ru.itis.tripbook.exception.UserNotFoundException;
import ru.itis.tripbook.model.User;
import ru.itis.tripbook.security.UserDetailsImpl;
import ru.itis.tripbook.service.UserService;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping("/")
public class HomeController {
    @Autowired
    private UserService userService;

    @PermitAll
    @GetMapping
    public ResponseEntity<?> getProfilePage(@AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            try {
                return ResponseEntity.ok(
                        new MyResponseBody(MyStatus.IS_AUTHORIZED,
                                userService.getUserByIdForUser(user.getUser().getId()))

                );
            } catch (UserIsBlockedException e) {
                return new ResponseEntity<>("User is not found", HttpStatus.NO_CONTENT);
            } catch (UserIsDeletedException e) {
                return new ResponseEntity<>("User is not found", HttpStatus.NO_CONTENT);
            } catch (UserNotFoundException e) {
                return new ResponseEntity<>("User is not found", HttpStatus.NO_CONTENT);
            }
        } else {
            return ResponseEntity.ok().body(
                    new MyResponseBody(MyStatus.NOT_AUTHORIZED)
            );
        }
    }
}
