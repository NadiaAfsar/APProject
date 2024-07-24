package controller.listeners;

import controller.GameManager;
import model.frame.Frame;

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
                Frame frame = GameManager.getINSTANCE().getGameModel().getInitialFrame();
                x = (int)frame.getX()+(int)(Math.random()* frame.getWidth());
                y = (int)frame.getY()+(int)(Math.random()* frame.getHeight());
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
