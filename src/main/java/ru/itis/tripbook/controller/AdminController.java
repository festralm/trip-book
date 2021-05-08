package ru.itis.tripbook.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.tripbook.dto.UserAdminDto;
import ru.itis.tripbook.exception.*;
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
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Успешно получено",
                    response = UserAdminDto.class),
            @ApiResponse(code = 204,
                    message = "Пользователь с таким id не найден")
    })
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUserByIdForAdmin(id));
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User is not found", HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "Удалить пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Успешно удалено",
                    response = UserAdminDto.class),
            @ApiResponse(code = 400,
                    message = "Пользователь уже был удален"),
            @ApiResponse(code = 204,
                    message = "Пользователь с таким id не найден")
    })
    @PostMapping("/users/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.deleteUserById(id));
        } catch (UserIsDeletedException e) {
            return new ResponseEntity<>("User is already deleted", HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User is not found", HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "Восстановить пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Успешно восстановлено",
                    response = UserAdminDto.class),
            @ApiResponse(code = 400,
                    message = "Пользователь не удален"),
            @ApiResponse(code = 204,
                    message = "Пользователь с таким id не найден")
    })
    @PostMapping("/users/restore/{id}")
    public ResponseEntity<?> restoreUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.restoreUserById(id));
        } catch (UserIsNotDeletedException e) {
            return new ResponseEntity<>("User is not deleted", HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User is not found", HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "Заблокировать пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Успешно заблокирвано",
                    response = UserAdminDto.class),
            @ApiResponse(code = 400,
                    message = "Пользователь уже был заблокирован"),
            @ApiResponse(code = 204,
                    message = "Пользователь с таким id не найден")
    })
    @PostMapping("/users/ban/{id}")
    public ResponseEntity<?> blockUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.blockUserById(id));
        } catch (UserIsBlockedException e) {
            return new ResponseEntity<>("User is already blocked", HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User is not found", HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "Разблокировать пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Успешно разблокирвано",
                    response = UserAdminDto.class),
            @ApiResponse(code = 400,
                    message = "Пользователь не заблокирован"),
            @ApiResponse(code = 204,
                    message = "Пользователь с таким id не найден")
    })
    @PostMapping("/users/unban/{id}")
    public ResponseEntity<?> unblockUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.unblockUserById(id));
        } catch (UserIsNotBlockedException e) {
            return new ResponseEntity<>("User is not blocked", HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User is not found", HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "Сделать пользователя администратором по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Успешно",
                    response = UserAdminDto.class),
            @ApiResponse(code = 400,
                    message = "Пользователь уже был администратором"),
            @ApiResponse(code = 204,
                    message = "Пользователь с таким id не найден")
    })
    @PostMapping("/make-admin/{id}")
    public ResponseEntity<?> makeAdminById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.makeAdminById(id));
        } catch (UserIsAlreadyAdminException e) {
            return new ResponseEntity<>("User is already admin", HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User is not found", HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "Упрать права администратора поользовтеля по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Успешно",
                    response = UserAdminDto.class),
            @ApiResponse(code = 400,
                    message = "Пользователь не является администратором"),
            @ApiResponse(code = 204,
                    message = "Пользователь с таким id не найден")
    })
    @PostMapping("/undo-admin/{id}")
    public ResponseEntity<?> undoAdminById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.undoAdminById(id));
        } catch (UserIsNotAdminException e) {
            return new ResponseEntity<>("User is not admin", HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User is not found", HttpStatus.NO_CONTENT);
        }
    }
    @ApiOperation(value = "Найти пользователей для администора")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Успешно",
                    response = UserAdminDto.class,
                    responseContainer = "List")
    })
    @PostMapping("/search")
    public ResponseEntity<List<?>> findUsers(@RequestParam(required = false) Long id,
                                             @RequestParam(required = false) String email) {
            return ResponseEntity.ok(userService.findUsers(id, email));
    }
}
