package controller;


import collision.Impactable;
import controller.audio.AudioController;
import model.BulletModel;
import model.Collective;
import model.Wave;
import model.enemies.Enemy;
import model.game.EasyGame;
import model.game.GameModel;
import model.game.HardGame;
import model.game.MediumGame;
import model.skills.Skill;
import model.skills.WritOfAceso;
import movement.Point;
import view.menu.GameFrame;
import view.game.GameView;

import java.util.ArrayList;

public class GameManager {
    private int totalXP;
    public static GameManager INSTANCE;
    private static int difficulty;
    private static int sensitivity;
    private Skill pickedSkill;
    private boolean gameStarted;
    private ArrayList<Enemy> diedEnemies;
    private ArrayList<BulletModel> vanishedBullets;
    private ArrayList<Collective> takenCollectives;
    private boolean wait;
    private GameModel gameModel;
    private GameView gameView;
    private boolean decreaseSize;
    private int wave;
    private long lastSavedTime;
    private long timePlayed;
    private Wave currentWave;
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
        lastSavedTime = System.currentTimeMillis();
    }
    private void initialDecreaseSize() {
        gameModel.setWidth(gameModel.getWidth() - 4);
        gameModel.setHeight(gameModel.getHeight() - 4);
        gameModel.setX((700 - gameModel.getWidth()) / 2);
        gameModel.setY((700 - gameModel.getHeight()) / 2);
        gameModel.getEpsilon().setInCenter();
        if (gameModel.getWidth() == 500) {
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
                AudioController.addCollisionSound();
            }
            for (int j = i+1; j < gameModel.getEnemies().size(); j++) {
                Enemy enemy2 = gameModel.getEnemies().get(j);
                point = enemy.getCollisionPoint(enemy2, enemy.getVertexes());
                if (point != null) {
                    enemy.impact(point, enemy2);
                    AudioController.addCollisionSound();
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
        gameModel.setTotalPR(gameModel.getTotalPR()+currentWave.getProgressRate());
        currentWave = new Wave(wave, gameModel.getWaves().get(wave));
        wave++;
    }
    private void checkBulletsCollision() {
        vanishedBullets = new ArrayList<>();
        diedEnemies = new ArrayList<>();
        for (int i = 0; i < gameModel.getBullets().size(); i++) {
            BulletModel bullet = gameModel.getBullets().get(i);
            if (GameManagerHelper.checkFrameCollisionWithBullet(bullet)) {
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
                            if (enemy.died(5+gameModel.getAres())) {
                                AudioController.addEnemyDyingSound();
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
    private void checkCollectives() {
        takenCollectives = new ArrayList<>();
        for (int i = 0; i < gameModel.getCollectives().size(); i++) {
            Collective collective = gameModel.getCollectives().get(i);
            Point point = gameModel.getEpsilon().getCollisionPoint(gameModel.getCollectives().get(i), null);
            if (point != null){
                takenCollectives.add(collective);
                gameModel.getEpsilon().setXP(gameModel.getEpsilon().getXP()+5+ gameModel.getEnemyXP());
                Controller.removeXP(collective);
                AudioController.addXPCollectingSound();
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
                nextWave();
            }
        }
        checkCollectives();
        Skill skill = GameManager.getINSTANCE().getPickedSkill();
        if (skill instanceof WritOfAceso) {
            ((WritOfAceso)skill).increaseHP();
        }
        setTimePlayed();
    }
    public void activateAthena() {
        gameModel.setAthena(true);
        gameModel.setAthenaActivationTime(System.currentTimeMillis());
    }
    public void checkAthenaTime() {
        long currentTime = System.currentTimeMillis();
        if (currentTime-gameModel.getAthenaActivationTime() >= 10000) {
            gameModel.setAthena(false);
        }
    }
    private void endGame() {
        Controller.endGame();
        AudioController.addWinningSound();
        Controller.removeEpsilonVertexes();
        for (int i = 0; i < gameModel.getCollectives().size(); i++) {
            Controller.removeXP(gameModel.getCollectives().get(i));
        }
        for (int i = 0; i < gameModel.getBullets().size(); i++) {
            Controller.removeBullet(gameModel.getBullets().get(i));
        }
        for (int i = 0; i < gameModel.getEnemies().size(); i++) {
            Controller.removeEnemy(gameModel.getEnemies().get(i));
        }
        Controller.gameFinished = true;
        GameManager game = GameManager.getINSTANCE();
        game.setTotalXP(game.getTotalXP()+gameModel.getEpsilon().getXP());
    }
    public void destroyFrame() {
        if (gameModel.isFinished()) {
            if (gameModel.getWidth() >= 2) {
                gameModel.setWidth(gameModel.getWidth()-5);
            }
            if (gameModel.getHeight() >= 2) {
                gameModel.setHeight(gameModel.getHeight()-5);
            }
            if (gameModel.getWidth() <= 2 && gameModel.getHeight() <= 2) {
                gameModel.setFinished(false);
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


    public ArrayList<Enemy> getDiedEnemies() {
        return diedEnemies;
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }
    public void setLastSavedTime() {
        lastSavedTime = System.currentTimeMillis();
    }

    private void setTimePlayed() {
        long newTime = System.currentTimeMillis();
        long addedTime = newTime - lastSavedTime;
        timePlayed += addedTime;
        lastSavedTime = newTime;
    }

    public long getTimePlayed() {
        return timePlayed/1000;
    }


    public void setWait(boolean wait) {
        this.wait = wait;
    }
}
