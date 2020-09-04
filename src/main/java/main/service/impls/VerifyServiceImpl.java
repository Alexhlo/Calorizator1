package main.service.impls;

import javafx.scene.control.TextField;
import main.service.VerifyService;
import main.utils.animations.Shaker;

import java.util.List;
import java.util.Objects;

public class VerifyServiceImpl implements VerifyService {

    private static final String RED_STYLE = "-fx-border-color: red; -fx-border-radius: 3; -fx-text-fill: black;";
    private static final String SILVER_STYLE = "-fx-border-color: silver; -fx-border-radius: 3; -fx-text-fill: black;";

    @Override
    public boolean shakeTextFields(List<TextField> textFieldList) {

        for (TextField textField : textFieldList) {
            Shaker shaker = new Shaker(textField);

            if ((Objects.isNull(textField.getText())
                    || textField.getText().trim().isEmpty()
                    || Double.parseDouble(textField.getText()) <= 0)
                    && !textField.isDisable()) {

                textField.setStyle(RED_STYLE);
                shaker.playAnim();
            } else {
                textField.setStyle(SILVER_STYLE);
            }
        }

        return false;
    }

}
