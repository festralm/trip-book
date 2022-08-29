package ru.itis.tripbook.service;

import org.springframework.stereotype.Service;
import ru.itis.tripbook.dto.car.CarModelDto;
import ru.itis.tripbook.exception.CarModelNotFoundException;
import ru.itis.tripbook.model.CarModel;

import java.util.List;

@Service
public interface CarModelService {

    List<CarModelDto> getModelsByBrandId(Long id);

    CarModel getModelById(Long modelId) throws CarModelNotFoundException;
}
