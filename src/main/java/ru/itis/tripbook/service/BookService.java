package ru.itis.tripbook.service;

import ru.itis.tripbook.dto.book.BookForm;
import ru.itis.tripbook.dto.car.CarDto;
import ru.itis.tripbook.exception.CarIsBlockedException;
import ru.itis.tripbook.exception.CarIsDeletedException;
import ru.itis.tripbook.exception.CarNotFoundException;
import ru.itis.tripbook.model.User;

public interface BookService {
    CarDto bookCar(BookForm bookForm, Long carId, User user) throws CarNotFoundException, CarIsDeletedException, CarIsBlockedException;
}
