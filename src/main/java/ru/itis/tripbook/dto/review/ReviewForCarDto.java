package ru.itis.tripbook.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tripbook.dto.user.UserForCarDto;
import ru.itis.tripbook.model.Review;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewForCarDto {
    private Long id;
    private String text;
    private UserForCarDto user;
    private Integer rating;
    private Timestamp datetime;


    public static ReviewForCarDto from(Review review) {
        return ReviewForCarDto.builder()
                .id(review.getId())
                .user(UserForCarDto.from(review.getUser()))
                .text(review.getText())
                .rating(review.getRating())
                .datetime(review.getDatetime())
                .build();
    }

    public static List<ReviewForCarDto> from(List<Review> reviews, boolean allDetails) {
        return reviews == null ? new ArrayList<>() : reviews
                .stream()
                .filter(x -> allDetails ||
                        !x.getCar().getIsBlocked() &&
                                !x.getCar().getIsDeleted() &&
                                !x.getUser().getIsDeleted() &&
                                !x.getUser().getIsBlocked())
                .map(ReviewForCarDto::from)
                .collect(Collectors.toList());
    }
}
