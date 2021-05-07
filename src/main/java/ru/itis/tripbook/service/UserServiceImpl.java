package ru.itis.tripbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.tripbook.dto.UserAdminDto;
import ru.itis.tripbook.dto.UserDto;
import ru.itis.tripbook.dto.UserForm;
import ru.itis.tripbook.exception.EmailAlreadyTakenException;
import ru.itis.tripbook.exception.UserIsBlockedException;
import ru.itis.tripbook.exception.UserIsDeletedException;
import ru.itis.tripbook.model.Role;
import ru.itis.tripbook.model.User;
import ru.itis.tripbook.repository.UserRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
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
    public User save(UserForm user) throws EmailAlreadyTakenException {
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
    public UserDto getUserById(Long id) throws UserIsBlockedException, UserIsDeletedException {
        var user = userRepository.getOne(id);
        if (user.getIsBlocked() != null) {
            throw new UserIsBlockedException(user.getEmail());
        }
        if (user.getIsDeleted() != null) {
            throw new UserIsDeletedException(user.getEmail());
        }
        return UserDto.from(user);
    }

    @Override
    public List<UserAdminDto> getUsersForAdmin() {
        var users = userRepository.findAll();
        return UserAdminDto.from(users);
    }

    @Override
    public UserAdminDto getUserByIdForAdmin(Long id) {
        User user = userRepository.getOne(id);
        return UserAdminDto.from(user);
    }
}
