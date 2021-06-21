package ru.itis.tripbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tripbook.model.Car;
import ru.itis.tripbook.model.User;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookForm {
    private Timestamp start;
    private Timestamp end;
    private Car car;
    private User user;
}
