package controller;


import controller.save.Configs;
import controller.save.ReaderWriter;
import controller.update.ModelLoop;
import controller.update.ViewLoop;
import model.*;
import model.enemies.mini_boss.Barricados;
import model.enemies.mini_boss.black_orb.BlackOrb;
import model.enemies.mini_boss.black_orb.BlackOrbVertex;
import model.enemies.smiley.Fist;
import model.interfaces.collision.Impactable;
import controller.audio.AudioController;
import model.enemies.Enemy;
import model.game.EasyGame;
import model.game.GameModel;
import model.game.HardGame;
import model.game.MediumGame;
import model.skills.Skill;
import model.skills.attack.WritOfAres;
import model.skills.attack.WritOfAstrape;
import model.skills.attack.WritOfCerberus;
import model.skills.defence.WritOfAceso;
import model.interfaces.movement.Point;
import model.skills.defence.WritOfAthena;
import model.skills.defence.WritOfChiron;
import model.skills.defence.WritOfMelampus;
import model.skills.transform.WritOfDolus;
import model.skills.transform.WritOfEmpusa;
import model.skills.transform.WritOfProteus;
import view.menu.GameFrame;
import view.game.GameView;

import java.util.ArrayList;

public class GameManager {
    private int totalXP;
    private static GameManager INSTANCE;
    private int difficulty;
    private int sensitivity;
    private Skill pickedSkill;
    private static GameModel gameModel;
    private static GameView gameView;
    private static GameFrame gameFrame;
    public static Configs configs;
    public static ReaderWriter readerWriter;
    private ArrayList<Skill> unlockedSkills;
    private boolean deimos;
    private long deimosActivated;
    private boolean hypnos;
    private long hypnosActivated;
    private boolean phonoi;
    private long phonoiUsed;
    private boolean saved;
    private GameManager() {
        totalXP = 10000;
        sensitivity = 2;
        difficulty = 1;
        configs = readerWriter.getConfigs();
        unlockedSkills = new ArrayList<>();
        setSkills();
        gameFrame = new GameFrame();
        new ModelLoop().start();
        new ViewLoop().start();
    }
    private void setSkills(){
        WritOfAres.setBooleans();
        WritOfAstrape.setBooleans();
        WritOfCerberus.setBooleans();
        WritOfAceso.setBooleans();
        WritOfAthena.setBooleans();
        WritOfMelampus.setBooleans();
        WritOfChiron.setBooleans();
        WritOfProteus.setBooleans();
        WritOfDolus.setBooleans();
        WritOfEmpusa.setBooleans();
    }
    public void startGame() {
        gameView = new GameView();
        setGameModel();
    }
    private void setGameModel(){
            if (difficulty == 1) {
                gameModel = new EasyGame();
            } else if (difficulty == 2) {
                gameModel = new MediumGame();
            } else {
                gameModel = new HardGame();
            }
            if (pickedSkill != null) {
                if (pickedSkill instanceof WritOfDolus) {
                    ((WritOfDolus) pickedSkill).pickSkills();
                }
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
    public void nextWave() {
        gameModel.setWave(gameModel.getWave()+1);
        if (gameModel.getCurrentWave() != null) {
            new CheckPoint();
            gameModel.setTotalPR(gameModel.getTotalPR() + gameModel.getCurrentWave().getProgressRate());
            gameModel.setKilledEnemies(gameModel.getKilledEnemies()+gameModel.getCurrentWave().getDiedEnemies());
        }
        gameModel.setCurrentWave(new Wave(gameModel.getWave(), gameModel.getEnemiesToKill().get(gameModel.getWave())));
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
                    gameModel.addSuccessfulBullet();
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
                    gameModel.addSuccessfulBullet();
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
        if (!hypnos) {
            moveEnemiesBullets();
        }
        checkEnemiesBulletsCollision();
        gameModel.getEnemies().removeAll(gameModel.getDiedEnemies());
        gameModel.setDiedEnemies(new ArrayList<>());
        if (getGameModel().getCurrentWave() != null) {
            gameModel.getCurrentWave().checkWave();
        }
        else {
            nextWave();
        }
        checkCollectibles();
        if (pickedSkill != null) {
            if (pickedSkill instanceof WritOfAceso) {
                ((WritOfAceso) pickedSkill).increaseHP();
            }
        }
        if (deimos){
            if (System.currentTimeMillis()-deimosActivated >= 10000){
                deimos = false;
            }
        }
        if (hypnos){
            if (System.currentTimeMillis()-hypnosActivated >= 10000){
                hypnos = false;
            }
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
    public void endGame() {
        saved = false;
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
        Controller.gameRunning = true;
        Controller.gameFinished = true;
        INSTANCE.setTotalXP(totalXP+gameModel.getEpsilon().getXP());
    }
    public void destroyFrame() {
        if (gameModel.isFinished()) {
            if (gameModel.getInitialFrame().getWidth() >= 2) {
                gameModel.getInitialFrame().setWidth(gameModel.getInitialFrame().getWidth()-5);
            }
            if (gameModel.getInitialFrame().getHeight() >= 2) {
                gameModel.getInitialFrame().setHeight(gameModel.getInitialFrame().getHeight()-5);
            }
            if (gameModel.getInitialFrame().getWidth() <= 2 && gameModel.getInitialFrame().getHeight() <= 2) {
                gameModel.setFinished(false);
                Controller.gameOver(gameModel.getEpsilon().getXP(), gameModel.getTimePlayed(), gameModel.getTotalBullets(),
                        gameModel.getSuccessfulBullets(), gameModel.getKilledEnemies());
            }
        }
    }

    public static GameManager getINSTANCE() {
        if (INSTANCE == null) {
            readerWriter = new ReaderWriter();
            GameManager gameManager = readerWriter.getGameManager();
            if (gameManager != null){
                return gameManager;
            }
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

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(int sensitivity) {
        this.sensitivity = sensitivity;
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
    public boolean athena() {
        EpsilonModel epsilonModel = GameManager.getINSTANCE().getGameModel().getEpsilon();
        if (epsilonModel.getXP() >= 75) {
            GameManager.getINSTANCE().activateAthena();
            epsilonModel.setXP(epsilonModel.getXP()-75);
            return true;
        }
        return false;
    }
    public boolean apollo() {
        EpsilonModel epsilonModel = GameManager.getINSTANCE().getGameModel().getEpsilon();
        if (epsilonModel.getXP() >= 50) {
            epsilonModel.setHP(epsilonModel.getHP()+10);
            epsilonModel.setXP(epsilonModel.getXP()-50);
            return true;
        }
        return false;
    }
    public boolean hephaestus() {
        EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
        if (epsilon.getXP() >= 100) {
            Impactable.impactOnOthers(new Point(epsilon.getCenter().getX(), epsilon.getCenter().getY()));
            epsilon.setXP(epsilon.getXP()-100);
            return true;
        }
        return false;
    }
    public boolean deimos(){
        EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
        if (epsilon.getXP() >= 120) {
            deimos = true;
            deimosActivated = System.currentTimeMillis();
            epsilon.setXP(epsilon.getXP()-120);
            return true;
        }
        return false;
    }

    public boolean isDeimos() {
        return deimos;
    }
    public boolean hypnos(){
        EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
        if (epsilon.getXP() >= 150) {
            hypnos = true;
            hypnosActivated = System.currentTimeMillis();
            epsilon.setXP(epsilon.getXP()-150);
            return true;
        }
        return false;
    }

    public boolean isHypnos() {
        return hypnos;
    }

    public boolean phonoi(){
        EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
        if (epsilon.getXP() >= 200 && System.currentTimeMillis()-phonoiUsed >= 120000) {
            phonoi = true;
            epsilon.setXP(epsilon.getXP()-200);
            return true;
        }
        return false;
    }

    public boolean isPhonoi() {
        return phonoi;
    }

    public void setPhonoi(boolean phonoi) {
        this.phonoi = phonoi;
        if (!phonoi){
            phonoiUsed = System.currentTimeMillis();
        }
    }
    public void save(){
        saved = true;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}
