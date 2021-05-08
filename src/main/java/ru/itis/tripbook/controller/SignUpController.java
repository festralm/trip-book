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
import org.springframework.web.bind.annotation.*;
import ru.itis.tripbook.dto.UserSignInForm;
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
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "Зарегистрировать пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Успешно",
                    responseContainer = "HashMap"),
            @ApiResponse(code = 400,
                    message = "Пароли не совпадают"),
            @ApiResponse(code = 409,
                    message = "Email уже занят")
    })
    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody UserSignUpForm user) {
        try {
            if (user.getPassword().equals(user.getRepeatPassword())) {
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
            } else {
                return new ResponseEntity<>("Passwords are unequal", HttpStatus.BAD_REQUEST);
            }
        } catch (EmailAlreadyTakenException exception) {
            return new ResponseEntity<>("Email is already taken", HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Получить страницу \"Зарегистрироваться\"")
    @GetMapping
    @ResponseBody
    public String getSignUpPage() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Зарегистрироваться</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<form action=\"/sign-in\" method=\"post\">\n" +
                "    <input type=\"email\" name=\"username\">\n" +
                "    <input type=\"password\" name=\"password\">\n" +
                "    <label>\n" +
                "        <input type=\"checkbox\" name=\"remember-me\">Запомни меня\n" +
                "        </label>\n" +
                "    <input type=\"submit\" value=\"Зарегистрироваться\">\n" +
                "    </form>\n" +
                "</body>\n" +
                "</html>\n";
    }
}
