package ru.itis.tripbook.service;

import ru.itis.tripbook.dto.UserAdminDto;
import ru.itis.tripbook.dto.UserDto;
import ru.itis.tripbook.exception.UserIsBlockedException;
import ru.itis.tripbook.exception.UserIsDeletedException;

import java.util.List;

public interface UserService {
    UserDto getUserById(Long id) throws UserIsBlockedException, UserIsDeletedException;

    List<UserAdminDto> getUsersForAdmin();

    UserAdminDto getUserByIdForAdmin(Long id);
}
