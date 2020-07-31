package main.service;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import main.entity.Product;
import main.utils.PopupMenu;

import java.util.List;

public interface ProductControlService {

    void setupTableColumnInTable(TableColumn<Product, Object> tableColumn, String columnName, String tableName,
                                 TableView<Product> tableView, boolean setVisible, ObservableList<Product> productData);

    void searchDataInTable(ObservableList<Product> tableProductData, TextField searchField, TableView<Product> tableView);

    boolean shakeAddTextFields(TextField textField);

    void popupMenuActions(TableView<Product> tableView, String tableName, String newTableName,ObservableList<Product> productData, TableColumn<Product, Object> tableColumn);

    void popupActionAddMealMenu(PopupMenu popupMenu, TableView<Product> tableView, String nameNewTable, String tableName);

    void popupActionRefreshTableView(PopupMenu popupMenu, String tableName, ObservableList<Product> productData);

    void popupActionShowIdColumn(PopupMenu popupMenu, TableColumn<Product, Object> tableColId, String tableName, ObservableList<Product> productData);

    void popupActionEditTableView(PopupMenu popupMenu, TableView<Product> tableView, String tableName, ObservableList<Product> productData);

    void popupActionDeleteRow(PopupMenu popupMenu, TableView<Product> tableView, String tableName, ObservableList<Product> productData);

    void addRowInTable(Button btn, String tableName, ObservableList<Product> productData, List<TextField> textFieldList);

    void refreshTable(ObservableList<Product> productData, String tableName);

}
