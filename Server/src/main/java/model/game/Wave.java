package model.game;

import controller.game_manager.GameManager;
import controller.game_manager.GameManagerHelper;
import controller.audio.AudioController;
import model.game.enemies.Enemy;
import model.game.enemies.smiley.Smiley;
import model.game.frame.MyFrame;
import model.interfaces.movement.Point;

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

    public Wave(int waveNumber, GameManager gameManager) {
        this.waveNumber = waveNumber;
        this.enemies = waveNumber*2;
        this.gameManager = gameManager;
        gameModel = gameManager.getGameModel();
        startTime = System.currentTimeMillis()/1000;
        waitBeforeNextWave();
        spawn = true;
    }
    private void startWave() {
        addedEnemies = new boolean[4][6];
        gameModel.setEnemies(new ArrayList<>());
        addEnemies(2);
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
    private int getElapsedTime() {
        return (int) (System.currentTimeMillis()/1000-startTime);
    }
    public void newEnemyDied(){
        diedEnemies++;
    }
    public void checkWave(){
            if (diedEnemies >= enemies) {
                if (gameManager.getGameModel().getEnemies().size() == 0) {
                    gameManager.nextWave();
                } else {
                    spawn = false;
                }
            }
            else if (getElapsedTime() != 0) {
                if (System.currentTimeMillis() - lastSpawning > 10000 / Math.pow(getElapsedTime(), 1d/3) && spawn && !gameModel.isWait()) {
                    addEnemies(3);
                }
            }
    }
    private void addEnemies(int n){
        AudioController.addEnemyEnteringSound();
        for (int i = 0; i < n; i++) {
            gameManager.addEnemy(waveNumber);
        }
        lastSpawning = System.currentTimeMillis();
    }

    public int getDiedEnemies() {
        return diedEnemies;
    }
}
