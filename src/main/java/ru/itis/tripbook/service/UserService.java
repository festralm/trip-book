package ru.itis.tripbook.service;

import ru.itis.tripbook.dto.UserAdminDto;
import ru.itis.tripbook.dto.UserDto;
import ru.itis.tripbook.dto.UserSignInForm;
import ru.itis.tripbook.dto.UserSignUpForm;
import ru.itis.tripbook.exception.*;
import ru.itis.tripbook.model.User;

import java.util.List;

public interface UserService {
    UserDto getUserByIdForUser(Long id) throws UserIsBlockedException, UserIsDeletedException, UserNotFoundException;

    List<UserAdminDto> getUsersForAdmin();

    UserAdminDto getUserByIdForAdmin(Long id) throws UserNotFoundException;

    User findByEmail(String email);

    User save(UserSignUpForm user) throws EmailAlreadyTakenException;

    UserAdminDto deleteUserById(Long id) throws UserIsDeletedException, UserNotFoundException;

    UserAdminDto restoreUserById(Long id) throws UserIsNotDeletedException, UserNotFoundException;

    UserAdminDto blockUserById(Long id) throws UserIsBlockedException, UserNotFoundException;

    UserAdminDto unblockUserById(Long id) throws UserIsNotBlockedException, UserNotFoundException;

    UserAdminDto makeAdminById(Long id) throws UserIsAlreadyAdminException, UserNotFoundException;

    UserAdminDto undoAdminById(Long id) throws UserIsNotAdminException, UserNotFoundException;

    User getUserById(Long id) throws UserNotFoundException;

    List<UserAdminDto> findUsers(UserAdminDto user);
}


