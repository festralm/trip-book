package ru.itis.tripbook.model.enums;

public enum BmwModel  {
    _02E10("02 (E10)"),
    _1_SERIES("1 серии"),
    _1M("1M"),
    _2_SERIES("2 серии"),
    _2_SERIES_ACTIVE_TOURER("2 серии Active Tourer"),
    _2_SERIES_GRAN_TOURER("2 серии Gran Tourer"),
    _2000_C_CS("2000 C/CS"),
    _3_SERIES("3 серии"),
    _3_15("3/15"),
    _315("315"),
    _3200("3200"),
    _321("321"),
    _4_SERIES("4 серии"),
    _5_SERIES("5 серии"),
    _6_SERIES("6 серии"),
    _7_SERIES("7 серии"),
    _8_SERIES("8 серии"),
    M4("M4"),
    X1("X1"),
    X2("X2"),
    X3("X3"),
    X3_M("X3 M"),
    X4("X4"),
    X5("X5"),
    X5_M("X5 M"),
    X6("X6"),
    X6_M("X6 M"),
    X7("X7"),
    Z4("Z4"),
    I3("i3");

    private String name;

    BmwModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
