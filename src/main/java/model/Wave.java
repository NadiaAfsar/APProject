package model;

import controller.GameManager;
import controller.GameManagerHelper;
import controller.audio.AudioController;
import model.enemies.Enemy;
import model.frame.Frame;
import model.game.GameModel;
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
    private final GameModel gameModel;

    public Wave(int waveNumber, int enemies) {
        this.waveNumber = waveNumber;
        this.enemies = enemies;
        gameModel = GameManager.getINSTANCE().getGameModel();
        startTime = System.currentTimeMillis();
        waitBeforeNextWave();
    }
    private void addEnemy() {
        Frame frame = gameModel.getEpsilon().getFrame();
        Point position = GameManagerHelper.getRandomPosition(addedEnemies, frame.getWidth(), frame.getHeight());
        Enemy enemy = GameManagerHelper.getNewEnemy(new Point(frame.getX()+ position.getX(),
                frame.getY()+ position.getY()), gameModel.getEnemyHP(), gameModel.getEnemyVelocity());
        gameModel.getEnemies().add(enemy);
    }
    private void startWave() {
        AudioController.addEnemyEnteringSound();
        addedEnemies = new boolean[4][6];
        gameModel.setEnemies(new ArrayList<>());
        for (int i = 0; i < 1; i++) {
            addEnemy();
        }
    }
    private void waitBeforeNextWave() {
        if (waveNumber != 1) {
            GameManager.getINSTANCE().getGameModel().setWait(true);
            AudioController.addWaveEndSound();
            Timer timer = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startWave();
                    GameManager.getINSTANCE().getGameModel().setWait(false);
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
