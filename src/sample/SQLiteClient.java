package sample;

import javafx.collections.ObservableList;
import pojo.Product;

import java.sql.*;

public class SQLiteClient {

    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;

    private static void connectDB() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        String host = "jdbc:sqlite:Calorifier.db";
        connection = DriverManager.getConnection(host);
        statement = connection.createStatement();
        System.out.println("Database connection has been done.");
    }

    private static void closeDB() throws NullPointerException, SQLException {
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

    public static void addLineToTableDB(String table, String name, double protein, double fat, double carb, int cal){
        String z = ",";
        try {
            statement.addBatch(
                    "INSERT INTO " + table +" (name, protein, fat, carb, cal, weight) VALUES (" + name + z + protein + z + fat + z + carb + z + cal +", 100)");
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeLineFromTableDB (String table, int id) {
        try {
            PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM " + table + " WHERE id = " + id );
            prepStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}




