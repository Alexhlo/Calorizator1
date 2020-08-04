package main.utils;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import main.entity.Product;
import main.entity.enums.ProductColumnName;

import java.sql.*;

public class SQLiteClient {

    private final static String JDBC_CLASS_NAME = "org.sqlite.JDBC";
    private final static String HOST = "jdbc:sqlite:Calorifier.db";

    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;

    public static void connectDB() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_CLASS_NAME);
        connection = DriverManager.getConnection(HOST);
        statement = connection.createStatement();
        System.out.println("Database connection has been done.");
    }

    public static void closeDB() throws NullPointerException, SQLException {
        statement.close();
        resultSet.close();
        connection.close();
        if (resultSet.isClosed() && statement.isClosed() && connection.isClosed()) {
            System.out.println("---= Database closed! =---");
        }
    }

    public static void executeTableFromDB (String table,  ObservableList<Product> obsList) {
        //TODO ПЕРЕИМЕНОВАТЬ ПОД ЕДИНЫЙ СТАНДАРТ НАИМЕНОВАНИЯ КОЛОНОК
        try {
            connectDB();
            resultSet = connection.createStatement().executeQuery("SELECT * FROM " + table);
            while (resultSet.next()) {
                Product product = new Product();
                product.id.set(resultSet.getInt(ProductColumnName.COLUMN_ID.getName()));
                product.name.set(resultSet.getString(ProductColumnName.COLUMN_NAME.getName()));
                product.protein.set(resultSet.getDouble(ProductColumnName.COLUMN_PROTEIN.getName()));
                product.fats.set(resultSet.getDouble("fat")); //должно быть fats
                product.carbs.set(resultSet.getDouble("carb")); //должно быть carbs
                product.calories.set(resultSet.getInt("cal")); //должно быть calories
                obsList.add(product);
            }
            System.out.println("-----------------=Таблица выведена=-----------------");
        } catch (SQLException | ClassNotFoundException e) {
            e.getStackTrace();
        }
    }

    public static void addLineToTableDB(String table, String name, String protein, String fats, String carbs, String calories) {

        try {
            String request = String.format(
                    "INSERT INTO %s (name, protein, fat, carb, cal, weight) VALUES (%s, %s, %s, %s, %s, %s)",
                    table, name, protein, fats, carbs, calories, 100);
            PreparedStatement preparedStatement = connection.prepareStatement(request);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void removeLineFromTableDB (TableView<Product> tableView, String table) {

        try {
            int selectedIndex = tableView.getSelectionModel().getSelectedItem().getId();
            String request = String.format("DELETE FROM %s WHERE id = %s", table, selectedIndex);
            PreparedStatement preparedStatement = connection.prepareStatement(request);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void editNameFromTableDB(String table, TableView<Product> tableView, String newNameValue) {

        try {
            int selectedCell = tableView.getSelectionModel().getSelectedItem().getId();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE " + table + " SET name = ? WHERE id = " + selectedCell);
            preparedStatement.setString(1, newNameValue);
            preparedStatement.executeUpdate();
            System.out.println("id = " + selectedCell + " изменено на " + newNameValue);
        } catch (SQLException e){
            System.out.println("Error");
            e.printStackTrace(System.err);
        }
    }
    public static void editProteinFromTableDB(String table, TableView<Product> tableView, double newProteinValue) {

        try {
            int selectedCell = tableView.getSelectionModel().getSelectedItem().getId();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE " + table + " SET protein = ? WHERE id = " + selectedCell);
            preparedStatement.setDouble(1, newProteinValue);
            preparedStatement.executeUpdate();
            System.out.println("id = " + selectedCell + " изменено на " + newProteinValue);
        } catch (SQLException e){
            System.out.println("Error");
            e.printStackTrace(System.err);
        }
    }
    public static void editFatsFromTableDB(String table, TableView<Product> tableView, double newFatsValue) {

        try {
            int selectedCell = tableView.getSelectionModel().getSelectedItem().getId();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE " + table + " SET fat = ? WHERE id = " + selectedCell);
            preparedStatement.setDouble(1, newFatsValue);
            preparedStatement.executeUpdate();
            System.out.println("id = " + selectedCell + " изменено на " + newFatsValue);
        } catch (SQLException e){
            System.out.println("Error");
            e.printStackTrace(System.err);
        }
    }
    public static void editCarbsFromTableDB(String table, TableView<Product> tableView, double newCarbsValue) {

        try {
            int selectedCell = tableView.getSelectionModel().getSelectedItem().getId();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE " + table + " SET carb = ? WHERE id = " + selectedCell);
            preparedStatement.setDouble(1, newCarbsValue);
            preparedStatement.executeUpdate();
            System.out.println("id = " + selectedCell + " изменено на " + newCarbsValue);
        } catch (SQLException e){
            System.out.println("Error");
            e.printStackTrace(System.err);
        }
    }
    public static void editCaloriesFromTableDB(String table, TableView<Product> tableView, int newCalValue) {

        try {
            int selectedCell = tableView.getSelectionModel().getSelectedItem().getId();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE " + table + " SET cal = ? WHERE id = " + selectedCell);
            preparedStatement.setDouble(1, newCalValue);
            preparedStatement.executeUpdate();
            System.out.println("id = " + selectedCell + " изменено на " + newCalValue);
        } catch (SQLException e){
            System.out.println("Error");
            e.printStackTrace(System.err);
        }
    }


    public static void createNewTable(String tableName) {

        try {
            connectDB();
            statement = connection.createStatement();
            String createTable = String.format(
                    "CREATE TABLE if not exists %s ('id' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "'name' STRING, 'protein' DOUBLE, 'fat' DOUBLE, 'carb' DOUBLE, 'cal' INTEGER, 'weight' INTEGER)", tableName);
            statement.execute(createTable);
            System.out.println(String.format("Table with name %s has created!", tableName));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void insertDataInTableFromTable (TableView<Product> tableView, String newTab, String oldTab) {

        try {
            int selectedCell = tableView.getSelectionModel().getSelectedItem().getId();
            String insertion =
                    "INSERT INTO " + newTab + " (name, protein, fat, carb, cal, weight) " +
                            "SELECT name, protein, fat, carb, cal, weight FROM " + oldTab + " WHERE id = " + selectedCell;
            PreparedStatement preparedStatement = connection.prepareStatement(insertion);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteTableFromDB (String tableName) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE " + tableName);
            preparedStatement.executeUpdate();
            System.out.println(String.format("Table with name %s has dropped!", tableName));
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}




