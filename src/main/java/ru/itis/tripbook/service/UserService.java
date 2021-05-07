package ru.itis.tripbook.service;

import ru.itis.tripbook.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();

    UserDto getUserById(Long id);
}
