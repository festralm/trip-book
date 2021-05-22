package ru.itis.tripbook.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tripbook.model.enums.BmwModel;
import ru.itis.tripbook.model.enums.CarBrand;

import javax.annotation.security.PermitAll;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
public class TransportController {

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

}
