package ru.itis.tripbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.tripbook.annotation.ExecutionLoggable;
import ru.itis.tripbook.annotation.Loggable;
import ru.itis.tripbook.annotation.SignatureLoggable;
import ru.itis.tripbook.dto.admin.UserAdminForm;
import ru.itis.tripbook.dto.user.UserDto;
import ru.itis.tripbook.dto.user.UserEditForm;
import ru.itis.tripbook.dto.user.UserSignUpForm;
import ru.itis.tripbook.exception.*;
import ru.itis.tripbook.model.Role;
import ru.itis.tripbook.model.User;
import ru.itis.tripbook.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarService carService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @SignatureLoggable
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                String.format("Email %s not found.", email)
                        )
                );
    }

    @Override
    @ExecutionLoggable
    public User save(UserSignUpForm user) throws EmailAlreadyTakenException {
        try {
            findByEmail(user.getEmail());
            throw new EmailAlreadyTakenException(user.getEmail());
        } catch (UsernameNotFoundException exception) {
            User newUser = User.builder()
                    .email(user.getEmail())
                    .photoUrl("default-user.png")
                    .hashPassword(passwordEncoder.encode(user.getPassword()))
                    .isBlocked(false)
                    .isDeleted(false)
                    .role(Role.USER)
                    .joined(user.getJoined())
                    .build();
            userRepository.save(newUser);
            return newUser;
        }
    }

    @Override
    @Loggable
    public UserDto deleteUserById(Long id)
            throws UserIsDeletedException,
            UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        if (user.getIsDeleted()) {
            throw new UserIsDeletedException(user.getEmail());
        }
        user.setIsDeleted(true);
        userRepository.save(user);
        return UserDto.from(user, true);
    }

    @Loggable
    @Override
    public UserDto restoreUserById(Long id) throws UserIsNotDeletedException, UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        if (!user.getIsDeleted()) {
            throw new UserIsNotDeletedException(user.getEmail());
        }
        user.setIsDeleted(false);
        userRepository.save(user);
        return UserDto.from(user, true);
    }

    @Override
    @Loggable
    public UserDto blockUserById(Long id)
            throws UserIsBlockedException,
            UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        if (user.getIsBlocked()) {
            throw new UserIsBlockedException(user.getEmail());
        }
        user.setIsBlocked(true);
        userRepository.save(user);
        return UserDto.from(user, true);
    }


    @Override
    @Loggable
    public UserDto unblockUserById(Long id)
            throws UserIsNotBlockedException,
            UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        if (!user.getIsBlocked()) {
            throw new UserIsNotBlockedException(user.getEmail());
        }
        user.setIsBlocked(false);
        userRepository.save(user);
        return UserDto.from(user, true);
    }

    @Override
    @Loggable
    public UserDto makeAdminById(Long id)
            throws UserIsAlreadyAdminException,
            UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        if (user.getRole() == Role.ADMIN) {
            throw new UserIsAlreadyAdminException(user.getEmail());
        }
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        return UserDto.from(user, true);
    }

    @Override
    @Loggable
    public UserDto undoAdminById(Long id)
            throws UserIsNotAdminException,
            UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        if (user.getRole() != Role.ADMIN) {
            throw new UserIsNotAdminException(user.getEmail());
        }
        user.setRole(Role.USER);
        userRepository.save(user);
        return UserDto.from(user, true);
    }


    @Override
    @Loggable
    public UserDto getUserById(Long id)
            throws
            UserIsBlockedException,
            UserIsDeletedException,
            UserNotFoundException
    {
        var user = getUserByIdAllDetails(id);
        var userDto = UserDto.from(user, false);
        if (user.getIsBlocked()) {
            throw new UserIsBlockedException(user.getEmail());
        }
        if (user.getIsDeleted()) {
            throw new UserIsDeletedException(user.getEmail());
        }
        return userDto;
    }

    @Override
    @Loggable
    public User getUserByIdAllDetails(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)
        );
    }

    @Override
    @Loggable
    public List<UserDto> findUsers(UserAdminForm user) {
        return UserDto.from(userRepository.findUsersByParams(
                user.getId(),
                user.getEmail(),
                user.getIsDeleted(),
                user.getIsBlocked(),
                user.getRole(),
                user.getName()
        ), true);
    }

    @Override
    @Loggable
    public UserDto addToWishlist(Long carId, Long userId)
            throws UserNotFoundException,
            CarNotFoundException {
        var user = getUserByIdAllDetails(userId);
        var car = carService.getCarByIdAllDetails(carId);
        user.getWishedCars().add(car);
        userRepository.save(user);
        return UserDto.from(user, false);
    }

    @Override
    @Loggable
    public UserDto deleteFromWishlist(Long carId, Long userId) throws UserNotFoundException, CarNotFoundException {
        var user = getUserByIdAllDetails(userId);
        var car = carService.getCarByIdAllDetails(carId);;
        user.getWishedCars().remove(car);
        userRepository.save(user);
        return UserDto.from(user, false);
    }

    @Override
    @Loggable
    public UserDto editUser(Long id, UserEditForm userForm) throws UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        if (userForm.getName() != null) {
            user.setName(userForm.getName());
        }
        if (userForm.getDescription() != null) {
            user.setDescription(userForm.getDescription());
        }
        if (userForm.getPhotoUrl() != null) {
            user.setPhotoUrl(userForm.getPhotoUrl());
        }
        return UserDto.from(userRepository.save(user), false);
    }

    @Override
    public UserDto changePassword(Long id, String oldPassword, String newPassword)
            throws UserNotFoundException, OldPasswordIsWrongException {
        var user = getUserByIdAllDetails(id);
        if (passwordEncoder.matches(oldPassword, user.getHashPassword())) {
            user.setHashPassword(passwordEncoder.encode(newPassword));
            return UserDto.from(userRepository.save(user), false);
        } else {
            throw new OldPasswordIsWrongException();
        }
    }

    @Override
    @Loggable
    public UserDto getUserByIdForAdmin(Long id) throws UserNotFoundException {
        return UserDto.from(getUserByIdAllDetails(id), true);
    }
}
