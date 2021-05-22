package ru.itis.tripbook.dto;

import lombok.Data;
import ru.itis.tripbook.model.User;

@Data
public class CarForm {
private Boolean withDriver;
private String brand;
private String model;
private User user;
}
