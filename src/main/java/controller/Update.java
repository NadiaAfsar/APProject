package controller;

import model.EpsilonModel;
import model.GameModel;
import view.EpsilonView;
import view.GameView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
                updateModel();
            }
        });
        modelUpdateTimer.start();
    }
    private void updateView() throws IOException {
        if (GameView.INSTANCE != null) {
            GameModel gameModel = GameModel.getINSTANCE();
            GameView.getINSTANCE().update(gameModel.getX(), gameModel.getY(), gameModel.getWidth(), GameModel.getINSTANCE().getHeight());
        }
        if (EpsilonView.INSTANCE != null) {
            EpsilonView.getINSTANCE().update(EpsilonModel.getINSTANCE().getX(), EpsilonModel.getINSTANCE().getY());
        }
    }
    private void updateModel() {
        if (GameModel.INSTANCE != null) {
            GameModel.getINSTANCE().decreaseSize();
        }
    }
}
