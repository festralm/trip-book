package ru.itis.tripbook.service;

import ru.itis.tripbook.dto.CarBrandDto;
import ru.itis.tripbook.exception.CarBrandNotFoundException;
import ru.itis.tripbook.model.CarBrand;

import java.util.List;

public interface CarBrandService {
    List<CarBrandDto> getAllBrands();

    CarBrand getBrandById(Long brand) throws CarBrandNotFoundException;
}
