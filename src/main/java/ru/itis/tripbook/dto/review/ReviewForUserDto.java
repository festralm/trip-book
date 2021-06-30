package ru.itis.tripbook.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tripbook.dto.car.CarForUserDto;
import ru.itis.tripbook.model.CarPhotoUrl;
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

    public static ReviewForUserDto from(Review review, boolean allDetails) {
        return ReviewForUserDto.builder()
                .id(review.getId())
                .text(review.getText())
                .car(CarForUserDto.from(review.getCar(), allDetails))
                .rating(review.getRating())
                .datetime(review.getDatetime())
                .build();
    }

    public static List<ReviewForUserDto> from(List<Review> reviews, boolean allDetails) {
        return reviews
                .stream()
                .filter(x -> allDetails ||
                        !x.getCar().getIsBlocked() &&
                                !x.getCar().getIsDeleted() &&
                                !x.getUser().getIsDeleted() &&
                                !x.getUser().getIsBlocked())
                .map(x -> ReviewForUserDto.from(x, allDetails))
                .collect(Collectors.toList());
    }
}
