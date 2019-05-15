package sample;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class PopupMenu {

    public final ContextMenu contextMenu = new ContextMenu();


    public void popupProductMenu(AnchorPane anchorPane){
        MenuItem cut = new MenuItem("Вырезать");
        MenuItem copy = new MenuItem("Копировать");
        MenuItem paste = new MenuItem("Вставить");
        MenuItem add = new MenuItem("Добавить в рацион");

        anchorPane.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, event -> {
            contextMenu.show(anchorPane, event.getScreenX(), event.getScreenY());
        });

        anchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            contextMenu.hide();
        });

        copy.setAccelerator(KeyCombination.keyCombination("CTRL+C"));
        copy.setOnAction(event -> {

        });

        contextMenu.getItems().addAll(cut,copy,paste,add);

    }



}
