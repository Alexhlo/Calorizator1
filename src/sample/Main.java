package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//    https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/table-view.htm#CJAGAAEE%23CJAGAAEE
//    http://qaru.site/questions/1548323/javafx-table-cell-editing
//    https://code.makery.ch/ru/library/javafx-tutorial/
//    http://qaru.site/questions/13757619/right-click-event-and-double-click-event-on-tableview-javafx
//    https://o7planning.org/ru/10973/java-basic

public class Main extends Application {
    @Override
    public void start(Stage caloStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../views/calcWindow.fxml"));
        caloStage.setTitle("Калоризатор");
        Scene calorizator = new Scene(root);
        caloStage.setScene(calorizator);
        caloStage.setResizable(false);
        caloStage.show();
        caloStage.setOnCloseRequest(event -> { Platform.exit();System.exit(0);});
    }

    public static void main(String[] args) {
        launch(args);
    }
}
