package main.controllers.productControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import main.service.ProductControlService;
import main.service.impls.ProductControlServiceImpl;
import main.entity.Product;
import main.entity.enums.ProductColumnName;
import main.entity.enums.TableProductName;
import main.utils.PopupMenu;
import main.utils.SQLiteClient;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class BurgerKingController implements Initializable {

    @FXML private Button btnAdd;
    @FXML private TextField txtFldSearch;
    @FXML private TextField txtFldAddName;
    @FXML private TextField txtFldAddProtein;
    @FXML private TextField txtFldAddFat;
    @FXML private TextField txtFldAddCarbs;
    @FXML private TextField txtFldAddCal;
    @FXML private TableColumn<Product, Object> tableColCalories;
    @FXML private TableColumn<Product, Object> tableColId;
    @FXML private TableColumn<Product, Object> tabColProtein;
    @FXML private TableColumn<Product, Object> tableColCarbs;
    @FXML private TableColumn<Product, Object> tableColFats;
    @FXML private TableColumn<Product, Object> tabColName;
    @FXML private TableView<Product> tableViewProducts;

    private final ObservableList<Product> TABLE_PRODUCT_DATA = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        ProductControlService productControlService = new ProductControlServiceImpl();
        List<TextField> textFieldList = Arrays.asList(txtFldAddName, txtFldAddProtein, txtFldAddFat, txtFldAddCarbs, txtFldAddCal);

        tableViewProducts.getSelectionModel().setCellSelectionEnabled(true);
        tableViewProducts.setEditable(false);

        productControlService.setupTableColumnInTable(tableColId, ProductColumnName.COLUMN_ID.getName(), TableProductName.BURGER_KING.getName(), tableViewProducts, false, TABLE_PRODUCT_DATA);
        productControlService.setupTableColumnInTable(tabColName, ProductColumnName.COLUMN_NAME.getName(), TableProductName.BURGER_KING.getName(), tableViewProducts, true, TABLE_PRODUCT_DATA);
        productControlService.setupTableColumnInTable(tabColProtein, ProductColumnName.COLUMN_PROTEIN.getName(), TableProductName.BURGER_KING.getName(), tableViewProducts, true, TABLE_PRODUCT_DATA);
        productControlService.setupTableColumnInTable(tableColFats, ProductColumnName.COLUMN_FATS.getName(), TableProductName.BURGER_KING.getName(), tableViewProducts, true, TABLE_PRODUCT_DATA);
        productControlService.setupTableColumnInTable(tableColCarbs, ProductColumnName.COLUMN_CARBS.getName(), TableProductName.BURGER_KING.getName(), tableViewProducts, true, TABLE_PRODUCT_DATA);
        productControlService.setupTableColumnInTable(tableColCalories, ProductColumnName.COLUMN_CALORIES.getName(), TableProductName.BURGER_KING.getName(), tableViewProducts, true, TABLE_PRODUCT_DATA);
        tableViewProducts.setItems(TABLE_PRODUCT_DATA);

        SQLiteClient.executeTableFromDB(TableProductName.BURGER_KING.getName(), TABLE_PRODUCT_DATA);
        productControlService.searchDataInTable(TABLE_PRODUCT_DATA, txtFldSearch, tableViewProducts);

        productControlService.addRowInTable(btnAdd, TableProductName.BURGER_KING.getName(), TABLE_PRODUCT_DATA, textFieldList);

        PopupMenu popupMenu = new PopupMenu();
        popupMenu.popupProductMenu(tableViewProducts);

        productControlService.popupActionDeleteRow(popupMenu, tableViewProducts, TableProductName.BURGER_KING.getName(), TABLE_PRODUCT_DATA);
        productControlService.popupActionEditTableView(popupMenu, tableViewProducts, TableProductName.BURGER_KING.getName(), TABLE_PRODUCT_DATA);
        productControlService.popupActionShowIdColumn(popupMenu, tableColId, TableProductName.BURGER_KING.getName(), TABLE_PRODUCT_DATA);
        productControlService.popupActionRefreshTableView(popupMenu, TableProductName.BURGER_KING.getName(), TABLE_PRODUCT_DATA);
        productControlService.popupActionAddMealMenu(popupMenu, tableViewProducts, TableProductName.BURGER_KING.getName());
    }
}

