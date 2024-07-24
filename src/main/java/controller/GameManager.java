package controller;


import controller.save.Configs;
import controller.save.ReaderWriter;
import controller.update.ModelLoop;
import controller.update.ViewLoop;
import model.enemies.mini_boss.Barricados;
import model.enemies.mini_boss.black_orb.BlackOrb;
import model.enemies.mini_boss.black_orb.BlackOrbVertex;
import model.enemies.smiley.Fist;
import model.interfaces.collision.Impactable;
import controller.audio.AudioController;
import model.BulletModel;
import model.Collectible;
import model.Wave;
import model.enemies.Enemy;
import model.game.EasyGame;
import model.game.GameModel;
import model.game.HardGame;
import model.game.MediumGame;
import model.skills.Skill;
import model.skills.defence.WritOfAceso;
import model.interfaces.movement.Point;
import model.skills.transform.WritOfDolus;
import view.menu.GameFrame;
import view.game.GameView;

import java.util.ArrayList;

public class GameManager {
    private int totalXP;
    private static GameManager INSTANCE;
    private static int difficulty;
    private static int sensitivity;
    private Skill pickedSkill;
    private GameModel gameModel;
    private GameView gameView;
    private GameFrame gameFrame;
    public static Configs configs;
    public static ReaderWriter readerWriter;
    private ArrayList<Skill> unlockedSkills;
    private GameManager() {
        totalXP = 10000;
        sensitivity = 2;
        difficulty = 1;
        readerWriter = new ReaderWriter();
        configs = readerWriter.getConfigs();
        gameFrame = new GameFrame();
        unlockedSkills = new ArrayList<>();
        new ModelLoop().start();
        new ViewLoop().start();
    }
    public void startGame() {
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
        gameModel.setDecreaseSize(true);
        gameModel.setWave(1);
        if (pickedSkill instanceof WritOfDolus){
            ((WritOfDolus) pickedSkill).pickSkills();
        }
    }
    private void initialShrinkage() {
        gameModel.getEpsilon().getFrame().setWidth(gameModel.getEpsilon().getFrame().getWidth() - 4);
        gameModel.getEpsilon().getFrame().setHeight(gameModel.getEpsilon().getFrame().getHeight() - 4);
        gameModel.getEpsilon().getFrame().setX(Configs.FRAME_SIZE.width/2 - gameModel.getEpsilon().getFrame().getWidth()/2);
        gameModel.getEpsilon().getFrame().setY(Configs.FRAME_SIZE.height/2 - gameModel.getEpsilon().getFrame().getHeight()/2);
        gameModel.getEpsilon().setInCenter();
        if (gameModel.getEpsilon().getFrame().getWidth() == 500) {
            gameModel.setGameStarted(true);
            gameModel.setDecreaseSize(false);
        }
    }
    private void shrinkage() {
        for (int i = 0; i < gameModel.getFrames().size(); i++) {
            gameModel.getFrames().get(i).shrinkage();
        }
        gameModel.getEpsilon().setInFrame();
    }


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
            if (!checkBulletCollisionWithFrames(bullet, gameModel.getVanishedEnemiesBullets())) {
                Point collisionPoint = bullet.getCollisionPoint(gameModel.getEpsilon());
                if (collisionPoint != null) {
                    bulletCollided(bullet, collisionPoint, gameModel.getVanishedEnemiesBullets());
                    gameModel.getEpsilon().decreaseHP(bullet.getDamage());
                }
            }
        }
        gameModel.getEnemiesBullets().removeAll(gameModel.getVanishedEnemiesBullets());
        gameModel.setVanishedBullets(new ArrayList<>());
    }
    private void nextWave() {
        if (gameModel.getCurrentWave() != null) {
            gameModel.setTotalPR(gameModel.getTotalPR() + gameModel.getCurrentWave().getProgressRate());
        }
        gameModel.setCurrentWave(new Wave(gameModel.getWave(), gameModel.getWaves().get(gameModel.getWave())));
        gameModel.setWave(gameModel.getWave()+1);
    }
    private void checkBulletsCollision() {
        for (int i = 0; i < gameModel.getBullets().size(); i++) {
            BulletModel bullet = gameModel.getBullets().get(i);
            if (!checkBulletCollisionWithFrames(bullet, gameModel.getVanishedBullets())) {
                checkBulletCollisionWithEnemies(bullet);
            }
        }
        gameModel.getBullets().removeAll(gameModel.getVanishedBullets());
        gameModel.setVanishedBullets(new ArrayList<>());
    }
    private boolean checkBulletCollisionWithFrames(BulletModel bulletModel, ArrayList<BulletModel> vanishedBullets) {
        if (bulletModel.getFrame() != null) {
            if (!bulletModel.getFrame().isInOverLap(bulletModel.getX2(), bulletModel.getY2())) {
                if (GameManagerHelper.checkFrameCollisionWithBullet(bulletModel)) {
                    vanishedBullets.add(bulletModel);
                    Controller.removeBulletView(bulletModel);
                    return true;
                }
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
            Point point = bullet.getCollisionPoint(enemy);
            if (point != null) {
                bulletCollided(bullet, point, gameModel.getVanishedBullets());
                if (!(enemy instanceof Barricados) && !(enemy instanceof Fist)) {
                    enemy.decreaseHP(bullet.getDamage());
                }
            }
    }
    private void checkCollisionWithBlackOrb(BulletModel bullet, BlackOrb blackOrb) {
        ArrayList<BlackOrbVertex> vertices = blackOrb.getBlackOrbVertices();
        for (int i = 0; i < vertices.size(); i++) {
                Point point = bullet.getCollisionPoint(vertices.get(i));
                if (point != null) {
                    bulletCollided(bullet, point, gameModel.getVanishedBullets());
                    vertices.get(i).decreaseHP(bullet.getDamage());
                }
        }
    }
    private void bulletCollided(BulletModel bullet, Point point, ArrayList<BulletModel> vanishedBullets) {
        Impactable.impactOnOthers(point);
        vanishedBullets.add(bullet);
        Controller.removeBulletView(bullet);
    }
    private void checkCollectibles() {
        gameModel.setTakenCollectibles(new ArrayList<>());
        for (int i = 0; i < gameModel.getCollectibles().size(); i++) {
            Collectible collectible = gameModel.getCollectibles().get(i);
            Point point = gameModel.getEpsilon().getCollisionPoint(gameModel.getCollectibles().get(i));
            if (point != null){
                gameModel.getTakenCollectibles().add(collectible);
                gameModel.getEpsilon().setXP(gameModel.getEpsilon().getXP()+5+ gameModel.getEnemyXP());
                Controller.removeCollectibleView(collectible);
                AudioController.addXPCollectingSound();
            }
            else {
                long currentTime = System.currentTimeMillis();
                if (currentTime- collectible.getTime() >= 6000) {
                    gameModel.getTakenCollectibles().add(collectible);
                    Controller.removeCollectibleView(collectible);
                }
            }
        }
        gameModel.getCollectibles().removeAll(gameModel.getTakenCollectibles());
        gameModel.setTakenCollectibles(new ArrayList<>());
    }
    public void update() {
        if (gameModel.isDecreaseSize()) {
            initialShrinkage();
        }
        else {
            shrinkage();
        }
        moveBullets();
        gameModel.getEpsilon().nextMove();
        checkBulletsCollision();
        moveEnemiesBullets();
        checkEnemiesBulletsCollision();
        gameModel.getEnemies().removeAll(gameModel.getDiedEnemies());
        gameModel.setDiedEnemies(new ArrayList<>());
        if (gameModel.isGameStarted() && gameModel.getEnemies().size() == 0 && !gameModel.isWait()) {
            if (gameModel.getWave() == 4) {
                endGame();
            }
            else {
                nextWave();
            }
        }
        checkCollectibles();
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
        for (int i = 0; i < gameModel.getCollectibles().size(); i++) {
            Controller.removeCollectibleView(gameModel.getCollectibles().get(i));
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
    public GameFrame getGameFrame() {
        return gameFrame;
    }
    private void setTimePlayed() {
        long newTime = System.currentTimeMillis();
        long addedTime = newTime - gameModel.getLastSavedTime();
        gameModel.setTimePlayed(gameModel.getTimePlayed()+addedTime);
        gameModel.setLastSavedTime(newTime);
    }

    public ArrayList<Skill> getUnlockedSkills() {
        return unlockedSkills;
    }
}
