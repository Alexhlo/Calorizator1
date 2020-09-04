package main.entity.enums;

public enum WindowMenuName {

    BURGER_KING("Burger King menu");

    private final String name;

    public String getName() {
        return this.name;
    }

    WindowMenuName(String name) {
        this.name = name;
    }
}
