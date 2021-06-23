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
import ru.itis.tripbook.annotation.Loggable;
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
            var userDto = UserDto.from(newUser);
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
