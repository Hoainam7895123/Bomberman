package com.example.bomberman.screens;


import com.example.bomberman.general.AudioManager;
import com.example.bomberman.general.ImageManager;
import com.example.bomberman.menu.GameMenu;
import com.example.bomberman.menu.TopMenu;
import com.example.bomberman.object.*;

import java.util.ArrayList;
import java.util.List;


public class GameScreen implements Screen {

    Bomber bomber;
    List<Wall> wallList;
    List<Root> rootList;
    List<Bom> bomList;
    List<Fire> fireList;
    List<Item> itemList;

    List<Enemy> enemyList;
    Portal portal;
    Map map;
    TopMenu topMenu;

    GameMenu gameMenu;

    float time = 100;

    public GameScreen() {
        AudioManager.setMusic("in_game_music.mp3");
        map = new Map("src/main/resources/com/g10/asset/map/stage1.txt", ImageManager.getImage("asset/map/stage1.png"));
        wallList = map.createWall();
        rootList = map.createRoot();
        itemList = map.createItem();
        portal = map.createPortal();
        enemyList = map.createEnemy();
        bomList = new ArrayList<>();

        bomber = new Bomber();
        fireList = new ArrayList<>();
        topMenu = new TopMenu();
        gameMenu = new GameMenu();

    }

    @Override
    public void render() {
        map.render();
//        wallList.forEach(Wall::render);
        itemList.forEach(VisibleObject::render);
        portal.render();
        rootList.forEach(VisibleObject::render);
        bomList.forEach(UpdatableObject::render);
        fireList.forEach(UpdatableObject::render);
        bomber.render();
        enemyList.forEach(VisibleObject::render);
        topMenu.render();
        gameMenu.render();
    }

    @Override
    public void update(float deltaTime) {

        if (!gameMenu.isActive()) {

            if (bomber.isAlive()) {
                bomber.update(itemList);
                bomber.update(deltaTime, wallList, rootList, bomList); //update movement
                bomber.update(bomList, fireList, wallList, rootList);
                bomber.update(fireList, enemyList);
                bomber.update(enemyList, portal);
                //update plant bomb
                //update death
            } else {
                bomber.update();
            }
            bomList.forEach(UpdatableObject::update);
            fireList.forEach(UpdatableObject::update);
            itemList.forEach(UpdatableObject::update);
            rootList.forEach(UpdatableObject::update);
            for (Enemy enemy : enemyList) {
                enemy.update(deltaTime, wallList, rootList, bomList, bomber);
                enemy.update(fireList, enemyList);
            }
            portal.update();
            time -= deltaTime;
            if(time < 0) ScreenManager.switchScreen(ScreenType.TIME_OUT_SCREEN);
            topMenu.update(time);
        }
        gameMenu.update();
    }
}