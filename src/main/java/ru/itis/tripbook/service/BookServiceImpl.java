package ru.itis.tripbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.tripbook.annotation.ResultLoggable;
import ru.itis.tripbook.dto.book.BookForm;
import ru.itis.tripbook.dto.car.CarDto;
import ru.itis.tripbook.exception.CarIsBlockedException;
import ru.itis.tripbook.exception.CarIsDeletedException;
import ru.itis.tripbook.exception.CarNotFoundException;
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
            throws CarNotFoundException, CarIsDeletedException, CarIsBlockedException {
        bookForm.setUser(user);
        var car = carService.getCarByIdAllDetails(carId);
        bookForm.setCar(car);

        if (car.getIsDeleted()) {
            throw new CarIsDeletedException(car.getId());
        }

        if (car.getIsBlocked()) {
            throw new CarIsBlockedException(car.getId());
        }

        var newBook = bookRepository.save(
                Book.builder()
                        .car(bookForm.getCar())
                        .finish(bookForm.getEnd())
                        .start(bookForm.getStart())
                        .user(bookForm.getUser())
                        .build()
        );
        return CarDto.from(car, false);
    }
}
