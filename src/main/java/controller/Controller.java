package controller;

import model.BulletModel;
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
import view.enemies.EnemyView;
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
        EnemyView enemyView;
        if (enemy instanceof SquarantineModel) {
            enemyView = new SquarantineView(enemy.getX(), enemy.getY());
        }
        else {
            enemyView = new TrigorathView(enemy.getX(), enemy.getY());
        }
        GameView.getINSTANCE().getEnemies().put(enemy.getID(), enemyView);
    }
    public static void removeBullet(BulletModel bullet) {
        GameView gameView = GameView.getINSTANCE();
        gameView.remove(gameView.getBullets().get(bullet.getID()));
    }
    public static void removeEnemy(Enemy enemy) {
        GameView gameView = GameView.getINSTANCE();
        gameView.remove(gameView.getEnemies().get(enemy.getID()));
    }

}
