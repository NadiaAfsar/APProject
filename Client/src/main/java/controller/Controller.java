package controller;

import application.MyApplication;
import controller.audio.Audio;
import controller.game_manager.GameManager;
import model.game.BulletModel;
import model.game.Collectible;
import model.game.EpsilonModel;
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
import view.ConnectionFrame;
import view.game.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class Controller {
    public static Audio music;
    public static final Object epsilonLock = new Object();
    public static final Object cerberusLock = new Object();
    public static void runGame(ApplicationManager applicationManager){
        applicationManager.initialize();
        try {
            music = new Audio(MyApplication.configs.THEME_SONG);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        music.setRepeat();
    }
    public static void startGame(GameManager gameManager, int number) {
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
                gameManager.initialize();
                gameManager.startGame(number);
                gameManager.setRunning(true);
                gameManager.getGameMouseMotionListener().setEpsilonModel(gameManager.getGameModel().getMyEpsilon());
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    public static void addEpsilonView(GameManager gameManager, EpsilonModel epsilon, int number){
        String pic = null;
        if (number == 0){
            pic = MyApplication.configs.EPSILON1;
        }
        else {
            pic = MyApplication.configs.EPSILON2;
        }
        gameManager.getGameView().addEpsilonView(new EntityView(epsilon.getX(), epsilon.getY(),
                epsilon.getRadius()*2, epsilon.getRadius()*2, pic, epsilon.getID()));
    }
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
            gameView.addEnemyView(new EntityView(enemy.getX(), enemy.getY(), (int)enemy.getWidth(),
                    (int)enemy.getHeight(), MyApplication.configs.SQUARANTINE,  enemy.getID()));
        }
        else if (enemy instanceof TrigorathModel){
            gameView.addEnemyView(new EntityView(enemy.getX(), enemy.getY(), (int)enemy.getWidth(),
                    (int)enemy.getHeight(), MyApplication.configs.TRIGORATH,  enemy.getID()));;
        }
        else if (enemy instanceof Wyrm) {
            gameView.addEnemyView(new EntityView(enemy.getX(), enemy.getY(), (int)enemy.getWidth(),
                    (int)enemy.getHeight(), MyApplication.configs.WYRM,  enemy.getID()));
        }
        else if (enemy instanceof Omenoct) {
            gameView.addEnemyView(new EntityView(enemy.getX(), enemy.getY(), (int)enemy.getWidth(),
                    (int)enemy.getHeight(), MyApplication.configs.OMENOCT,  enemy.getID()));
        }
        else if (enemy instanceof Necropick) {
            gameView.addEnemyView(new EntityView(enemy.getX(), enemy.getY(), (int)enemy.getWidth(),
                    (int)enemy.getHeight(), MyApplication.configs.NECROPICK,  enemy.getID()));
        }
        else if (enemy instanceof Barricados) {
            gameView.addEnemyView(new EntityView(enemy.getX(), enemy.getY(), (int)enemy.getWidth(),
                    (int)enemy.getHeight(), MyApplication.configs.BARRICODES,  enemy.getID()));
        }
        else if (enemy instanceof Smiley){
            gameView.addEnemyView(new EntityView(enemy.getX(), enemy.getY(), (int)enemy.getWidth(),
                    (int) enemy.getHeight(), MyApplication.configs.SMILEY1, enemy.getID()));
        }
        else if (enemy instanceof RightHand){
            gameView.addEnemyView(new EntityView(enemy.getX(), enemy.getY(), (int)enemy.getWidth(),
                    (int) enemy.getHeight(), MyApplication.configs.RIGHT_HAND, enemy.getID()));
        }
        else if (enemy instanceof LeftHand){
            gameView.addEnemyView(new EntityView(enemy.getX(), enemy.getY(), (int)enemy.getWidth(),
                    (int) enemy.getHeight(), MyApplication.configs.LEFT_HAND, enemy.getID()));
        }
        else if (enemy instanceof Fist){
            gameView.addEnemyView(new EntityView(enemy.getX(), enemy.getY(), (int)enemy.getWidth(),
                    (int) enemy.getHeight(), MyApplication.configs.FIST, enemy.getID()));
        }
    }
    public static void addArchmireView(Archmire archmire, GameManager gameManager) {
        gameManager.getGameView().addArchmireView(new EntityView(archmire.getX(), archmire.getY(),
                (int)archmire.getWidth(), (int)archmire.getHeight(), MyApplication.configs.ARCHMIRE,  archmire.getID()));
    }
    public static void addAoEView(AoEAttack aoEAttack, GameManager gameManager) {
        gameManager.getGameView().addAoEView(new EntityView(aoEAttack.getX(), aoEAttack.getY(),
                (int)aoEAttack.getWidth(), (int)aoEAttack.getHeight(), MyApplication.configs.AoE1, aoEAttack.getID()));
    }
    public static void addSmileyAoE(SmileyAoEAttack aoEAttack, GameManager gameManager){
        gameManager.getGameView().addAoEView(new EntityView((int)aoEAttack.getX(), (int)aoEAttack.getY(),
                (int)aoEAttack.getWidth(), (int)aoEAttack.getHeight(), MyApplication.configs.AOE_ATTACK_1, aoEAttack.getID()));
    }
    public static void addBlackOrbVertexView(BlackOrbVertex blackOrbVertex, GameManager gameManager) {
        gameManager.getGameView().addEnemyView(new EntityView((int)blackOrbVertex.getX(),
                (int)blackOrbVertex.getY(), (int)blackOrbVertex.getWidth(), (int)blackOrbVertex.getHeight()
                , MyApplication.configs.BLACK_ORB_VERTEX, blackOrbVertex.getID()));
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
        gameManager.getGameView().addCollectivesView(new EntityView(collectible.getX(), collectible.getY()
                , collectible.getRadius()*2, collectible.getRadius()*2, MyApplication.configs.COLLECTIBLE
                ,collectible.getID()));
    }
    public static void removeCollectibleView(Collectible collectible, GameManager gameManager) {
        gameManager.getGameView().removeCollectibleView(collectible.getID());
    }
    public static void gameOver(int xp, long time, int bullets, int successfulBullets, int killedEnemies, GameManager gameManager) {
        endGame(gameManager);
        gameManager.getGameView().removeFrames();
        gameManager.setEnded(true);
        new GameOver(xp, time, bullets, successfulBullets, killedEnemies, gameManager);
    }
    public static void smileyDied(Smiley smiley, GameManager gameManager){
        gameManager.getGameView().getEnemiesMap().get(smiley.getID()).smileyDied();
    }
    public static void smileyPhase2(Smiley smiley, GameManager gameManager) {
        gameManager.getGameView().getEnemiesMap().get(smiley.getID()).smileyPhase2();
    }

    public static void addVertexesToEpsilon(GameManager gameManager, EpsilonModel epsilon) {
        synchronized (epsilonLock) {
            GameView gameView = gameManager.getGameView();
            gameView.removeVertexes(epsilon.getID());
            ArrayList<RotatablePoint> vertexes = epsilon.getVertexes();
            for (int i = 0; i < vertexes.size(); i++) {
                RotatablePoint vertex = vertexes.get(i);
                gameView.addVertex(new EntityView((int)vertex.getRotatedX()+3, (int)vertex.getRotatedY()+3, 6,
                        6, MyApplication.configs.CIRCLE, "a"), epsilon.getID());
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
        gameManager.getGameModel().setFinished(false);
        gameManager.getGameView().removeFrames();
    }

    public static Audio getMusic() {
        return music;
    }
    public static void removeEpsilonVertexes(GameManager gameManager, EpsilonModel epsilon) {
        gameManager.getGameView().removeVertexes(epsilon.getID());
    }
    public static void announceAppearance(Point point, String ID, GameManager gameManager) {
        gameManager.getGameView().addNecropickAnnouncement(new EntityView((int)point.getX(),
                (int)point.getY(), MyApplication.configs.NECROPICK_ANNOUNCEMENT_WIDTH,
                MyApplication.configs.NECROPICK_ANNOUNCEMENT_WIDTH, MyApplication.configs.NECROPICK_ANNOUNCEMENT, ID));
    }
    public static void removeAnnouncement(String ID, GameManager gameManager){
        gameManager.getGameView().removeNecropickAnnouncement(ID);
    }
    public static void addCerberusView(GameManager gameManager, EpsilonModel epsilon) {
        synchronized (cerberusLock) {
            GameView gameView = gameManager.getGameView();
            gameView.removeCerberus(epsilon.getID());
            for (int i = 0; i < epsilon.getCerberusList().size(); i++) {
                RotatablePoint cerberus = epsilon.getCerberusList().get(i);
                gameView.addCerberus(new EntityView((int)cerberus.getRotatedX()-10, (int)cerberus.getRotatedY()-10,
                        20, 20, MyApplication.configs.CIRCLE, "a"), epsilon.getID());
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
        return gameManager.getApplicationManager().getGameFrame().option("Do you pay "+PR+" XPs to save the game?");
    }
    public static boolean connectionError(){
        return ConnectionFrame.showConnectionError();
    }
    public static void removeEpsilonView(EpsilonModel epsilon, GameManager gameManager){
        gameManager.getGameView().removeEpsilon(epsilon.getID());
    }
}
