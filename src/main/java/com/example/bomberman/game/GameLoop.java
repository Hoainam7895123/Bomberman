package com.example.bomberman.game;

import com.example.bomberman.screens.ScreenManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameLoop {
    static long deltaTime = System.nanoTime();

    public static void start() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(16), actionEvent -> {

            ScreenManager.getCurrentScreen().update((float) ((System.nanoTime() - deltaTime) / 1000000000.0));

            deltaTime = System.nanoTime();

            ScreenManager.getCurrentScreen().render();


        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
