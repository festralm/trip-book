package ru.itis.tripbook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.tripbook.annotation.ResultLoggable;
import ru.itis.tripbook.dto.BookDto;
import ru.itis.tripbook.dto.BookForm;
import ru.itis.tripbook.dto.CarDto;
import ru.itis.tripbook.exception.TransportIsBlockedException;
import ru.itis.tripbook.exception.TransportIsDeletedException;
import ru.itis.tripbook.exception.TransportNotFoundException;
import ru.itis.tripbook.model.Book;
import ru.itis.tripbook.model.User;
import ru.itis.tripbook.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CarService carService;

    @ResultLoggable
    @Override
    public CarDto bookCar(BookForm bookForm, Long carId, User user)
            throws TransportNotFoundException, TransportIsDeletedException, TransportIsBlockedException {
        bookForm.setUser(user);
        var car = carService.getCarByIdAllDetails(carId);
        bookForm.setCar(car);

        if (car.getIsDeleted()) {
            throw new TransportIsDeletedException(car.getId());
        }

        if (car.getIsBlocked()) {
            throw new TransportIsBlockedException(car.getId());
        }

        var newBook = bookRepository.save(
                Book.builder()
                        .car(bookForm.getCar())
                        .finish(bookForm.getEnd())
                        .start(bookForm.getStart())
                        .user(bookForm.getUser())
                        .build()
        );
        return CarDto.from(car);
    }
}
