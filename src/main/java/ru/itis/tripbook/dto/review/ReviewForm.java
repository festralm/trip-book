package ru.itis.tripbook.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tripbook.model.Car;
import ru.itis.tripbook.model.User;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewForm {
    private String text;
    private Integer rating;
    private User user;
    private Car car;
    private Timestamp datetime;
}
