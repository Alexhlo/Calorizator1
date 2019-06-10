package main.java.animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shaker {
    private TranslateTransition tt;

    public Shaker(Node node){
        tt = new TranslateTransition(Duration.millis(70), node);
        tt.setFromX(0f); // отступ от Х = 0;
        tt.setByX(10f); // насколько он передвинется относительно его позиции = 10 юнитов
        tt.setCycleCount(4); // 4 раза будет трястись
        tt.setAutoReverse(true);

        //когда мы перетаскиваем поле на 10 единиц вправо, то что бы оно вовзращалось
    }

    public void playAnim(){
        tt.playFromStart();
    }
}
