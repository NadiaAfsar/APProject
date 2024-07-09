package model;

import controller.Controller;
import controller.GameManager;
import controller.GameManagerHelper;
import controller.SoundController;
import model.enemies.Enemy;
import model.game.GameModel;
import movement.Point;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Wave {
    private boolean[][] addedEnemies;
    private final int waveNumber;
    private final int enemies;
    private final long startTime;
    private final GameModel gameModel;

    public Wave(int waveNumber, int enemies) {
        this.waveNumber = waveNumber;
        this.enemies = enemies;
        gameModel = GameManager.getINSTANCE().getGameModel();
        startTime = System.currentTimeMillis();
        waitBeforeNextWave();
    }
    private void addEnemy() {
        Point position = GameManagerHelper.getRandomPosition(addedEnemies, gameModel.getWidth(), gameModel.getHeight());
        Enemy enemy = GameManagerHelper.getNewEnemy(position, gameModel.getEnemyHP(), gameModel.getEnemyVelocity());
        gameModel.getEnemies().add(enemy);
        Controller.addEnemyView(enemy);
    }
    private void startWave() {
        SoundController.addEnemyEnteringSound();
        addedEnemies = new boolean[4][6];
        gameModel.setEnemies(new ArrayList<>());
        for (int i = 0; i < enemies; i++) {
            addEnemy();
        }
    }
    private void waitBeforeNextWave() {
        if (waveNumber != 1) {
            GameManager.getINSTANCE().setWait(true);
            SoundController.addWaveEndSound();
            Timer timer = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startWave();
                    GameManager.getINSTANCE().setWait(false);
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
        return 10 * gameModel.getEpsilon().getXP() * (gameModel.getTotalPR()+getProgressRate()) / gameModel.getEpsilon().getHP();
    }
    private int getElapsedTime() {
        return (int) (System.currentTimeMillis()/1000-startTime);
    }
}
