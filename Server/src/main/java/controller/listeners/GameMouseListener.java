package controller.listeners;

import controller.GameManager;
import model.game.frame.MyFrame;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameMouseListener extends MouseAdapter{
    private static boolean gameRunning;
    public static GameMouseListener INSTANCE;
    @Override
    public void mouseClicked(MouseEvent e) {
        if (gameRunning) {
            super.mouseClicked(e);
            int x = 0;
            int y = 0;
            if (GameManager.getINSTANCE().getGameModel().isQuake()){
                MyFrame myFrame = GameManager.getINSTANCE().getGameModel().getInitialFrame();
                x = (int) myFrame.getX()+(int)(Math.random()* myFrame.getWidth());
                y = (int) myFrame.getY()+(int)(Math.random()* myFrame.getHeight());
            }
            else {
                x = (int) MouseInfo.getPointerInfo().getLocation().getX();
                y = (int) MouseInfo.getPointerInfo().getLocation().getY();
            }
            GameManager.getINSTANCE().getGameModel().getEpsilon().shootBullet(x,y);
        }

    }



    public static void setGameRunning(boolean gameRunning) {
        GameMouseListener.gameRunning = gameRunning;
    }

    public static GameMouseListener getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new GameMouseListener();
        }
        return INSTANCE;
    }
}
