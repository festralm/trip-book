package ru.itis.tripbook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.tripbook.dto.BookDto;
import ru.itis.tripbook.dto.BookForm;
import ru.itis.tripbook.dto.CarDto;
import ru.itis.tripbook.exception.TransportNotFoundException;
import ru.itis.tripbook.model.Book;
import ru.itis.tripbook.model.User;
import ru.itis.tripbook.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CarService carService;

    @Override
    public CarDto bookCar(BookForm bookForm, Long carId, User user) throws TransportNotFoundException {
        bookForm.setUser(user);
        LOGGER.info("Set user to bookForm");
        var car = carService.getCarByIdAllDetails(carId);
        LOGGER.info("Found car with id {}", carId);
        bookForm.setCar(car);
        LOGGER.info("Set car to bookForm");

        var newBook = bookRepository.save(
                Book.builder()
                        .car(bookForm.getCar())
                        .finish(bookForm.getEnd())
                        .start(bookForm.getStart())
                        .user(bookForm.getUser())
                        .build()
        );
        LOGGER.info("Saved book");
        var carDto = CarDto.from(car);
        LOGGER.info("Returning {}", carDto);
        return carDto;
    }
}
