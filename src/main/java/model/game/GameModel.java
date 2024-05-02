package model.game;

import collision.Collidable;
import controller.Constants;
import controller.Controller;
import model.BulletModel;
import model.EpsilonModel;
import model.GameManager;
import model.XP;
import model.enemies.Enemy;
import model.enemies.SquarantineModel;
import model.enemies.TrigorathModel;
import model.skills.Skill;
import model.skills.WritOfAceso;
import movement.Point;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class GameModel {
    private double width;
    private double height;
    private double x;
    private double y;
    public static GameModel INSTANCE;
    private boolean decreaseSize;
    private ArrayList<Enemy> enemies;
    private ArrayList<BulletModel> bullets;
    private int wave;
    protected int[] waves;
    protected double enemyVelocity;
    protected int enemyPower;
    protected int enemyHP;
    protected int enemyXP;
    private boolean[][] addedEnemies;
    private boolean gameStarted;
    private ArrayList<Enemy> collidedEnemies;
    private ArrayList<BulletModel> collidedBullets;
    private ArrayList<XP> XPs;
    private ArrayList<XP> takenXPs;
    private int ares;
    private boolean athena;
    private long athenaActivationTime;
    private boolean finished;
    private boolean wait;

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
            int d = GameManager.getDifficulty();
            if (d == 1) {
                INSTANCE = new EasyGame();
            }
            else if (d == 2) {
                INSTANCE = new MediumGame();
            }
            else {
                INSTANCE = new HardGame();
            }
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
                Controller.setGameHUI();
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
            enemy = new SquarantineModel(position, enemyHP, enemyVelocity);
        }
        else {
            enemy = new TrigorathModel(position, enemyHP, enemyVelocity);
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
        Controller.addEnemyEnteringSound();
        addedEnemies = new boolean[4][6];
        this.enemies = new ArrayList<>();
        if (wave == 1) {
            enemies = waves[0];
        }
        else if ( wave == 2) {
            enemies = waves[1];
        }
        else if (wave == 3){
            enemies = waves[2];
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
            enemy.setHP(enemy.getHP()-5-ares);
            System.out.println(enemy.getHP());
            if (enemy.getHP() <= 0) {
                Controller.addEnemyDyingSound();
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
            else {
                long currentTime = System.currentTimeMillis();
                if (currentTime-xp.getTime() >= 6000) {
                    takenXPs.add(xp);
                    Controller.removeXP(xp);
                }
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
        if (isGameStarted() && getEnemies().size() == 0 && !wait) {
            if (wave == 4) {
                endGame();
            }
            else {
                if (wave != 1) {
                    wait = true;
                    Controller.addWaveEndSound();
                    Timer timer = new Timer(2000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            nextWave();
                            wait = false;
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
                else {
                    nextWave();
                }
            }
        }
        checkXPCollision();
        Skill skill = GameManager.getINSTANCE().getPickedSkill();
        if (skill instanceof WritOfAceso) {
            ((WritOfAceso)skill).increaseHP();
        }
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

    public int getWave() {
        return wave;
    }
    public boolean checkPosition() {
        if (x < 0) {
            setX(0);
            return true;
        }
        else if (x > Constants.FRAME_SIZE.getWidth()-width) {
            setX(Constants.FRAME_SIZE.getWidth()-width);
            return true;
        }
        if (y < 0) {
            setY(0);
            return true;
        }
        else if (y > Constants.FRAME_SIZE.getHeight()-height) {
            setY(Constants.FRAME_SIZE.getHeight()-height);
            return true;
        }
        return false;
    }

    public int getAres() {
        return ares;
    }

    public void setAres(int ares) {
        this.ares = ares;
    }
    public void activateAthena() {
        athena = true;
        athenaActivationTime = System.currentTimeMillis();
    }
    private void checkAthenaTime() {
        long currentTime = System.currentTimeMillis();
        if (currentTime-athenaActivationTime >= 10000) {
            athena = false;
        }
    }
    public void shotBullet(int x, int y) {
        checkAthenaTime();
        if (athena) {
            addBullet(x,y);
            double dx = bullets.get(0).getDirection().getDx();
            double dy = bullets.get(0).getDirection().getDy();
            addBullet(x,y);
            BulletModel bullet = bullets.get(1);
            bullet.setPosition(bullet.getX1()+dx*100, bullet.getY1()+dy*100);
            addBullet(x,y);
            bullet = bullets.get(2);
            bullet.setPosition(bullet.getX1()+dx*200, bullet.getY1()+dy*200);
        }
        else {
            addBullet(x,y);
        }
    }
    private void addBullet(int x, int y) {
        BulletModel bulletModel = new BulletModel(x, y);
        getBullets().add(bulletModel);
        Controller.addBulletView(bulletModel);
    }

    public boolean isAthena() {
        return athena;
    }

    public int getEnemyPower() {
        return enemyPower;
    }

    public int getEnemyXP() {
        return enemyXP;
    }
    private void endGame() {
        Controller.endGame();
        Controller.addWinningSound();
        Controller.removeEpsilonVertexes();
        for (int i = 0; i < XPs.size(); i++) {
            Controller.removeXP(XPs.get(i));
        }
        for (int i = 0; i < bullets.size(); i++) {
            Controller.removeBullet(bullets.get(i));
        }
        for (int i = 0; i < enemies.size(); i++) {
            Controller.removeEnemy(enemies.get(i));
        }
        Controller.gameFinished = true;
        GameManager game = GameManager.getINSTANCE();
        game.setTotalXP(game.getTotalXP()+EpsilonModel.INSTANCE.getXP());
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
    public void destroyFrame() {
        if (finished) {
            if (width >= 2) {
                width -= 5;
            }
            if (height >= 2) {
                height -= 5;
            }
            if (width <= 2 && height <= 2) {
                finished = false;
                Controller.gameOver(EpsilonModel.getINSTANCE().getXP());
            }
        }
    }
}
