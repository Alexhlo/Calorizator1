package sample;

import java.sql.*;

public class SQLiteClient {

    public static Connection connection;
    private static Statement statement;
    public static ResultSet resultSet;

    public static void connectDB() throws ClassNotFoundException, SQLException {
        connection = null;
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

//    public static void addIntoDB() {
//
//    }
//
//    public static void deleteFromDB() {
//
//    }
//
//    public static void readDB(String request , TableView<Product> tableView, ObservableList<Product> observableList) {
//        try {
//           resultSet = connection.createStatement().executeQuery(request);
//            while (resultSet.next()) {
//                Product product = new Product();
//                product.name.set(SQLiteClient.resultSet.getString(Const.TABLE_NAME));
//                product.protein.set(SQLiteClient.resultSet.getDouble(Const.TABLE_PROTEIN));
//                product.fats.set(SQLiteClient.resultSet.getDouble(Const.TABLE_FATS));
//                product.carbs.set(SQLiteClient.resultSet.getDouble(Const.TABLE_CARBS));
//                product.calories.set(SQLiteClient.resultSet.getInt(Const.TABLE_CALORIES));
//                observableList.add(product);
//                tableView.setItems(observableList);
//            }
//            System.out.println("-----------------=Таблица выведена=-----------------");
//
////            SQLiteClient.closeDB();
//        } catch (SQLException e) {
//            e.getStackTrace();
//        }
//    }
}




