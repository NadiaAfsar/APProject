package controller;

import application.MyApplication;
import controller.audio.Audio;
import model.game.BulletModel;
import model.game.Collectible;
import model.game.enemies.Enemy;
import model.game.enemies.SquarantineModel;
import model.game.enemies.TrigorathModel;
import model.game.enemies.mini_boss.Barricados;
import model.game.enemies.mini_boss.black_orb.BlackOrbLaser;
import model.game.enemies.mini_boss.black_orb.BlackOrbVertex;
import model.game.enemies.normal.Necropick;
import model.game.enemies.normal.Omenoct;
import model.game.enemies.normal.Wyrm;
import model.game.enemies.normal.archmire.AoEAttack;
import model.game.enemies.normal.archmire.Archmire;
import model.game.enemies.smiley.*;
import model.game.frame.MyFrame;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;
import view.game.BulletView;
import view.game.CollectibleView;
import view.game.GameOver;
import view.game.GameView;
import view.game.enemies.*;
import view.game.enemies.archmire.AoEView;
import view.game.enemies.archmire.ArchmireView;
import view.game.enemies.black_orb.BlackOrbLaserView;
import view.game.enemies.black_orb.BlackOrbVertexView;
import view.game.enemies.necropick.NecropickAnnouncement;
import view.game.enemies.necropick.NecropickView;
import view.game.enemies.smiley.*;
import view.game.epsilon.EpsilonView;

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
    public static final Object epsilonLock = new Object();
    public static final Object cerberusLock = new Object();
    public static void runGame(GameManager gameManager) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        gameManager.initialize();
        music = new Audio(MyApplication.configs.THEME_SONG);
        music.setRepeat();
    }
    public static void startGame(GameManager gameManager) {
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
                gameManager.startGame();
                gameManager.setRunning(true);
                gameManager.getGameMouseMotionListener().setEpsilonModel(gameManager.getGameModel().getEpsilon());
                gameRunning = true;
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
//    public static boolean playSavedGame(GameManager gameManager){
//        return gameManager.getGameFrame().option("Do you want to play your saved game?");
//    }
    public static void addLaserView(BlackOrbLaser laser, GameManager gameManager) {
        gameManager.getGameView().addLaser(new BlackOrbLaserView(laser.getX1(), laser.getY1(),
                laser.getX2(), laser.getY2(), laser.getID()));
    }
    public static void removeLaserView(BlackOrbLaser laser, GameManager gameManager) {
        gameManager.getGameView().removeLaser(laser.getID());
    }
    public static void addFrameView(MyFrame myFrame, GameManager gameManager) {
        gameManager.getGameView().addPanel((int) myFrame.getX(), (int) myFrame.getY(), (int) myFrame.getWidth(),
                (int) myFrame.getHeight(), myFrame.getID());
    }
    public static void addEnemyView(Enemy enemy, GameManager gameManager) {
        GameView gameView = gameManager.getGameView();
        if (enemy instanceof SquarantineModel) {
            gameView.addEnemyView(new SquarantineView(enemy.getX(), enemy.getY(), (int)enemy.getWidth(),
                    (int)enemy.getHeight(),  enemy.getID()));
        }
        else if (enemy instanceof TrigorathModel){
            gameView.addEnemyView(new TrigorathView(enemy.getX(), enemy.getY(), (int)enemy.getWidth(),
                    (int)enemy.getHeight(),  enemy.getID()));;
        }
        else if (enemy instanceof Wyrm) {
            gameView.addEnemyView(new WyrmView(enemy.getX(), enemy.getY(), (int)enemy.getWidth(),
                    (int)enemy.getHeight(),  enemy.getID()));
        }
        else if (enemy instanceof Omenoct) {
            gameView.addEnemyView(new OmenoctView(enemy.getX(), enemy.getY(), (int)enemy.getWidth(),
                    (int)enemy.getHeight(),  enemy.getID()));
        }
        else if (enemy instanceof Necropick) {
            gameView.addEnemyView(new NecropickView(enemy.getX(), enemy.getY(), (int)enemy.getWidth(),
                    (int)enemy.getHeight(),  enemy.getID()));
        }
        else if (enemy instanceof Barricados) {
            gameView.addEnemyView(new BarricadosView(enemy.getX(), enemy.getY(), (int)enemy.getWidth(),
                    (int)enemy.getHeight(),  enemy.getID()));
        }
        else if (enemy instanceof Smiley){
            gameView.addEnemyView(new SmileyView(enemy.getX(), enemy.getY(), (int)enemy.getWidth(),
                    (int) enemy.getHeight(), enemy.getID()));
        }
        else if (enemy instanceof RightHand){
            gameView.addEnemyView(new RightHandView(enemy.getX(), enemy.getY(), (int)enemy.getWidth(),
                    (int) enemy.getHeight(), enemy.getID()));
        }
        else if (enemy instanceof LeftHand){
            gameView.addEnemyView(new LeftHandView(enemy.getX(), enemy.getY(), (int)enemy.getWidth(),
                    (int) enemy.getHeight(), enemy.getID()));
        }
        else if (enemy instanceof Fist){
            gameView.addEnemyView(new FistView(enemy.getX(), enemy.getY(), (int)enemy.getWidth(),
                    (int) enemy.getHeight(), enemy.getID()));
        }
    }
    public static void addArchmireView(Archmire archmire, GameManager gameManager) {
        gameManager.getGameView().addArchmireView(new ArchmireView(archmire.getX(), archmire.getY(),
                (int)archmire.getWidth(), (int)archmire.getHeight(),  archmire.getID()));
    }
    public static void addAoEView(AoEAttack aoEAttack, GameManager gameManager) {
        gameManager.getGameView().addAoEView(new AoEView(aoEAttack.getX(), aoEAttack.getY(),
                (int)aoEAttack.getWidth(), (int)aoEAttack.getHeight(), aoEAttack.getID()));
    }
    public static void addSmileyAoE(SmileyAoEAttack aoEAttack, GameManager gameManager){
        gameManager.getGameView().addAoEView(new SmileyAoEView((int)aoEAttack.getX(), (int)aoEAttack.getY(),
                (int)aoEAttack.getWidth(), (int)aoEAttack.getHeight(), aoEAttack.getID()));
    }
    public static void addBlackOrbVertexView(BlackOrbVertex blackOrbVertex, GameManager gameManager) {
        gameManager.getGameView().addEnemyView(new BlackOrbVertexView((int)blackOrbVertex.getX(),
                (int)blackOrbVertex.getY(), (int)blackOrbVertex.getWidth(), (int)blackOrbVertex.getHeight(),
                blackOrbVertex.getID()));
    }
    public static void removeBulletView(BulletModel bullet, GameManager gameManager) {
        gameManager.getGameView().removeBulletView(bullet.getID());
    }
    public static void removeEnemyView(Enemy enemy, GameManager gameManager) {
        gameManager.getGameView().removeEnemyView(enemy.getID());
    }
    public static void removeArchmireView(Archmire archmire, GameManager gameManager) {
        gameManager.getGameView().removeArchmireView(archmire.getID());
    }
    public static void removeAoEAttackView(String ID, GameManager gameManager) {
        gameManager.getGameView().removeAoEView(ID);
    }
    public static void removeBlackOrbVertexView(BlackOrbVertex blackOrbVertex, GameManager gameManager) {
        gameManager.getGameView().removeEnemyView(blackOrbVertex.getID());
    }
    public static void addCollectibleView(Collectible collectible, GameManager gameManager) {
        gameManager.getGameView().addCollectivesView(new CollectibleView(collectible.getX(), collectible.getY(),
                collectible.getID()));
    }
    public static void removeCollectibleView(Collectible collectible, GameManager gameManager) {
        gameManager.getGameView().removeCollectibleView(collectible.getID());
    }
    public static void gameOver(int xp, long time, int bullets, int successfulBullets, int killedEnemies, GameManager gameManager) {
        endGame(gameManager);
        gameManager.getGameView().removeFrames();
        GameManager.readerWriter.saveGameManger(gameManager);
        GameManager.readerWriter.deleteSavedGames();
        new GameOver(xp, time, bullets, successfulBullets, killedEnemies, gameManager);
    }
    public static void smileyDied(Smiley smiley, GameManager gameManager){
        ((SmileyView)gameManager.getGameView().getEnemiesMap().get(smiley.getID())).die();
    }
    public static void smileyPhase2(Smiley smiley, GameManager gameManager) {
        ((SmileyView)gameManager.getGameView().getEnemiesMap().get(smiley.getID())).phase2();
    }

    public static void setXP(int xp, GameManager gameManager) {
        gameManager.setTotalXP(xp);
    }
    public static int getXP(GameManager gameManager) {
        return gameManager.getTotalXP();
    }
    public static void addVertexesToEpsilon(GameManager gameManager) {
        synchronized (epsilonLock) {
            EpsilonView epsilonView = gameManager.getGameView().getEpsilonView();
            epsilonView.removeVertexes();
            ArrayList<RotatablePoint> vertexes = gameManager.getGameModel().getEpsilon().getVertexes();
            for (int i = 0; i < vertexes.size(); i++) {
                RotatablePoint vertex = vertexes.get(i);
                epsilonView.addVertex((int) vertex.getRotatedX(), (int) vertex.getRotatedY());
            }
        }
    }
    public static void addBulletView(BulletModel bulletModel, GameManager gameManager) {
        gameManager.getGameView().addBulletView(new BulletView((int)bulletModel.getX1(),
                (int)bulletModel.getY1(), bulletModel.getDirection(), bulletModel.getID()));
    }
    public static void endGame(GameManager gameManager) {
        gameManager.setRunning(false);
        gameManager.getGameMouseMotionListener().setEpsilonModel(null);
        gameRunning = false;
        gameFinished = false;
        gameManager.getGameView().removeFrames();
    }
    public static int getDifficulty(GameManager gameManager) {
        return gameManager.getDifficulty();
    }
    public static void setDifficulty(int d, GameManager gameManager) {
        gameManager.setDifficulty(d);
    }
    public static int getSensitivity(GameManager gameManager) {
        return gameManager.getSensitivity();
    }
    public static void setSensitivity(int s, GameManager gameManager) {
        gameManager.setSensitivity(s);
    }

    public static Audio getMusic() {
        return music;
    }
    public static int getTotalXP(GameManager gameManager) {
        return gameManager.getTotalXP();
    }
    public static void removeEpsilonVertexes(GameManager gameManager) {
        gameManager.getGameView().getEpsilonView().removeVertexes();
    }
    public static void announceAppearance(Point point, String ID, GameManager gameManager) {
        gameManager.getGameView().addNecropickAnnouncement(new NecropickAnnouncement((int)point.getX(),
                (int)point.getY(), ID));
    }
    public static void removeAnnouncement(String ID, GameManager gameManager){
        gameManager.getGameView().removeNecropickAnnouncement(ID);
    }
    public static void addCerberusView(ArrayList<RotatablePoint> cerberus, GameManager gameManager) {
        synchronized (cerberusLock) {
            EpsilonView epsilon = gameManager.getGameView().getEpsilonView();
            epsilon.removeCerbeuses();
            for (int i = 0; i < cerberus.size(); i++) {
                epsilon.addCerberus((int)cerberus.get(i).getRotatedX(), (int)cerberus.get(i).getRotatedY());
            }
        }
    }
    public static void addPortal(Point point, GameManager gameManager){
        gameManager.getGameView().addPortal(point);
    }
    public static void removePortal(GameManager gameManager){
        gameManager.getGameView().removePortal();
    }
    public static boolean saveGame(GameManager gameManager){
        int PR = (int)gameManager.getGameModel().getCurrentWave().getProgressRisk();
        //return gameManager.getGameFrame().option("Do you pay "+PR+" XPs to save the game?");
        return false;
    }
//    public static boolean connectionError(){
//        return ConnectionFrame.showConnectionError();
//    }
}
