package ru.itis.tripbook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarService carService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findByEmail(String email) {
        LOGGER.info("Getting user with email {}", email);
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("Email %s not found.", email)));
    }

    @Override
    public User save(UserSignUpForm user) throws EmailAlreadyTakenException {
        LOGGER.info("Trying to save user {}", user.toString());
        try {
            LOGGER.info("Checking if email is already is in database");
            findByEmail(user.getEmail());
            LOGGER.info("Email {} is found", user.getEmail());
            throw new EmailAlreadyTakenException(user.getEmail());
        } catch (UsernameNotFoundException exception) {
            LOGGER.info("Email {} is not found", user.getEmail());
            LOGGER.info("Creating new user");
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
            LOGGER.info("Saved new user {}", newUser.toString());
            LOGGER.info("Returning new user");
            return newUser;
        }
    }

    @Override
    public UserAdminDto deleteUserById(Long id) throws UserIsDeletedException, UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        if (user.getIsDeleted()) {
            LOGGER.error("User is already deleted");
            throw new UserIsDeletedException(user.getEmail());
        }
        user.setIsDeleted(true);
        LOGGER.info("Set isDeleted to user");
        userRepository.save(user);
        LOGGER.info("Saved user");
        UserAdminDto userAdminDto = UserAdminDto.from(user);
        LOGGER.info("Returning userAdminDto");
        return userAdminDto;
    }

    @Override
    public UserAdminDto restoreUserById(Long id) throws UserIsNotDeletedException, UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        LOGGER.info("Got user with id {}", id);
        if (!user.getIsDeleted()) {
            LOGGER.error("User is already not deleted");
            throw new UserIsNotDeletedException(user.getEmail());
        }
        user.setIsDeleted(false);
        LOGGER.info("Set isDeleted to user ");
        userRepository.save(user);
        LOGGER.info("Saved user");
        UserAdminDto userAdminDto = UserAdminDto.from(user);
        LOGGER.info("Returning userAdminDto");
        return userAdminDto;
    }

    @Override
    public UserAdminDto blockUserById(Long id) throws UserIsBlockedException, UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        LOGGER.info("Got user with id {}", id);
        if (user.getIsBlocked()) {
            LOGGER.error("User is already blocked");
            throw new UserIsBlockedException(user.getEmail());
        }
        user.setIsBlocked(true);
        LOGGER.info("Set isBlocked to user");
        userRepository.save(user);
        LOGGER.info("Saved user");
        UserAdminDto userAdminDto = UserAdminDto.from(user);
        LOGGER.info("Returning userAdminDto");
        return userAdminDto;
    }


    @Override
    public UserAdminDto unblockUserById(Long id) throws UserIsNotBlockedException, UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        LOGGER.info("Got user with id {}", id);
        if (!user.getIsBlocked()) {
            LOGGER.error("User is already not blocked");
            throw new UserIsNotBlockedException(user.getEmail());
        }
        user.setIsBlocked(false);
        LOGGER.info("Set isBlocked to user");
        userRepository.save(user);
        LOGGER.info("Saved user");
        UserAdminDto userAdminDto = UserAdminDto.from(user);
        LOGGER.info("Returning userAdminDto");
        return userAdminDto;
    }

    @Override
    public UserAdminDto makeAdminById(Long id) throws UserIsAlreadyAdminException, UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        LOGGER.info("Got user with id {}", id);
        if (user.getRole() == Role.ADMIN) {
            LOGGER.error("User is already admin");
            throw new UserIsAlreadyAdminException(user.getEmail());
        }
        user.setRole(Role.ADMIN);
        LOGGER.info("Set role to user");
        userRepository.save(user);
        LOGGER.info("Saved user");
        UserAdminDto userAdminDto = UserAdminDto.from(user);
        LOGGER.info("Returning userAdminDto");
        return userAdminDto;
    }

    @Override
    public UserAdminDto undoAdminById(Long id) throws UserIsNotAdminException, UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        LOGGER.info("Got user with id {}", id);
        if (user.getRole() != Role.ADMIN) {
            LOGGER.error("User is already not admin");
            throw new UserIsNotAdminException(user.getEmail());
        }
        user.setRole(Role.USER);
        LOGGER.info("Set role to user");
        userRepository.save(user);
        LOGGER.info("Saved user");
        UserAdminDto userAdminDto = UserAdminDto.from(user);
        LOGGER.info("Returning userAdminDto");
        return userAdminDto;
    }


    @Override
    public UserDto getUserById(Long id)
            throws
            UserIsBlockedException,
            UserIsDeletedException,
            UserNotFoundException
    {
        LOGGER.info("Getting user by id " + id);
        var user = getUserByIdAllDetails(id);
        var userDto = UserDto.from(user);
        LOGGER.info("Got user {}", userDto.toString());
        if (user.getIsBlocked()) {
            LOGGER.info("User is blocked");
            throw new UserIsBlockedException(user.getEmail());
        }
        if (user.getIsDeleted()) {
            LOGGER.info("User is deleted");
            throw new UserIsDeletedException(user.getEmail());
        }
        LOGGER.info("Returning UserDto");
        return userDto;
    }

    @Override
    public User getUserByIdAllDetails(Long id) throws UserNotFoundException {
        LOGGER.info("Returning user by id " + id + " with all details");
        var user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        LOGGER.info("Got user with {} cars", user.getCars().size());
        return user;
    }

    @Override
    public List<UserAdminDto> findUsers(UserAdminForm user) {
        LOGGER.info("Searching users");

        var list = UserAdminDto.from(userRepository.findUsersByParams(
                user.getId(),
                user.getEmail(),
                user.getIsDeleted(),
                user.getIsBlocked(),
                user.getRole()
        ));
        LOGGER.info("Found " + list.size() + " users");
        LOGGER.info("Users: " + list.toString());
        return list;
    }

    @Override
    public UserDto addToWishlist(Long carId, Long userId) throws UserNotFoundException, TransportNotFoundException {
        var user = getUserByIdAllDetails(userId);
        LOGGER.info("Got user with id {}", userId);
        var car = carService.getCarByIdAllDetails(carId);
        LOGGER.info("Got car with id {}", carId);
        user.getWishedCars().add(car);
        LOGGER.info("Added car to user's list");
        userRepository.save(user);
        LOGGER.info("Saved user");
        return UserDto.from(user);
    }

    @Override
    public UserDto deleteFromWishlist(Long carId, Long userId) throws UserNotFoundException, TransportNotFoundException {
        var user = getUserByIdAllDetails(userId);
        LOGGER.info("Got user with id {}", userId);
        var car = carService.getCarByIdAllDetails(carId);
        LOGGER.info("Got car with id {}", carId);
        LOGGER.info("Users wished cars contains car: {}", user.getWishedCars().contains(car));
        if (user.getWishedCars().remove(car)) {
            LOGGER.info("Removed car from user's list");
        } else {
            LOGGER.info("Could not remove car from user's list");
        }
        userRepository.save(user);
        LOGGER.info("Saved user");
        return UserDto.from(user);
    }

    @Override
    public UserAdminDto getUserByIdForAdmin(Long id) throws UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        LOGGER.info("Returning User with all details");
        return UserAdminDto.from(user);
    }
}
