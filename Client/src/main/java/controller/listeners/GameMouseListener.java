package controller.listeners;

import controller.game_manager.GameManager;
import model.game.frame.MyFrame;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameMouseListener extends MouseAdapter{
    private GameManager gameManager;

    public GameMouseListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (gameManager.isRunning()) {
            super.mouseClicked(e);
            int x = 0;
            int y = 0;
            if (gameManager.getGameModel().isQuake()){
                MyFrame myFrame = gameManager.getGameModel().getInitialFrame();
                x = (int) myFrame.getX()+(int)(Math.random()* myFrame.getWidth());
                y = (int) myFrame.getY()+(int)(Math.random()* myFrame.getHeight());
            }
            else {
                x = (int) MouseInfo.getPointerInfo().getLocation().getX();
                y = (int) MouseInfo.getPointerInfo().getLocation().getY();
            }
            gameManager.getGameModel().getMyEpsilon().shootBullet(x,y);
            if (gameManager.isOnline()){
                gameManager.getApplicationManager().getClientHandler().shootBullet(x,y);
            }
        }

    }

}
