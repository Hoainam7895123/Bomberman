package com.example.bomberman.general;

import com.example.bomberman.constants.GlobalConstant;
import com.example.bomberman.game.GameLoop;
import com.example.bomberman.screens.ScreenManager;
import com.example.bomberman.screens.ScreenType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Sandbox {
    private static Stage stage;
    private static Scene scene;
    private static Group root;
    private static Canvas canvas;
    private static GraphicsContext gc;

    public static void init(Stage stage) {
        Sandbox.stage = stage;
        root = new Group();
        canvas = new Canvas(GlobalConstant.SCREEN_WIDTH, GlobalConstant.SCREEN_HEIGHT);
        root.getChildren().add(canvas);
        scene = new Scene(root);
        gc = canvas.getGraphicsContext2D();
        ScreenManager.switchScreen(ScreenType.HOME_SCREEN);
        Input.attachEventHandle(scene);
        GameLoop.start();
    }

    public static Scene getScene() {
        return scene;
    }

    public static GraphicsContext getGc() {
        return gc;
    }

    public static void closeStage() {
        stage.close();
    }
}
