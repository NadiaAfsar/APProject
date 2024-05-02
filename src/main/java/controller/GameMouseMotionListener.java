package controller;

import model.EpsilonModel;
import model.game.GameModel;
import movement.Direction;
import movement.Point;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class GameMouseMotionListener implements MouseMotionListener {
    public static GameMouseMotionListener INSTANCE;
    private EpsilonModel epsilonModel;
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println(1);
        if (epsilonModel != null) {
            if (epsilonModel.getVertexes().size() != 0) {
                int x = (int) MouseInfo.getPointerInfo().getLocation().getX() - GameModel.getINSTANCE().getX();
                int y = (int) MouseInfo.getPointerInfo().getLocation().getY() - GameModel.getINSTANCE().getY();
                epsilonModel.rotateVertexes(x,y);

            }
        }
    }
    public static GameMouseMotionListener getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new GameMouseMotionListener();
        }
        return INSTANCE;
    }

    public void setEpsilonModel(EpsilonModel epsilonModel) {
        this.epsilonModel = epsilonModel;
    }
}
