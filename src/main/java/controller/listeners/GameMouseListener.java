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
            int x = (int) MouseInfo.getPointerInfo().getLocation().getX() - (int)GameManager.getINSTANCE().getGameModel().getEpsilon().getFrame().getX();
            int y = (int) MouseInfo.getPointerInfo().getLocation().getY() - (int)GameManager.getINSTANCE().getGameModel().getEpsilon().getFrame().getY();
            GameManager.getINSTANCE().getGameModel().getEpsilon().shotBullet(x,y);
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
