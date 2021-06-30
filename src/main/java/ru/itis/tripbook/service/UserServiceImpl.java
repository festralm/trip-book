package ru.itis.tripbook.service;

import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.tripbook.annotation.ExecutionLoggable;
import ru.itis.tripbook.annotation.Loggable;
import ru.itis.tripbook.annotation.SignatureLoggable;
import ru.itis.tripbook.dto.UserAdminDto;
import ru.itis.tripbook.dto.UserAdminForm;
import ru.itis.tripbook.dto.UserDto;
import ru.itis.tripbook.dto.UserSignUpForm;
import ru.itis.tripbook.exception.*;
import ru.itis.tripbook.model.Role;
import ru.itis.tripbook.model.User;
import ru.itis.tripbook.repository.UserRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                    .joined(new Timestamp(System.currentTimeMillis()))
                    .build();
            userRepository.save(newUser);
            return newUser;
        }
    }

    @Override
    @Loggable
    public UserAdminDto deleteUserById(Long id)
            throws UserIsDeletedException,
            UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        if (user.getIsDeleted()) {
            throw new UserIsDeletedException(user.getEmail());
        }
        user.setIsDeleted(true);
        userRepository.save(user);
        return UserAdminDto.from(user);
    }

    @Loggable
    @Override
    public UserAdminDto restoreUserById(Long id) throws UserIsNotDeletedException, UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        if (!user.getIsDeleted()) {
            throw new UserIsNotDeletedException(user.getEmail());
        }
        user.setIsDeleted(false);
        userRepository.save(user);
        return UserAdminDto.from(user);
    }

    @Override
    @Loggable
    public UserAdminDto blockUserById(Long id)
            throws UserIsBlockedException,
            UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        if (user.getIsBlocked()) {
            throw new UserIsBlockedException(user.getEmail());
        }
        user.setIsBlocked(true);
        userRepository.save(user);
        return UserAdminDto.from(user);
    }


    @Override
    @Loggable
    public UserAdminDto unblockUserById(Long id)
            throws UserIsNotBlockedException,
            UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        if (!user.getIsBlocked()) {
            throw new UserIsNotBlockedException(user.getEmail());
        }
        user.setIsBlocked(false);
        userRepository.save(user);
        return UserAdminDto.from(user);
    }

    @Override
    @Loggable
    public UserAdminDto makeAdminById(Long id)
            throws UserIsAlreadyAdminException,
            UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        if (user.getRole() == Role.ADMIN) {
            throw new UserIsAlreadyAdminException(user.getEmail());
        }
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        return UserAdminDto.from(user);
    }

    @Override
    @Loggable
    public UserAdminDto undoAdminById(Long id)
            throws UserIsNotAdminException,
            UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        if (user.getRole() != Role.ADMIN) {
            throw new UserIsNotAdminException(user.getEmail());
        }
        user.setRole(Role.USER);
        userRepository.save(user);
        return UserAdminDto.from(user);
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
        var userDto = UserDto.from(user);
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
    public List<UserAdminDto> findUsers(UserAdminForm user) {
        return UserAdminDto.from(userRepository.findUsersByParams(
                user.getId(),
                user.getEmail(),
                user.getIsDeleted(),
                user.getIsBlocked(),
                user.getRole()
        ));
    }

    @Override
    @Loggable
    public UserDto addToWishlist(Long carId, Long userId)
            throws UserNotFoundException,
            TransportNotFoundException {
        var user = getUserByIdAllDetails(userId);
        var car = carService.getCarByIdAllDetails(carId);
        user.getWishedCars().add(car);
        userRepository.save(user);
        return UserDto.from(user);
    }

    @Override
    @Loggable
    public UserDto deleteFromWishlist(Long carId, Long userId) throws UserNotFoundException, TransportNotFoundException {
        var user = getUserByIdAllDetails(userId);
        var car = carService.getCarByIdAllDetails(carId);;
        user.getWishedCars().remove(car);
        userRepository.save(user);
        return UserDto.from(user);
    }

    @Override
    @Loggable
    public UserAdminDto getUserByIdForAdmin(Long id) throws UserNotFoundException {
        return UserAdminDto.from(getUserByIdAllDetails(id));
    }
}
