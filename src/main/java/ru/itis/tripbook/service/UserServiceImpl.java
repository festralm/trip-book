package ru.itis.tripbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.tripbook.dto.UserDto;
import ru.itis.tripbook.model.User;
import ru.itis.tripbook.repository.UsersRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<UserDto> getUsers() {
        List<User> users = usersRepository.findAll();
        return UserDto.from(users);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = usersRepository.getOne(id);
        return UserDto.from(user);
    }
}
