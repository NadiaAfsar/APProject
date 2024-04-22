package controller;

import model.EpsilonModel;
import model.GameModel;
import model.enemies.Enemy;
import model.enemies.SquarantineModel;
import movement.Direction;
import movement.Point;
import view.BulletView;
import view.EpsilonView;
import view.GameFrame;
import view.GameView;
import view.enemies.SquarantineView;
import view.enemies.TrigorathView;

import java.awt.event.MouseListener;
import java.io.IOException;

public class Controller {
    public static void addFrame() {
        GameFrame.getINSTANCE();
    }
    public static void startGame() {
        GameModel.getINSTANCE();
        GameView.getINSTANCE();
        EpsilonModel.getINSTANCE();
        EpsilonView.getINSTANCE();
        GameView.getINSTANCE().add(EpsilonView.getINSTANCE());
        new Update();
        GameMouseListener.getINSTANCE().setGameRunning(true);
        GameView.getINSTANCE().addMouseListener(GameMouseListener.getINSTANCE());
    }
    public static void addEnemyView(Enemy enemy) {
        if (enemy instanceof SquarantineModel) {
            new SquarantineView(enemy.getX(), enemy.getY());
        }
        else {
            new TrigorathView(enemy.getX(), enemy.getY());
        }
    }

}
