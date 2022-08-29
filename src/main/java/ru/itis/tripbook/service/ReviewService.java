package ru.itis.tripbook.service;

import ru.itis.tripbook.dto.car.CarDto;
import ru.itis.tripbook.dto.review.ReviewForm;
import ru.itis.tripbook.exception.CarNotFoundException;

public interface ReviewService {

    CarDto saveReview(Long carId, ReviewForm review) throws CarNotFoundException;
}
