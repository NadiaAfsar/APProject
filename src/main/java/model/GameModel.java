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
    private ArrayList<Enemy> collidedEnemies;
    private ArrayList<BulletModel> collidedBullets;
    private ArrayList<XP> XPs;
    private ArrayList<XP> takenXPs;

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
        XPs = new ArrayList<>();
    }

    public static GameModel getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new GameModel();
        }
        return INSTANCE;
    }
    private void decreaseSize() {
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
        else if (width > 300 || height > 300) {
            if (width > 300) {
                width -= 0.1;
            }
            if (height > 300) {
                height -= 0.1;
            }
            EpsilonModel.getINSTANCE().setInFrame();
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
    private void moveEnemies()  {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).move();
        }
        for (int i = 0; i < enemies.size(); i++) {
            Collidable.collided(enemies.get(i), i);
        }
    }
    private void moveBullets() {
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
    private void nextWave() {
        int enemies = 0;
        addedEnemies = new boolean[4][6];
        this.enemies = new ArrayList<>();
        if (wave == 1) {
            enemies = 3;
        }
        else if ( wave == 2) {
            enemies = 4;
        }
        else if (wave == 3){
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
    private void checkBulletsCollision() {
        collidedBullets = new ArrayList<>();
        collidedEnemies = new ArrayList<>();
        for (int i = 0; i < bullets.size(); i++) {
            BulletModel bullet = bullets.get(i);
            if (bullet.checkFrameCollision(this)) {
                collidedBullets.add(bullet);
                Controller.removeBullet(bullet);
            } else {
                for (int j = 0; j < enemies.size(); j++) {
                    Enemy enemy = enemies.get(j);
                    if (Math.abs(enemy.getX() - bullet.getX2()) < 40 && Math.abs(enemy.getY() - bullet.getY2()) < 40) {
                        if (Collidable.collisionPoint(bullet, enemy) != null) {
                            collidedBullets.add(bullet);
                            Controller.removeBullet(bullet);
                            collidedEnemies.add(enemy);
                        }
                    }
                }
            }
        }
        removeEnemies();
        removeBullets();
    }
    private void removeEnemies() {
        for (int i = 0; i < collidedEnemies.size(); i++) {
            Enemy enemy = collidedEnemies.get(i);
            enemy.setHP(enemy.getHP()-5);
            if (enemy.getHP() == 0) {
                getEnemies().remove(enemy);
                Controller.removeEnemy(enemy);
                enemy.addXP();
            }
        }
    }
    private void removeBullets() {
        for (int i = 0; i < collidedBullets.size(); i++) {
            BulletModel bullet = collidedBullets.get(i);
            getBullets().remove(bullet);
        }
    }

    public ArrayList<XP> getXPs() {
        return XPs;
    }
    private void checkXPCollision() {
        takenXPs = new ArrayList<>();
        for (int i = 0; i < XPs.size(); i++) {
            XP xp = XPs.get(i);
            if (Collidable.collisionPoint(xp, EpsilonModel.getINSTANCE()) != null){
                takenXPs.add(xp);
            }
        }
        removeXPs();
    }
    private void removeXPs() {
        for (int i = 0; i < takenXPs.size(); i++) {
            XPs.remove(takenXPs.get(i));
        }
    }
    public void update() {
        decreaseSize();
        moveEnemies();
        moveBullets();
        checkBulletsCollision();
        if (isGameStarted() && getEnemies().size() == 0) {
            nextWave();
        }
        checkXPCollision();
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
