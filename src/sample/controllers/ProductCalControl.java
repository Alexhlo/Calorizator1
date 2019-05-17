package sample.controllers;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import pojo.Product;
import sample.Const;
import sample.SQLiteClient;

public class ProductCalControl implements Initializable {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private AnchorPane productPane;
    @FXML public TableColumn<Product, Integer> tableColCal;
    @FXML public TableColumn<Product, Double> tabColProtein;
    @FXML public TableColumn<Product, Double> tableColCarb;
    @FXML public TableColumn<Product, Double> tableColFat;
    @FXML public TableColumn<Product, String> tabColName;
    @FXML public  TableView<Product> tableViewProducts;
    @FXML private Button btnAdd;
    @FXML private Button btnSearch;
    @FXML private TextField txtFldSearch;

    public ObservableList<Product> tableProductData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tabColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tabColProtein.setCellValueFactory(new PropertyValueFactory<>("protein"));
        tableColFat.setCellValueFactory(new PropertyValueFactory<>("fats"));
        tableColCarb.setCellValueFactory(new PropertyValueFactory<>("carbs"));
        tableColCal.setCellValueFactory(new PropertyValueFactory<>("calories"));


        public void initProductTableData(){
            try {
                SQLiteClient.resultSet = SQLiteClient.connection.createStatement().executeQuery(Const.REQUEST_BURGER_KING);
                while (SQLiteClient.resultSet.next()) {
                    Product product = new Product();
                    product.name.set(SQLiteClient.resultSet.getString(Const.TABLE_NAME));
                    product.protein.set(SQLiteClient.resultSet.getDouble(Const.TABLE_PROTEIN));
                    product.fats.set(SQLiteClient.resultSet.getDouble(Const.TABLE_FATS));
                    product.carbs.set(SQLiteClient.resultSet.getDouble(Const.TABLE_CARBS));
                    product.calories.set(SQLiteClient.resultSet.getInt(Const.TABLE_CALORIES));
                    tableProductData.add(product);
                }
                tableViewProducts.setItems(tableProductData);
                System.out.println("-----------------=Таблица выведена=-----------------");
                SQLiteClient.closeDB();
            } catch (SQLException e) {
                e.getStackTrace();
            }
        }
    }
}
