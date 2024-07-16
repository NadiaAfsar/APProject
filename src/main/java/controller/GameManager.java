package controller;


import controller.save.Configs;
import controller.save.ReaderWriter;
import controller.update.ModelLoop;
import controller.update.ViewLoop;
import model.enemies.mini_boss.black_orb.BlackOrb;
import model.enemies.mini_boss.black_orb.BlackOrbVertex;
import model.interfaces.collision.Impactable;
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
import model.interfaces.movement.Point;
import view.menu.GameFrame;
import view.game.GameView;

import java.util.ArrayList;

public class GameManager {
    private int totalXP;
    private static GameManager INSTANCE;
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
    public static Configs configs;
    public static ReaderWriter readerWriter;
    private GameManager() {
        totalXP = 2000;
        sensitivity = 2;
        difficulty = 1;
        readerWriter = new ReaderWriter();
        configs = readerWriter.getConfigs();
        gameFrame = new GameFrame();
        new ModelLoop().start();
        new ViewLoop().start();
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
        gameView.getEpsilonView().setPanel(gameView.getGamePanelMap().get(gameModel.getEpsilon().getFrame().getID()));
        lastSavedTime = System.currentTimeMillis();
    }
    private void initialDecreaseSize() {
        gameModel.getEpsilon().getFrame().setWidth(gameModel.getEpsilon().getFrame().getWidth() - 4);
        gameModel.getEpsilon().getFrame().setHeight(gameModel.getEpsilon().getFrame().getHeight() - 4);
        gameModel.getEpsilon().getFrame().setX(Configs.FRAME_SIZE.width/2 - gameModel.getEpsilon().getFrame().getWidth()/2);
        gameModel.getEpsilon().getFrame().setY(Configs.FRAME_SIZE.height/2 - gameModel.getEpsilon().getFrame().getHeight()/2);
        gameModel.getEpsilon().setInCenter();
        if (gameModel.getEpsilon().getFrame().getWidth() == 500) {
            gameStarted = true;
            decreaseSize = false;
        }
    }
    private void decreaseSize() {
        if (gameModel.getEpsilon().getFrame().getWidth() > 300) {
            gameModel.getEpsilon().getFrame().setWidth(gameModel.getEpsilon().getFrame().getWidth()-0.1);
        }
        if (gameModel.getEpsilon().getFrame().getHeight() > 300) {
            gameModel.getEpsilon().getFrame().setHeight(gameModel.getEpsilon().getFrame().getHeight()-0.1);
        }
        gameModel.getEpsilon().setInFrame();
    }

//    private void moveEnemies()  {
//        diedEnemies = new ArrayList<>();
//        for (int i = 0; i < gameModel.getEnemies().size(); i++) {
//            gameModel.getEnemies().get(i).move();
//        }
//        for (int i = 0; i < gameModel.getEnemies().size(); i++) {
//            Enemy enemy = gameModel.getEnemies().get(i);
//            Point point = enemy.getCollisionPoint(gameModel.getEpsilon(), enemy.getVertexes());
//            if (point != null) {
//                enemy.impact(point, gameModel.getEpsilon());
//                AudioController.addCollisionSound();
//            }
//            for (int j = i+1; j < gameModel.getEnemies().size(); j++) {
//                Enemy enemy2 = gameModel.getEnemies().get(j);
//                point = enemy.getCollisionPoint(enemy2, enemy.getVertexes());
//                if (point != null) {
//                    enemy.impact(point, enemy2);
//                    AudioController.addCollisionSound();
//                }
//            }
//        }
//    }

    private void moveBullets() {
        for (int i = 0; i < gameModel.getBullets().size(); i++) {
            gameModel.getBullets().get(i).move();
        }
    }
    private void moveEnemiesBullets() {
        for (int i = 0; i < gameModel.getEnemiesBullets().size(); i++) {
            gameModel.getEnemiesBullets().get(i).move();
        }
    }
    private void checkEnemiesBulletsCollision() {
        for (int i = 0; i < gameModel.getEnemiesBullets().size(); i++) {
            BulletModel bullet = gameModel.getEnemiesBullets().get(i);
            if (!checkBulletCollisionWithFrames(bullet)) {
                Point collisionPoint = bullet.getCollisionPoint(gameModel.getEpsilon());
                if (collisionPoint != null) {
                    bulletCollided(bullet, collisionPoint);
                    gameModel.getEpsilon().decreaseHP(bullet.getDamage());
                }
            }
        }
    }
    private void nextWave() {
        if (currentWave != null) {
            gameModel.setTotalPR(gameModel.getTotalPR() + currentWave.getProgressRate());
        }
        currentWave = new Wave(wave, gameModel.getWaves().get(wave));
        wave++;
    }
    private void checkBulletsCollision() {
        vanishedBullets = new ArrayList<>();
        diedEnemies = new ArrayList<>();
        for (int i = 0; i < gameModel.getBullets().size(); i++) {
            BulletModel bullet = gameModel.getBullets().get(i);
            if (!checkBulletCollisionWithFrames(bullet)) {
                checkBulletCollisionWithEnemies(bullet);
            }
        }
        GameManagerHelper.removeFrom(gameModel.getEnemies(), diedEnemies);
        GameManagerHelper.removeFrom(gameModel.getBullets(), vanishedBullets);
    }
    private boolean checkBulletCollisionWithFrames(BulletModel bulletModel) {
        for (int i = 0; i < gameModel.getFrames().size(); i++) {
            if (GameManagerHelper.checkFrameCollisionWithBullet(bulletModel, gameModel.getFrames().get(i))) {
                vanishedBullets.add(bulletModel);
                Controller.removeBulletView(bulletModel);
                return true;
            }
        }
        return false;
    }
    private void checkBulletCollisionWithEnemies(BulletModel bullet) {
        for (int i = 0; i < gameModel.getEnemies().size(); i++) {
            Enemy enemy = gameModel.getEnemies().get(i);
            if (enemy instanceof BlackOrb) {
                checkCollisionWithBlackOrb(bullet, (BlackOrb) enemy);
            }
            else {
                checkCollisionWithEnemy(bullet, enemy);
            }
        }
    }
    private void checkCollisionWithEnemy(BulletModel bullet, Enemy enemy) {
        if (Math.abs(enemy.getX() - bullet.getX2()) < 40 && Math.abs(enemy.getY() - bullet.getY2()) < 40) {
            Point point = bullet.getCollisionPoint(enemy);
            if (point != null) {
                Impactable.impactOnOthers(point);
                vanishedBullets.add(bullet);
                Controller.removeBulletView(bullet);
                enemy.decreaseHP(bullet.getDamage());
            }
        }
    }
    private void checkCollisionWithBlackOrb(BulletModel bullet, BlackOrb blackOrb) {
        ArrayList<BlackOrbVertex> vertices = blackOrb.getBlackOrbVertices();
        for (int i = 0; i < vertices.size(); i++) {
            if (Math.abs(vertices.get(i).getCenter().getX() - bullet.getX2()) < 40 &&
                    Math.abs(vertices.get(i).getCenter().getY() - bullet.getY2()) < 40) {
                Point point = bullet.getCollisionPoint(vertices.get(i));
                if (point != null) {
                    bulletCollided(bullet, point);
                    vertices.get(i).decreaseHP(bullet.getDamage());
                }
            }
        }
    }
    private void bulletCollided(BulletModel bullet, Point point) {
        Impactable.impactOnOthers(point);
        vanishedBullets.add(bullet);
        Controller.removeBulletView(bullet);
    }
    private void checkCollectives() {
        takenCollectives = new ArrayList<>();
        for (int i = 0; i < gameModel.getCollectives().size(); i++) {
            Collective collective = gameModel.getCollectives().get(i);
            Point point = gameModel.getEpsilon().getCollisionPoint(gameModel.getCollectives().get(i));
            if (point != null){
                takenCollectives.add(collective);
                gameModel.getEpsilon().setXP(gameModel.getEpsilon().getXP()+5+ gameModel.getEnemyXP());
                Controller.removeCollectiveView(collective);
                AudioController.addXPCollectingSound();
            }
            else {
                long currentTime = System.currentTimeMillis();
                if (currentTime- collective.getTime() >= 6000) {
                    takenCollectives.add(collective);
                    Controller.removeCollectiveView(collective);
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
        moveEnemies();
        moveBullets();
        gameModel.getEpsilon().nextMove();
        checkBulletsCollision();
        moveEnemiesBullets();
        checkEnemiesBulletsCollision();
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
    private void moveEnemies() {
        for (int i = 0; i < gameModel.getEnemies().size(); i++) {
            gameModel.getEnemies().get(i).nextMove();
        }
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
            Controller.removeCollectiveView(gameModel.getCollectives().get(i));
        }
        for (int i = 0; i < gameModel.getBullets().size(); i++) {
            Controller.removeBulletView(gameModel.getBullets().get(i));
        }
        for (int i = 0; i < gameModel.getEnemies().size(); i++) {
            Controller.removeEnemyView(gameModel.getEnemies().get(i));
        }
        Controller.gameFinished = true;
        GameManager game = GameManager.getINSTANCE();
        game.setTotalXP(game.getTotalXP()+gameModel.getEpsilon().getXP());
    }
//    public void destroyFrame() {
//        if (gameModel.isFinished()) {
//            if (gameModel.getWidth() >= 2) {
//                gameModel.setWidth(gameModel.getWidth()-5);
//            }
//            if (gameModel.getHeight() >= 2) {
//                gameModel.setHeight(gameModel.getHeight()-5);
//            }
//            if (gameModel.getWidth() <= 2 && gameModel.getHeight() <= 2) {
//                gameModel.setFinished(false);
//                Controller.gameOver(gameModel.getEpsilon().getXP());
//            }
//        }
//    }

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
        synchronized (gameModel.getEnemyLock()) {
            return diedEnemies;
        }
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
