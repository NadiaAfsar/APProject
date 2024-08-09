package model.game;

import controller.game_manager.Colosseum;
import controller.game_manager.GameManager;
import controller.game_manager.GameManagerHelper;
import controller.audio.AudioController;
import controller.game_manager.Monomachia;
import model.game.enemies.Enemy;
import model.game.enemies.smiley.Smiley;
import model.game.frame.MyFrame;
import model.game_model.GameModel;
import model.interfaces.movement.Point;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Wave {
    private boolean[][] addedEnemies;
    private final int waveNumber;
    private final int enemies;
    private final long startTime;
    private long lastSpawning;
    private int diedEnemies;
    private final GameModel gameModel;
    GameManager gameManager;
    private boolean spawn;
    private Logger logger;

    public Wave(int waveNumber, int enemies, GameManager gameManager) {
        logger = Logger.getLogger(Wave.class);
        this.waveNumber = waveNumber;
        this.enemies = enemies;
        this.gameManager = gameManager;
        gameModel = gameManager.getGameModel();
        startTime = System.currentTimeMillis()/1000;
        waitBeforeNextWave();
        spawn = true;
    }
    private void addEnemy() {
        int enemies = 7;
        if (waveNumber > 2){
            enemies = 9;
        }
        if (gameManager instanceof Monomachia){
            if (gameManager.getGameModel().getEpsilonNumber() == 0) {
                gameManager.getApplicationManager().getClientHandler().addEnemy(enemies);
            }
        }
        else if (gameManager instanceof Colosseum){
            gameManager.getApplicationManager().getClientHandler().addEnemy(enemies);
        }
        else {
            int enemyNumber = (int)(Math.random()*enemies);
            Point position = GameManagerHelper.getRandomPosition(400, 400);
            Enemy enemy = GameManagerHelper.getNewEnemy(position, gameModel.getEnemyHP(), gameModel.getEnemyVelocity(), enemyNumber,
                    gameManager.getGameModel().getMyEpsilon());
            gameModel.getEnemies().add(enemy);
        }
    }
    private void startWave() {
        addedEnemies = new boolean[4][6];
        gameModel.setEnemies(new ArrayList<>());
        if (waveNumber != 6 || gameManager instanceof Monomachia) {
            addEnemies(1);
        }
        else {
            Smiley smiley = new Smiley(new Point(200, 200), 0, gameModel.getEnemyVelocity(), gameManager, gameManager.getGameModel().getMyEpsilon());
            gameModel.getEnemies().add(smiley);
        }
    }
    private void waitBeforeNextWave() {
        if (waveNumber != 1) {
            gameManager.getGameModel().setWait(true);
            AudioController.addWaveEndSound();
            Timer timer = new Timer(6000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startWave();
                    gameManager.getGameModel().setWait(false);
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
        else {
            startWave();
        }
    }
    public int getProgressRate() {
        return waveNumber * getElapsedTime();
    }
    public double getProgressRisk() {
        return 10 * gameModel.getMyEpsilon().getXP() * (gameModel.getTotalPR()+getProgressRate()) / gameModel.getMyEpsilon().getHP();
    }
    private int getElapsedTime() {
        return (int) (System.currentTimeMillis()/1000-startTime);
    }
    public void newEnemyDied(){
        diedEnemies++;
    }
    public void checkWave(){
        if (waveNumber != 6) {
            if (diedEnemies >= enemies) {
                if (gameManager.getGameModel().getEnemies().size() == 0) {
                    gameManager.nextWave();
                } else {
                    spawn = false;
                }
            }
            else if (getElapsedTime() != 0) {
                if (System.currentTimeMillis() - lastSpawning > 100000 / Math.pow(getElapsedTime(), 1d/4) && spawn && !gameModel.isWait()) {
                    addEnemies(1);
                }
            }
        }
    }
    private void addEnemies(int n){
        AudioController.addEnemyEnteringSound();
        for (int i = 0; i < n; i++) {
            addEnemy();
        }
        lastSpawning = System.currentTimeMillis();
    }

    public int getDiedEnemies() {
        return diedEnemies;
    }
}
