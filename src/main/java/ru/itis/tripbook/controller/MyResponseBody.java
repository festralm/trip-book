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
    WRONG_AUTH(1, "Неверный email или пароль"),
    EMAIL_TAKEN(2, "Email уже зарегистрирован"),
    NOT_AUTHORIZED(3, "Пользователь анонимный"),
    IS_AUTHORIZED(4, "Пользователь распознан"),
    ADMIN_IS_AUTHORIZED(5, "Админ распознан");

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
