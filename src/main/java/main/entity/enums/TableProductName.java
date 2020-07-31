package main.entity.enums;

public enum TableProductName {

    ALCOHOL("alcohol"),
    BABY_FOOD("baby_food"),
    BAKERY("bakery"),
    BERRIES("berries"),
    BURGER_KING("burger_king"),
    CEREALS("cereals"),
    CONDIMENTS("condiments"),
    DAIRY("dairy"),
    EGGS("eggs"),
    FIRST_COURSE("firs_course"),
    FLOUR_PRODUCT("flour_products"),
    FRUITS("fruits"),
    JAPAN_FOOD("japan_food"),
    JUICE("juice"),
    KFC("kfc"),
    MC_DONALDS("mcdonalds"),
    MEAT_PRODUCTS("meat_products"),
    MUSHROOM("mushroom"),
    NUTS("nuts"),
    OILS("oils"),
    SALADS("salads"),
    SAUSAGES("sausages"),
    SEA_FOOD("sea_food"),
    SNACKS("snacks"),
    SPORT_NUTRITION("sport_nutrition"),
    SWEETS("sweets"),
    UN_ALCOHOL("unalcohol"),
    VEGETABLE("vegetable");

    private final String name;

    public String getName() {
        return this.name;
    }

    private TableProductName(String name) {
        this.name = name;
    }
}
