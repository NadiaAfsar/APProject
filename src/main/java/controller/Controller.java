package controller;

import model.BulletModel;
import model.EpsilonModel;
import model.GameModel;
import model.XP;
import model.enemies.Enemy;
import model.enemies.SquarantineModel;
import movement.Direction;
import movement.Point;
import view.*;
import view.enemies.EnemyView;
import view.enemies.SquarantineView;
import view.enemies.TrigorathView;

import java.awt.event.MouseListener;
import java.io.IOException;

public class Controller {
    public static boolean gameRunning;
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
        gameRunning = true;
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
    public static void addXPView(XP xp) {
        XPView xpView = new XPView(xp.getX(), xp.getY(), xp.getColor());
        GameView gameView = GameView.getINSTANCE();
        gameView.add(xpView);
        gameView.getXPs().put(xp.getID(), xpView);
    }
    public static void removeXP(XP xp) {
        GameView gameView = GameView.getINSTANCE();
        gameView.remove(gameView.getXPs().get(xp.getID()));
    }
    public static void gameOver(int xp) {
        new GameOver(xp);
        GameMouseListener.getINSTANCE().setGameRunning(false);
        gameRunning = false;
    }

}
