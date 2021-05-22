package ru.itis.tripbook.model.enums;

public enum DriveUnit {
FONT("Передний"),
    BACK("Задний"),
    FULL("Полный");
    private String name;

    DriveUnit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
