package ru.itis.tripbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.itis.tripbook.annotation.Loggable;
import ru.itis.tripbook.annotation.ResultLoggable;
import ru.itis.tripbook.dto.*;
import ru.itis.tripbook.exception.*;
import ru.itis.tripbook.security.UserDetailsImpl;
import ru.itis.tripbook.service.*;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

    @Autowired
    private CarBrandService carBrandService;

    @Autowired
    private CarModelService carModelService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    @ResultLoggable
    public ResponseEntity<?> authenticate(@RequestBody CarForm car,
                                          @AuthenticationPrincipal UserDetailsImpl user) {
        car.setUser(user.getUser());
        try {
            return ResponseEntity.ok().body(carService.saveCar(car));
        } catch (CarBrandNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (CarModelNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Loggable
    @GetMapping("/brands")
    @PermitAll
    public ResponseEntity<?> getCarBrands() {
        return ResponseEntity.ok().body(carBrandService.getAllBrands());
    }

    @Loggable
    @PermitAll
    @GetMapping("/{id}/models")
    public ResponseEntity<?> getCarModelsByBrandId(@PathVariable Long id) {
        return ResponseEntity.ok().body(carModelService.getModelsByBrandId(id));
    }

    @Loggable
    @GetMapping("/best/{count}")
    @PermitAll
    public ResponseEntity<?> getBestCarsOfCount(@PathVariable Long count) {
        return ResponseEntity.ok().body(carService.getBestCars(count));
    }

    @Loggable
    @GetMapping("/{id}")
    @PermitAll
    public ResponseEntity<?> getCarById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(carService.getCarById(id));
        } catch (TransportNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (TransportIsBlockedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (TransportIsDeletedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @ResultLoggable
    @PostMapping("/wishlist/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addToWishList(@PathVariable Long id,
                                           @AuthenticationPrincipal UserDetailsImpl user) {
        try {
            return ResponseEntity.ok().body(
                    userService.addToWishlist(
                            id,
                            user.getUser().getId()
                    )
            );
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (TransportNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @ResultLoggable
    @PostMapping("/delete-wishlist/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteFromWishList(@PathVariable Long id,
                                                @AuthenticationPrincipal UserDetailsImpl user) {
        try {
            return ResponseEntity.ok().body(
                    userService.deleteFromWishlist(
                            id,
                            user.getUser().getId()
                    )
            );
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (TransportNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @ResultLoggable
    @PostMapping("/book/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> bookCar(@RequestBody BookForm book,
                                     @PathVariable Long id,
                                     @AuthenticationPrincipal UserDetailsImpl user) {
        try {
            return ResponseEntity.ok().body(
                    bookService.bookCar(
                            book,
                            id,
                            user.getUser()
                    )
            );
        } catch (TransportNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (TransportIsBlockedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (TransportIsDeletedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/review/{id}")
    @PreAuthorize("isAuthenticated()")
    @ResultLoggable
    public ResponseEntity<?> saveReview(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody ReviewForm review) {
        review.setUser(user.getUser());
        try {
            return ResponseEntity.ok().body(reviewService.saveReview(id, review));
        } catch (TransportNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
