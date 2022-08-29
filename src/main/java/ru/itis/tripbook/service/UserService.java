package ru.itis.tripbook.service;

import ru.itis.tripbook.dto.*;
import ru.itis.tripbook.exception.*;
import ru.itis.tripbook.model.User;

import java.util.List;

public interface UserService {
    UserDto getUserById(Long id)
            throws UserIsBlockedException,
            UserIsDeletedException,
            UserNotFoundException;

    UserAdminDto getUserByIdForAdmin(Long id) throws UserNotFoundException;

    User findByEmail(String email);

    User save(UserSignUpForm user) throws EmailAlreadyTakenException;

    UserAdminDto deleteUserById(Long id)
            throws UserIsDeletedException,
            UserNotFoundException;

    UserAdminDto restoreUserById(Long id)
            throws UserIsNotDeletedException,
            UserNotFoundException;

    UserAdminDto blockUserById(Long id)
            throws UserIsBlockedException,
            UserNotFoundException;

    UserAdminDto unblockUserById(Long id)
            throws UserIsNotBlockedException,
            UserNotFoundException;

    UserAdminDto makeAdminById(Long id)
            throws UserIsAlreadyAdminException,
            UserNotFoundException;

    UserAdminDto undoAdminById(Long id)
            throws UserIsNotAdminException,
            UserNotFoundException;

    User getUserByIdAllDetails(Long id) throws UserNotFoundException;

    List<UserAdminDto> findUsers(UserAdminForm user);

    UserDto addToWishlist(Long carId, Long userId) throws UserNotFoundException, TransportNotFoundException;

    UserDto deleteFromWishlist(Long carId, Long userId) throws UserNotFoundException, TransportNotFoundException;
}


