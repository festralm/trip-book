package ru.itis.tripbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tripbook.exception.JwtAuthenticationException;
import ru.itis.tripbook.exception.UserIsBlockedException;
import ru.itis.tripbook.exception.UserIsDeletedException;
import ru.itis.tripbook.exception.UserNotFoundException;
import ru.itis.tripbook.security.UserDetailsImpl;
import ru.itis.tripbook.service.UserService;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping("/")
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserService userService;

    @PermitAll
    @GetMapping
    public ResponseEntity<?> getDefault(@AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            LOGGER.info("User is found");
            try {
                var userReturn = userService.getUserById(user.getUser().getId());
                LOGGER.info("Returning status 200(OK) and {}", userReturn);
                return ResponseEntity.ok(
                        new MyResponseBody(MyStatus.IS_AUTHORIZED, userReturn)

                );
            } catch (UserIsBlockedException e) {
                LOGGER.info("User is blocked");
                var myBody = new MyResponseBody(MyStatus.USER_IS_BLOCKED);
                LOGGER.info("Returning {}", myBody);
                return ResponseEntity.ok().body(myBody);
            } catch (UserIsDeletedException e) {
                LOGGER.info("User is deleted");
                var myBody = new MyResponseBody(MyStatus.USER_IS_DELETED);
                LOGGER.info("Returning {}", myBody);
                return ResponseEntity.ok().body(myBody);
            } catch (UserNotFoundException e) {
                LOGGER.info("User is not found");
                var myBody = new MyResponseBody(MyStatus.USER_IS_NOT_FOUND);
                LOGGER.info("Returning {}", myBody);
                return ResponseEntity.ok().body(myBody);
            }
        } else {
            LOGGER.info("User is anonymous");
            var myBody = new MyResponseBody(MyStatus.NOT_AUTHORIZED);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        }
    }

}
