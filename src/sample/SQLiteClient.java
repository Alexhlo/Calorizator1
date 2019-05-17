package sample;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import pojo.Product;
import sample.controllers.ProductCalControl;

import java.sql.*;

public class SQLiteClient {

    private static String HOST = "jdbc:sqlite:Calorifier.db";
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void connectDB() throws ClassNotFoundException, SQLException {
        connection = null;
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(HOST);
        statement = connection.createStatement();
        System.out.println("Database connection has been done.");
    }

    public static void closeDB() throws NullPointerException, SQLException {
        statement.close();
        resultSet.close();
        connection.close();
        if(resultSet.isClosed()){
            System.out.println("Database result set close.");
        }
        if (statement.isClosed()) {
            System.out.println("Database statement close");
        }
    }

    public static void addIntoDB() {

    }

    public static void deleteFromDB() {

    }

    public static void readDB(String request , TableView<Product> tableView, ObservableList<Product> observableList) {
        try {
           resultSet = connection.createStatement().executeQuery(request);
            while (resultSet.next()) {
                Product product = new Product();
                product.name.set(SQLiteClient.resultSet.getString(Const.TABLE_NAME));
                product.protein.set(SQLiteClient.resultSet.getDouble(Const.TABLE_PROTEIN));
                product.fats.set(SQLiteClient.resultSet.getDouble(Const.TABLE_FATS));
                product.carbs.set(SQLiteClient.resultSet.getDouble(Const.TABLE_CARBS));
                product.calories.set(SQLiteClient.resultSet.getInt(Const.TABLE_CALORIES));
                observableList.add(product);
                tableView.setItems(observableList);
            }

            SQLiteClient.closeDB();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }
}




