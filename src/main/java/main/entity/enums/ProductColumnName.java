package main.entity.enums;

public enum ProductColumnName {

    COLUMN_ID("id"),
    COLUMN_NAME("name"),
    COLUMN_PROTEIN("protein"),
    COLUMN_FATS("fats"),
    COLUMN_CARBS("carbs"),
    COLUMN_CAL("calories");

    private final String name;

    public String getName() {
        return this.name;
    }

    private ProductColumnName(String name) {
        this.name = name;
    }
}
