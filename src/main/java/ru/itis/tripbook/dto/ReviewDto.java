package ru.itis.tripbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tripbook.model.Review;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {
    private Long id;
    private String text;
    private CarForUserDto car;
    private UserForCarDto user;

    public static ReviewDto from(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .text(review.getText())
                .user(UserForCarDto.from(review.getUser()))
                .car(CarForUserDto.from(review.getCar()))
                .build();
    }

    public static List<ReviewDto> from(List<Review> reviews) {
        return reviews.stream().map(ReviewDto::from).collect(Collectors.toList());
    }
}
