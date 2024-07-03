package controller;

import model.game.GameModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class GameMouseListener extends MouseAdapter{
    private static boolean gameRunning;
    public static GameMouseListener INSTANCE;
    @Override
    public void mouseClicked(MouseEvent e) {
        if (gameRunning) {
            super.mouseClicked(e);
            int x = (int) MouseInfo.getPointerInfo().getLocation().getX() - GameModel.getINSTANCE().getX();
            int y = (int) MouseInfo.getPointerInfo().getLocation().getY() - GameModel.getINSTANCE().getY();
            GameModel.getINSTANCE().shotBullet(x,y);
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
