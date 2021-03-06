package main.java.sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage caloStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/calcWindow.fxml"));
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
