package main.entity.enums;

public enum WindowPath {

    BURGER_KING_WINDOW("/fxml/products/burgerKingWindow.fxml"),
    KFC_WINDOW("/fxml/products/kfcWindow.fxml");

    private final String path;

    public String getPath() {
        return this.path;
    }

    private WindowPath(String path) {
        this.path = path;
    }
}
