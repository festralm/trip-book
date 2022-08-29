package ru.itis.tripbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tripbook.model.Review;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewForUserDto {
    private Long id;
    private String text;
    private CarForUserDto car;
    private Integer rating;
    private Timestamp datetime;

    public static ReviewForUserDto from(Review review) {
        return ReviewForUserDto.builder()
                .id(review.getId())
                .text(review.getText())
                .car(CarForUserDto.from(review.getCar()))
                .rating(review.getRating())
                .datetime(review.getDatetime())
                .build();
    }

    public static List<ReviewForUserDto> from(List<Review> reviews) {
        return reviews
                .stream()
                .filter(x ->
                        !x.getCar().getIsBlocked() &&
                                !x.getCar().getIsDeleted() &&
                        !x.getUser().getIsDeleted() &&
                        !x.getUser().getIsBlocked())
                .map(ReviewForUserDto::from)
                .collect(Collectors.toList());
    }
}
