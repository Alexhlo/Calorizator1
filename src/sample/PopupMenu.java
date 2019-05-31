package sample;

import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

public class PopupMenu {

    private final ContextMenu contextMenu = new ContextMenu();
    public MenuItem addRation = new MenuItem("Добавить в рацион");
    private SeparatorMenuItem sep1 = new SeparatorMenuItem();
    private SeparatorMenuItem sep2 = new SeparatorMenuItem();
    private SeparatorMenuItem sep3 = new SeparatorMenuItem();
    private SeparatorMenuItem sep4 = new SeparatorMenuItem();
    private SeparatorMenuItem sep5 = new SeparatorMenuItem();
    public MenuItem delRow = new MenuItem("Удалить строку из БД");
    public MenuItem editRow = new MenuItem("Изменить строку из БД");
    public MenuItem refresh = new MenuItem("Обновить таблицу");
    public MenuItem editTabView = new CheckMenuItem("Редактировать таблицу");
    public MenuItem showIdCol = new CheckMenuItem("Показать колонку ID");

    public void popupProductMenu(TableView tableView){
        tableView.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, event ->
                contextMenu.show(tableView, event.getScreenX(), event.getScreenY()));

        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> contextMenu.hide());
        contextMenu.getItems().addAll(addRation, sep1, editRow, sep2, delRow, sep3, refresh, sep4, editTabView, sep5, showIdCol);
    }
}
