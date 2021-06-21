package ru.itis.tripbook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.tripbook.dto.UserAdminForm;
import ru.itis.tripbook.dto.UserAdminSearchForm;
import ru.itis.tripbook.dto.UserDto;
import ru.itis.tripbook.dto.UserSignUpForm;
import ru.itis.tripbook.exception.*;
import ru.itis.tripbook.model.Role;
import ru.itis.tripbook.model.User;
import ru.itis.tripbook.repository.UserRepository;

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
                    .build();
            userRepository.save(newUser);
            LOGGER.info("Saved new user {}", newUser.toString());
            LOGGER.info("Returning new user");
            return newUser;
        }
    }

    @Override
    public UserAdminSearchForm deleteUserById(Long id) throws UserIsDeletedException, UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        if (user.getIsDeleted() != null) {
            throw new UserIsDeletedException(user.getEmail());
        }
        user.setIsDeleted(true);
        userRepository.save(user);
        return UserAdminSearchForm.from(user);
    }

    @Override
    public UserAdminSearchForm restoreUserById(Long id) throws UserIsNotDeletedException, UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        if (user.getIsDeleted() == null) {
            throw new UserIsNotDeletedException(user.getEmail());
        }
        user.setIsDeleted(null);
        userRepository.save(user);
        return UserAdminSearchForm.from(user);
    }

    @Override
    public UserAdminSearchForm blockUserById(Long id) throws UserIsBlockedException, UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        if (user.getIsBlocked() != null) {
            throw new UserIsBlockedException(user.getEmail());
        }
        user.setIsBlocked(true);
        userRepository.save(user);
        return UserAdminSearchForm.from(user);
    }


    @Override
    public UserAdminSearchForm unblockUserById(Long id) throws UserIsNotBlockedException, UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        if (user.getIsBlocked() == null) {
            throw new UserIsNotBlockedException(user.getEmail());
        }
        user.setIsBlocked(null);
        userRepository.save(user);
        return UserAdminSearchForm.from(user);
    }

    @Override
    public UserAdminSearchForm makeAdminById(Long id) throws UserIsAlreadyAdminException, UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        if (user.getRole() == Role.ADMIN) {
            throw new UserIsAlreadyAdminException(user.getEmail());
        }
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        return UserAdminSearchForm.from(user);
    }

    @Override
    public UserAdminSearchForm undoAdminById(Long id) throws UserIsNotAdminException, UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        if (user.getRole() != Role.ADMIN) {
            throw new UserIsNotAdminException(user.getEmail());
        }
        user.setRole(Role.USER);
        userRepository.save(user);
        return UserAdminSearchForm.from(user);
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
    public List<UserAdminForm> findUsers(UserAdminSearchForm user) {
        LOGGER.info("Searching users");

        var list = UserAdminForm.from(userRepository.findUsersByParams(
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
    public UserAdminSearchForm getUserByIdForAdmin(Long id) throws UserNotFoundException {
        var user = getUserByIdAllDetails(id);
        LOGGER.info("Returning User with all details");
        return UserAdminSearchForm.from(user);
    }
}
