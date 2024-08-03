package controller.listeners;

import controller.game_manager.GameManager;
import model.game.EpsilonModel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class GameMouseMotionListener implements MouseMotionListener {
    private GameManager gameManager;
    public GameMouseMotionListener(GameManager gameManager){
        this.gameManager = gameManager;
    }
    private EpsilonModel epsilonModel;
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (epsilonModel != null) {
            if (epsilonModel.getVertexes().size() != 0) {
                int x = (int) MouseInfo.getPointerInfo().getLocation().getX();
                int y = (int) MouseInfo.getPointerInfo().getLocation().getY();
                epsilonModel.rotateVertexes(x,y);

            }
        }
    }

    public void setEpsilonModel(EpsilonModel epsilonModel) {
        this.epsilonModel = epsilonModel;
    }
}
