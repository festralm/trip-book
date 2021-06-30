package ru.itis.tripbook.service;

import ru.itis.tripbook.dto.book.BookForm;
import ru.itis.tripbook.dto.car.CarDto;
import ru.itis.tripbook.exception.TransportIsBlockedException;
import ru.itis.tripbook.exception.TransportIsDeletedException;
import ru.itis.tripbook.exception.TransportNotFoundException;
import ru.itis.tripbook.model.User;

public interface BookService {
    CarDto bookCar(BookForm bookForm, Long carId, User user) throws TransportNotFoundException, TransportIsDeletedException, TransportIsBlockedException;
}
