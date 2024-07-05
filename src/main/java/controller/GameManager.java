package controller;


import collision.Impactable;
import model.BulletModel;
import model.EpsilonModel;
import model.Collective;
import model.enemies.Enemy;
import model.game.EasyGame;
import model.game.GameModel;
import model.game.HardGame;
import model.game.MediumGame;
import model.skills.Skill;
import model.skills.WritOfAceso;
import movement.Point;
import view.GameFrame;
import view.GameView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameManager {
    private int totalXP;
    public static GameManager INSTANCE;
    private static int difficulty;
    private static int sensitivity;
    private Skill pickedSkill;
    private boolean[][] addedEnemies;
    private boolean gameStarted;
    private ArrayList<Enemy> diedEnemies;
    private ArrayList<BulletModel> vanishedBullets;
    private ArrayList<Collective> takenCollectives;
    private int ares;
    private boolean athena;
    private long athenaActivationTime;
    private boolean finished;
    private boolean wait;
    private GameModel gameModel;
    private GameView gameView;
    private boolean decreaseSize;
    private int wave;
    private GameFrame gameFrame;
    private GameManager() {
        totalXP = 2000;
        sensitivity = 2;
        difficulty = 1;
        gameFrame = new GameFrame();
    }
    public void startGame() {
        decreaseSize = true;
        wave = 1;
        gameView = new GameView();
        if (difficulty == 1) {
            gameModel = new EasyGame();
        }
        else if (difficulty == 2) {
            gameModel = new MediumGame();
        }
        else {
            gameModel = new HardGame();
        }
    }
    private void initialDecreaseSize() {
        gameModel.setWidth(gameModel.getWidth() - 4);
        gameModel.setHeight(gameModel.getHeight() - 4);
        gameModel.setX((700 - gameModel.getWidth()) / 2);
        gameModel.setY((700 - gameModel.getHeight()) / 2);
        gameModel.getEpsilon().setInCenter();
        if (gameModel.getWidth() == 500) {
            Controller.setGameHUI();
            gameStarted = true;
            decreaseSize = false;
        }
    }
    private void decreaseSize() {
        if (gameModel.getWidth() > 300) {
            gameModel.setWidth(gameModel.getWidth()-0.1);
        }
        if (gameModel.getHeight() > 300) {
            gameModel.setHeight(gameModel.getHeight()-0.1);
        }
        gameModel.getEpsilon().setInFrame();
    }
    private void addEnemy() {
        Point position = GameManagerHelper.getRandomPosition(addedEnemies, gameModel.getWidth(), gameModel.getHeight());
        Enemy enemy = GameManagerHelper.getNewEnemy(position, gameModel.getEnemyHP(), gameModel.getEnemyVelocity());
        gameModel.getEnemies().add(enemy);
        Controller.addEnemyView(enemy);
    }
    private void moveEnemies()  {
        diedEnemies = new ArrayList<>();
        for (int i = 0; i < gameModel.getEnemies().size(); i++) {
            gameModel.getEnemies().get(i).move();
        }
        for (int i = 0; i < gameModel.getEnemies().size(); i++) {
            Enemy enemy = gameModel.getEnemies().get(i);
            Point point = enemy.getCollisionPoint(gameModel.getEpsilon(), enemy.getVertexes());
            if (point != null) {
                enemy.impact(point, gameModel.getEpsilon());
                SoundController.addCollisionSound();
            }
            for (int j = i+1; j < gameModel.getEnemies().size(); j++) {
                Enemy enemy2 = gameModel.getEnemies().get(j);
                point = enemy.getCollisionPoint(enemy2, enemy.getVertexes());
                if (point != null) {
                    enemy.impact(point, enemy2);
                    SoundController.addCollisionSound();
                }
            }
        }
    }
    private void moveBullets() {
        for (int i = 0; i < gameModel.getBullets().size(); i++) {
            gameModel.getBullets().get(i).move();
        }
    }
    private void nextWave() {
        SoundController.addEnemyEnteringSound();
        addedEnemies = new boolean[4][6];
        gameModel.setEnemies(new ArrayList<>());
        int enemies = gameModel.getWaves().get(wave);
        for (int i = 0; i < enemies; i++) {
            addEnemy();
        }
        wave++;
    }
    private void checkBulletsCollision() {
        vanishedBullets = new ArrayList<>();
        diedEnemies = new ArrayList<>();
        for (int i = 0; i < gameModel.getBullets().size(); i++) {
            BulletModel bullet = gameModel.getBullets().get(i);
            if (bullet.checkFrameCollision(gameModel)) {
                vanishedBullets.add(bullet);
                Controller.removeBullet(bullet);
            } else {
                for (int j = 0; j < gameModel.getEnemies().size(); j++) {
                    Enemy enemy = gameModel.getEnemies().get(j);
                    if (Math.abs(enemy.getX() - bullet.getX2()) < 40 && Math.abs(enemy.getY() - bullet.getY2()) < 40) {
                        Point point = enemy.getCollisionPoint(bullet, enemy.getVertexes());
                        if (point != null) {
                            Impactable.impactOnOthers(point);
                            vanishedBullets.add(bullet);
                            Controller.removeBullet(bullet);
                            if (enemy.died(5+ares)) {
                                SoundController.addEnemyDyingSound();
                                Controller.removeEnemy(enemy);
                                diedEnemies.add(enemy);
                            }
                        }
                    }
                }
            }
        }
        GameManagerHelper.removeFrom(gameModel.getEnemies(), diedEnemies);
        GameManagerHelper.removeFrom(gameModel.getBullets(), vanishedBullets);
    }
//    private void removeEnemies() {
//        for (int i = 0; i < diedEnemies.size(); i++) {
//            Enemy enemy = diedEnemies.get(i);
//            enemy.setHP(enemy.getHP()-5-ares);
//            if (enemy.getHP() <= 0) {
//                Controller.addEnemyDyingSound();
//                gameModel.getEnemies().remove(enemy);
//                Controller.removeEnemy(enemy);
//                enemy.addCollective(gameModel);
//            }
//        }
//    }
    private void checkCollectives() {
        takenCollectives = new ArrayList<>();
        for (int i = 0; i < gameModel.getCollectives().size(); i++) {
            Collective collective = gameModel.getCollectives().get(i);
            Point point = gameModel.getEpsilon().getCollisionPoint(gameModel.getCollectives().get(i), null);
            if (point != null){
                takenCollectives.add(collective);
                gameModel.getEpsilon().setXP(gameModel.getEpsilon().getXP()+5+ gameModel.getEnemyXP());
                Controller.removeXP(collective);
                SoundController.addXPCollectingSound();
            }
            else {
                long currentTime = System.currentTimeMillis();
                if (currentTime- collective.getTime() >= 6000) {
                    takenCollectives.add(collective);
                    Controller.removeXP(collective);
                }
            }
        }
        GameManagerHelper.removeFrom(gameModel.getCollectives(), takenCollectives);
    }
    public void update() {
        if (decreaseSize) {
            initialDecreaseSize();
        }
        else {
            decreaseSize();
        }
        gameModel.getEpsilon().move();
        moveEnemies();
        moveBullets();
        checkBulletsCollision();
        if (gameStarted && gameModel.getEnemies().size() == 0 && !wait) {
            if (wave == 4) {
                endGame();
            }
            else {
                if (wave != 1) {
                    waitBeforeNextWave();
                }
                else {
                    nextWave();
                }
            }
        }
        checkCollectives();
        Skill skill = GameManager.getINSTANCE().getPickedSkill();
        if (skill instanceof WritOfAceso) {
            ((WritOfAceso)skill).increaseHP();
        }
    }
    private void waitBeforeNextWave() {
        wait = true;
        SoundController.addWaveEndSound();
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
    public boolean checkPosition() {
        if (gameModel.getX() < 0) {
            gameModel.setX(0);
            return true;
        }
        else if (gameModel.getX() > Constants.FRAME_SIZE.getWidth()-gameModel.getWidth()) {
            gameModel.setX(Constants.FRAME_SIZE.getWidth()-gameModel.getWidth());
            return true;
        }
        if (gameModel.getY() < 0) {
            gameModel.setY(0);
            return true;
        }
        else if (gameModel.getY() > Constants.FRAME_SIZE.getHeight()-gameModel.getHeight()) {
            gameModel.setY(Constants.FRAME_SIZE.getHeight()-gameModel.getHeight());
            return true;
        }
        return false;
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
            double dx = gameModel.getBullets().get(0).getDirection().getDx();
            double dy = gameModel.getBullets().get(0).getDirection().getDy();
            addBullet(x,y);
            BulletModel bullet = gameModel.getBullets().get(1);
            bullet.setPosition(bullet.getX1()+dx*100, bullet.getY1()+dy*100);
            addBullet(x,y);
            bullet = gameModel.getBullets().get(2);
            bullet.setPosition(bullet.getX1()+dx*200, bullet.getY1()+dy*200);
        }
        else {
            addBullet(x,y);
        }
    }
    private void addBullet(int x, int y) {
        BulletModel bulletModel = new BulletModel(x, y);
        gameModel.getBullets().add(bulletModel);
        Controller.addBulletView(bulletModel);
    }
    private void endGame() {
        Controller.endGame();
        SoundController.addWinningSound();
        Controller.removeEpsilonVertexes();
        for (int i = 0; i < gameModel.getCollectives().size(); i++) {
            Controller.removeXP(gameModel.getCollectives().get(i));
        }
        for (int i = 0; i < gameModel.getCollectives().size(); i++) {
            Controller.removeBullet(gameModel.getBullets().get(i));
        }
        for (int i = 0; i < gameModel.getEnemies().size(); i++) {
            Controller.removeEnemy(gameModel.getEnemies().get(i));
        }
        Controller.gameFinished = true;
        GameManager game = GameManager.getINSTANCE();
        game.setTotalXP(game.getTotalXP()+EpsilonModel.INSTANCE.getXP());
    }
    public void destroyFrame() {
        if (finished) {
            if (gameModel.getWidth() >= 2) {
                gameModel.setWidth(gameModel.getWidth()-5);
            }
            if (gameModel.getHeight() >= 2) {
                gameModel.setHeight(gameModel.getHeight()-5);
            }
            if (gameModel.getWidth() <= 2 && gameModel.getHeight() <= 2) {
                finished = false;
                Controller.gameOver(gameModel.getEpsilon().getXP());
            }
        }
    }

    public static GameManager getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new GameManager();
        }
        return INSTANCE;
    }

    public int getTotalXP() {
        return totalXP;
    }

    public void setTotalXP(int totalXP) {
        this.totalXP = totalXP;
    }


    public Skill getPickedSkill() {
        return pickedSkill;
    }

    public void setPickedSkill(Skill pickedSkill) {
        this.pickedSkill = pickedSkill;
    }

    public static int getDifficulty() {
        return difficulty;
    }

    public static void setDifficulty(int difficulty) {
        GameManager.difficulty = difficulty;
    }

    public static int getSensitivity() {
        return sensitivity;
    }

    public static void setSensitivity(int sensitivity) {
        GameManager.sensitivity = sensitivity;
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public GameView getGameView() {
        return gameView;
    }

    public int getWave() {
        return wave;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getAres() {
        return ares;
    }

    public void setAres(int ares) {
        this.ares = ares;
    }

    public ArrayList<Enemy> getDiedEnemies() {
        return diedEnemies;
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }
}
