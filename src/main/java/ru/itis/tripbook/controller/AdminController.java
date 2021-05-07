package ru.itis.tripbook.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tripbook.dto.UserDto;
import ru.itis.tripbook.model.User;
import ru.itis.tripbook.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;


    @ApiOperation(value = "Получить список всех пользователей")
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "Успешно получено",
            response = UserDto.class,
            responseContainer = "List")})
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @ApiOperation(value = "Получить пользователя по id")
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "Успешно получено",
            response = UserDto.class)})
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
