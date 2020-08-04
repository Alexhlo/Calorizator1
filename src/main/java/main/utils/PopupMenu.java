package main.utils;

import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import main.entity.Product;

public class PopupMenu {

    private final ContextMenu contextMenu = new ContextMenu();
    public Menu addRation = new Menu("Добавить в рацион");
    public MenuItem mealMenuItem1 = new MenuItem("Приём пищи 1");
    public MenuItem mealMenuItem2 = new MenuItem("Приём пищи 2");
    public MenuItem mealMenuItem3 = new MenuItem("Приём пищи 3");
    public MenuItem mealMenuItem4 = new MenuItem("Приём пищи 4");
    public MenuItem mealMenuItem5 = new MenuItem("Приём пищи 5");

    public MenuItem createTable = new MenuItem("Создать таблицу");
    public MenuItem deleteTable = new MenuItem("Удалить таблицу");
    private SeparatorMenuItem sep2 = new SeparatorMenuItem();
    private SeparatorMenuItem sep3 = new SeparatorMenuItem();
    private SeparatorMenuItem sep4 = new SeparatorMenuItem();
    private SeparatorMenuItem sep5 = new SeparatorMenuItem();
    private SeparatorMenuItem sep6 = new SeparatorMenuItem();

    public MenuItem delRow = new MenuItem("Удалить строку из БД");
    public MenuItem refresh = new MenuItem("Обновить таблицу");
    public CheckMenuItem editTabView = new CheckMenuItem("Редактировать таблицу");
    public CheckMenuItem showIdCol = new CheckMenuItem("Показать колонку ID");


    public void popupProductMenu(TableView<Product> tableView) {

        addRation.getItems().addAll(mealMenuItem1,mealMenuItem2,mealMenuItem3,mealMenuItem4,mealMenuItem5);
        tableView.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, event ->
                contextMenu.show(tableView, event.getScreenX(), event.getScreenY()));

        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> contextMenu.hide());
        contextMenu.getItems().addAll(addRation, sep2, delRow, sep3, refresh, sep4, editTabView, sep5, showIdCol);
    }

    public void popupTableAction(VBox vBox) {

        vBox.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, event ->
                contextMenu.show(vBox, event.getScreenX(), event.getScreenY()));

        vBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> contextMenu.hide());
        contextMenu.getItems().addAll(createTable, sep6, deleteTable);
    }

    public void popupRationMenu(TableView<Product> tableView) {

        addRation.getItems().addAll(mealMenuItem1, mealMenuItem2, mealMenuItem3, mealMenuItem4, mealMenuItem5);
        tableView.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, event ->
                contextMenu.show(tableView, event.getScreenX(), event.getScreenY()));

        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> contextMenu.hide());
        contextMenu.getItems().addAll(delRow, sep3, refresh, sep4, editTabView, sep5, showIdCol);
    }
}
