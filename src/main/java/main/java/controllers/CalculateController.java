package main.java.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.animations.Shaker;
import main.java.pojo.IndexMassBody;
import main.java.pojo.Product;
import main.java.sample.Const;
import main.java.sample.PopupMenu;
import main.java.sample.SQLiteClient;
import javafx.scene.control.TableColumn;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CalculateController implements Initializable {

    /**
     * Вкладка калоризатора
     */

    @FXML private RadioButton rbCoA2;
    @FXML private RadioButton rbCoA1;
    @FXML private RadioButton rbFemale;
    @FXML private RadioButton rbCoA4;
    @FXML private RadioButton rbCoA3;
    @FXML private ComboBox<String> menuFormula;
    @FXML private RadioButton rbMale;
    @FXML private TextField txtFldWL;
    @FXML private Button btnCalc;
    @FXML private TextField txtFldMM;
    @FXML private RadioButton rbCoA5;
    @FXML private TextField txtFldFat;
    @FXML private TextField txtFldAge;
    @FXML private TextField txtFldBMR;
    @FXML private TextField txtFldHeight;
    @FXML private Button btnCancel;
    @FXML private TextField txtFldWeight;
    @FXML private TextField txtFldBKA;
    @FXML private AnchorPane apCalcKCal;
    private final ToggleGroup TOGGLE_GROUP_GENDER = new ToggleGroup();
    private final ToggleGroup TOGGLE_GROUP_COA = new ToggleGroup();
    private double RESULT;
    private final String EMPTY = "";

    /**
     * Вкладка расчет индекса массы тела IMB
     */

    @FXML private TableView<IndexMassBody> tableViewIMB;
    @FXML private TableColumn<IndexMassBody, Integer> column1;
    @FXML private TableColumn<IndexMassBody, Integer> column2;
    @FXML private TableColumn<IndexMassBody, Integer> column3;
    @FXML private TableColumn<IndexMassBody, Integer> column4;
    @FXML private TableColumn<IndexMassBody, Integer> column5;
    @FXML private TableColumn<IndexMassBody, Integer> column6;
    @FXML private TableColumn<IndexMassBody, Integer> column7;
    @FXML private TableColumn<IndexMassBody, Integer> column8;
    @FXML private TableColumn<IndexMassBody, String> columnHeight;
    @FXML private TableColumn<IndexMassBody, String> columnImb;
    @FXML private Button btnCancelImb;
    @FXML private Button btnCalcImb;
    @FXML private TextField txtFldWeightImb;
    @FXML private TextField txtFldHeightImb;
    @FXML private TextField txtFldResultImb;
    private ObservableList<IndexMassBody> tableImbData = FXCollections.observableArrayList();

    /**
     * Вкладка продукты
     */

    @FXML
    public Button btnBurgerKing;
    @FXML
    public Button btnKFC;
    @FXML
    public Button btnMcDonalds;
    @FXML
    public Button btnAlcohol;
    @FXML
    public Button btnUnAlcohol;
    @FXML
    public Button btnMushroom;
    @FXML
    public Button btnBabyFood;
    @FXML
    public Button btnSausages;
    @FXML
    public Button btnCereals;
    @FXML
    public Button btnOilFats;
    @FXML
    public Button btnDairy;
    @FXML
    public Button btnFlourProducts;
    @FXML
    public Button btnMeatProducts;
    @FXML
    public Button btnVegetable;
    @FXML
    public Button btnNuts;
    @FXML
    public Button btnFirstCourse;
    @FXML
    public Button btnSeaFood;
    @FXML
    public Button btnSalad;
    @FXML
    public Button btnSweets;
    @FXML
    public Button btnSnacks;
    @FXML
    public Button btnJuice;
    @FXML
    public Button btnSportNutrition;
    @FXML
    public Button btnCondiments;
    @FXML
    public Button btnFruits;
    @FXML
    public Button btnBakery;
    @FXML
    public Button btnBerriers;
    @FXML
    public Button btnEggs;
    @FXML
    public Button btnJapanFood;
    @FXML
    public Button btnSearchAll;

    /**
     * Вкладка рацион
     */

    @FXML public VBox rationVBox;
    @FXML public AnchorPane rationAnchorPane;
    private PopupMenu popupMenu = new PopupMenu();
    public ObservableList<Product> tableMealData1 = FXCollections.observableArrayList();
    private TableColumn<Product, Integer> tableColId = new TableColumn<>("id");
    private TableColumn<Product, Integer> tableColCal = new TableColumn<>("Калории");
    private TableColumn<Product, Integer> tableColWeight = new TableColumn<>("Вес");
    private TableColumn<Product, Double> tabColProtein = new TableColumn<>("Белки, г");
    private TableColumn<Product, Double> tableColCarb = new TableColumn<>("Углеводы, г");
    private TableColumn<Product, Double> tableColFat = new TableColumn<>("Жиры, г");
    private TableColumn<Product, String> tabColName = new TableColumn<>("Наименование");

    public ObservableList<TableView> tableViewList = FXCollections.observableArrayList();

    public TableView<Product> meal1 = new TableView<Product>();
    public TableView<Product> meal2 = new TableView<Product>();
    public TableView<Product> meal3 = new TableView<Product>();
    public TableView<Product> meal4 = new TableView<Product>();
    public TableView<Product> meal5 = new TableView<Product>();

    @FXML public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> menuFormula.requestFocus());
        menuFormula.getItems().addAll(Const.FORMULA_MIFFLIN, Const.FORMULA_HARRISON, Const.FORMULA_KETCH);
        menuFormula.setValue(Const.FORMULA_MIFFLIN);

        setToggleGroupsRadioButton();
        setupImbTable();
        popupAction();
        allActions();
        openNewProductWindow(btnBurgerKing,Const.BURGER_KING_WINDOW,"Burger King menu");
        setupTableMealColumns();
    }

    private void setupTableMealColumns(){
        meal1.getSelectionModel().setCellSelectionEnabled(true);
        meal1.setEditable(true);

        meal1.getColumns().addAll(tableColId,tabColName,tabColProtein,tableColFat,tableColCarb,tableColCal, tableColWeight);

        tableColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tabColProtein.setCellValueFactory(new PropertyValueFactory<>("protein"));
        tableColFat.setCellValueFactory(new PropertyValueFactory<>("fats"));
        tableColCarb.setCellValueFactory(new PropertyValueFactory<>("carbs"));
        tableColCal.setCellValueFactory(new PropertyValueFactory<>("calories"));
        tableColWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));

        meal1.setItems(tableMealData1);

    }

    private void allActions (){
        apCalcKCal.setOnMouseEntered(event -> {
            if (menuFormula.getValue().equals(Const.FORMULA_MIFFLIN)) {
                txtFldFat.clear();
                txtFldFat.setDisable(true);
            }
            if (menuFormula.getValue().equals(Const.FORMULA_HARRISON)) {
                txtFldFat.clear();
                txtFldFat.setDisable(true);
            }
            if (menuFormula.getValue().equals(Const.FORMULA_KETCH)) {
                txtFldFat.setDisable(false);
            }});
        btnCancel.setOnAction(event ->  clearAllTextFields());
        btnCalc.setOnAction(event -> {
            try {
                if (!shakeTextFields()) conditionMenuFormulaBMRCoA();
            }catch (NumberFormatException ignored){} });
        btnCancelImb.setOnAction(event -> clearImbTextFields());
        btnCalcImb.setOnAction(event -> {
            try {
                if (!shakeTextImbFields()) resultImb();
            }catch (NumberFormatException ignored){} });
    }

    private void popupAction(){
        popupMenu.popupTableAction(rationVBox);
        popupMenu.createTable.setOnAction(event -> {
            tableViewList.add(meal1);
            rationVBox.getChildren().addAll(tableViewList);
            SQLiteClient.createNewTable("Meal_1");
            SQLiteClient.executeTableFromDB("Meal_1",tableMealData1);
        });
        popupMenu.deleteTable.setOnAction(event -> {
            Alert alert = new Alert(
                    Alert.AlertType.WARNING, "Удалить таблицу?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Внимание!");
            alert.setHeaderText("Если вы удалите таблицу, вернуть её обратно уже будет нельзя!");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.orElse(null) == ButtonType.YES){
                SQLiteClient.deleteTableFromDB("Meal_1");
                refreshTable();
            }else {
                alert.close();
            }
        });
    }

    private void refreshTable(){
        tableMealData1.clear();
        SQLiteClient.executeTableFromDB(Const.BURGER_KING,tableMealData1);
    }

    private void setToggleGroupsRadioButton() {
        rbFemale.setToggleGroup(TOGGLE_GROUP_GENDER);
        rbMale.setToggleGroup(TOGGLE_GROUP_GENDER);
        rbCoA1.setToggleGroup(TOGGLE_GROUP_COA);
        rbCoA2.setToggleGroup(TOGGLE_GROUP_COA);
        rbCoA3.setToggleGroup(TOGGLE_GROUP_COA);
        rbCoA4.setToggleGroup(TOGGLE_GROUP_COA);
        rbCoA5.setToggleGroup(TOGGLE_GROUP_COA);
    }

    private void conditionCoA() {
        //CoA - coefficient of activity
        if (rbCoA1.isSelected()) RESULT *= 1.2;
        if (rbCoA2.isSelected()) RESULT *= 1.375;
        if (rbCoA3.isSelected()) RESULT *= 1.55;
        if (rbCoA4.isSelected()) RESULT *= 1.725;
        if (rbCoA5.isSelected()) RESULT *= 1.9;
    }

    private void conditionMenuFormulaBMRCoA() {
        double weight = Double.parseDouble(txtFldWeight.getText());
        int height = Integer.parseInt(txtFldHeight.getText());
        int age = Integer.parseInt(txtFldAge.getText());
        if (menuFormula.getValue().equals(Const.FORMULA_MIFFLIN)) {
            if (rbMale.isSelected()) {
                RESULT = 10 * weight + 6.25 * height - 5 * age + 5;
                txtFldBMR.setText(EMPTY + Math.rint(RESULT));
                conditionCoA();
                txtFldBKA.setText(EMPTY + Math.rint(RESULT));
                txtFldMM.setText(EMPTY + (Math.rint(RESULT) + Const.MUSCLE_MASS));
                txtFldWL.setText(EMPTY + (Math.rint(RESULT) - Const.WEIGHT_LOSS));
            }
            if (rbFemale.isSelected()) {
                RESULT = 10 * weight + 6.25 * height - 5 * age - 161;
                txtFldBMR.setText(EMPTY + Math.rint(RESULT));
                conditionCoA();
                txtFldBKA.setText(EMPTY + Math.rint(RESULT));
                txtFldMM.setText(EMPTY + (Math.rint(RESULT) + Const.MUSCLE_MASS));
                txtFldWL.setText(EMPTY + (Math.rint(RESULT) - Const.WEIGHT_LOSS));
            }
        }
        if (menuFormula.getValue().equals(Const.FORMULA_HARRISON)) {
            if (rbMale.isSelected()) {
                RESULT = 66 + (13.7 * weight) + (5 * height) - (6.8 * age);
                txtFldBMR.setText(EMPTY + Math.rint(RESULT));
                conditionCoA();
                txtFldBKA.setText(EMPTY + Math.rint(RESULT));
                txtFldMM.setText(EMPTY + (Math.rint(RESULT) + Const.MUSCLE_MASS));
                txtFldWL.setText(EMPTY + (Math.rint(RESULT) - Const.WEIGHT_LOSS));
            } else {
                RESULT = 655 + (9.6 * weight) + (1.8 * height) - (4.7 * age);
                txtFldBMR.setText(EMPTY + Math.rint(RESULT));
                conditionCoA();
                txtFldBKA.setText(EMPTY + Math.rint(RESULT));
                txtFldMM.setText(EMPTY + (Math.rint(RESULT) + Const.MUSCLE_MASS));
                txtFldWL.setText(EMPTY + (Math.rint(RESULT) - Const.WEIGHT_LOSS));
            }
        }
        if (menuFormula.getValue().equals(Const.FORMULA_KETCH)) {
            double fat = Double.parseDouble(txtFldFat.getText());
            RESULT = 370 + (21.6 * (weight - (weight * fat / 100)));
            txtFldBMR.setText(EMPTY + Math.rint(RESULT));
            conditionCoA();
            txtFldBKA.setText(EMPTY + Math.rint(RESULT));
            txtFldMM.setText(EMPTY + (Math.rint(RESULT) + Const.MUSCLE_MASS));
            txtFldWL.setText(EMPTY + (Math.rint(RESULT) - Const.WEIGHT_LOSS));
        }
    }

    private void clearAllTextFields() {
        txtFldWeight.clear();
        txtFldHeight.clear();
        txtFldAge.clear();
        txtFldFat.clear();
        txtFldBMR.clear();
        txtFldBKA.clear();
        txtFldMM.clear();
        txtFldWL.clear();
    }

    private void clearSupportTextFields() {
        txtFldBMR.clear();
        txtFldBKA.clear();
        txtFldMM.clear();
        txtFldWL.clear();
    }

    private boolean shakeTextFields() {
        Shaker fldWeight = new Shaker(txtFldWeight);
        Shaker fldHeight = new Shaker(txtFldHeight);
        Shaker fldAge = new Shaker(txtFldAge);
        Shaker fldFat = new Shaker(txtFldFat);
        String redStyle = "-fx-border-color: red; -fx-border-radius: 3; -fx-text-fill: black;";
        String silverStyle ="-fx-border-color: silver; -fx-border-radius: 3; -fx-text-fill: black;";
        if (txtFldWeight.getText() == null
                || txtFldWeight.getText().trim().isEmpty()
                || Double.parseDouble(txtFldWeight.getText()) <= 0) {
            txtFldWeight.setStyle(redStyle);
            fldWeight.playAnim();
            clearSupportTextFields();
        }else txtFldWeight.setStyle(silverStyle);
        if (txtFldHeight.getText() == null
                || txtFldHeight.getText().trim().isEmpty()
                || Double.parseDouble(txtFldHeight.getText()) <= 0) {
            txtFldHeight.setStyle(redStyle);
            fldHeight.playAnim();
            clearSupportTextFields();
        }else txtFldHeight.setStyle(silverStyle);
        if (txtFldAge.getText() == null
                || txtFldAge.getText().trim().isEmpty()
                || Double.parseDouble(txtFldAge.getText()) <= 0) {
            txtFldAge.setStyle(redStyle);
            fldAge.playAnim();
            clearSupportTextFields();
        }else txtFldAge.setStyle(silverStyle);
        if(menuFormula.getValue().equals(Const.FORMULA_KETCH)){
            if (txtFldFat.getText() == null
                    || txtFldFat.getText().trim().isEmpty()
                    || Double.parseDouble(txtFldFat.getText()) <= 0) {
                txtFldFat.setStyle(redStyle);
                fldFat.playAnim();
                clearSupportTextFields();
            }else txtFldFat.setStyle(silverStyle);
        }
        return false;
    }

    private void setupImbTable() {
        columnHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
        column1.setCellValueFactory(new PropertyValueFactory<>("col_1"));
        column2.setCellValueFactory(new PropertyValueFactory<>("col_2"));
        column3.setCellValueFactory(new PropertyValueFactory<>("col_3"));
        column4.setCellValueFactory(new PropertyValueFactory<>("col_4"));
        column5.setCellValueFactory(new PropertyValueFactory<>("col_5"));
        column6.setCellValueFactory(new PropertyValueFactory<>("col_6"));
        column7.setCellValueFactory(new PropertyValueFactory<>("col_7"));
        column8.setCellValueFactory(new PropertyValueFactory<>("col_8"));
        columnImb.setCellValueFactory(new PropertyValueFactory<>("imb"));

        tableImbData.add(new IndexMassBody("Вес,кг", null, null, null, null, null, null, null, null, "ИМТ"));
        tableImbData.add(new IndexMassBody("", 40, 43, 46, 49, 52, 55, 58, 62, "18"));
        tableImbData.add(new IndexMassBody("", 43, 46, 49, 52, 52, 58, 62, 65, "19"));
        tableImbData.add(new IndexMassBody("", 45, 48, 51, 54, 52, 61, 65, 68, "20"));
        tableImbData.add(new IndexMassBody("", 47, 50, 54, 57, 52, 64, 68, 72, "21"));
        tableImbData.add(new IndexMassBody("", 50, 53, 56, 60, 52, 67, 71, 75, "22"));
        tableImbData.add(new IndexMassBody("", 52, 55, 59, 63, 52, 70, 75, 79, "23"));
        tableImbData.add(new IndexMassBody("", 54, 57, 61, 65, 52, 73, 78, 82, "24"));
        tableImbData.add(new IndexMassBody("", 56, 60, 64, 68, 52, 77, 81, 86, "25"));
        tableImbData.add(new IndexMassBody("", 63, 67, 72, 76, 52, 86, 91, 96, "28"));
        tableImbData.add(new IndexMassBody("", 67, 72, 77, 82, 52, 92, 97, 103, "30"));
        tableImbData.add(new IndexMassBody("", 80, 84, 90, 95, 52, 107, 113, 120, "35"));
        tableImbData.add(new IndexMassBody("", 90, 96, 102, 109, 116, 122, 130, 137, "40"));

        tableViewIMB.setItems(tableImbData);
    }

    private void clearImbTextFields() {
        txtFldWeightImb.clear();
        txtFldHeightImb.clear();
        txtFldResultImb.clear();
    }

    private boolean shakeTextImbFields() {
        Shaker fldWeight = new Shaker(txtFldWeightImb);
        Shaker fldHeight = new Shaker(txtFldHeightImb);
        String redStyle = "-fx-border-color: red; -fx-border-radius: 3; -fx-text-fill: black;";
        String silverStyle ="-fx-border-color: silver; -fx-border-radius: 3; -fx-text-fill: black;";
        if (txtFldWeightImb.getText() == null || txtFldWeightImb.getText().trim().isEmpty()
                || Double.parseDouble(txtFldWeightImb.getText()) <= 0) {
            txtFldWeightImb.setStyle(redStyle);
            fldWeight.playAnim();
            txtFldResultImb.clear();
        } else txtFldWeightImb.setStyle(silverStyle);

        if (txtFldHeightImb.getText() == null || txtFldHeightImb.getText().trim().isEmpty()
                || Double.parseDouble(txtFldHeightImb.getText()) <= 0) {
            txtFldHeightImb.setStyle(redStyle);
            fldHeight.playAnim();
            txtFldResultImb.clear();
        } else txtFldHeightImb.setStyle(silverStyle);
        return false;
    }

    private void resultImb() {
        double weight = Double.parseDouble(txtFldWeightImb.getText());
        double height = Double.parseDouble(txtFldHeightImb.getText());
        double resultImb = weight / ((height / 100) * (height / 100));
        txtFldResultImb.setText(EMPTY + (Math.round(resultImb * 100d) / 100d));
    }

    private void openNewProductWindow(Button btn, String productWindow, String windowTitle) {
        btn.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(productWindow));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
//            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(windowTitle);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
    }
}