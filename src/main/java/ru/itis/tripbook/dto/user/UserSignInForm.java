package ru.itis.tripbook.dto.user;

import lombok.Data;

@Data
public class UserSignInForm {
    private String email;
    private String password;
}
