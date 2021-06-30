package ru.itis.tripbook.dto.user;

import lombok.Data;

@Data
public class UserSignUpForm {
    private String email;
    private Long phoneNumber;
    private String password;
}
