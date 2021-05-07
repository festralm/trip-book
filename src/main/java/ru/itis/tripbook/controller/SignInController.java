package ru.itis.tripbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itis.tripbook.dto.UserSignInDto;
import ru.itis.tripbook.model.Role;
import ru.itis.tripbook.model.User;
import ru.itis.tripbook.repository.UserRepository;
import ru.itis.tripbook.security.jwt.JwtTokenProvider;
import ru.itis.tripbook.service.UserService;

import javax.annotation.security.PermitAll;
import java.util.HashMap;
import java.util.Map;

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

    @PostMapping()
    public ResponseEntity<?> authenticate(@RequestBody UserSignInDto user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            var newUser = userService.findByEmail(user.getEmail());
            var token = jwtTokenProvider.create(newUser.getEmail(), newUser.getRole());
            var response = new HashMap<Object, Object>();
            response.put("email", user.getEmail());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException exception) {
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }
}
