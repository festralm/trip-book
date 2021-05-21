package ru.itis.tripbook.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tripbook.dto.UserAdminDto;
import ru.itis.tripbook.model.User;
import ru.itis.tripbook.security.UserDetailsImpl;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping("/")
public class HomeController {
    @PermitAll
    @GetMapping
    public ResponseEntity<?> getProfilePage(@AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            return ResponseEntity.ok(
                    new MyResponseBody(MyStatus.IS_AUTHORIZED, user.getUser())

            );
        } else {
            return ResponseEntity.ok().body(
                    new MyResponseBody(MyStatus.NOT_AUTHORIZED)
            );
        }
    }
}
