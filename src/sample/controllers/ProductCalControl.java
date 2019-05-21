package sample.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import pojo.Product;
import sample.Const;
import sample.SQLiteClient;

public class ProductCalControl implements Initializable {

//    @FXML private ResourceBundle resources;
//    @FXML private URL location;
//    @FXML private AnchorPane productPane;
    @FXML private Button btnAdd;
    @FXML private TextField txtFldSearch;
    @FXML public TableColumn<Product, Integer> tableColCal;
    @FXML public TableColumn<Product, Double> tabColProtein;
    @FXML public TableColumn<Product, Double> tableColCarb;
    @FXML public TableColumn<Product, Double> tableColFat;
    @FXML public TableColumn<Product, String> tabColName;
    @FXML public  TableView<Product> tableViewProducts;

    private ObservableList<Product> tableProductData = FXCollections.observableArrayList();
//    https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/table-view.htm#CJAGAAEE%23CJAGAAEE
//    http://qaru.site/questions/1548323/javafx-table-cell-editing

    @Override
    public void initialize(URL location, ResourceBundle resources)  {

        tabColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tabColProtein.setCellValueFactory(new PropertyValueFactory<>("protein"));
        tableColFat.setCellValueFactory(new PropertyValueFactory<>("fats"));
        tableColCarb.setCellValueFactory(new PropertyValueFactory<>("carbs"));
        tableColCal.setCellValueFactory(new PropertyValueFactory<>("calories"));
        tableViewProducts.setItems(tableProductData);

        SQLiteClient.executeTableFromDB(Const.BURGER_KING,tableProductData);
//        SQLiteClient.executeTableFromDB(Const.KFC,tableProductData);

        btnAdd.setOnAction(event -> {
            SQLiteClient.addLineToTableDB(Const.BURGER_KING,"'govno4'",5,5,5,5);
            SQLiteClient.removeLineFromTableDB(Const.BURGER_KING,38);
        });

        searchData();
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
}

