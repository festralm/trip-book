package ru.itis.tripbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import ru.itis.tripbook.annotation.Loggable;
import ru.itis.tripbook.dto.user.UserDto;
import ru.itis.tripbook.dto.user.UserSignInForm;
import ru.itis.tripbook.security.jwt.JwtTokenProvider;
import ru.itis.tripbook.service.UserService;

import javax.annotation.security.PermitAll;

@RequestMapping("/sign-in")
@RestController
@PermitAll
public class SignInController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Loggable
    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody UserSignInForm user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    user.getPassword())
            );
            var newUser = userService.findByEmail(user.getEmail());
            var userDto = UserDto.from(newUser, false);
            var token = jwtTokenProvider.create(newUser.getEmail(), newUser.getRole());
            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            token
                    )
                    .body(
                            userDto
                    );
        } catch (AuthenticationException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
