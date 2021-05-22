package ru.itis.tripbook.model.enums;

public enum EngineType {
    FUEL("Бензин"),
    DIESEL("Дизель"),
    HYBRID("Гибрид"),
    GAZ("Газ"),
    ELECTRIC("Электрический"),
    TURBO("Турбированный"),
    ATMOSPHERIC("Атмосферный");

    private String name;

    EngineType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
