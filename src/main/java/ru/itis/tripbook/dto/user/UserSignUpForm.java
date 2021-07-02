package ru.itis.tripbook.dto.user;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserSignUpForm {
    private String email;
    private Long phoneNumber;
    private String password;
    private Timestamp joined;
}
