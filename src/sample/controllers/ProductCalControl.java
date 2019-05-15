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
import pojo.Product;
import sample.Const;
import sample.PopupMenu;
import sample.SQLiteClient;


public class ProductCalControl implements Initializable {

    public ObservableList<Product> tableProductData = FXCollections.observableArrayList();
    private CalcCalControl ccc = new CalcCalControl();
    private PopupMenu popupMenu = new PopupMenu();


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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setProductTableColumnValue();
        initProductTableDataListing();

        showButtonDialog(btnAdd);
        showButtonDialog(btnSearch);
        popupMenu.popupProductMenu(productPane);
    }

    public void setProductTableColumnValue() {
        tabColName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        tabColProtein.setCellValueFactory(new PropertyValueFactory<Product, Double>("protein"));
        tableColFat.setCellValueFactory(new PropertyValueFactory<Product, Double>("fats"));
        tableColCarb.setCellValueFactory(new PropertyValueFactory<Product, Double>("carbs"));
        tableColCal.setCellValueFactory(new PropertyValueFactory<Product, Integer>("calories"));
    }

    private void initProductTableData(String request, Button btn) {

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
                }
                tableViewProducts.setItems(tableProductData);

            } catch (ClassNotFoundException | SQLException e) {
                e.getStackTrace();
            }


    }

    private void initProductTableDataListing(){

        initProductTableData(Const.REQUEST_BURGER_KING,ccc.btnBurgerKing );
        initProductTableData(Const.REQUEST_KFC, ccc.btnKFC);
        initProductTableData(Const.REQUEST_MCDONALDS, ccc.btnMcDonalds);
        initProductTableData(Const.REQUEST_ALCOHOL, ccc.btnAlcohol);
        initProductTableData(Const.REQUEST_BABY_FOOD, ccc.btnBabyFood);
        initProductTableData(Const.REQUEST_BAKERY, ccc.btnBakery);
        initProductTableData(Const.REQUEST_BERRIES, ccc.btnBerriers);
        initProductTableData(Const.REQUEST_CEREALS, ccc.btnCereals);
        initProductTableData(Const.REQUEST_CONDIMENTS, ccc.btnCondiments);
        initProductTableData(Const.REQUEST_FIRST_COURSE, ccc.btnFirstCourse);
        initProductTableData(Const.REQUEST_DAIRY, ccc.btnDairy);
        initProductTableData(Const.REQUEST_EGGS, ccc.btnEggs);
        initProductTableData(Const.REQUEST_FLOUR_PRODUCT, ccc.btnFlourProducts);
        initProductTableData(Const.REQUEST_FRUITS, ccc.btnFruits);
        initProductTableData(Const.REQUEST_JAPAN_FOOD, ccc.btnJapanFood);
        initProductTableData(Const.REQUEST_JUICE, ccc.btnJuice);
        initProductTableData(Const.REQUEST_MEAT_PRODUCTS, ccc.btnMeatProducts);
        initProductTableData(Const.REQUEST_MUSHROOM, ccc.btnMushroom);
        initProductTableData(Const.REQUEST_NUTS, ccc.btnNuts);
        initProductTableData(Const.REQUEST_OILS, ccc.btnOilFats);
        initProductTableData(Const.REQUEST_SALADS, ccc.btnSalad);
        initProductTableData(Const.REQUEST_SAUSAGES, ccc.btnSausages);
        initProductTableData(Const.REQUEST_UNALCOHOL, ccc.btnUnAlcohol);
        initProductTableData(Const.REQUEST_SWEETS, ccc.btnSweets);
        initProductTableData(Const.REQUEST_SPORT_NUTRITION, ccc.btnSportNutrition);
        initProductTableData(Const.REQUEST_VEGETABLE, ccc.btnVegetable);
        initProductTableData(Const.REQUEST_SEA_FOOD, ccc.btnSeaFood);
        initProductTableData(Const.REQUEST_SNACKS, ccc.btnSnacks);
    }

    private void showButtonDialog(Button btn){
        btn.setOnAction(event -> {
            Alert dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setTitle("Инфо");
            dialog.setHeaderText("СДЕЛАЙ КНОПКУ!!!");
            dialog.showAndWait();
        });

    }



}