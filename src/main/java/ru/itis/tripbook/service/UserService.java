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

    UserAdminSearchForm getUserByIdForAdmin(Long id) throws UserNotFoundException;

    User findByEmail(String email);

    User save(UserSignUpForm user) throws EmailAlreadyTakenException;

    UserAdminSearchForm deleteUserById(Long id)
            throws UserIsDeletedException,
            UserNotFoundException;

    UserAdminSearchForm restoreUserById(Long id)
            throws UserIsNotDeletedException,
            UserNotFoundException;

    UserAdminSearchForm blockUserById(Long id)
            throws UserIsBlockedException,
            UserNotFoundException;

    UserAdminSearchForm unblockUserById(Long id)
            throws UserIsNotBlockedException,
            UserNotFoundException;

    UserAdminSearchForm makeAdminById(Long id)
            throws UserIsAlreadyAdminException,
            UserNotFoundException;

    UserAdminSearchForm undoAdminById(Long id)
            throws UserIsNotAdminException,
            UserNotFoundException;

    User getUserByIdAllDetails(Long id) throws UserNotFoundException;

    List<UserAdminForm> findUsers(UserAdminSearchForm user);

    UserDto addToWishlist(Long carId, Long userId) throws UserNotFoundException, TransportNotFoundException;

    UserDto deleteFromWishlist(Long carId, Long userId) throws UserNotFoundException, TransportNotFoundException;
}


