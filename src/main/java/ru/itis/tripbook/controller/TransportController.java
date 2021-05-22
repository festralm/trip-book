package ru.itis.tripbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tripbook.dto.CarForm;
import ru.itis.tripbook.exception.UserNotFoundException;
import ru.itis.tripbook.model.enums.BmwModel;
import ru.itis.tripbook.model.enums.CarBrand;
import ru.itis.tripbook.security.UserDetailsImpl;
import ru.itis.tripbook.service.CarService;

import javax.annotation.security.PermitAll;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
public class TransportController {
    @Autowired
    private CarService carService;

    @PermitAll
    @GetMapping("/transports/brands")
    public ResponseEntity<?> getAllBrands() {
        return ResponseEntity.ok().body(
                Arrays.stream(CarBrand.values())
                        .map(CarBrand::getName)
                        .collect(Collectors.toList()));
    }
    @PermitAll
    @GetMapping("/transports/bmw-models")
    public ResponseEntity<?> getBmwModels() {
        return ResponseEntity.ok().body(
                Arrays.stream(BmwModel.values())
                        .map(BmwModel::getName)
                        .collect(Collectors.toList()));
    }

    @PostMapping("/transports/create")
    public ResponseEntity<?> authenticate(@RequestBody CarForm car,
                                          @AuthenticationPrincipal UserDetailsImpl user) {

            car.setUser(user.getUser());
            return ResponseEntity.ok().body(carService.saveCar(car));

    }
}
