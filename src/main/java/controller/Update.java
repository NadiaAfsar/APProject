package controller;

import model.BulletModel;
import model.EpsilonModel;
import model.game.GameModel;
import model.enemies.Enemy;
import view.BulletView;
import view.EpsilonView;
import view.GameView;
import view.enemies.EnemyView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

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
        if (Controller.gameRunning) {
            if (GameView.INSTANCE != null) {
                GameModel gameModel = GameModel.getINSTANCE();
                EpsilonModel epsilon = EpsilonModel.getINSTANCE();
                GameView gameView = GameView.getINSTANCE();
                EpsilonView epsilonView = EpsilonView.getINSTANCE();
                gameView.update(gameModel.getX(), gameModel.getY(), gameModel.getWidth(), gameModel.getHeight()
                        , epsilon.getHP(), epsilon.getXP(), gameModel.getWave());
                updateEnemies();
                updateBullets();
                gameView.update();
                epsilonView.update(epsilon.getX(), epsilon.getY());
                epsilonView.updateVertexes(epsilon.getVertexes());
            }

        }
        else if (Controller.gameFinished) {
            EpsilonModel epsilonModel = EpsilonModel.getINSTANCE();
            EpsilonView.getINSTANCE().increaseSize(epsilonModel.getX(), epsilonModel.getY(), epsilonModel.getRadius());
            GameModel game = GameModel.getINSTANCE();
            GameView.getINSTANCE().destroy(game.getWidth(), game.getHeight());
        }
    }
    private void updateModel() {
        if (Controller.gameRunning) {
            if (GameModel.INSTANCE != null) {
                GameModel.getINSTANCE().update();
                EpsilonModel.getINSTANCE().move();
            }
        }
        else if (Controller.gameFinished) {
            EpsilonModel.getINSTANCE().increaseSize();
            GameModel.getINSTANCE().destroyFrame();
        }
    }
    private void updateEnemies() {
        Map<String, EnemyView> enemiesView = GameView.getINSTANCE().getEnemies();
        ArrayList<Enemy> enemies = GameModel.getINSTANCE().getEnemies();
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            enemiesView.get(enemy.getID()).update(enemy.getCenter(), enemy.getAngle());
        }
    }
    private void updateBullets() {
        Map<String, BulletView> bulletsView = GameView.getINSTANCE().getBullets();
        ArrayList<BulletModel> bullets = GameModel.getINSTANCE().getBullets();
        for (int i = 0; i < bullets.size(); i++) {
            BulletModel bullet = bullets.get(i);
            bulletsView.get(bullet.getID()).update((int)bullet.getX1(), (int)bullet.getY1());
        }
    }

}
