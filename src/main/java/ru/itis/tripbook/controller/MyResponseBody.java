package ru.itis.tripbook.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyResponseBody {
    private int statusNumber;
    private String message;
    private Object body;

    public MyResponseBody(MyStatus status) {
        this.statusNumber = status.getStatusNumber();
        this.message = status.getMessage();
    }
    public MyResponseBody(MyStatus status, Object body) {
        this(status);
        this.body = body;
    }
}

enum MyStatus {
    NOT_AUTHORIZED(1, "Пользователь анонимный"),
    USER_IS_BLOCKED(2, "Пользователь заблокирован"),
    USER_IS_DELETED(3, "Пользователь удален"),
    USER_IS_NOT_FOUND(4, "Пользователь не найден"),
    WRONG_AUTH(5, "Неверный email или пароль"),
    EMAIL_TAKEN(6, "Email уже зарегистрирован"),
    IS_AUTHORIZED(7, "Пользователь распознан"),
    BRAND_IS_NOT_FOUND(8, "Марка не найдена"),
    MODEL_IS_NOT_FOUND(9, "Модель не найдена"),
    TRANSPORT_IS_BLOCKED(10, "Транспорт заблокирован"),
    TRANSPORT_IS_DELETED(11, "Транспорт удален"),
    TRANSPORT_IS_NOT_FOUND(12, "Транспорт не найден");

    private final int statusNumber;
    private final String message;

    MyStatus(int statusNumber, String message) {
        this.statusNumber = statusNumber;
        this.message = message;
    }

    public int getStatusNumber() {
        return statusNumber;
    }

    public String getMessage() {
        return message;
    }

}
