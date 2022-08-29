package ru.itis.tripbook;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import ru.itis.tripbook.model.Role;
import ru.itis.tripbook.model.User;
import ru.itis.tripbook.repository.UserRepository;
import ru.itis.tripbook.service.CarService;
import ru.itis.tripbook.service.UserService;
import ru.itis.tripbook.service.UserServiceImpl;
import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class UserServiceTest {
    @TestConfiguration
    static class UserServiceTestContextConfiguration {
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }

        @Bean PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

    }
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CarService carService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        User user = User.builder()
                .id(1L)
                .email("test@test")
                .hashPassword("password")
                .isDeleted(false)
                .isBlocked(false)
                .photoUrl("default-user.png")
                .role(Role.USER)
                .joined(new Timestamp(System.currentTimeMillis()))
                .name("test")
                .description("description")
                .build();

        Mockito.when(userRepository.findByEmail(user.getEmail()))
        .thenReturn(Optional.of(user));
    }

    @Test
    public void findUserByEmailTest() {
        String email = "test@test";
        User found = userService.findByEmail(email);

        Assert.assertEquals(email, found.getEmail());
    }
}
