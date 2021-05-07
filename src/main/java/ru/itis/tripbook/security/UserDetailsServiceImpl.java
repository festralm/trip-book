package ru.itis.tripbook.security;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.tripbook.repository.UsersRepository;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        val user = usersRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("Email %s not found.", email)));
        return new UserDetailsImpl(user);
    }
}