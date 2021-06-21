package ru.itis.tripbook.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.itis.tripbook.dto.UserDto;
import ru.itis.tripbook.dto.UserSignInForm;
import ru.itis.tripbook.exception.JwtAuthenticationException;
import ru.itis.tripbook.security.UserDetailsImpl;
import ru.itis.tripbook.security.jwt.JwtTokenProvider;
import ru.itis.tripbook.service.UserService;

import javax.annotation.security.PermitAll;

@RequestMapping("/sign-in")
@RestController
@PermitAll
public class SignInController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignInController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody UserSignInForm user) {
        LOGGER.info("User form {}", user.toString());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    user.getPassword())
            );
            var newUser = userService.findByEmail(user.getEmail());
            var userDto = UserDto.from(newUser);
            LOGGER.info("Found user {}", userDto);
            var token = jwtTokenProvider.create(newUser.getEmail(), newUser.getRole());
            LOGGER.info("Got token {}", token);
            LOGGER.info("Returning status 200(OK), token and UserDto");
            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            token
                    )
                    .body(
                            userDto
                    );
        } catch (AuthenticationException exception) {
            LOGGER.error("AuthenticationException");
            var myBody = new MyResponseBody(MyStatus.WRONG_AUTH);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        }
    }

}
