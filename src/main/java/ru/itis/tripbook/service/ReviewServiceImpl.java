package ru.itis.tripbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.tripbook.annotation.ResultLoggable;
import ru.itis.tripbook.dto.car.CarDto;
import ru.itis.tripbook.dto.review.ReviewForm;
import ru.itis.tripbook.exception.CarNotFoundException;
import ru.itis.tripbook.model.Review;
import ru.itis.tripbook.repository.ReviewRepository;

import java.sql.Timestamp;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private CarService carService;

    @Autowired
    private ReviewRepository reviewRepository;

    @ResultLoggable
    @Override
    public CarDto saveReview(Long carId, ReviewForm review) throws CarNotFoundException {
        var car = carService.getCarByIdAllDetails(carId);
        review.setCar(car);
        var newReview = reviewRepository.save(
                Review.builder()
                        .car(review.getCar())
                        .user(review.getUser())
                        .rating(review.getRating())
                        .text(review.getText())
                        .datetime(new Timestamp(System.currentTimeMillis()))
                        .build()
        );

        return CarDto.from(car, true);
    }
}
