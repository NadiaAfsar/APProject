package controller;


import controller.audio.AudioController;
import controller.listeners.GameMouseListener;
import controller.listeners.GameMouseMotionListener;
import controller.save.Configs;
import controller.save.ReaderWriter;
import controller.update.ModelLoop;
import controller.update.ViewLoop;
import model.game.*;
import model.game.enemies.Enemy;
import model.game.enemies.mini_boss.Barricados;
import model.game.enemies.mini_boss.black_orb.BlackOrb;
import model.game.enemies.mini_boss.black_orb.BlackOrbVertex;
import model.game.enemies.smiley.Fist;
import model.game.skills.Skill;
import model.game.skills.attack.WritOfAres;
import model.game.skills.attack.WritOfAstrape;
import model.game.skills.attack.WritOfCerberus;
import model.game.skills.defence.WritOfAceso;
import model.game.skills.defence.WritOfAthena;
import model.game.skills.defence.WritOfChiron;
import model.game.skills.defence.WritOfMelampus;
import model.game.skills.transform.WritOfDolus;
import model.game.skills.transform.WritOfEmpusa;
import model.game.skills.transform.WritOfProteus;
import model.interfaces.collision.Impactable;
import model.interfaces.movement.Point;
import network.ClientHandler;
import view.game.GameView;
import view.menu.GameFrame;

import java.util.ArrayList;

public class GameManager {
    private int totalXP;
    private int difficulty;
    private int sensitivity;
    private Skill pickedSkill;
    private boolean running;
    private static GameModel gameModel;
    private static GameView gameView;
    private static GameFrame gameFrame;
    public static ReaderWriter readerWriter;
    private ArrayList<Skill> unlockedSkills;
    private boolean deimos;
    private long deimosActivated;
    private boolean hypnos;
    private long hypnosActivated;
    private boolean phonoi;
    private long phonoiUsed;
    private boolean saved;
    private ClientHandler clientHandler;
    private GameMouseListener gameMouseListener;
    private GameMouseMotionListener gameMouseMotionListener;
    private boolean online;
    public GameManager(ClientHandler clientHandler, boolean online) {
        this.clientHandler = clientHandler;
        this.online = online;
        gameMouseListener = new GameMouseListener(this);
        gameMouseMotionListener = new GameMouseMotionListener(this);
    }
    public void initialize(){
        totalXP = 10000;
        sensitivity = 2;
        difficulty = 1;
        unlockedSkills = new ArrayList<>();
        setSkills();
        gameFrame = new GameFrame(this, online);
        new ModelLoop(this).start();
        new ViewLoop(this).start();
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
        gameView = new GameView(this);
        setGameModel();
    }
    private void setGameModel(){
            if (difficulty == 1) {
                gameModel = new EasyGame(this);
            } else if (difficulty == 2) {
                gameModel = new MediumGame(this);
            } else {
                gameModel = new HardGame(this);
            }
            if (pickedSkill != null) {
                if (pickedSkill instanceof WritOfDolus) {
                    ((WritOfDolus) pickedSkill).pickSkills(this);
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
            new CheckPoint(this);
            gameModel.setTotalPR(gameModel.getTotalPR() + gameModel.getCurrentWave().getProgressRate());
            gameModel.setKilledEnemies(gameModel.getKilledEnemies()+gameModel.getCurrentWave().getDiedEnemies());
        }
        gameModel.setCurrentWave(new Wave(gameModel.getWave(), gameModel.getEnemiesToKill().get(gameModel.getWave()), this));
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
                    Controller.removeBulletView(bulletModel, this);
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
        Impactable.impactOnOthers(point, this);
        vanishedBullets.add(bullet);
        Controller.removeBulletView(bullet, this);
    }
    private void checkCollectibles() {
        gameModel.setTakenCollectibles(new ArrayList<>());
        for (int i = 0; i < gameModel.getCollectibles().size(); i++) {
            Collectible collectible = gameModel.getCollectibles().get(i);
            Point point = gameModel.getEpsilon().getCollisionPoint(gameModel.getCollectibles().get(i));
            if (point != null){
                gameModel.getTakenCollectibles().add(collectible);
                gameModel.getEpsilon().setXP(gameModel.getEpsilon().getXP()+5+ gameModel.getEnemyXP());
                Controller.removeCollectibleView(collectible, this);
                AudioController.addXPCollectingSound();
            }
            else {
                long currentTime = System.currentTimeMillis();
                if (currentTime- collectible.getTime() >= 6000) {
                    gameModel.getTakenCollectibles().add(collectible);
                    Controller.removeCollectibleView(collectible, this);
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
                ((WritOfAceso) pickedSkill).increaseHP(this);
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
        Controller.endGame(this);
        AudioController.addWinningSound();
        Controller.removeEpsilonVertexes(this);
        for (int i = 0; i < gameModel.getCollectibles().size(); i++) {
            Controller.removeCollectibleView(gameModel.getCollectibles().get(i), this);
        }
        for (int i = 0; i < gameModel.getBullets().size(); i++) {
            Controller.removeBulletView(gameModel.getBullets().get(i), this);
        }
        for (int i = 0; i < gameModel.getEnemies().size(); i++) {
            Controller.removeEnemyView(gameModel.getEnemies().get(i), this);
        }
        Controller.gameRunning = true;
        Controller.gameFinished = true;
        setTotalXP(totalXP+gameModel.getEpsilon().getXP());
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
                        gameModel.getSuccessfulBullets(), gameModel.getKilledEnemies(), this);
            }
        }
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
        EpsilonModel epsilonModel = gameModel.getEpsilon();
        if (epsilonModel.getXP() >= 75) {
            activateAthena();
            epsilonModel.setXP(epsilonModel.getXP()-75);
            return true;
        }
        return false;
    }
    public boolean apollo() {
        EpsilonModel epsilonModel = gameModel.getEpsilon();
        if (epsilonModel.getXP() >= 50) {
            epsilonModel.setHP(epsilonModel.getHP()+10);
            epsilonModel.setXP(epsilonModel.getXP()-50);
            return true;
        }
        return false;
    }
    public boolean hephaestus() {
        EpsilonModel epsilon = gameModel.getEpsilon();
        if (epsilon.getXP() >= 100) {
            Impactable.impactOnOthers(new Point(epsilon.getCenter().getX(), epsilon.getCenter().getY()), this);
            epsilon.setXP(epsilon.getXP()-100);
            return true;
        }
        return false;
    }
    public boolean deimos(){
        EpsilonModel epsilon = gameModel.getEpsilon();
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
        EpsilonModel epsilon = gameModel.getEpsilon();
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
        EpsilonModel epsilon = gameModel.getEpsilon();
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
    public void stopGame(){
        for (int i = 0; i < gameModel.getEnemies().size(); i++){
            gameModel.getEnemies().get(i).setDied(true);
        }
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public boolean isOnline() {
        return online;
    }

    public boolean isRunning() {
        return running;
    }

    public GameMouseListener getGameMouseListener() {
        return gameMouseListener;
    }

    public GameMouseMotionListener getGameMouseMotionListener() {
        return gameMouseMotionListener;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
