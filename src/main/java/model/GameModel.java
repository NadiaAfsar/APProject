package model;

import collision.Collidable;
import controller.Controller;
import model.enemies.Enemy;
import model.enemies.SquarantineModel;
import model.enemies.TrigorathModel;
import movement.Direction;
import movement.Point;

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
    private ArrayList<BulletModel> bullets;

    public GameModel() {
        x = 0;
        y = 0;
        width = 700;
        height = 700;
        decreaseSize = true;
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
    }

    public static GameModel getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new GameModel();
        }
        return INSTANCE;
    }
    public void decreaseSize() {
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
    private void addEnemy() {
        int x = (int)(Math.random()*2);
        Enemy enemy;
//        if (x == 0) {
//            enemy = new SquarantineModel(new Point(23,23));
//        }
//        else {
            enemy = new TrigorathModel(new Point(23,25));
//        }
        Controller.addEnemyView(enemy);
    }
    public void moveEnemies()  {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).move();
        }
        for (int i = 0; i < enemies.size(); i++) {
            Collidable.collided(enemies.get(i), i);
        }
    }
    public void moveBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).move();
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<BulletModel> getBullets() {
        return bullets;
    }

}
