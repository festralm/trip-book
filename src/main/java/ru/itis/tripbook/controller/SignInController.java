package ru.itis.tripbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SignInController {

    @GetMapping("/sign-in")
    @ResponseBody
    public String getLoginPage() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Sign In</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <form action=\"/sign-in\" method=\"post\">\n" +
                "        <input name=\"username\" type=\"email\" placeholder=\"Введите ваш email\">\n" +
                "        <input name=\"password\" type=\"password\" placeholder=\"Введите пароль\">\n" +
                "        <input type=\"submit\" value=\"Войти\">\n" +
                "    </form>\n" +
                "</body>\n" +
                "</html>\n";
    }

    @GetMapping("/home")
    @ResponseBody
    public String getHomePage() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Home</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "Home\n" +
                "<form action=\"logout\" method=\"post\"\n>" +
                "<button type=\"submit\">Выйти</button>\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>\n";
    }

}
