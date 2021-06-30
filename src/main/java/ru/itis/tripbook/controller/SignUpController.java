package ru.itis.tripbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.tripbook.annotation.Loggable;
import ru.itis.tripbook.dto.user.UserDto;
import ru.itis.tripbook.dto.user.UserSignUpForm;
import ru.itis.tripbook.exception.EmailAlreadyTakenException;
import ru.itis.tripbook.model.Role;
import ru.itis.tripbook.security.jwt.JwtTokenProvider;
import ru.itis.tripbook.service.UserService;

import javax.annotation.security.PermitAll;

@RequestMapping("/sign-up")
@RestController
@PermitAll
public class SignUpController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Loggable
    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody UserSignUpForm user) {
        try {
            var newUser = userService.save(user);
            var userDto = UserDto.from(newUser, false);
            var token = jwtTokenProvider.create(user.getEmail(), Role.USER);
            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            token
                    )
                    .body(
                            userDto
                    );
        } catch (EmailAlreadyTakenException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
