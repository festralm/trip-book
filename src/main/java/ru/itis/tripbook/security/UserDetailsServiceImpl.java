package ru.itis.tripbook.security;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.itis.tripbook.dto.UserForm;
import ru.itis.tripbook.model.User;
import ru.itis.tripbook.service.UserService;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        val user = userService.findByEmail(email);
        return new UserDetailsImpl(user);
    }
}
