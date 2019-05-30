package sample.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import pojo.Product;
import sample.Const;
import sample.PopupMenu;
import sample.SQLiteClient;

public class ProductCalControl implements Initializable {

//    @FXML private ResourceBundle resources;
//    @FXML private URL location;
//    @FXML private AnchorPane productPane;
    @FXML private Button btnAdd;
    @FXML private TextField txtFldSearch;
    @FXML private TextField txtFldAddName;
    @FXML private TextField txtFldAddProtein;
    @FXML private TextField txtFldAddFat;
    @FXML private TextField txtFldAddCarb;
    @FXML private TextField txtFldAddCal;
    @FXML public TableColumn<Product, Integer> tableColCal;
    @FXML public TableColumn<Product, Integer> tableColId;
    @FXML public TableColumn<Product, Double> tabColProtein;
    @FXML public TableColumn<Product, Double> tableColCarb;
    @FXML public TableColumn<Product, Double> tableColFat;
    @FXML public TableColumn<Product, String> tabColName;
    @FXML public  TableView<Product> tableViewProducts;

    private ObservableList<Product> tableProductData = FXCollections.observableArrayList();

//    https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/table-view.htm#CJAGAAEE%23CJAGAAEE
//    http://qaru.site/questions/1548323/javafx-table-cell-editing
//    https://code.makery.ch/ru/library/javafx-tutorial/
//    http://qaru.site/questions/13757619/right-click-event-and-double-click-event-on-tableview-javafx

    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        setUpTableColumns();
        SQLiteClient.executeTableFromDB(Const.BURGER_KING,tableProductData);

        searchData();
        allActions();
    }

    private void setUpTableColumns(){
        tableViewProducts.getSelectionModel().setCellSelectionEnabled(true);
        tableViewProducts.setEditable(true);

        tableColId.setCellValueFactory(new PropertyValueFactory<>("id"));

        tabColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tabColName.setCellFactory(TextFieldTableCell.forTableColumn());
        tabColName.setOnEditCommit(event -> {
            Product product = event.getRowValue();
            product.setName(event.getNewValue());
            SQLiteClient.editLineFromTableDB(Const.BURGER_KING, tableViewProducts, event.getNewValue());
            refreshTable();
        });


        tabColProtein.setCellValueFactory(new PropertyValueFactory<>("protein"));
        tabColProtein.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
//        tabColProtein.setOnEditCommit((TableColumn.CellEditEvent<Product, Double> event) ->
//                (event.getTableView().getItems().get(event.getTablePosition().getRow())).setProtein(event.getNewValue()));
//        tabColProtein.setOnEditCommit(event -> {
//            Product product = event.getRowValue();
//            product.setName(event.getNewValue());
//            SQLiteClient.editLineFromTableDB(Const.BURGER_KING, tableViewProducts, event.getNewValue());
//            refreshTable();
//        });

        tableColFat.setCellValueFactory(new PropertyValueFactory<>("fats"));
        tableColFat.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
//        tableColFat.setOnEditCommit((TableColumn.CellEditEvent<Product, Double> event) ->
//                (event.getTableView().getItems().get(event.getTablePosition().getRow())).setFats(event.getNewValue()));
//        tableColFat.setOnEditCommit(event -> {
//            Product product = event.getRowValue();
//            product.setName(event.getNewValue());
//            SQLiteClient.editLineFromTableDB(Const.BURGER_KING, tableViewProducts, event.getNewValue());
//            refreshTable();
//        });

        tableColCarb.setCellValueFactory(new PropertyValueFactory<>("carbs"));
        tableColCarb.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
//        tableColCarb.setOnEditCommit((TableColumn.CellEditEvent<Product, Double> event) ->
//                (event.getTableView().getItems().get(event.getTablePosition().getRow())).setCarbs(event.getNewValue()));
//        tableColCarb.setOnEditCommit(event -> {
//            Product product = event.getRowValue();
//            product.setName(event.getNewValue());
//            SQLiteClient.editLineFromTableDB(Const.BURGER_KING, tableViewProducts, event.getNewValue());
//            refreshTable();
//        });

        tableColCal.setCellValueFactory(new PropertyValueFactory<>("calories"));
        tableColCal.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
//        tableColCal.setOnEditCommit((TableColumn.CellEditEvent<Product, Integer> event) ->
//                (event.getTableView().getItems().get(event.getTablePosition().getRow())).setCalories(event.getNewValue()));
//        tableColCal.setOnEditCommit(event -> {
//            Product product = event.getRowValue();
//            product.setName(event.getNewValue());
//            SQLiteClient.editLineFromTableDB(Const.BURGER_KING, tableViewProducts, event.getNewValue());
//            refreshTable();
//        });

        tableViewProducts.setItems(tableProductData);
    }

    private void searchData(){
        FilteredList<Product> filteredData = new FilteredList<>(tableProductData, p -> true);
        SortedList<Product> sortedData = new SortedList<>(filteredData);
        txtFldSearch.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(product -> {
            if(newValue == null || newValue.isEmpty()) {
                return true;
            }
                String lowerCaseFilter = newValue.toLowerCase();
            return String.valueOf(product.getName()).toLowerCase().contains(lowerCaseFilter);
        }));
        sortedData.comparatorProperty().bind(tableViewProducts.comparatorProperty());
        tableViewProducts.setItems(sortedData);
    }

    private void allActions(){
        PopupMenu popupMenu = new PopupMenu();
        popupMenu.popupProductMenu(tableViewProducts);
        btnAdd.setOnAction(event -> {
            SQLiteClient.addLineToTableDB(Const.BURGER_KING,txtFldAddName,txtFldAddProtein,txtFldAddFat,txtFldAddCarb,txtFldAddCal);
            refreshTable();
        });
        popupMenu.delRow.setOnAction(event -> {
            SQLiteClient.removeLineFromTableDB(tableViewProducts ,Const.BURGER_KING);
            refreshTable();
        });

        popupMenu.refresh.setOnAction(event -> refreshTable());
    }

    private void refreshTable(){
        tableProductData.clear();
        SQLiteClient.executeTableFromDB(Const.BURGER_KING,tableProductData);
    }
}

