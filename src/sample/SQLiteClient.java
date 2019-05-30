package sample;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import pojo.Product;

import java.sql.*;

public class SQLiteClient {

    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;

    public static void connectDB() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        String host = "jdbc:sqlite:Calorifier.db";
        connection = DriverManager.getConnection(host);
        statement = connection.createStatement();
        System.out.println("Database connection has been done.");
    }

    public static void closeDB() throws NullPointerException, SQLException {
        statement.close();
        resultSet.close();
        connection.close();
        if(resultSet.isClosed() && statement.isClosed() && connection.isClosed()){
            System.out.println("---= Database RS,STMNT,CON is closed! =---");
        }
    }

    public static void executeTableFromDB (String table,  ObservableList<Product> obsList){
        try {
            connectDB();
            resultSet = connection.createStatement().executeQuery("SELECT * FROM " + table);
            while (resultSet.next()){
                Product product = new Product();
                product.id.set(resultSet.getInt(Const.TABLE_ID));
                product.name.set(resultSet.getString(Const.TABLE_NAME));
                product.protein.set(resultSet.getDouble(Const.TABLE_PROTEIN));
                product.fats.set(resultSet.getDouble(Const.TABLE_FATS));
                product.carbs.set(resultSet.getDouble(Const.TABLE_CARBS));
                product.calories.set(resultSet.getInt(Const.TABLE_CALORIES));
                obsList.add(product);
            }
            System.out.println("-----------------=Таблица выведена=-----------------");
        }catch (SQLException | ClassNotFoundException e){
            e.getStackTrace();
        }
    }

    public static void addLineToTableDB(String table, TextField name, TextField protein, TextField fat, TextField carb, TextField cal){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO " + table + " (name,protein,fat,carb,cal,weight) VALUES (?, ?, ?, ?, ?, 100)");
            preparedStatement.setString(1,name.getText());
            preparedStatement.setString(2,protein.getText());
            preparedStatement.setString(3,fat.getText());
            preparedStatement.setString(4,carb.getText());
            preparedStatement.setString(5,cal.getText());
            preparedStatement.executeUpdate();
            name.clear();
            protein.clear();
            fat.clear();
            carb.clear();
            cal.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeLineFromTableDB (TableView<Product> tableView ,String table) {
        try {
            int selectedIndex = tableView.getSelectionModel().getSelectedItem().getId();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + table + " WHERE id = " + selectedIndex );
            preparedStatement.executeUpdate();
        } catch (Exception e) {
                    e.printStackTrace();
        }
    }

    public static void editLineFromTableDB (String table, TableView<Product> tableView, String newValue){
        try {
            int selectedCell = tableView.getSelectionModel().getSelectedItem().getId();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE " + table + " SET name = ? WHERE id = " + selectedCell);

            preparedStatement.setString(1,newValue);
            preparedStatement.executeUpdate();
            System.out.println("id = " + selectedCell + " изменено на " + newValue);
        }catch (SQLException e){
            System.out.println("Error");
            e.printStackTrace(System.err);
        }
    }
}




