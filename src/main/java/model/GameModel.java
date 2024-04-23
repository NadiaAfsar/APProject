package model;

import collision.Collidable;
import controller.Controller;
import model.enemies.Enemy;
import model.enemies.SquarantineModel;
import model.enemies.TrigorathModel;
import movement.Point;

import java.util.ArrayList;

public class GameModel {
    private double width;
    private double height;
    private double x;
    private double y;
    public static GameModel INSTANCE;
    private boolean decreaseSize;
    private ArrayList<Enemy> enemies;
    private ArrayList<BulletModel> bullets;
    private int wave;
    private boolean[][] addedEnemies;
    private boolean gameStarted;

    public GameModel() {
        x = 0;
        y = 0;
        width = 700;
        height = 700;
        decreaseSize = true;
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        wave = 1;
        addedEnemies = new boolean[4][6];
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
            if (width == 500) {
                gameStarted = true;
                decreaseSize = false;
            }
        }
//        else if (width > 200 && height > 200) {
//            width -= 0.1;
//            height -= 0.1;
//            x = (700 - width) / 2;
//            y = (700 - width) / 2;
//        }
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
        Point position = getRandomPosition();
        Enemy enemy;
        if (x == 0) {
            enemy = new SquarantineModel(position);
        }
        else {
            enemy = new TrigorathModel(position);
        }
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
    public void nextWave() {
        int enemies;
        this.enemies = new ArrayList<>();
        if (wave == 1) {
            enemies = 3;
        }
        else if ( wave == 2) {
            enemies = 4;
        }
        else {
            enemies = 5;
        }
        for (int i = 0; i < enemies; i++) {
            addEnemy();
        }
        wave++;
    }
    private Point getRandomPosition() {
        double x = 0;
        double y = 0;
        int random1 = (int)(Math.random()*4);
        int random2 = (int)(Math.random()*6);
        if (!addedEnemies[random1][random2]) {
            addedEnemies[random1][random2] = true;
            if (random1 == 0) {
                y = -10;
                x = random2 * ( width / 6) + 10;
            } else if (random1 == 1) {
                x = (int) width;
                y = random2 * ( height/ 6) + 10;
            } else if (random1 == 2) {
                y = (int)height;
                x = random2 * ( width / 6) + 10;
            }
            else {
                x = -10;
                y = random2 * ( height/ 6) + 10;
            }
            return new Point(x,y);
        }
        return getRandomPosition();
    }

    public boolean isGameStarted() {
        return gameStarted;
    }
}
