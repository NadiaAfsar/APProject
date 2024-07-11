package controller;

import collision.Impactable;
import controller.audio.Audio;
import controller.listeners.GameMouseListener;
import controller.listeners.GameMouseMotionListener;
import model.*;
import model.enemies.Enemy;
import model.enemies.SquarantineModel;
import model.skills.WritOfAceso;
import model.skills.WritOfAres;
import model.skills.WritOfProteus;
import movement.Point;
import movement.RotatablePoint;
import save.Save;
import view.game.*;
import view.game.enemies.EnemyView;
import view.game.enemies.SquarantineView;
import view.game.enemies.TrigorathView;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;


public class Controller {
    public static boolean gameRunning;
    public static boolean gameFinished;
    public static Audio music;
    public static void runGame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        GameManager.getINSTANCE();
        music = new Audio("src/main/resources/15 - Bad n Crazy - Kim Woo Kun (320).wav");
        music.setRepeat();
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
                GameManager.getINSTANCE().startGame();
                new Update();
                GameMouseListener.setGameRunning(true);
                GameManager.getINSTANCE().getGameView().addMouseListener(GameMouseListener.getINSTANCE());
                GameMouseMotionListener.getINSTANCE().setEpsilonModel(GameManager.getINSTANCE().getGameModel().getEpsilon());
                GameManager.getINSTANCE().getGameView().addMouseMotionListener(GameMouseMotionListener.getINSTANCE());
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
        GameManager.getINSTANCE().getGameView().getEnemies().put(enemy.getID(), enemyView);
    }
    public static void removeBullet(BulletModel bullet) {
        GameView gameView = GameManager.getINSTANCE().getGameView();
        gameView.remove(gameView.getBullets().get(bullet.getID()));
    }
    public static void removeEnemy(Enemy enemy) {
        GameView gameView = GameManager.getINSTANCE().getGameView();
        gameView.remove(gameView.getEnemies().get(enemy.getID()));
    }
    public static void addCollectiveView(Collective collective) {
        CollectiveView collectiveView = new CollectiveView(collective.getX(), collective.getY(), collective.getColor());
        GameView gameView = GameManager.getINSTANCE().getGameView();
        gameView.add(collectiveView);
        gameView.getCollectives().put(collective.getID(), collectiveView);
    }
    public static void removeXP(Collective collective) {
        GameView gameView = GameManager.getINSTANCE().getGameView();
        gameView.remove(gameView.getCollectives().get(collective.getID()));
    }
    public static void gameOver(int xp) {
        endGame();
        new GameOver(xp);
    }

    public static void setAres(boolean ares) {
        WritOfAres.setPicked(ares);
        if (ares) {
            GameManager.getINSTANCE().setPickedSkill(new WritOfAres());
        }
    }

    public static void setAceso(boolean aceso) {
        WritOfAceso.setPicked(aceso);
        if (aceso) {
            GameManager.getINSTANCE().setPickedSkill(new WritOfAceso());
        }
    }

    public static void setProteus(boolean proteus) {
        WritOfProteus.setPicked(proteus);
        if (proteus) {
            GameManager.getINSTANCE().setPickedSkill(new WritOfProteus());
        }
    }

    public static boolean isAresUnlocked() {
        return WritOfAres.isAresUnlocked();
    }

    public static void setAresUnlocked(boolean aresUnlocked) {
        WritOfAres.setAresUnlocked(aresUnlocked);

    }

    public static boolean isAcesoUnlocked() {
        return WritOfAceso.isAcesoUnlocked();
    }

    public static void setAcesoUnlocked(boolean acesoUnlocked) {
        WritOfAceso.setAcesoUnlocked(acesoUnlocked);
    }

    public static boolean isProteusUnlocked() {
        return WritOfProteus.isProteusUnlocked();
    }

    public static void setProteusUnlocked(boolean proteusUnlocked) {
        WritOfProteus.setProteusUnlocked(proteusUnlocked);
    }
    public static void setXP(int xp) {
        GameManager.getINSTANCE().setTotalXP(xp);
    }
    public static int getXP() {
        return GameManager.getINSTANCE().getTotalXP();
    }
    public static void addVertexesToEpsilon() {
        EpsilonView epsilonView = GameManager.getINSTANCE().getGameView().getEpsilonView();
        epsilonView.removeVertexes();
        ArrayList<RotatablePoint> vertexes = GameManager.getINSTANCE().getGameModel().getEpsilon().getVertexes();
        for (int i = 0; i < vertexes.size(); i++) {
            RotatablePoint vertex = vertexes.get(i);
            epsilonView.addVertex((int)vertex.getRotatedX(), (int)vertex.getRotatedY());
        }
    }
    public static boolean hephaestus() {
        EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
        if (epsilon.getXP() >= 100) {
            Impactable.impactOnOthers(new Point(epsilon.getCenter().getX(), epsilon.getCenter().getY()));
            epsilon.setXP(epsilon.getXP()-100);
            return true;
        }
        return false;
    }
    public static void addBulletView(BulletModel bulletModel) {
        BulletView bulletView = new BulletView((int) bulletModel.getX1(), (int) bulletModel.getY1(), bulletModel.getDirection());
        GameManager.getINSTANCE().getGameView().getBullets().put(bulletModel.getID(), bulletView);
        GameManager.getINSTANCE().getGameView().add(bulletView);
    }
    public static boolean athena() {
        EpsilonModel epsilonModel = GameManager.getINSTANCE().getGameModel().getEpsilon();
        if (epsilonModel.getXP() >= 75) {
            GameManager.getINSTANCE().activateAthena();
            epsilonModel.setXP(epsilonModel.getXP()-75);
            return true;
        }
        return false;
    }
    public static boolean apollo() {
        EpsilonModel epsilonModel = GameManager.getINSTANCE().getGameModel().getEpsilon();
        if (epsilonModel.getXP() >= 50) {
            epsilonModel.setHP(epsilonModel.getHP()+10);
            epsilonModel.setXP(epsilonModel.getXP()-50);
            return true;
        }
        return false;
    }
    public static boolean isAresPicked() {
        return WritOfAres.isPicked();
    }
    public static boolean isAcesoPicked() {
        return WritOfAceso.isPicked();
    }
    public static boolean isProteusPicked() {
        return WritOfProteus.isPicked();
    }
    public static void endGame() {
        GameMouseListener.setGameRunning(false);
        GameMouseMotionListener.getINSTANCE().setEpsilonModel(null);
        gameRunning = false;
        gameFinished = false;
        Save.save();
    }
    public static int getDifficulty() {
        return GameManager.getDifficulty();
    }
    public static void setDifficulty(int d) {
        GameManager.setDifficulty(d);
    }
    public static int getSensitivity() {
        return GameManager.getSensitivity();
    }
    public static void setSensitivity(int s) {
        GameManager.setSensitivity(s);
    }

    public static Audio getMusic() {
        return music;
    }
    public static int getTotalXP() {
        return GameManager.getINSTANCE().getTotalXP();
    }
    public static void removeEpsilonVertexes() {
        GameManager.getINSTANCE().getGameView().getEpsilonView().removeVertexes();
    }
    public static void announceAppearance(Point point) {

    }
}
