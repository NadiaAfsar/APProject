package controller;

import model.EpsilonModel;
import model.GameModel;
import model.enemies.Enemy;
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
                try {
                    updateView();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        frameUpdateTimer.start();
        modelUpdateTimer = new Timer((int) Constants.MODEL_UPDATE_TIME, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateModel();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        modelUpdateTimer.start();
    }
    private void updateView() throws IOException {
        if (GameView.INSTANCE != null) {
            GameModel gameModel = GameModel.getINSTANCE();
            GameView.getINSTANCE().update(gameModel.getX(), gameModel.getY(), gameModel.getWidth(), GameModel.getINSTANCE().getHeight());
            updateEnemies();
        }
        if (EpsilonView.INSTANCE != null) {
            EpsilonView.getINSTANCE().update(EpsilonModel.getINSTANCE().getX(), EpsilonModel.getINSTANCE().getY());
        }
    }
    private void updateModel() throws IOException {
        if (GameModel.INSTANCE != null) {
            GameModel.getINSTANCE().decreaseSize();
            GameModel.getINSTANCE().moveEnemies();
        }
    }
    private void updateEnemies() {
        ArrayList<EnemyView> enemiesView = GameView.getINSTANCE().getEnemies();
        ArrayList<Enemy> enemies = GameModel.getINSTANCE().getEnemies();
        for (int i = 0; i < enemiesView.size(); i++) {
            enemiesView.get(i).update(enemies.get(i).getCenter(), enemies.get(i).getAngle());
        }
    }
}
