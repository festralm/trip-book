package ru.itis.tripbook.service;

import ru.itis.tripbook.dto.CarDto;
import ru.itis.tripbook.dto.ReviewForm;
import ru.itis.tripbook.exception.TransportNotFoundException;

public interface ReviewService {

    CarDto saveReview(Long carId, ReviewForm review) throws TransportNotFoundException;
}
