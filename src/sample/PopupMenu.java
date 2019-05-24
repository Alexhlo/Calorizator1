package sample;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class PopupMenu {

    private final ContextMenu contextMenu = new ContextMenu();
    public MenuItem addRation = new MenuItem("Добавить в рацион");
    private SeparatorMenuItem sep1 = new SeparatorMenuItem();
    private SeparatorMenuItem sep2 = new SeparatorMenuItem();
    private SeparatorMenuItem sep3 = new SeparatorMenuItem();
    public MenuItem delRow = new MenuItem("Удалить строку из БД");
    public MenuItem editRow = new MenuItem("Изменить строку из БД");
    public MenuItem refresh = new MenuItem("Обновить таблицу");

    public void popupProductMenu(TableView tableView){
        tableView.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, event ->
                contextMenu.show(tableView, event.getScreenX(), event.getScreenY()));

        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> contextMenu.hide());
        contextMenu.getItems().addAll(addRation, sep1, editRow, sep2,delRow, sep3, refresh);
    }
}
