package model.game;

import controller.GameManager;
import controller.GameManagerHelper;
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
    private boolean spawn;

    public Wave(int waveNumber, int enemies) {
        this.waveNumber = waveNumber;
        this.enemies = enemies;
        gameModel = GameManager.getINSTANCE().getGameModel();
        startTime = System.currentTimeMillis()/1000;
        waitBeforeNextWave();
        spawn = true;
    }
    private void addEnemy() {
        int enemyNumber = 0;
        if (waveNumber > 2){
            enemyNumber = (int)(Math.random()*8);
        }
        else {
            enemyNumber = (int)(Math.random()*6);
        }
        MyFrame myFrame = gameModel.getInitialFrame();
        Point position = GameManagerHelper.getRandomPosition(myFrame.getWidth(), myFrame.getHeight());
        Enemy enemy = GameManagerHelper.getNewEnemy(new Point(myFrame.getX()+ position.getX(),
                myFrame.getY()+ position.getY()), gameModel.getEnemyHP(), gameModel.getEnemyVelocity(), enemyNumber);
        gameModel.getEnemies().add(enemy);
    }
    private void startWave() {
        addedEnemies = new boolean[4][6];
        gameModel.setEnemies(new ArrayList<>());
        if (waveNumber != 6) {
            addEnemies(2);
        }
        else {
            Smiley smiley = new Smiley(new Point(200, 200), gameModel.getEnemyVelocity());
            gameModel.getEnemies().add(smiley);
        }
    }
    private void waitBeforeNextWave() {
        if (waveNumber != 1) {
            GameManager.getINSTANCE().getGameModel().setWait(true);
            AudioController.addWaveEndSound();
            Timer timer = new Timer(6000, new ActionListener() {
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
    public void newEnemyDied(){
        diedEnemies++;
    }
    public void checkWave(){
        if (waveNumber != 6) {
            if (diedEnemies >= enemies) {
                if (GameManager.getINSTANCE().getGameModel().getEnemies().size() == 0) {
                    GameManager.getINSTANCE().nextWave();
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
