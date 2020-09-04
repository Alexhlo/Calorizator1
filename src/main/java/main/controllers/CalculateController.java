package main.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.entity.IndexMassBody;
import main.entity.Product;
import main.entity.enums.WindowMenuName;
import main.entity.enums.WindowPath;
import main.service.VerifyService;
import main.service.impls.VerifyServiceImpl;
import main.utils.PopupMenu;
import main.utils.ProductWindowLoader;
import main.utils.SQLiteClient;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

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

    /**
     * Вкладка продукты
     */
    @FXML public Button btnBurgerKing;
    @FXML public Button btnKFC;
    @FXML public Button btnMcDonalds;
    @FXML public Button btnAlcohol;
    @FXML public Button btnUnAlcohol;
    @FXML public Button btnMushroom;
    @FXML public Button btnBabyFood;
    @FXML public Button btnSausages;
    @FXML public Button btnCereals;
    @FXML public Button btnOilFats;
    @FXML public Button btnDairy;
    @FXML public Button btnFlourProducts;
    @FXML public Button btnMeatProducts;
    @FXML public Button btnVegetable;
    @FXML public Button btnNuts;
    @FXML public Button btnFirstCourse;
    @FXML public Button btnSeaFood;
    @FXML public Button btnSalad;
    @FXML public Button btnSweets;
    @FXML public Button btnSnacks;
    @FXML public Button btnJuice;
    @FXML public Button btnSportNutrition;
    @FXML public Button btnCondiments;
    @FXML public Button btnFruits;
    @FXML public Button btnBakery;
    @FXML public Button btnBerries;
    @FXML public Button btnEggs;
    @FXML public Button btnJapanFood;
    @FXML public Button btnSearchAll;

    /**
     * Вкладка рацион
     */
    @FXML public VBox rationVBox;
    @FXML public AnchorPane rationAnchorPane;

    private final ToggleGroup TOGGLE_GROUP_GENDER = new ToggleGroup();
    private final ToggleGroup TOGGLE_GROUP_COA = new ToggleGroup();
    private double RESULT;
    private static final String EMPTY = "";
    public ObservableList<Product> tableMealData = observableArrayList();
    public final ObservableList<TableView<Product>> tableViewList = FXCollections.observableArrayList(
            new TableView<>(), new TableView<>(), new TableView<>(), new TableView<>(), new TableView<>());
    public static final String FORMULA_MIFFLIN = "Формула Миффлина-Сан Жеора (2005г)";
    public static final String FORMULA_HARRISON = "Формула Гарриса-Бенедикта (ВОО на основе общей массы тела)";
    public static final String FORMULA_KETCH = "Формула Кетча-МакАрдла (ВОО на основе мышечной массы тела)";
    public static final String SILVER_STYLE ="-fx-border-color: silver; -fx-border-radius: 3; -fx-text-fill: black;";
    public static final double MUSCLE_MASS = 500;
    public static final double WEIGHT_LOSS = 300;
    public static final int HUNDRED = 100;

    private int mealCount = 0;
    private String mealName = "Meal_";

    @FXML public void initialize(URL location, ResourceBundle resources) {

        VerifyService verifyService = new VerifyServiceImpl();
        PopupMenu popupMenu = new PopupMenu();

        List<TextField> textFieldList = Arrays.asList(txtFldWeight, txtFldHeight, txtFldAge, txtFldFat);
        List<TextField> imbTextFieldList = Arrays.asList(txtFldWeightImb, txtFldHeightImb);

        Platform.runLater(() -> menuFormula.requestFocus());
        menuFormula.getItems().addAll(FORMULA_MIFFLIN, FORMULA_HARRISON, FORMULA_KETCH);
        menuFormula.setValue(FORMULA_MIFFLIN);
        menuFormulaAction();

        setToggleGroupsRadioButton();
        tableViewIMB = setupImbTable();

        popupMenu.popupTableAction(rationVBox);

        createMealTable(popupMenu);
        deleteMealTable(popupMenu);
        refreshMealTable(popupMenu);
        setupTableMealColumns();

        btnCancel.setOnAction(event ->  clearAllTextFields());
        btnCancelImb.setOnAction(event -> clearImbTextFields());

        btnCalc.setOnAction(event -> {
            if (!verifyService.shakeTextFields(textFieldList)) conditionMenuFormulaBMRCoA();
        });

        btnCalcImb.setOnAction(event -> {
            if (!verifyService.shakeTextFields(imbTextFieldList)) {
                double weight = Double.parseDouble(txtFldWeightImb.getText());
                double height = Double.parseDouble(txtFldHeightImb.getText());
                double resultImb = weight / ((height / HUNDRED) * (height / HUNDRED));
                txtFldResultImb.setText(EMPTY + (Math.round(resultImb * (double) HUNDRED) / (double) HUNDRED));
            }
        });

        ProductWindowLoader.openNewProductWindow(btnBurgerKing, WindowPath.BURGER_KING_WINDOW.getPath(), WindowMenuName.BURGER_KING.getName(), false);
    }

    private void setupTableMealColumns() {

        TableColumn<Product, Integer> tableColId = new TableColumn<>("id");
        TableColumn<Product, String> tabColName = new TableColumn<>("Наименование");
        TableColumn<Product, Double> tabColProtein = new TableColumn<>("Белки, г");
        TableColumn<Product, Double> tableColFat = new TableColumn<>("Жиры, г");
        TableColumn<Product, Double> tableColCarbs = new TableColumn<>("Углеводы, г");
        TableColumn<Product, Integer> tableColCal = new TableColumn<>("Калории");
        TableColumn<Product, Integer> tableColWeight = new TableColumn<>("Вес");

        tableColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tabColProtein.setCellValueFactory(new PropertyValueFactory<>("protein"));
        tableColFat.setCellValueFactory(new PropertyValueFactory<>("fats"));
        tableColCarbs.setCellValueFactory(new PropertyValueFactory<>("carbs"));
        tableColCal.setCellValueFactory(new PropertyValueFactory<>("calories"));
        tableColWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));

        tableViewList.get(mealCount).getSelectionModel().setCellSelectionEnabled(true);
        tableViewList.get(mealCount).setEditable(true);
        tableViewList.get(mealCount).getColumns().addAll(tableColId, tabColName, tabColProtein, tableColFat, tableColCarbs, tableColCal, tableColWeight);
        tableViewList.get(mealCount).setItems(tableMealData);
    }

    private void deleteMealTable(PopupMenu popupMenu) {

        popupMenu.deleteTable.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Удалить таблицу?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Внимание!");
            alert.setHeaderText("Если вы удалите таблицу, вернуть её обратно уже будет нельзя!");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.orElse(null) == ButtonType.YES) {
                SQLiteClient.deleteTableFromDB(mealName);
                refreshTable(mealName, tableMealData);
            } else {
                alert.close();
            }
        });
    }

    private void createMealTable(PopupMenu popupMenu) {

        popupMenu.createTable.setOnAction(event -> {
            mealName = String.format("%s%s", mealName, ++mealCount);
            rationVBox.getChildren().addAll(tableViewList.get(mealCount));
            if (rationVBox.getChildren().size() < tableViewList.size()) {
                SQLiteClient.createNewTable(mealName);
                SQLiteClient.executeTableFromDB(String.format("%s%s", mealName, mealCount), tableMealData);
            }
        });
    }

    private void refreshMealTable(PopupMenu popupMenu) {
        popupMenu.refresh.setOnAction(event -> refreshTable(mealName, tableMealData));
    }

    private void refreshTable(String tableName, ObservableList<Product> tableData) {

        tableMealData.clear();
        SQLiteClient.executeTableFromDB(tableName, tableData);
    }

    private void menuFormulaAction() {

        apCalcKCal.setOnMouseEntered(event -> {
            if (menuFormula.getValue().equals(FORMULA_MIFFLIN)) {
                txtFldFat.clear();
                txtFldFat.setDisable(true);
            }
            if (menuFormula.getValue().equals(FORMULA_HARRISON)) {
                txtFldFat.clear();
                txtFldFat.setDisable(true);
            }
            if (menuFormula.getValue().equals(FORMULA_KETCH)) {
                txtFldFat.setDisable(false);
            }});
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

        if (menuFormula.getValue().equals(FORMULA_MIFFLIN)) {
            if (rbMale.isSelected()) {
                RESULT = 10 * weight + 6.25 * height - 5 * age + 5;
            } else  {
                RESULT = 10 * weight + 6.25 * height - 5 * age - 161;
            }
            txtFldBMR.setText(EMPTY + Math.rint(RESULT));
            conditionCoA();
            txtFldBKA.setText(EMPTY + Math.rint(RESULT));
            txtFldMM.setText(EMPTY + (Math.rint(RESULT) + MUSCLE_MASS));
            txtFldWL.setText(EMPTY + (Math.rint(RESULT) - WEIGHT_LOSS));
        }
        if (menuFormula.getValue().equals(FORMULA_HARRISON)) {
            if (rbMale.isSelected()) {
                RESULT = 66 + (13.7 * weight) + (5 * height) - (6.8 * age);
            } else {
                RESULT = 655 + (9.6 * weight) + (1.8 * height) - (4.7 * age);
            }
            txtFldBMR.setText(EMPTY + Math.rint(RESULT));
            conditionCoA();
            txtFldBKA.setText(EMPTY + Math.rint(RESULT));
            txtFldMM.setText(EMPTY + (Math.rint(RESULT) + MUSCLE_MASS));
            txtFldWL.setText(EMPTY + (Math.rint(RESULT) - WEIGHT_LOSS));
        }
        if (menuFormula.getValue().equals(FORMULA_KETCH)) {
            double fat = Double.parseDouble(txtFldFat.getText());
            RESULT = 370 + (21.6 * (weight - (weight * fat / HUNDRED)));
            txtFldBMR.setText(EMPTY + Math.rint(RESULT));
            conditionCoA();
            txtFldBKA.setText(EMPTY + Math.rint(RESULT));
            txtFldMM.setText(EMPTY + (Math.rint(RESULT) + MUSCLE_MASS));
            txtFldWL.setText(EMPTY + (Math.rint(RESULT) - WEIGHT_LOSS));
        }
    }

    private TableView<IndexMassBody> setupImbTable() {

        ObservableList<IndexMassBody> tableImbData = observableArrayList();

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
        tableImbData.add(new IndexMassBody(EMPTY, 40, 43, 46, 49, 52, 55, 58, 62, "18"));
        tableImbData.add(new IndexMassBody(EMPTY, 43, 46, 49, 52, 52, 58, 62, 65, "19"));
        tableImbData.add(new IndexMassBody(EMPTY, 45, 48, 51, 54, 52, 61, 65, 68, "20"));
        tableImbData.add(new IndexMassBody(EMPTY, 47, 50, 54, 57, 52, 64, 68, 72, "21"));
        tableImbData.add(new IndexMassBody(EMPTY, 50, 53, 56, 60, 52, 67, 71, 75, "22"));
        tableImbData.add(new IndexMassBody(EMPTY, 52, 55, 59, 63, 52, 70, 75, 79, "23"));
        tableImbData.add(new IndexMassBody(EMPTY, 54, 57, 61, 65, 52, 73, 78, 82, "24"));
        tableImbData.add(new IndexMassBody(EMPTY, 56, 60, 64, 68, 52, 77, 81, 86, "25"));
        tableImbData.add(new IndexMassBody(EMPTY, 63, 67, 72, 76, 52, 86, 91, 96, "28"));
        tableImbData.add(new IndexMassBody(EMPTY, 67, 72, 77, 82, 52, 92, 97, 103, "30"));
        tableImbData.add(new IndexMassBody(EMPTY, 80, 84, 90, 95, 52, 107, 113, 120, "35"));
        tableImbData.add(new IndexMassBody(EMPTY, 90, 96, 102, 109, 116, 122, 130, 137, "40"));
        tableViewIMB.setItems(tableImbData);
        return tableViewIMB;
    }

    private void clearImbTextFields() {

        txtFldWeightImb.clear();
        txtFldWeightImb.setStyle(SILVER_STYLE);
        txtFldHeightImb.clear();
        txtFldHeightImb.setStyle(SILVER_STYLE);
        txtFldResultImb.clear();
    }

    private void clearAllTextFields() {

        txtFldWeight.clear();
        txtFldWeight.setStyle(SILVER_STYLE);
        txtFldHeight.clear();
        txtFldHeight.setStyle(SILVER_STYLE);
        txtFldAge.clear();
        txtFldAge.setStyle(SILVER_STYLE);
        txtFldFat.clear();
        txtFldFat.setStyle(SILVER_STYLE);
        txtFldBMR.clear();
        txtFldBKA.clear();
        txtFldMM.clear();
        txtFldWL.clear();
    }
}