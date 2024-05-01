package controller;

import model.*;
import model.enemies.Enemy;
import model.enemies.SquarantineModel;
import model.game.EasyGame;
import model.game.GameModel;
import model.game.HardGame;
import model.game.MediumGame;
import model.skills.WritOfAceso;
import model.skills.WritOfAres;
import model.skills.WritOfProteus;
import movement.RotatablePoint;
import view.*;
import view.enemies.EnemyView;
import view.enemies.SquarantineView;
import view.enemies.TrigorathView;

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
    public static Sound music;
    public static void runGame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        GameManager.getINSTANCE();
        GameFrame.getINSTANCE();
        music = new Sound("src/main/resources/15 - Bad n Crazy - Kim Woo Kun (320).wav");
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
                int d = GameManager.getDifficulty();
                if (d == 1) {
                    GameModel.INSTANCE = new EasyGame();
                }
                else if (d == 2) {
                    GameModel.INSTANCE = new MediumGame();
                }
                else {
                    GameModel.INSTANCE = new HardGame();
                }
                GameView.INSTANCE = new GameView();
                EpsilonModel.INSTANCE = new EpsilonModel();
                EpsilonView.INSTANCE = new EpsilonView();
                GameView.getINSTANCE().add(EpsilonView.getINSTANCE());
                new Update();
                GameMouseListener.setGameRunning(true);
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
        endGame();
    }
    public static void setGameHUI() {
        GameView.getINSTANCE().setHUI();
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
        GameManager.getINSTANCE().setTotallXP(xp);
    }
    public static int getXP() {
        return GameManager.getINSTANCE().getTotallXP();
    }
    public static void addVertexesToEpsilon() {
        EpsilonView epsilonView = EpsilonView.getINSTANCE();
        epsilonView.removeVertexes();
        ArrayList<RotatablePoint> vertexes = EpsilonModel.getINSTANCE().getVertexes();
        for (int i = 0; i < vertexes.size(); i++) {
            RotatablePoint vertex = vertexes.get(i);
            epsilonView.addVertex((int)vertex.getRotatedX(), (int)vertex.getRotatedY());
        }
    }
    public static boolean hephaestus() {
        EpsilonModel epsilon = EpsilonModel.getINSTANCE();
        if (epsilon.getXP() >= 100) {
            Enemy.impactOnOthers(new RotatablePoint(epsilon.getCenter().getX(), epsilon.getCenter().getY()));
            epsilon.setXP(epsilon.getXP()-100);
            return true;
        }
        return false;
    }
    public static void addBulletView(BulletModel bulletModel) {
        BulletView bulletView = new BulletView((int) bulletModel.getX1(), (int) bulletModel.getY1(), bulletModel.getDirection());
        GameView.getINSTANCE().getBullets().put(bulletModel.getID(), bulletView);
        GameView.getINSTANCE().add(bulletView);
    }
    public static boolean athena() {
        EpsilonModel epsilonModel = EpsilonModel.getINSTANCE();
        if (epsilonModel.getXP() >= 75) {
            GameModel.getINSTANCE().activateAthena();
            epsilonModel.setXP(epsilonModel.getXP()-75);
            return true;
        }
        return false;
    }
    public static boolean apollo() {
        EpsilonModel epsilonModel = EpsilonModel.getINSTANCE();
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
        gameRunning = false;
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

    public static Sound getMusic() {
        return music;
    }
}
