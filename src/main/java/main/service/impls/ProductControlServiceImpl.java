package main.service.impls;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import main.service.ProductControlService;
import main.entity.Product;
import main.utils.PopupMenu;
import main.utils.SQLiteClient;
import main.utils.animations.Shaker;

import java.util.List;
import java.util.Optional;

public class ProductControlServiceImpl implements ProductControlService {

    public void setupTableColumnInTable(TableColumn<Product, Object> tableColumn, String columnName,
                                        String tableName, TableView<Product> tableView, boolean setVisible, ObservableList<Product> productData) {
        //TODO придётся вернуть StringConverter для каждого типа (string, integer, double) сейчас не даёт исправлять внутри таблицы значения

        tableColumn.setVisible(setVisible);
        tableColumn.setCellValueFactory(new PropertyValueFactory<>(columnName));
        tableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Object>() {
            @Override
            public String toString(Object object) {
                return String.valueOf(object);
            }

            @Override
            public Object fromString(String string) {
                return null;
            }
        }));

        tableColumn.setOnEditCommit(event -> {
            Product product = event.getRowValue(); // NPE возникает тут без StringConverter
            product.setName(event.getNewValue().toString());
            SQLiteClient.editNameFromTableDB(tableName, tableView, event.getNewValue().toString());
            refreshTable(productData, tableName);
        });
    }

    public void searchDataInTable(ObservableList<Product> tableProductData, TextField searchField, TableView<Product> tableView) {

        FilteredList<Product> filteredData = new FilteredList<>(tableProductData, p -> true);
        SortedList<Product> sortedData = new SortedList<>(filteredData);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(product -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            return String.valueOf(product.getName()).toLowerCase().contains(lowerCaseFilter);
        }));

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }

    public boolean shakeAddTextFields(TextField textField) {

        Shaker shaker = new Shaker(textField);
        String redStyle = "-fx-border-color: red; -fx-border-radius: 3; -fx-text-fill: black;";
        String silverStyle = "-fx-border-color: silver; -fx-border-radius: 3; -fx-text-fill: black;";

        if (textField.getText() == null || textField.getText().trim().isEmpty() || Double.parseDouble(textField.getText()) <= 0) {
            textField.setStyle(redStyle);
            shaker.playAnim();
            return true;
        } else {
            textField.setStyle(silverStyle);
            return false;
        }
    }

    public void popupMenuActions(TableView<Product> tableView, String tableName, String newTableName,ObservableList<Product> productData, TableColumn<Product, Object> tableIdColumn) {

        PopupMenu popupMenu = new PopupMenu();
        popupMenu.popupProductMenu(tableView);

        popupActionDeleteRow(popupMenu, tableView, tableName, productData);

        popupActionEditTableView(popupMenu, tableView, tableName, productData);

        popupActionShowIdColumn(popupMenu, tableIdColumn, tableName, productData);

        popupActionRefreshTableView(popupMenu,tableName, productData);

        popupActionAddMealMenu(popupMenu, tableView, newTableName, tableName);
    }

    public void popupActionAddMealMenu(PopupMenu popupMenu, TableView<Product> tableView, String nameNewTable, String tableName) {
        popupMenu.mealMenuItem1.setOnAction(event -> {
            //TODO сделать счетчик добавленных таблиц приемов пищи Meal + 1 при условии что такой таблицы еще нет
            SQLiteClient.insertDataInTableFromTable(tableView, nameNewTable, tableName);
        });
    }

    public void popupActionRefreshTableView(PopupMenu popupMenu, String tableName, ObservableList<Product> productData) {
        popupMenu.refresh.setOnAction(event -> refreshTable(productData, tableName));
    }

    public void popupActionShowIdColumn(PopupMenu popupMenu, TableColumn<Product, Object> tableColId, String tableName, ObservableList<Product> productData) {
        popupMenu.showIdCol.setOnAction(event -> {
            if (popupMenu.showIdCol.isSelected()) {
                tableColId.setVisible(true);
                refreshTable(productData, tableName);
            } else {
                tableColId.setVisible(false);
                refreshTable(productData, tableName);
            }

        });
    }

    public void popupActionEditTableView(PopupMenu popupMenu, TableView<Product> tableView, String tableName, ObservableList<Product> productData) {
        popupMenu.editTabView.setOnAction(event -> {
            if (popupMenu.editTabView.isSelected()) {
                tableView.setEditable(true);
                refreshTable(productData, tableName);
            } else {
                tableView.setEditable(false);
                refreshTable(productData, tableName);
            }
        });
    }

    public void popupActionDeleteRow(PopupMenu popupMenu, TableView<Product> tableView, String tableName, ObservableList<Product> productData) {
        popupMenu.delRow.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Удалить содержимое ячейки?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Внимание!");
            alert.setHeaderText("Если вы удалите содержимое ячейки, вернуть обратно уже будет нельзя!");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(null) == ButtonType.YES) {
                SQLiteClient.removeLineFromTableDB(tableView ,tableName);
                refreshTable(productData, tableName);
            } else {
                alert.close();
            }
        });
    }

    public void addRowInTable(Button btn, String tableName, ObservableList<Product> productData, List<TextField> textFieldList) {

        btn.setOnAction(event -> {

                boolean needShakeAll = shakeAddTextFields(textFieldList.get(0)) && shakeAddTextFields(textFieldList.get(1)) && shakeAddTextFields(textFieldList.get(2))
                        && shakeAddTextFields(textFieldList.get(3)) && shakeAddTextFields(textFieldList.get(4));
                boolean needShakeEachOne = shakeAddTextFields(textFieldList.get(0)) || shakeAddTextFields(textFieldList.get(1)) || shakeAddTextFields(textFieldList.get(2))
                        || shakeAddTextFields(textFieldList.get(3)) || shakeAddTextFields(textFieldList.get(4)) ;

                boolean shake = needShakeAll || needShakeEachOne;

                if (!shake) {
                    SQLiteClient.addLineToTableDB(tableName, textFieldList.get(0), textFieldList.get(1), textFieldList.get(2), textFieldList.get(3), textFieldList.get(4));
                    refreshTable(productData, tableName);
                }
        });
    }

    public void refreshTable(ObservableList<Product> productData, String tableName) {

        productData.clear();
        SQLiteClient.executeTableFromDB(tableName, productData);
    }
}
