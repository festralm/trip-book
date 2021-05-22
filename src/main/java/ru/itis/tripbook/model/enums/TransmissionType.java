package ru.itis.tripbook.model.enums;

public enum TransmissionType {
    AUTO("Автомат"),
    AUTOMATIC("Автоматическая"),
    ROBOT("Робот"),
    VARIABLE("Вариатор"),
    MECHANIC("Механическая");

    private String name;

    TransmissionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
