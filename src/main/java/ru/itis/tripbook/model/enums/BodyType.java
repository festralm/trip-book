package ru.itis.tripbook.model.enums;

public enum BodyType {
    SEDAN("Судан"),
    HATCHBACK("Хэтчбек"),
    HATCHBACK_3_DOORS("Хэтчбек 3 дв."),
    HATCHBACK_5_DOORS("Хэтчбек 5 дв."),
    LIFT_BACK("Лифтбек"),
    SUV("Внедорожник"),
    SUV_3_DOORS("Внедорожник 3 дв."),
    SUV_5_DOORS("Внедорожник 5 дв."),
    UNIVERSAL("Универсал"),
    COUPE("Купе"),
    MINIVAN("Минивен"),
    PICKUP("Пикап"),
    LIMOUSINE("Лимузин"),
    VAN("Фургон"),
    CABRIOLET("Кабриолет");



    private String name;

    BodyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
