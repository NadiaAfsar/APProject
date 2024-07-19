package controller.update;

import controller.Controller;
import controller.GameManager;
import model.BulletModel;
import model.EpsilonModel;
import model.enemies.mini_boss.black_orb.BlackOrb;
import model.enemies.mini_boss.black_orb.BlackOrbLaser;
import model.enemies.mini_boss.black_orb.BlackOrbVertex;
import model.enemies.Enemy;
import model.enemies.normal.archmire.AoEAttack;
import model.enemies.normal.archmire.Archmire;
import model.frame.Frame;
import view.game.BulletView;
import view.game.EpsilonView;
import view.game.GamePanel;
import view.game.GameView;
import view.game.enemies.EnemyView;
import view.game.enemies.archmire.AoEView;
import view.game.enemies.black_orb.BlackOrbLaserView;

import java.util.ArrayList;
import java.util.Map;

public class Update {
    public static void updateView() {
        if (Controller.gameRunning) {
            if (GameManager.getINSTANCE().getGameView() != null) {
                EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
                GameView gameView = GameManager.getINSTANCE().getGameView();
                EpsilonView epsilonView = GameManager.getINSTANCE().getGameView().getEpsilonView();
                gameView.update(epsilon.getHP(), epsilon.getXP(), GameManager.getINSTANCE().getWave(),
                        GameManager.getINSTANCE().getTimePlayed(), GameManager.getINSTANCE().getPickedSkill());
                gameView.updatePanels();
                updateEnemies();
                updateBullets();
                updateEnemiesBullets();
                epsilonView.update(epsilon.getX(), epsilon.getY());
                epsilonView.updateVertexes(epsilon.getVertexes());
                updatePanels();
            }

        }
//        else if (Controller.gameFinished) {
//            EpsilonModel epsilonModel = GameManager.getINSTANCE().getGameModel().getEpsilon();
//            GameManager.getINSTANCE().getGameView().getEpsilonView().increaseSize(epsilonModel.getX(), epsilonModel.getY(), epsilonModel.getRadius());
//            GameModel game = GameManager.getINSTANCE().getGameModel();
//            GameManager.getINSTANCE().getGameView().destroy((int)game.getWidth(), (int)game.getHeight());
//        }
    }
    public static void updateModel() {
        if (Controller.gameRunning) {
            if (GameManager.getINSTANCE().getGameModel() != null) {
                GameManager.getINSTANCE().update();
            }
        }
//        else if (Controller.gameFinished) {
//            GameManager.getINSTANCE().getGameModel().getEpsilon().increaseSize();
//            GameManager.getINSTANCE().destroyFrame();
//        }
    }
    private static void updateEnemies() {
        Map<String, EnemyView> enemiesView = GameManager.getINSTANCE().getGameView().getEnemiesMap();
        ArrayList<Enemy> enemies = GameManager.getINSTANCE().getGameModel().getEnemies();
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (enemy instanceof BlackOrb) {
                updateBlackOrbVertexes((BlackOrb) enemy);
                updateBlackOrbLasers((BlackOrb) enemy);
            }
            else if (enemiesView.get(enemy.getID()) != null) {
                enemiesView.get(enemy.getID()).update(enemy.getCenter(), enemy.getAngle());
                if (enemy instanceof Archmire){
                    updateAoEs((Archmire) enemy);
                }
            }
        }
    }
    private static void updateBullets() {
        Map<String, BulletView> bulletsView = GameManager.getINSTANCE().getGameView().getBulletsMap();
        ArrayList<BulletModel> bullets = GameManager.getINSTANCE().getGameModel().getBullets();
        for (int i = 0; i < bullets.size(); i++) {
            BulletModel bullet = bullets.get(i);
            if (bulletsView.get(bullet.getID()) != null) {
                bulletsView.get(bullet.getID()).update((int) bullet.getX1(), (int) bullet.getY1());
            }
        }
    }
    private static void updateEnemiesBullets() {
        Map<String, BulletView> bulletsView = GameManager.getINSTANCE().getGameView().getBulletsMap();
        ArrayList<BulletModel> bullets = GameManager.getINSTANCE().getGameModel().getEnemiesBullets();
        for (int i = 0; i < bullets.size(); i++) {
            BulletModel bullet = bullets.get(i);
            if (bulletsView.get(bullet.getID()) != null) {
                bulletsView.get(bullet.getID()).update((int) bullet.getX1(), (int) bullet.getY1());
            }
        }
    }
    private static void updateBlackOrbVertexes(BlackOrb blackOrb) {
        Map<String, EnemyView> verticesView = GameManager.getINSTANCE().getGameView().getEnemiesMap();
        ArrayList<BlackOrbVertex> vertices = blackOrb.getBlackOrbVertices();
        for (int i = 0; i < vertices.size(); i++) {
            BlackOrbVertex vertex = vertices.get(i);
            verticesView.get(vertex.getID()).update(vertex.getCenter(), 0);
        }
    }
    private static void updateBlackOrbLasers(BlackOrb blackOrb) {
        Map<String, BlackOrbLaserView> laserViewMap = GameManager.getINSTANCE().getGameView().getLaserViewMap();
        ArrayList<BlackOrbLaser> lasers = blackOrb.getLasers();
        for (int i = 0; i < lasers.size(); i++) {
            BlackOrbLaser laser = lasers.get(i);
            laserViewMap.get(laser.getID()).update(laser.getX1(), laser.getY1(), laser.getX2(), laser.getY2());
        }
    }
    private static void updatePanels() {
        ArrayList<Frame> frames = GameManager.getINSTANCE().getGameModel().getFrames();
        Map<String, GamePanel> panels = GameManager.getINSTANCE().getGameView().getGamePanelMap();
        for (int i = 0; i < frames.size(); i++) {
            Frame frame = frames.get(i);
            panels.get(frames.get(i).getID()).update((int)frame.getX(), (int)frame.getY(), (int)frame.getWidth(),
                    (int)frame.getHeight());
        }
    }
    private static void updateAoEs(Archmire archmire){
        ArrayList<AoEAttack> aoes = archmire.getAoeAttacks();
        Map<String, AoEView> aoEViewMap = GameManager.getINSTANCE().getGameView().getAoEViewMap();
        for (int i = 0; i < aoes.size(); i++){
            if (aoEViewMap.get(aoes.get(i).getID()) != null) {
                aoEViewMap.get(aoes.get(i).getID()).update(aoes.get(i).getClarity());
            }
        }
    }

}
