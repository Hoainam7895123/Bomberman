package com.example.bomberman.object;

import com.example.bomberman.constants.GlobalConstant;
import javafx.scene.image.Image;

import java.util.List;

public class MovingObject extends UpdatableObject {

    protected float vel;

    protected float velX;

    protected float velY;

    public MovingObject(Image image, float x, float y) {
        super(image, x, y);
    }

    public MovingObject(Image image, float x, float y, int width, int height) {
        super(image, x, y, width, height);
    }

    public void update(float deltaTime, List<BaseObject> obstructingObjectList) {
        super.update();
        move(deltaTime, obstructingObjectList);
    }

    public void update(float deltatime, int[][] map) {
        super.update();
        move(deltatime, map);
    }

    private void move(float deltaTime, int[][] map) {
        int i = (int) ((x + width / 2) / GlobalConstant.TILE_SIZE);
        int j = (int) ((y + height / 2) / GlobalConstant.TILE_SIZE);
        if ((velX > 0) && (x + velX * deltaTime < i * GlobalConstant.TILE_SIZE || x + velX * deltaTime > i * GlobalConstant.TILE_SIZE && map[j][i + 1] == 0)
                || ((velX < 0) && (x + velX * deltaTime > i * GlobalConstant.TILE_SIZE || x + velX * deltaTime < i * GlobalConstant.TILE_SIZE && map[j][i - 1] == 0))) {

            x = x + velX * deltaTime;
            if (Math.abs(y - j * GlobalConstant.TILE_SIZE) < vel * deltaTime) {
                y = j * GlobalConstant.TILE_SIZE;
            } else if (y > j * GlobalConstant.TILE_SIZE) {
                y = y - vel * deltaTime;
            } else if (y < j * GlobalConstant.TILE_SIZE) {
                y = y + vel * deltaTime;
            }

        }
        if ((velY > 0) && (y + velY * deltaTime < j * GlobalConstant.TILE_SIZE || y + velY * deltaTime > j * GlobalConstant.TILE_SIZE && map[j + 1][i] == 0)
                || (velY < 0) && (y + velY * deltaTime > j * GlobalConstant.TILE_SIZE || y + velY * deltaTime < j * GlobalConstant.TILE_SIZE && map[j - 1][i] == 0)) {
            y = y + velY * deltaTime;
            if (Math.abs(x - i * GlobalConstant.TILE_SIZE) < vel * deltaTime) {
                x = i * GlobalConstant.TILE_SIZE;
            } else if (x > i * GlobalConstant.TILE_SIZE) {
                x = x - vel * deltaTime;
            } else if (x < i * GlobalConstant.TILE_SIZE) {
                x = x + vel * deltaTime;
            }
        }
    }

    private void move(float deltaTime, List<BaseObject> obstructingObjectList) {
        int i = (int) ((x + width / 2) / GlobalConstant.TILE_SIZE);
        int j = (int) ((y + height / 2) / GlobalConstant.TILE_SIZE);
        int[][] map = new int[1000][1000];
        for (BaseObject object : obstructingObjectList) {
            map[(int) ((object.y + object.height / 2) / GlobalConstant.TILE_SIZE)][(int) ((object.x + object.width / 2) / GlobalConstant.TILE_SIZE)] = 1;
        }
        if ((velX > 0) && (x + velX * deltaTime < i * GlobalConstant.TILE_SIZE || x + velX * deltaTime > i * GlobalConstant.TILE_SIZE && map[j][i + 1] == 0)
                || ((velX < 0) && (x + velX * deltaTime > i * GlobalConstant.TILE_SIZE || x + velX * deltaTime < i * GlobalConstant.TILE_SIZE && map[j][i - 1] == 0))) {

            x = x + velX * deltaTime;
            if (Math.abs(y - j * GlobalConstant.TILE_SIZE) < vel * deltaTime) {
                y = j * GlobalConstant.TILE_SIZE;
            } else if (y > j * GlobalConstant.TILE_SIZE) {
                y = y - vel * deltaTime;
            } else if (y < j * GlobalConstant.TILE_SIZE) {
                y = y + vel * deltaTime;
            }

        }
        if ((velY > 0) && (y + velY * deltaTime < j * GlobalConstant.TILE_SIZE || y + velY * deltaTime > j * GlobalConstant.TILE_SIZE && map[j + 1][i] == 0)
                || (velY < 0) && (y + velY * deltaTime > j * GlobalConstant.TILE_SIZE || y + velY * deltaTime < j * GlobalConstant.TILE_SIZE && map[j - 1][i] == 0)) {
            y = y + velY * deltaTime;
            if (Math.abs(x - i * GlobalConstant.TILE_SIZE) < vel * deltaTime) {
                x = i * GlobalConstant.TILE_SIZE;
            } else if (x > i * GlobalConstant.TILE_SIZE) {
                x = x - vel * deltaTime;
            } else if (x < i * GlobalConstant.TILE_SIZE) {
                x = x + vel * deltaTime;
            }
        }
    }
}
