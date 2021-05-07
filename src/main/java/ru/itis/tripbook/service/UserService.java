package ru.itis.tripbook.service;

import ru.itis.tripbook.dto.UserAdminDto;
import ru.itis.tripbook.dto.UserDto;
import ru.itis.tripbook.dto.UserForm;
import ru.itis.tripbook.exception.EmailAlreadyTakenException;
import ru.itis.tripbook.exception.UserIsBlockedException;
import ru.itis.tripbook.exception.UserIsDeletedException;
import ru.itis.tripbook.model.User;

import java.util.List;

public interface UserService {
    UserDto getUserById(Long id) throws UserIsBlockedException, UserIsDeletedException;

    List<UserAdminDto> getUsersForAdmin();

    UserAdminDto getUserByIdForAdmin(Long id);

    User findByEmail(String email);

    User save(UserForm user) throws EmailAlreadyTakenException;
}


