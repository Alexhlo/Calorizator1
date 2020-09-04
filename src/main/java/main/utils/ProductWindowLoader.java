package main.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductWindowLoader {

    public static void openNewProductWindow(Button btn, String productWindow, String windowTitle, boolean modality) {

        btn.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ProductWindowLoader.class.getResource(productWindow));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            if (modality) {
                stage.initModality(Modality.APPLICATION_MODAL);
            }
            stage.setTitle(windowTitle);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
    }
}
