package com.example.bomberman.object;

import com.example.bomberman.constants.GlobalConstant;
import com.example.bomberman.game.GameStatus;
import com.example.bomberman.general.Sandbox;
import javafx.scene.image.Image;
import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Map extends VisibleObject {

    private final int[][] a;

    public Map(String path, Image image) {
        super(image, 0, 0);
        File file = new File(path);
        try {
            Scanner sc = new Scanner(file);
            height = sc.nextInt();
            width = sc.nextInt();
            a = new int[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    a[i][j] = sc.nextInt();
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    public List<Wall> createWall() {
        List<Wall> wallList = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (a[i][j] == 1) {
                    wallList.add(new Wall(j * GlobalConstant.TILE_SIZE, i * GlobalConstant.TILE_SIZE));
//                    System.out.println(i + " " +  j);
                }
            }
        }
        return wallList;
    }

    public List<Root> createRoot() {
        List<Root> rootList = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (a[i][j] != 1 && !(i == 1 && j == 2) && !(i == 1 && j == 3) && !(i == 2 && j == 2)) {
                    int rand = (int) (Math.random() * 100 % 4);
                    if (rand == 0) {
                        rootList.add(new Root(j * GlobalConstant.TILE_SIZE, i * GlobalConstant.TILE_SIZE));
                        a[i][j] = 2;
                    }
                }
            }
        }
        return rootList;
    }

    public Portal createPortal() {
        List<Pair<Integer, Integer>> rootLocation = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (a[i][j] == 2) {
                    rootLocation.add(new Pair<>(i, j));
                }
            }
        }
        Collections.shuffle(rootLocation);
        Portal portal = new Portal(rootLocation.get(0).getValue() * GlobalConstant.TILE_SIZE, rootLocation.get(0).getKey() * GlobalConstant.TILE_SIZE);
        a[rootLocation.get(0).getKey()][rootLocation.get(0).getValue()] = 1;
        return portal;
    }

    public List<Item> createItem() {
        List<Pair<Integer, Integer>> rootLocation = new ArrayList<>();
        List<Item> itemList = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (a[i][j] == 2) {
                    rootLocation.add(new Pair<>(i, j));
                }
            }
        }

        int numOfBomUp = 0;
        int numOfSpeedUp = 0;
        int numOfFireUp = 0;
        int numOfLivesUp = 0;

        switch (GameStatus.getStage()) {
            case 1:
                numOfBomUp = 1;
                numOfFireUp = 1;
                break;
            case 2:
                numOfSpeedUp = 1;
                break;
            case 3:
                numOfSpeedUp = 1;
                numOfFireUp = 1;
                numOfLivesUp = 1;
                break;
            case 4:
                numOfBomUp = 1;
                break;
            case 5:
                numOfBomUp = 1;
                numOfSpeedUp = 1;
                numOfFireUp = 1;
                numOfLivesUp = 1;
                break;
        }

        for (int t = 0; t < numOfBomUp; t++) {
            Collections.shuffle(rootLocation);
            if (rootLocation.get(0).getValue() > 3 && rootLocation.get(0).getKey() > 3) {
                itemList.add(new Item(ItemType.BOM_UP, rootLocation.get(0).getValue() * GlobalConstant.TILE_SIZE, rootLocation.get(0).getKey() * GlobalConstant.TILE_SIZE));
                a[rootLocation.get(0).getKey()][rootLocation.get(0).getValue()] = 1;
                rootLocation.remove(rootLocation.get(0));
            } else {
                t--;
            }
        }

        for (int t = 0; t < numOfSpeedUp; t++) {
            Collections.shuffle(rootLocation);
            if (rootLocation.get(0).getValue() > 3 && rootLocation.get(0).getKey() > 3) {
                itemList.add(new Item(ItemType.SPEED_UP, rootLocation.get(0).getValue() * GlobalConstant.TILE_SIZE, rootLocation.get(0).getKey() * GlobalConstant.TILE_SIZE));
                a[rootLocation.get(0).getKey()][rootLocation.get(0).getValue()] = 1;
                rootLocation.remove(rootLocation.get(0));
            } else {
                t--;
            }
        }

        for (int t = 0; t < numOfFireUp; t++) {
            Collections.shuffle(rootLocation);
            if (rootLocation.get(0).getValue() > 3 && rootLocation.get(0).getKey() > 3) {
                itemList.add(new Item(ItemType.FIRE_UP, rootLocation.get(0).getValue() * GlobalConstant.TILE_SIZE, rootLocation.get(0).getKey() * GlobalConstant.TILE_SIZE));
                a[rootLocation.get(0).getKey()][rootLocation.get(0).getValue()] = 1;
                rootLocation.remove(rootLocation.get(0));
            } else {
                t--;
            }
        }

        for (int t = 0; t < numOfLivesUp; t++) {
            Collections.shuffle(rootLocation);
            if (rootLocation.get(0).getValue() > 3 && rootLocation.get(0).getKey() > 3) {
                itemList.add(new Item(ItemType.LIVES_UP, rootLocation.get(0).getValue() * GlobalConstant.TILE_SIZE, rootLocation.get(0).getKey() * GlobalConstant.TILE_SIZE));
                a[rootLocation.get(0).getKey()][rootLocation.get(0).getValue()] = 1;
                rootLocation.remove(rootLocation.get(0));
            } else {
                t--;
            }
        }
        return itemList;
    }

    public List<Enemy> createEnemy() {
        List<Pair<Integer, Integer>> grassLocation = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (a[i][j] == 0) {
                    grassLocation.add(new Pair<>(i, j));
                }
            }
        }
        List<Enemy> enemyList = new ArrayList<>();

        int numOfPuropen = 0;
        int numOfMetalPuropen = 0;
        int numOfNutsStar = 0;
        int numOfPakupa = 0;
        int numOfDenkyun = 0;
        int numOfCuppen = 0;

        switch(GameStatus.getStage()) {
            case 1: // instruction - easy
                numOfMetalPuropen = 1;
                numOfPuropen = 3;
                break;
            case 2: // medium
                numOfDenkyun = 2;
                numOfPuropen = 1;
                numOfMetalPuropen = 1;
                break;
            case 3: // đòi hỏi player đặt nhiều bom và timing chính xác
                numOfDenkyun = 1;
                numOfMetalPuropen = 2;
                numOfPakupa = 2;
                break;
            case 4: // đòi hỏi player di chuyển nhanh nhạy khi gặp enemy mạnh
                numOfMetalPuropen = 1;
                numOfPuropen = 1;
                numOfNutsStar = 1;
                numOfDenkyun = 2;
                break;
            case 5: // final
                numOfNutsStar = 2;
                numOfMetalPuropen = 1;
                numOfPakupa = 1;
                numOfCuppen = 1;
                break;

            // enemy demo stage
            case 101:
                numOfPuropen = 1;
                break;
            case 102:
                numOfMetalPuropen = 1;
                break;
            case 103:
                numOfDenkyun = 1;
                break;
            case 104:
                numOfPakupa = 1;
                break;
            case 105:
                numOfNutsStar = 1;
                break;
            case 106:
                numOfCuppen = 1;
                break;
        }

        for (int t = 0; t < numOfPakupa; t++) {
            Collections.shuffle(grassLocation);
            if (grassLocation.get(0).getValue() > 3 && grassLocation.get(0).getKey() > 3) {
                enemyList.add(new Pakupa(grassLocation.get(0).getValue() * GlobalConstant.TILE_SIZE, grassLocation.get(0).getKey() * GlobalConstant.TILE_SIZE));
                grassLocation.remove(grassLocation.get(0));
            } else {
                t--;
            }
        }

        for (int t = 0; t < numOfNutsStar; t++) {
            Collections.shuffle(grassLocation);
            if (grassLocation.get(0).getValue() > 3 && grassLocation.get(0).getKey() > 3) {
                enemyList.add(new NutsStar(grassLocation.get(0).getValue() * GlobalConstant.TILE_SIZE, grassLocation.get(0).getKey() * GlobalConstant.TILE_SIZE));
                grassLocation.remove(grassLocation.get(0));
            } else {
                t--;
            }
        }

        for (int t = 0; t < numOfPuropen; t++) {
            Collections.shuffle(grassLocation);
            if (grassLocation.get(0).getValue() > 3 && grassLocation.get(0).getKey() > 3) {
                enemyList.add(new Puropen(grassLocation.get(0).getValue() * GlobalConstant.TILE_SIZE, grassLocation.get(0).getKey() * GlobalConstant.TILE_SIZE));
                grassLocation.remove(grassLocation.get(0));
            } else {
                t--;
            }
        }

        for (int t = 0; t < numOfMetalPuropen; t++) {
            Collections.shuffle(grassLocation);
            if (grassLocation.get(0).getValue() > 3 && grassLocation.get(0).getKey() > 3) {
                enemyList.add(new MetalPuropen(grassLocation.get(0).getValue() * GlobalConstant.TILE_SIZE, grassLocation.get(0).getKey() * GlobalConstant.TILE_SIZE));
                grassLocation.remove(grassLocation.get(0));
            } else {
                t--;
            }
        }

        for (int t = 0; t < numOfDenkyun; t++) {
            Collections.shuffle(grassLocation);
            if (grassLocation.get(0).getValue() > 3 && grassLocation.get(0).getKey() > 3) {
                enemyList.add(new Denkyun(grassLocation.get(0).getValue() * GlobalConstant.TILE_SIZE, grassLocation.get(0).getKey() * GlobalConstant.TILE_SIZE));
                grassLocation.remove(grassLocation.get(0));
            } else {
                t--;
            }
        }

        for (int t = 0; t < numOfCuppen; t++) {
            Collections.shuffle(grassLocation);
            if (grassLocation.get(0).getValue() > 3 && grassLocation.get(0).getKey() > 3) {
                enemyList.add(new Cuppen(grassLocation.get(0).getValue() * GlobalConstant.TILE_SIZE, grassLocation.get(0).getKey() * GlobalConstant.TILE_SIZE));
                grassLocation.remove(grassLocation.get(0));
            } else {
                t--;
            }
        }

        return enemyList;
    }

    @Override
    public void render() {
        Sandbox.getGc().drawImage(image, x, GlobalConstant.MENU_TOP_HEIGHT + y, width * GlobalConstant.TILE_SIZE, height * GlobalConstant.TILE_SIZE);
    }
}