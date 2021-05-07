package ru.itis.tripbook.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@RequestMapping("/sign-up")
@RestController
@PermitAll
public class SignUpController {
}
