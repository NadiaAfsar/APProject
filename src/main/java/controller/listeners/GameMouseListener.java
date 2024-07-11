package controller.listeners;

import controller.GameManager;

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
            int x = (int) MouseInfo.getPointerInfo().getLocation().getX() - GameManager.getINSTANCE().getGameModel().getX();
            int y = (int) MouseInfo.getPointerInfo().getLocation().getY() - GameManager.getINSTANCE().getGameModel().getY();
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
