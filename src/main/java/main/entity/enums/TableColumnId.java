package main.entity.enums;

public enum TableColumnId {

    COLUMN_ID("tableColId"),
    COLUMN_NAME("tabColName"),
    COLUMN_PROTEIN("tabColProtein"),
    COLUMN_FATS("tableColFats"),
    COLUMN_CARBS("tableColCarbs"),
    COLUMN_CALORIES("tableColCalories"),
    COLUMN_WEIGHT("tableColWeight");

    private final String id;

    public String getId() {
        return this.id;
    }

    TableColumnId(String id) {
        this.id = id;
    }
}
