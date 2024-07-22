package com.example.bomberman.object;

import com.example.bomberman.constants.GlobalConstant;
import javafx.util.Duration;

public class Portal extends UpdatableObject {
    private static final int PORTAL_COUNT = 2;
    private static final int DURATION_PORTAL_ANIMATION = 200;

    public Portal(int x, int y) {
        super(null, x, y, GlobalConstant.TILE_SIZE, GlobalConstant.TILE_SIZE);
        animation.setStr("asset/portal/portal");
        animation.setCount(PORTAL_COUNT);
        animation.setDuration(Duration.millis(DURATION_PORTAL_ANIMATION));
        animation.play();
    }

}
