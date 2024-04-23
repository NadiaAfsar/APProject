package controller;

import model.BulletModel;
import model.EpsilonModel;
import model.GameModel;
import model.enemies.Enemy;
import view.BulletView;
import view.EpsilonView;
import view.GameView;
import view.enemies.EnemyView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Update {
    private Timer frameUpdateTimer;
    private Timer modelUpdateTimer;
    public Update() {
        frameUpdateTimer = new Timer((int)Constants.FRAME_UPDATE_TIME, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateView();
            }
        });
        frameUpdateTimer.start();
        modelUpdateTimer = new Timer((int) Constants.MODEL_UPDATE_TIME, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateModel();
            }
        });
        modelUpdateTimer.start();
    }
    private void updateView() {
        if (GameView.INSTANCE != null) {
            GameModel gameModel = GameModel.getINSTANCE();
            GameView.getINSTANCE().update(gameModel.getX(), gameModel.getY(), gameModel.getWidth(), GameModel.getINSTANCE().getHeight());
            updateEnemies();
            updateBullets();
            GameView.getINSTANCE().update();
        }
        if (EpsilonView.INSTANCE != null) {
            EpsilonView.getINSTANCE().update(EpsilonModel.getINSTANCE().getX(), EpsilonModel.getINSTANCE().getY());
        }
    }
    private void updateModel() {
        if (GameModel.INSTANCE != null) {
            GameModel gameModel = GameModel.getINSTANCE();
            gameModel.decreaseSize();
            gameModel.moveEnemies();
            gameModel.moveBullets();
            if (gameModel.isGameStarted() && gameModel.getEnemies().size() == 0) {
                gameModel.nextWave();
            }
            EpsilonModel.getINSTANCE().move();
        }
    }
    private void updateEnemies() {
        ArrayList<EnemyView> enemiesView = GameView.getINSTANCE().getEnemies();
        ArrayList<Enemy> enemies = GameModel.getINSTANCE().getEnemies();
        for (int i = 0; i < enemiesView.size(); i++) {
            enemiesView.get(i).update(enemies.get(i).getCenter(), enemies.get(i).getAngle());
        }
    }
    private void updateBullets() {
        ArrayList<BulletView> bulletsView = GameView.getINSTANCE().getBullets();
        ArrayList<BulletModel> bullets = GameModel.getINSTANCE().getBullets();
        for (int i = 0; i < bulletsView.size(); i++) {
            bulletsView.get(i).update((int)bullets.get(i).getX1(), (int)bullets.get(i).getY1());
        }
    }

}
