package ru.itis.tripbook.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tripbook.model.Book;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    private Timestamp start;
    private Timestamp end;
    private Long carId;
    private Long userId;

    public static BookDto from(Book book) {
        return BookDto.builder()
                .start(book.getStart())
                .end(book.getFinish())
                .carId(book.getCar().getId())
                .userId(book.getUser().getId())
                .build();
    }

    public static List<BookDto> from(List<Book> books) {
        return books == null ? new ArrayList<>() :
                books
                        .stream()
                        .map(BookDto::from)
                        .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "start=" + start +
                ", end=" + end +
                ", carId=" + carId +
                ", userId=" + userId +
                '}';
    }
}
