package model;

import controller.Controller;
import model.enemies.Enemy;
import model.enemies.SquarantineModel;
import model.enemies.TrigorathModel;
import view.GameView;
import view.enemies.EnemyView;

import java.io.IOException;
import java.util.ArrayList;

public class GameModel {
    private double width;
    private double height;
    private double x;
    private double y;
    public static GameModel INSTANCE;
    private boolean decreaseSize;
    int w;
    private ArrayList<Enemy> enemies;

    public GameModel() {
        x = 0;
        y = 0;
        width = 700;
        height = 700;
        decreaseSize = true;
        enemies = new ArrayList<>();
    }

    public static GameModel getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new GameModel();
        }
        return INSTANCE;
    }
    public void decreaseSize() throws IOException {
        if (decreaseSize) {
            width -= 4;
            height -= 4;
            x = (700 - width) / 2;
            y = (700 - width) / 2;
            EpsilonModel.getINSTANCE().setInCenter();
            if (width == 300) {
                decreaseSize = false;
            }
        }
        else if (width > 200 && height > 200) {
            if (w == 0) {
                addEnemy();
                w++;
            }
            width -= 0.1;
            height -= 0.1;
            x = (700 - width) / 2;
            y = (700 - width) / 2;
        }
    }

    public int getWidth() {
        return (int)width;
    }

    public int getHeight() {
        return (int)height;
    }

    public int getX() {
        return (int)x;
    }

    public int getY() {
        return (int)y;
    }
    private void addEnemy() throws IOException {
        int x = (int)(Math.random()*2);
        Enemy enemy;
//        if (x == 0) {
            enemy = new SquarantineModel(10,10);
//        }
//        else {
//            enemy = new TrigorathModel(10, 10);
//        }
        Controller.addEnemyView(enemy);
    }
    public void moveEnemies() {
        for (Enemy enemy: enemies) {
            enemy.move();
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
