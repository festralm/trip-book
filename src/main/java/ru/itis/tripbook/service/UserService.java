package ru.itis.tripbook.service;

import ru.itis.tripbook.dto.admin.UserAdminForm;
import ru.itis.tripbook.dto.user.UserDto;
import ru.itis.tripbook.dto.user.UserEditForm;
import ru.itis.tripbook.dto.user.UserSignUpForm;
import ru.itis.tripbook.exception.*;
import ru.itis.tripbook.model.User;

import java.util.List;

public interface UserService {
    UserDto getUserById(Long id)
            throws UserIsBlockedException,
            UserIsDeletedException,
            UserNotFoundException;

    UserDto getUserByIdForAdmin(Long id) throws UserNotFoundException;

    User findByEmail(String email);

    User save(UserSignUpForm user) throws EmailAlreadyTakenException;

    UserDto deleteUserById(Long id)
            throws UserIsDeletedException,
            UserNotFoundException;

    UserDto restoreUserById(Long id)
            throws UserIsNotDeletedException,
            UserNotFoundException;

    UserDto blockUserById(Long id)
            throws UserIsBlockedException,
            UserNotFoundException;

    UserDto unblockUserById(Long id)
            throws UserIsNotBlockedException,
            UserNotFoundException;

    UserDto makeAdminById(Long id)
            throws UserIsAlreadyAdminException,
            UserNotFoundException;

    UserDto undoAdminById(Long id)
            throws UserIsNotAdminException,
            UserNotFoundException;

    User getUserByIdAllDetails(Long id) throws UserNotFoundException;

    List<UserDto> findUsers(UserAdminForm user);

    UserDto addToWishlist(Long carId, Long userId) throws UserNotFoundException, CarNotFoundException;

    UserDto deleteFromWishlist(Long carId, Long userId) throws UserNotFoundException, CarNotFoundException;

    UserDto editUser(Long id, UserEditForm userForm) throws UserNotFoundException;

    UserDto changePassword(Long id, String oldPassword, String newPassword) throws UserNotFoundException, OldPasswordIsWrongException;
}


