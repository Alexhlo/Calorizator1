package sample.controllers.productControllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import animations.Shaker;
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
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import pojo.Product;
import sample.Const;
import sample.PopupMenu;
import sample.SQLiteClient;

public class BurgerKingController implements Initializable {

//    @FXML private ResourceBundle resources;
//    @FXML private URL location;
    @FXML private AnchorPane productPane;
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

    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        setUpTableColumns();
        SQLiteClient.executeTableFromDB(Const.BURGER_KING,tableProductData);
        searchData();
        allActions();
    }

    private void setUpTableColumns(){
        tableViewProducts.getSelectionModel().setCellSelectionEnabled(true);
        tableViewProducts.setEditable(false);

        tableColId.setVisible(false);
        tableColId.setCellValueFactory(new PropertyValueFactory<>("id"));

        tabColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tabColName.setCellFactory(TextFieldTableCell.forTableColumn());
        tabColName.setOnEditCommit(event -> {
            Product product = event.getRowValue();
            product.setName(event.getNewValue());
            SQLiteClient.editNameFromTableDB(Const.BURGER_KING, tableViewProducts, event.getNewValue());
            refreshTable();
        });

        tabColProtein.setCellValueFactory(new PropertyValueFactory<>("protein"));
        tabColProtein.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        tabColProtein.setOnEditCommit(event -> {
            Product product = event.getRowValue();
            product.setProtein(event.getNewValue());
            SQLiteClient.editProteinFromTableDB(Const.BURGER_KING, tableViewProducts, event.getNewValue());
            refreshTable();
        });

        tableColFat.setCellValueFactory(new PropertyValueFactory<>("fats"));
        tableColFat.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        tableColFat.setOnEditCommit(event -> {
            Product product = event.getRowValue();
            product.setFats(event.getNewValue());
            SQLiteClient.editFatsFromTableDB(Const.BURGER_KING, tableViewProducts, event.getNewValue());
            refreshTable();
        });

        tableColCarb.setCellValueFactory(new PropertyValueFactory<>("carbs"));
        tableColCarb.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        tableColCarb.setOnEditCommit(event -> {
            Product product = event.getRowValue();
            product.setCarbs(event.getNewValue());
            SQLiteClient.editCarbFromTableDB(Const.BURGER_KING, tableViewProducts, event.getNewValue());
            refreshTable();
        });

        tableColCal.setCellValueFactory(new PropertyValueFactory<>("calories"));
        tableColCal.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tableColCal.setOnEditCommit(event -> {
            Product product = event.getRowValue();
            product.setCalories(event.getNewValue());
            SQLiteClient.editCalFromTableDB(Const.BURGER_KING, tableViewProducts, event.getNewValue());
            refreshTable();
        });

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

    private void allActions() {
        PopupMenu popupMenu = new PopupMenu();
        popupMenu.popupProductMenu(tableViewProducts);
        btnAdd.setOnAction(event -> {
                if (!shakeAddTextFields()) {
                    if(txtFldAddName.getText().isEmpty() || txtFldAddProtein.getText().isEmpty() || txtFldAddFat.getText().isEmpty() || txtFldAddCarb.getText().isEmpty() || txtFldAddCal.getText().isEmpty()){
                        if(txtFldAddName.getText().trim().isEmpty() || txtFldAddProtein.getText().trim().isEmpty() || txtFldAddFat.getText().trim().isEmpty() || txtFldAddCarb.getText().trim().isEmpty() || txtFldAddCal.getText().trim().isEmpty()){
                            System.out.println("Ячейка абсолютно пуста!");
                        }
                    }else {
                        SQLiteClient.addLineToTableDB(Const.BURGER_KING, txtFldAddName, txtFldAddProtein, txtFldAddFat, txtFldAddCarb, txtFldAddCal);
                        refreshTable();
                    }
                }});

        popupMenu.delRow.setOnAction(event -> {
            Alert alert = new Alert(
                    Alert.AlertType.WARNING, "Удалить содержимое ячейки?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Внимание!");
            alert.setHeaderText("Если вы удалите содержимое ячейки, вернуть обратно уже будет нельзя!");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.orElse(null) == ButtonType.YES){
                SQLiteClient.removeLineFromTableDB(tableViewProducts ,Const.BURGER_KING);
                refreshTable();
            }else {
                alert.close();
            }
        });

        popupMenu.editTabView.setOnAction(event -> {
            if(popupMenu.editTabView.isSelected()){
                tableViewProducts.setEditable(true);
                refreshTable();
            }else{
                tableViewProducts.setEditable(false);
                refreshTable();
            }
        });

        popupMenu.showIdCol.setOnAction(event -> {
            if(popupMenu.showIdCol.isSelected()){
                tableColId.setVisible(true);
                refreshTable();
            }else{
                tableColId.setVisible(false);
                refreshTable();
            }

        });

        popupMenu.refresh.setOnAction(event -> refreshTable());
    }

    private void refreshTable(){
        tableProductData.clear();
        SQLiteClient.executeTableFromDB(Const.BURGER_KING,tableProductData);
    }

    private boolean shakeAddTextFields() {
        Shaker fldName = new Shaker(txtFldAddName);
        Shaker fldProt = new Shaker(txtFldAddProtein);
        Shaker fldFat = new Shaker(txtFldAddFat);
        Shaker fldCarb = new Shaker(txtFldAddCarb);
        Shaker fldCal = new Shaker(txtFldAddCal);
        String redStyle = "-fx-border-color: red; -fx-border-radius: 3; -fx-text-fill: black;";
        String silverStyle = "-fx-border-color: silver; -fx-border-radius: 3; -fx-text-fill: black;";
        if (txtFldAddName.getText() == null || txtFldAddName.getText().trim().isEmpty()
                || Double.parseDouble(txtFldAddName.getText()) <= 0) {
            txtFldAddName.setStyle(redStyle);
            fldName.playAnim();
        } else txtFldAddName.setStyle(silverStyle);

        if (txtFldAddProtein.getText() == null || txtFldAddProtein.getText().trim().isEmpty()
                || Double.parseDouble(txtFldAddProtein.getText()) <= 0) {
            txtFldAddProtein.setStyle(redStyle);
            fldProt.playAnim();
        } else txtFldAddProtein.setStyle(silverStyle);

        if (txtFldAddFat.getText() == null || txtFldAddFat.getText().trim().isEmpty()
                || Double.parseDouble(txtFldAddFat.getText()) <= 0) {
            txtFldAddFat.setStyle(redStyle);
            fldFat.playAnim();
        } else txtFldAddFat.setStyle(silverStyle);

        if (txtFldAddCarb.getText() == null || txtFldAddCarb.getText().trim().isEmpty()
                || Double.parseDouble(txtFldAddCarb.getText()) <= 0) {
            txtFldAddCarb.setStyle(redStyle);
            fldCarb.playAnim();
        } else txtFldAddCarb.setStyle(silverStyle);

        if (txtFldAddCal.getText() == null || txtFldAddCal.getText().trim().isEmpty()
                || Double.parseDouble(txtFldAddCal.getText()) <= 0) {
            txtFldAddCal.setStyle(redStyle);
            fldCal.playAnim();
        } else txtFldAddCal.setStyle(silverStyle);
        return false;
    }
}

