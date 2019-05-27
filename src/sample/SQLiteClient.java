package sample;

import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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

    public static void addLineToTableDB(Button btn, String table, TextField name, TextField protein, TextField fat, TextField carb, TextField cal){
        String z = ",";
        btn.setOnAction(event -> {
            try {
                statement.addBatch(
                        "INSERT INTO " + table + " (name, protein, fat, carb, cal, weight) VALUES (" + name.getText() + z + protein.getText() + z + fat.getText() + z + carb.getText()+ z + cal.getText() + ", 100)");
                statement.executeBatch();
                name.clear();
                protein.clear();
                fat.clear();
                carb.clear();
                cal.clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }});
    }

    public static void removeLineFromTableDB (MenuItem delete,TableView tableView ,String table) {
        delete.setOnAction(event -> {
                try {
//                    TablePosition tabPos = tableView.getFocusModel().getFocusedCell();
                    int tabPos = tableView.getSelectionModel().getFocusedIndex();
                    PreparedStatement ps = connection.prepareStatement(
                            "DELETE FROM " + table + " WHERE id = " + tabPos);
                    ps.executeUpdate();
                    System.out.println(tabPos + (tabPos * 2));
                } catch (SQLException e) {
                    e.printStackTrace();
                } });
    }

    public static void editLineFromTableDB (MenuItem edit ,String table, TableColumn tabColName, TextField tablEditeName){
        edit.setOnAction(event -> {
            String z = ",";
            try {
                PreparedStatement ps = connection.prepareStatement(
                        "UPDATE " + table + " SET name = " + tablEditeName  + " WHERE name = " + tabColName.getText());
                ps.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
        });
    }
}




