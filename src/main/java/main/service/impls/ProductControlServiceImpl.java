package main.service.impls;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import main.entity.Product;
import main.entity.enums.TableColumnId;
import main.service.ProductControlService;
import main.service.VerifyService;
import main.utils.PopupMenu;
import main.utils.SQLiteClient;

import java.util.List;
import java.util.Optional;

public class ProductControlServiceImpl implements ProductControlService {

    public void setupTableColumnInTable(TableColumn<Product, Object> tableColumn, String columnName,
                                        String tableName, TableView<Product> tableView, boolean setVisible, ObservableList<Product> productData) {

        tableColumn.setVisible(setVisible);
        tableColumn.setCellValueFactory(new PropertyValueFactory<>(columnName));
        tableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {
            @Override
            public String toString(Object object) {
                return String.valueOf(object);
            }

            @Override
            public Object fromString(String string) {
                return string;
            }
        }));

        tableColumn.setOnEditCommit(event -> {
            Product product = event.getRowValue();
            if (tableColumn.getId().equals(TableColumnId.COLUMN_NAME.getId())) {
                String name = event.getNewValue().toString();
                product.setName(name);
                SQLiteClient.editNameFromTableDB(tableName, tableView, name);
            } else if (tableColumn.getId().equals(TableColumnId.COLUMN_PROTEIN.getId())) {
                double protein = Double.parseDouble(event.getNewValue().toString());
                product.setProtein(protein);
                SQLiteClient.editProteinFromTableDB(tableName, tableView, protein);
            } else if (tableColumn.getId().equals(TableColumnId.COLUMN_FATS.getId())) {
                double fats = Double.parseDouble(event.getNewValue().toString());
                product.setFats(fats);
                SQLiteClient.editFatsFromTableDB(tableName, tableView, fats);
            } else if (tableColumn.getId().equals(TableColumnId.COLUMN_CARBS.getId())) {
                double carbs = Double.parseDouble(event.getNewValue().toString());
                product.setCarbs(carbs);
                SQLiteClient.editCarbsFromTableDB(tableName, tableView, carbs);
            } else if (tableColumn.getId().equals(TableColumnId.COLUMN_CALORIES.getId())) {
                int calories = Integer.parseInt(event.getNewValue().toString());
                product.setCalories(calories);
                SQLiteClient.editCaloriesFromTableDB(tableName, tableView, calories);
            }

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

    public void popupActionAddMealMenu(PopupMenu popupMenu, TableView<Product> tableView, String tableName) {
        popupMenu.mealMenuItem1.setOnAction(event -> {
            int countMealTables = 0;
            String createdTableName = String.format("Meal_%s", ++countMealTables);
            //TODO сделать счетчик добавленных таблиц приемов пищи Meal + 1 при условии что такой таблицы еще нет
            SQLiteClient.insertDataInTableFromTable(tableView, createdTableName, tableName);
        });
    }

    public void popupActionRefreshTableView(PopupMenu popupMenu, String tableName, ObservableList<Product> productData) {
        popupMenu.refresh.setOnAction(event -> refreshTable(productData, tableName));
    }

    public void popupActionShowIdColumn(PopupMenu popupMenu, TableColumn<Product, Object> tableColId, String tableName, ObservableList<Product> productData) {

        popupMenu.showIdCol.setOnAction(event -> {
            tableColId.setVisible(popupMenu.showIdCol.isSelected());
            refreshTable(productData, tableName);
        });
    }

    public void popupActionEditTableView(PopupMenu popupMenu, TableView<Product> tableView, String tableName, ObservableList<Product> productData) {

        popupMenu.editTabView.setOnAction(event -> {
            tableView.setEditable(popupMenu.editTabView.isSelected());
            refreshTable(productData, tableName);
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

        VerifyService verifyService = new VerifyServiceImpl();

        btn.setOnAction(event -> {
            if (!verifyService.shakeTextFields(textFieldList)) {
                String name = textFieldList.get(0).getText();
                String protein = textFieldList.get(1).getText();
                String fats = textFieldList.get(2).getText();
                String carbs = textFieldList.get(3).getText();
                String calories = textFieldList.get(4).getText();
                SQLiteClient.addLineToTableDB(tableName, name, protein, fats, carbs, calories);
                textFieldList.forEach(TextInputControl::clear);
                refreshTable(productData, tableName);
            }
        });
    }

    private void refreshTable(ObservableList<Product> productData, String tableName) {

        productData.clear();
        SQLiteClient.executeTableFromDB(tableName, productData);
    }
}
