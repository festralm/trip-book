package ru.itis.tripbook.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tripbook.dto.UserAdminDto;
import ru.itis.tripbook.dto.UserDto;
import ru.itis.tripbook.model.User;
import ru.itis.tripbook.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @Autowired
    private UserService userService;


    @ApiOperation(value = "Получить список всех пользователей для администратора")
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "Успешно получено",
            response = UserAdminDto.class,
            responseContainer = "List")})
    @GetMapping("/users")
    public ResponseEntity<List<UserAdminDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getUsersForAdmin());
    }

    @ApiOperation(value = "Получить пользователя по id для администратора")
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "Успешно получено",
            response = UserAdminDto.class)})
    @GetMapping("/users/{id}")
    public ResponseEntity<UserAdminDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserByIdForAdmin(id));
    }
}
