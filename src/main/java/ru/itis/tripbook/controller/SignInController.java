package ru.itis.tripbook.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import ru.itis.tripbook.dto.UserSignInForm;
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

    @ApiOperation(value = "Аутентифицировать пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Успешно",
                    responseContainer = "HashMap"),
            @ApiResponse(code = 403,
                    message = "Неправильный логин или пароль")
    })
    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody UserSignInForm user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            var newUser = userService.findByEmail(user.getEmail());
            var token = jwtTokenProvider.create(newUser.getEmail(), newUser.getRole());
            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            token
                    )
                    .body(
                            newUser
                    );
        } catch (AuthenticationException exception) {
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @ApiOperation(value = "Получить страницу \"Войти\"")
    @GetMapping
    @ResponseBody
    public String getSignInPage() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Войти</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<form action=\"/sign-in\" method=\"post\">\n" +
                "    <input type=\"email\" name=\"username\">\n" +
                "    <input type=\"password\" name=\"password\">\n" +
                "    <label>\n" +
                "        <input type=\"checkbox\" name=\"remember-me\">Запомни меня\n" +
                "        </label>\n" +
                "    <input type=\"submit\" value=\"Войти\">\n" +
                "    </form>\n" +
                "</body>\n" +
                "</html>\n";
    }
}
