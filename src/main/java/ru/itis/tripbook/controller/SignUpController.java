package ru.itis.tripbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.tripbook.dto.UserDto;
import ru.itis.tripbook.dto.UserSignUpForm;
import ru.itis.tripbook.exception.EmailAlreadyTakenException;
import ru.itis.tripbook.model.Role;
import ru.itis.tripbook.security.jwt.JwtTokenProvider;
import ru.itis.tripbook.service.UserService;

import javax.annotation.security.PermitAll;

@RequestMapping("/sign-up")
@RestController
@PermitAll
public class SignUpController {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(SignUpController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody UserSignUpForm user) {
        LOGGER.info("User form " + user.toString());
        try {
            var newUser = userService.save(user);
            var userDto = UserDto.from(newUser);
            LOGGER.info("Enrolled user {}", userDto);
            var token = jwtTokenProvider.create(user.getEmail(), Role.USER);
            LOGGER.info("Created token {}", token);
            LOGGER.info("Returning status 200(OK), token and UserDto");
            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            token
                    )
                    .body(
                            userDto
                    );
        } catch (EmailAlreadyTakenException exception) {
            LOGGER.info("Email {} is taken", user.getEmail());
            var myBody = new MyResponseBody(MyStatus.EMAIL_TAKEN);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        }
    }
}
