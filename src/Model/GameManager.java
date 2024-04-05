package Model;

import Controller.GameViewConnector;
import View.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class GameManager {
    private GameViewConnector gameViewConnector;
    private Game gamePanel;
    private Epsilon epsilon;
    private Timer decreaseTimer;
    public GameManager() {
        gameViewConnector = new GameViewConnector(this);
        gameViewConnector.newFrame();
    }

    public void Start(Game gamePanel) throws IOException {
        this.gamePanel = gamePanel;
        epsilon = new Epsilon(gamePanel);
        addDecreaseTimer();
    }
    private void addDecreaseTimer() {
        decreaseTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decreaseSize();
            }
        });
        decreaseTimer.start();
    }
    private void decreaseSize() {
        gamePanel.decreaseSize();
        epsilon.decreaseSize();
        if (gamePanel.getWidth() == 200) {
            decreaseTimer.stop();
        }
    }
}
