package ru.itis.tripbook.dto;

import lombok.Data;

@Data
public class UserSignUpForm {
    private String email;
    private Long phoneNumber;
    private String password;
}
