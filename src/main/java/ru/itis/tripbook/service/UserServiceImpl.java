package ru.itis.tripbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.tripbook.dto.UserAdminDto;
import ru.itis.tripbook.dto.UserDto;
import ru.itis.tripbook.exception.UserIsBlockedException;
import ru.itis.tripbook.exception.UserIsDeletedException;
import ru.itis.tripbook.model.User;
import ru.itis.tripbook.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto getUserById(Long id) throws UserIsBlockedException, UserIsDeletedException {
        User user = userRepository.getOne(id);
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
        List<User> users = userRepository.findAll();
        return UserAdminDto.from(users);
    }

    @Override
    public UserAdminDto getUserByIdForAdmin(Long id) {
        User user = userRepository.getOne(id);
        return UserAdminDto.from(user);
    }
}
