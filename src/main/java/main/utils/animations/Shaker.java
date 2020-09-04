package main.utils.animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.util.List;

public class Shaker {

    private TranslateTransition translateTransition;

    public Shaker(Node node) {

            translateTransition = new TranslateTransition(Duration.millis(70), node);
            translateTransition.setFromX(0f); // отступ от Х = 0;
            translateTransition.setByX(10f); // насколько он передвинется относительно его позиции = 10 юнитов
            translateTransition.setCycleCount(4); // 4 раза будет трястись
            translateTransition.setAutoReverse(true);//когда мы перетаскиваем поле на 10 единиц вправо, то что бы оно возвращалось
    }

    public void playAnim(){
        translateTransition.playFromStart();
    }
}
