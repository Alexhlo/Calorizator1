package main.entity.enums;

public enum ProductColumnName {

    COLUMN_ID("id"),
    COLUMN_NAME("name"),
    COLUMN_PROTEIN("protein"),
    COLUMN_FATS("fats"),
    COLUMN_CARBS("carbs"),
    COLUMN_CALORIES("calories"),
    COLUMN_WEIGHT("weight");

    private final String name;

    public String getName() {
        return this.name;
    }

    ProductColumnName(String name) {
        this.name = name;
    }
}
