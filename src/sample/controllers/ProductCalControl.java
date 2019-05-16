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
import javafx.stage.StageStyle;
import org.sqlite.SQLiteConfig;
import pojo.Product;
import sample.Const;
import sample.PopupMenu;
import sample.SQLiteClient;

public class ProductCalControl implements Initializable {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private AnchorPane productPane;
    @FXML public TableColumn tableColCal;
    @FXML public TableColumn tabColProtein;
    @FXML public TableColumn tableColCarb;
    @FXML public TableColumn tableColFat;
    @FXML public TableColumn tabColName;
    @FXML public  TableView tableViewProducts;
    @FXML private Button btnAdd;
    @FXML private Button btnSearch;
    @FXML private TextField txtFldSearch;

    public CalcCalControl ccc = new CalcCalControl();
    private PopupMenu popupMenu = new PopupMenu();
    public ObservableList<Product> tableProductData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setProductTableColumnValue();
        initProductTableData(Const.REQUEST_BURGER_KING);

    }

    public void setProductTableColumnValue() {
        tabColName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        tabColProtein.setCellValueFactory(new PropertyValueFactory<Product, Double>("protein"));
        tableColFat.setCellValueFactory(new PropertyValueFactory<Product, Double>("fats"));
        tableColCarb.setCellValueFactory(new PropertyValueFactory<Product, Double>("carbs"));
        tableColCal.setCellValueFactory(new PropertyValueFactory<Product, Integer>("calories"));
    }



    private void initProductTableData(String request) {
            try {
                SQLiteClient.connectDB();
                SQLiteClient.resultSet = SQLiteClient.connection.createStatement().executeQuery(request);

                    while (SQLiteClient.resultSet.next()) {
                        Product product = new Product();
                        product.name.set(SQLiteClient.resultSet.getString(Const.TABLE_NAME));
                        product.protein.set(SQLiteClient.resultSet.getDouble(Const.TABLE_PROTEIN));
                        product.fats.set(SQLiteClient.resultSet.getDouble(Const.TABLE_FATS));
                        product.carbs.set(SQLiteClient.resultSet.getDouble(Const.TABLE_CARBS));
                        product.calories.set(SQLiteClient.resultSet.getInt(Const.TABLE_CALORIES));
                        tableProductData.add(product);
                    tableViewProducts.setItems(tableProductData);
                }
                SQLiteClient.closeDB();
            } catch (ClassNotFoundException | SQLException e) {
                e.getStackTrace();
            }
    }


}
