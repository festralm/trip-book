package ru.itis.tripbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.tripbook.dto.UserAdminDto;
import ru.itis.tripbook.dto.UserDto;
import ru.itis.tripbook.dto.UserSignInForm;
import ru.itis.tripbook.dto.UserSignUpForm;
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
    private PasswordEncoder passwordEncoder;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("Email %s not found.", email)));
    }

    @Override
    public User save(UserSignUpForm user) throws EmailAlreadyTakenException {
        try {
            findByEmail(user.getEmail());
            throw new EmailAlreadyTakenException(user.getEmail());
        } catch (UsernameNotFoundException exception) {
            User newUser = User.builder()
                    .email(user.getEmail())
                    .hashPassword(passwordEncoder.encode(user.getPassword()))
                    .role(Role.USER)
                    .build();
            userRepository.save(newUser);
            return newUser;
        }
    }

    @Override
    public UserAdminDto deleteUserById(Long id) throws UserIsDeletedException, UserNotFoundException {
        var user = getUserById(id);
        if (user.getIsDeleted() != null) {
            throw new UserIsDeletedException(user.getEmail());
        }
        user.setIsDeleted(true);
        userRepository.save(user);
        return UserAdminDto.from(user);
    }

    @Override
    public UserAdminDto restoreUserById(Long id) throws UserIsNotDeletedException, UserNotFoundException {
        var user = getUserById(id);
        if (user.getIsDeleted() == null) {
            throw new UserIsNotDeletedException(user.getEmail());
        }
        user.setIsDeleted(null);
        userRepository.save(user);
        return UserAdminDto.from(user);
    }

    @Override
    public UserAdminDto blockUserById(Long id) throws UserIsBlockedException, UserNotFoundException {
        var user = getUserById(id);
        if (user.getIsBlocked() != null) {
            throw new UserIsBlockedException(user.getEmail());
        }
        user.setIsBlocked(true);
        userRepository.save(user);
        return UserAdminDto.from(user);
    }


    @Override
    public UserAdminDto unblockUserById(Long id) throws UserIsNotBlockedException, UserNotFoundException {
        var user = getUserById(id);
        if (user.getIsBlocked() == null) {
            throw new UserIsNotBlockedException(user.getEmail());
        }
        user.setIsBlocked(null);
        userRepository.save(user);
        return UserAdminDto.from(user);
    }

    @Override
    public UserAdminDto makeAdminById(Long id) throws UserIsAlreadyAdminException, UserNotFoundException {
        var user = getUserById(id);
        if (user.getRole() == Role.ADMIN) {
            throw new UserIsAlreadyAdminException(user.getEmail());
        }
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        return UserAdminDto.from(user);
    }

    @Override
    public UserAdminDto undoAdminById(Long id) throws UserIsNotAdminException, UserNotFoundException {
        var user = getUserById(id);
        if (user.getRole() != Role.ADMIN) {
            throw new UserIsNotAdminException(user.getEmail());
        }
        user.setRole(Role.USER);
        userRepository.save(user);
        return UserAdminDto.from(user);
    }


    @Override
    public UserDto getUserByIdForUser(Long id) throws UserIsBlockedException, UserIsDeletedException, UserNotFoundException {
        var user = getUserById(id);
        if (user.getIsBlocked() != null) {
            throw new UserIsBlockedException(user.getEmail());
        }
        if (user.getIsDeleted() != null) {
            throw new UserIsDeletedException(user.getEmail());
        }
        return UserDto.from(user);
    }

    @Override
    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public List<UserAdminDto> findUsers(Long id, String email) {
        return UserAdminDto.from(userRepository.findUsersByParams(id, email));
    }

    @Override
    public List<UserAdminDto> getUsersForAdmin() {
        var users = userRepository.findAll();
        return UserAdminDto.from(users);
    }

    @Override
    public UserAdminDto getUserByIdForAdmin(Long id) throws UserNotFoundException {
        var user = getUserById(id);
        return UserAdminDto.from(user);
    }
}
