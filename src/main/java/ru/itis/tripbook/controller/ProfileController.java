package ru.itis.tripbook.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.tripbook.dto.UserDto;
import ru.itis.tripbook.exception.UserIsBlockedException;
import ru.itis.tripbook.exception.UserIsDeletedException;
import ru.itis.tripbook.response.MyResponse;
import ru.itis.tripbook.service.UserService;

import java.util.List;

@RestController
public class ProfileController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "Получить пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Успешно получено",
                    response = UserDto.class),
            @ApiResponse(code = 410,
                    message = "Пользователь заблокирован"),
            @ApiResponse(code = 404,
                    message = "Пользователь удален")
    })
    @GetMapping("/users/{id}")
    @ResponseBody
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        UserDto userDto = null;
        try {
            userDto = userService.getUserById(id);
        } catch (UserIsBlockedException e) {
            return ResponseEntity
                    .status(HttpStatus.GONE)
                    .body(MyResponse.USER_BLOCKED);
        } catch (UserIsDeletedException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(MyResponse.USER_DELETED);
        }
        return ResponseEntity.ok(userDto);
    }
}
