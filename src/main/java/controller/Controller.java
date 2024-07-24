package controller;

import controller.update.Update;
import model.enemies.TrigorathModel;
import model.enemies.mini_boss.Barricados;
import model.enemies.mini_boss.black_orb.BlackOrbLaser;
import model.enemies.mini_boss.black_orb.BlackOrbVertex;
import model.enemies.normal.Necropick;
import model.enemies.normal.Omenoct;
import model.enemies.normal.Wyrm;
import model.enemies.normal.archmire.AoEAttack;
import model.enemies.normal.archmire.Archmire;
import model.enemies.smiley.*;
import model.frame.Frame;
import model.interfaces.collision.Impactable;
import controller.audio.Audio;
import controller.listeners.GameMouseListener;
import controller.listeners.GameMouseMotionListener;
import model.*;
import model.enemies.Enemy;
import model.enemies.SquarantineModel;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;
import view.game.*;
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
    public static void runGame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        GameManager.getINSTANCE();
        music = new Audio(GameManager.configs.THEME_SONG);
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
                GameMouseMotionListener.getINSTANCE().setEpsilonModel(GameManager.getINSTANCE().getGameModel().getEpsilon());
                gameRunning = true;
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    public static boolean playSavedGame(){
        return GameManager.getINSTANCE().getGameFrame().option("Do you want to play your saved game?");
    }
    public static void addLaserView(BlackOrbLaser laser) {
        GameManager.getINSTANCE().getGameView().addLaser(new BlackOrbLaserView(laser.getX1(), laser.getY1(),
                laser.getX2(), laser.getY2(), laser.getID()));
    }
    public static void removeLaserView(BlackOrbLaser laser) {
        GameManager.getINSTANCE().getGameView().removeLaser(laser.getID());
    }
    public static void addFrameView(Frame frame) {
        GameManager.getINSTANCE().getGameView().addPanel((int)frame.getX(), (int)frame.getY(), (int)frame.getWidth(),
                (int)frame.getHeight(), frame.getID());
    }
    public static void addEnemyView(Enemy enemy) {
        GameView gameView = GameManager.getINSTANCE().getGameView();
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
    public static void addArchmireView(Archmire archmire) {
        GameManager.getINSTANCE().getGameView().addArchmireView(new ArchmireView(archmire.getX(), archmire.getY(),
                (int)archmire.getWidth(), (int)archmire.getHeight(),  archmire.getID()));
    }
    public static void addAoEView(AoEAttack aoEAttack) {
        GameManager.getINSTANCE().getGameView().addAoEView(new AoEView(aoEAttack.getX(), aoEAttack.getY(),
                (int)aoEAttack.getWidth(), (int)aoEAttack.getHeight(), aoEAttack.getID()));
    }
    public static void addSmileyAoE(SmileyAoEAttack aoEAttack){
        GameManager.getINSTANCE().getGameView().addAoEView(new SmileyAoEView((int)aoEAttack.getX(), (int)aoEAttack.getY(),
                (int)aoEAttack.getWidth(), (int)aoEAttack.getHeight(), aoEAttack.getID()));
    }
    public static void addBlackOrbVertexView(BlackOrbVertex blackOrbVertex) {
        GameManager.getINSTANCE().getGameView().addEnemyView(new BlackOrbVertexView((int)blackOrbVertex.getX(),
                (int)blackOrbVertex.getY(), (int)blackOrbVertex.getWidth(), (int)blackOrbVertex.getHeight(),
                blackOrbVertex.getID()));
    }
    public static void removeBulletView(BulletModel bullet) {
        GameManager.getINSTANCE().getGameView().removeBulletView(bullet.getID());
    }
    public static void removeEnemyView(Enemy enemy) {
        GameManager.getINSTANCE().getGameView().removeEnemyView(enemy.getID());
    }
    public static void removeArchmireView(Archmire archmire) {
        GameManager.getINSTANCE().getGameView().removeArchmireView(archmire.getID());
    }
    public static void removeAoEAttackView(String ID) {
        GameManager.getINSTANCE().getGameView().removeAoEView(ID);
    }
    public static void removeBlackOrbVertexView(BlackOrbVertex blackOrbVertex) {
        GameManager.getINSTANCE().getGameView().removeEnemyView(blackOrbVertex.getID());
    }
    public static void addCollectibleView(Collectible collectible) {
        GameManager.getINSTANCE().getGameView().addCollectivesView(new CollectibleView(collectible.getX(), collectible.getY(),
                collectible.getID()));
    }
    public static void removeCollectibleView(Collectible collectible) {
        GameManager.getINSTANCE().getGameView().removeCollectibleView(collectible.getID());
    }
    public static void gameOver(int xp, long time, int bullets, int successfulBullets, int killedEnemies) {
        endGame();
        GameManager.getINSTANCE().getGameView().removeFrames();
        GameManager.readerWriter.saveGameManger();
        GameManager.readerWriter.deleteSavedGames();
        new GameOver(xp, time, bullets, successfulBullets, killedEnemies);
    }
    public static void smileyDied(Smiley smiley){
        ((SmileyView)GameManager.getINSTANCE().getGameView().getEnemiesMap().get(smiley.getID())).die();
    }
    public static void smileyPhase2(Smiley smiley) {
        ((SmileyView)GameManager.getINSTANCE().getGameView().getEnemiesMap().get(smiley.getID())).phase2();
    }

    public static void setXP(int xp) {
        GameManager.getINSTANCE().setTotalXP(xp);
    }
    public static int getXP() {
        return GameManager.getINSTANCE().getTotalXP();
    }
    public static void addVertexesToEpsilon() {
        synchronized (epsilonLock) {
            EpsilonView epsilonView = GameManager.getINSTANCE().getGameView().getEpsilonView();
            epsilonView.removeVertexes();
            ArrayList<RotatablePoint> vertexes = GameManager.getINSTANCE().getGameModel().getEpsilon().getVertexes();
            for (int i = 0; i < vertexes.size(); i++) {
                RotatablePoint vertex = vertexes.get(i);
                epsilonView.addVertex((int) vertex.getRotatedX(), (int) vertex.getRotatedY());
            }
        }
    }
    public static void addBulletView(BulletModel bulletModel) {
        GameManager.getINSTANCE().getGameView().addBulletView(new BulletView((int)bulletModel.getX1(),
                (int)bulletModel.getY1(), bulletModel.getDirection(), bulletModel.getID()));
    }
    public static void endGame() {
        GameMouseListener.setGameRunning(false);
        GameMouseMotionListener.getINSTANCE().setEpsilonModel(null);
        gameRunning = false;
        gameFinished = false;
        GameManager.getINSTANCE().getGameView().removeFrames();
    }
    public static int getDifficulty() {
        return GameManager.getINSTANCE().getDifficulty();
    }
    public static void setDifficulty(int d) {
        GameManager.getINSTANCE().setDifficulty(d);
    }
    public static int getSensitivity() {
        return GameManager.getINSTANCE().getSensitivity();
    }
    public static void setSensitivity(int s) {
        GameManager.getINSTANCE().setSensitivity(s);
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
    public static void announceAppearance(Point point, String ID) {
        GameManager.getINSTANCE().getGameView().addNecropickAnnouncement(new NecropickAnnouncement((int)point.getX(),
                (int)point.getY(), ID));
    }
    public static void removeAnnouncement(String ID){
        GameManager.getINSTANCE().getGameView().removeNecropickAnnouncement(ID);
    }
    public static void addCerberusView(ArrayList<RotatablePoint> cerberus) {
        synchronized (cerberusLock) {
            EpsilonView epsilon = GameManager.getINSTANCE().getGameView().getEpsilonView();
            epsilon.removeCerbeuses();
            for (int i = 0; i < cerberus.size(); i++) {
                epsilon.addCerberus((int)cerberus.get(i).getRotatedX(), (int)cerberus.get(i).getRotatedY());
            }
        }
    }
    public static void addPortal(Point point){
        GameManager.getINSTANCE().getGameView().addPortal(point);
    }
    public static void removePortal(){
        GameManager.getINSTANCE().getGameView().removePortal();
    }
    public static boolean saveGame(){
        int PR = (int)GameManager.getINSTANCE().getGameModel().getCurrentWave().getProgressRisk();
        return GameManager.getINSTANCE().getGameFrame().option("Do you pay "+PR+" XPs to save the game?");
    }
}
