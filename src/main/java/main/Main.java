package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Collections;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/calcWindow.fxml"));
        stage.setTitle("Калоризатор");
        Scene cal = new Scene(root);
        stage.setScene(cal);
        stage.setResizable(false);
        stage.setAlwaysOnTop(false);

        javafx.scene.image.Image image = new javafx.scene.image.Image("griff.png");
        ObservableList<javafx.scene.image.Image> images = FXCollections.observableArrayList(Collections.singletonList(image));
        stage.getIcons().add(images.get(0));

        stage.show();
        stage.setOnCloseRequest(event -> {
            Platform.exit();System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
