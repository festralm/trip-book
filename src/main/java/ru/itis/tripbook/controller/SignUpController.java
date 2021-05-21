package ru.itis.tripbook.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.itis.tripbook.dto.UserSignInForm;
import ru.itis.tripbook.dto.UserSignUpForm;
import ru.itis.tripbook.exception.EmailAlreadyTakenException;
import ru.itis.tripbook.model.Role;
import ru.itis.tripbook.security.UserDetailsImpl;
import ru.itis.tripbook.security.jwt.JwtTokenProvider;
import ru.itis.tripbook.service.UserService;

import javax.annotation.security.PermitAll;

@RequestMapping("/sign-up")
@RestController
@PermitAll
public class SignUpController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody UserSignUpForm user) {
        try {
                var newUser = userService.save(user);
                var token = jwtTokenProvider.create(user.getEmail(), Role.USER);
                return ResponseEntity.ok()
                        .header(
                                HttpHeaders.AUTHORIZATION,
                                token
                        )
                        .body(
                                newUser
                        );
        } catch (EmailAlreadyTakenException exception) {
            return ResponseEntity.ok()
                    .body(new MyResponseBody(MyStatus.EMAIL_TAKEN));
        }
    }
}
