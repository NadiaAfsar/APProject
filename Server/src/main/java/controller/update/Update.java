package controller.update;

import controller.Controller;
import controller.GameManager;
import model.game.BulletModel;
import model.game.EpsilonModel;
import model.game.enemies.Enemy;
import model.game.enemies.mini_boss.black_orb.BlackOrb;
import model.game.enemies.mini_boss.black_orb.BlackOrbLaser;
import model.game.enemies.mini_boss.black_orb.BlackOrbVertex;
import model.game.enemies.normal.archmire.AoEAttack;
import model.game.enemies.normal.archmire.Archmire;
import model.game.enemies.smiley.Smiley;
import model.game.enemies.smiley.SmileyAoEAttack;
import model.game.frame.MyFrame;
import view.game.BulletView;
import view.game.GamePanel;
import view.game.GameView;
import view.game.enemies.EnemyView;
import view.game.enemies.archmire.AoEView;
import view.game.enemies.black_orb.BlackOrbLaserView;
import view.game.enemies.smiley.SmileyAoEView;
import view.game.enemies.smiley.SmileyView;
import view.game.epsilon.EpsilonView;

import java.util.ArrayList;
import java.util.Map;

public class Update {
    public static void updateView(GameManager gameManager) {
        if (Controller.gameRunning) {
            if (gameManager.getGameView() != null) {
                EpsilonModel epsilon = gameManager.getGameModel().getEpsilon();
                GameView gameView = gameManager.getGameView();
                EpsilonView epsilonView = gameManager.getGameView().getEpsilonView();
                gameView.update(epsilon.getHP(), epsilon.getXP(), gameManager.getGameModel().getWave(),
                        gameManager.getGameModel().getTimePlayed()/1000, gameManager.getPickedSkill());
                gameView.updatePanels();
                updateEnemies(gameManager);
                updateBullets(gameManager);
                updateEnemiesBullets(gameManager);
                epsilonView.update(epsilon.getX(), epsilon.getY(), epsilon.getRadius());
                epsilonView.updateVertexes(epsilon.getVertexes());
                epsilonView.updateCerberuses(epsilon.getCerberusList());
                updatePanels(gameManager);
            }
        }
    }
    public static void updateModel(GameManager gameManager) {
        if (Controller.gameFinished) {
            gameManager.getGameModel().getEpsilon().increaseSize();
            gameManager.destroyFrame();
        }
        else if (Controller.gameRunning) {
            if (gameManager.getGameModel() != null) {
                gameManager.update();
                updateFrames(gameManager);
            }
        }
        else if (gameManager.getGameModel() != null){
            EpsilonModel epsilon = gameManager.getGameModel().getEpsilon();
            epsilon.getUpTimer().stop();
            epsilon.getDownTimer().stop();
            epsilon.getRightTimer().stop();
            epsilon.getLeftTimer().stop();
        }
    }
    private static void updateEnemies(GameManager gameManager) {
        Map<String, EnemyView> enemiesView = gameManager.getGameView().getEnemiesMap();
        ArrayList<Enemy> enemies = gameManager.getGameModel().getEnemies();
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (enemy instanceof BlackOrb) {
                updateBlackOrbVertexes((BlackOrb) enemy, gameManager);
                updateBlackOrbLasers((BlackOrb) enemy, gameManager);
            }
            else if (enemiesView.get(enemy.getID()) != null) {
                if (enemy instanceof Smiley){
                    updateSmileyAoEs((Smiley) enemy, gameManager);
                    ((SmileyView)enemiesView.get(enemy.getID())).update((int)enemy.getWidth(), (int)enemy.getHeight(),
                            enemy.getCenter(), enemy.getAngle());
                }
                enemiesView.get(enemy.getID()).update(enemy.getCenter(), enemy.getAngle());
                if (enemy instanceof Archmire){
                    updateAoEs((Archmire) enemy, gameManager);
                }
            }
        }
    }
    private static void updateBullets(GameManager gameManager) {
        Map<String, BulletView> bulletsView = gameManager.getGameView().getBulletsMap();
        ArrayList<BulletModel> bullets = gameManager.getGameModel().getBullets();
        for (int i = 0; i < bullets.size(); i++) {
            BulletModel bullet = bullets.get(i);
            if (bulletsView.get(bullet.getID()) != null) {
                bulletsView.get(bullet.getID()).update((int) bullet.getX1(), (int) bullet.getY1());
            }
        }
    }
    private static void updateEnemiesBullets(GameManager gameManager) {
        Map<String, BulletView> bulletsView = gameManager.getGameView().getBulletsMap();
        ArrayList<BulletModel> bullets = gameManager.getGameModel().getEnemiesBullets();
        for (int i = 0; i < bullets.size(); i++) {
            BulletModel bullet = bullets.get(i);
            if (bulletsView.get(bullet.getID()) != null) {
                bulletsView.get(bullet.getID()).update((int) bullet.getX1(), (int) bullet.getY1());
            }
        }
    }
    private static void updateBlackOrbVertexes(BlackOrb blackOrb, GameManager gameManager) {
        Map<String, EnemyView> verticesView = gameManager.getGameView().getEnemiesMap();
        ArrayList<BlackOrbVertex> vertices = blackOrb.getBlackOrbVertices();
        for (int i = 0; i < vertices.size(); i++) {
            BlackOrbVertex vertex = vertices.get(i);
            verticesView.get(vertex.getID()).update(vertex.getCenter(), 0);
        }
    }
    private static void updateBlackOrbLasers(BlackOrb blackOrb, GameManager gameManager) {
        Map<String, BlackOrbLaserView> laserViewMap = gameManager.getGameView().getLaserViewMap();
        ArrayList<BlackOrbLaser> lasers = blackOrb.getLasers();
        for (int i = 0; i < lasers.size(); i++) {
            BlackOrbLaser laser = lasers.get(i);
            laserViewMap.get(laser.getID()).update(laser.getX1(), laser.getY1(), laser.getX2(), laser.getY2());
        }
    }
    private static void updateFrames(GameManager gameManager){
        ArrayList<MyFrame> myFrames = gameManager.getGameModel().getFrames();
        for (int i = 0; i < myFrames.size(); i++){
            myFrames.get(i).update();
        }
    }
    private static void updatePanels(GameManager gameManager) {
        ArrayList<MyFrame> myFrames = gameManager.getGameModel().getFrames();
        Map<String, GamePanel> panels = gameManager.getGameView().getGamePanelMap();
        for (int i = 0; i < myFrames.size(); i++) {
            MyFrame myFrame = myFrames.get(i);
            panels.get(myFrames.get(i).getID()).update((int) myFrame.getX(), (int) myFrame.getY(), (int) myFrame.getWidth(),
                    (int) myFrame.getHeight());
        }
    }
    private static void updateAoEs(Archmire archmire, GameManager gameManager){
        ArrayList<AoEAttack> aoes = archmire.getAoeAttacks();
        Map<String, EnemyView> aoEViewMap = gameManager.getGameView().getAoEViewMap();
        for (int i = 0; i < aoes.size(); i++){
            if (aoEViewMap.get(aoes.get(i).getID()) != null) {
                ((AoEView)aoEViewMap.get(aoes.get(i).getID())).update(aoes.get(i).getClarity());
            }
        }
    }
    private static void updateSmileyAoEs(Smiley smiley, GameManager gameManager){
        ArrayList<SmileyAoEAttack> aoes = smiley.getAoEAttacks();
        Map<String, EnemyView> aoEViewMap = gameManager.getGameView().getAoEViewMap();
        for (int i = 0; i < aoes.size(); i++){
            if (aoEViewMap.get(aoes.get(i).getID()) != null) {
                ((SmileyAoEView)aoEViewMap.get(aoes.get(i).getID())).update(aoes.get(i).getClarity());
            }
        }
    }

}
