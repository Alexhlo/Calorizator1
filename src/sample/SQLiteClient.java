package sample;

import pojo.Product;
import sample.controllers.ProductCalControl;

import java.sql.*;

public class SQLiteClient {

    private static String HOST = "jdbc:sqlite:Calorifier.db";

    public static Connection connection;
    public static Statement statement;
    public static ResultSet resultSet;

    public static void connectDB() throws ClassNotFoundException ,SQLException{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(HOST);
        statement = connection.createStatement();
//        System.out.println("Database connection has been done.");
    }

    public static void closeDB() throws NullPointerException , SQLException {
        connection.close();
        statement.close();
        resultSet.close();
        System.out.println("Database connection close.");
    }

    public static void addIntoDB(String request){

    }

    public static void deleteFromDB(String request){

    }

    public static void readDB(String request, Product product, ProductCalControl productCalControl) throws SQLException {

    }


}

