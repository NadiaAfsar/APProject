package controller;

import model.*;
import model.enemies.Enemy;
import model.enemies.SquarantineModel;
import model.skills.WritOfAceso;
import model.skills.WritOfAres;
import model.skills.WritOfProteus;
import movement.RotatablePoint;
import view.*;
import view.enemies.EnemyView;
import view.enemies.SquarantineView;
import view.enemies.TrigorathView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class Controller {
    public static boolean gameRunning;
    public static void runGame() {
        GameManager.getINSTANCE();
        GameFrame.getINSTANCE();
    }
    public static void startGame() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_WINDOWS);
            robot.keyPress(KeyEvent.VK_D);
            robot.keyRelease(KeyEvent.VK_D);
            robot.keyRelease(KeyEvent.VK_WINDOWS);
        }
        catch (AWTException e) {
            throw new RuntimeException(e);
        }
        Timer timer = new Timer(400, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameModel.INSTANCE = new GameModel();
                GameView.INSTANCE = new GameView();
                EpsilonModel.INSTANCE = new EpsilonModel();
                EpsilonView.INSTANCE = new EpsilonView();
                GameView.getINSTANCE().add(EpsilonView.getINSTANCE());
                new Update();
                GameMouseListener.getINSTANCE().setGameRunning(true);
                GameView.getINSTANCE().addMouseListener(GameMouseListener.getINSTANCE());
                gameRunning = true;
            }
        });
        timer.setRepeats(false);
        timer.start();
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
    public static void setGameHUI() {
        GameView.getINSTANCE().setHUI();
    }

    public static boolean isAres() {
        return WritOfAres.isPicked();
    }

    public static void setAres(boolean ares) {
        WritOfAres.setPicked(ares);
    }

    public static boolean isAceso() {
        return WritOfAceso.isPicked();
    }

    public static void setAceso(boolean aceso) {
        WritOfAceso.setPicked(aceso);
    }

    public static boolean isProteus() {
        return WritOfProteus.isPicked();
    }

    public static void setProteus(boolean proteus) {
        WritOfProteus.setPicked(proteus);
    }

    public static boolean isAresUnlocked() {
        return WritOfAres.isUnlocked();
    }

    public static void setAresUnlocked(boolean aresUnlocked) {
        WritOfAres.setUnlocked(aresUnlocked);
    }

    public static boolean isAcesoUnlocked() {
        return WritOfAceso.isPicked();
    }

    public static void setAcesoUnlocked(boolean acesoUnlocked) {
        WritOfAceso.setUnlocked(acesoUnlocked);
    }

    public static boolean isProteusUnlocked() {
        return WritOfProteus.isUnlocked();
    }

    public static void setProteusUnlocked(boolean proteusUnlocked) {
        WritOfProteus.setUnlocked(proteusUnlocked);
    }
    public static void setXP(int xp) {
        GameManager.getINSTANCE().setTotallXP(xp);
    }
    public static int getXP() {
        return GameManager.getINSTANCE().getTotallXP();
    }
    public static void addVertexesToEpsilon() {
        ArrayList<RotatablePoint> vertexes = EpsilonModel.getINSTANCE().getVertexes();
        for (int i = 0; i < vertexes.size(); i++) {

        }
    }
}
