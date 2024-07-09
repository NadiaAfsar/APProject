package controller;

import model.BulletModel;
import model.EpsilonModel;
import model.game.GameModel;
import model.enemies.Enemy;
import view.game.BulletView;
import view.game.EpsilonView;
import view.game.GameView;
import view.game.enemies.EnemyView;

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
            if (GameManager.getINSTANCE().getGameView() != null) {
                GameModel gameModel = GameManager.getINSTANCE().getGameModel();
                EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
                GameView gameView = GameManager.getINSTANCE().getGameView();
                EpsilonView epsilonView = GameManager.getINSTANCE().getGameView().getEpsilonView();
                gameView.update(gameModel.getX(), gameModel.getY(), (int)gameModel.getWidth(), (int)gameModel.getHeight()
                        , epsilon.getHP(), epsilon.getXP(), GameManager.getINSTANCE().getWave(),
                        GameManager.getINSTANCE().getTimePlayed(), GameManager.getINSTANCE().getPickedSkill());
                updateEnemies();
                updateBullets();
                gameView.update();
                epsilonView.update(epsilon.getX(), epsilon.getY());
                epsilonView.updateVertexes(epsilon.getVertexes());
            }

        }
        else if (Controller.gameFinished) {
            EpsilonModel epsilonModel = GameManager.getINSTANCE().getGameModel().getEpsilon();
            GameManager.getINSTANCE().getGameView().getEpsilonView().increaseSize(epsilonModel.getX(), epsilonModel.getY(), epsilonModel.getRadius());
            GameModel game = GameManager.getINSTANCE().getGameModel();
            GameManager.getINSTANCE().getGameView().destroy((int)game.getWidth(), (int)game.getHeight());
        }
    }
    private void updateModel() {
        if (Controller.gameRunning) {
            if (GameManager.getINSTANCE().getGameModel() != null) {
                GameManager.getINSTANCE().update();
            }
        }
        else if (Controller.gameFinished) {
            GameManager.getINSTANCE().getGameModel().getEpsilon().increaseSize();
            GameManager.getINSTANCE().destroyFrame();
        }
    }
    private void updateEnemies() {
        Map<String, EnemyView> enemiesView = GameManager.getINSTANCE().getGameView().getEnemies();
        ArrayList<Enemy> enemies = GameManager.getINSTANCE().getGameModel().getEnemies();
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            enemiesView.get(enemy.getID()).update(enemy.getCenter(), enemy.getAngle());
        }
    }
    private void updateBullets() {
        Map<String, BulletView> bulletsView = GameManager.getINSTANCE().getGameView().getBullets();
        ArrayList<BulletModel> bullets = GameManager.getINSTANCE().getGameModel().getBullets();
        for (int i = 0; i < bullets.size(); i++) {
            BulletModel bullet = bullets.get(i);
            bulletsView.get(bullet.getID()).update((int)bullet.getX1(), (int)bullet.getY1());
        }
    }

}
