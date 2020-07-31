package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/calcWindow.fxml"));
        stage.setTitle("Калоризатор");
        Scene cal = new Scene(root);
        stage.setScene(cal);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(event -> { Platform.exit();System.exit(0);});
    }

    public static void main(String[] args) {
        launch(args);
    }
}
