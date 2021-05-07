package ru.itis.tripbook.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tripbook.dto.UserAdminDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/logout")
@PreAuthorize("isAuthenticated()")
public class LogoutController {
    @ApiOperation(value = "Выйти из профиля")
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "Успешно")})
    @PostMapping
    public ResponseEntity<?> logout(HttpServletRequest request,
                                 HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler =
                new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
