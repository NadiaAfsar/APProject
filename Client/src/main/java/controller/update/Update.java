package controller.update;

import application.MyApplication;
import controller.Controller;
import controller.game_manager.GameManager;
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
import model.interfaces.movement.Point;
import view.game.*;

import java.util.ArrayList;
import java.util.Map;

public class Update {
    public static void updateView(GameManager gameManager) {
        if (gameManager.isRunning()) {
            if (gameManager.getGameView() != null) {
                GameView gameView = gameManager.getGameView();
                EpsilonModel epsilon = gameManager.getGameModel().getMyEpsilon();
                gameView.update(epsilon.getHP(), epsilon.getXP(), gameManager.getGameModel().getWave(),
                        gameManager.getGameModel().getTimePlayed()/1000, gameManager.getApplicationManager().
                                getPickedSkill(), gameManager.getCompetitorHP(), gameManager.getCompetitorXP());
                gameView.updatePanels();
                updateEnemies(gameManager);
                updateBullets(gameManager);
                updateEnemiesBullets(gameManager);
                updatePanels(gameManager);
                updateEpsilons(gameManager);
            }
        }
    }
    private static void updateEpsilons(GameManager gameManager){
        for (int i = 0; i < gameManager.getGameModel().getEpsilons().size(); i++){
            EpsilonModel epsilon = gameManager.getGameModel().getEpsilons().get(i);
            EntityView epsilonView = gameManager.getGameView().getEpsilonsMap().get(epsilon.getID());
            epsilonView.update(new Point(epsilon.getX(), epsilon.getY()), 0);
            gameManager.getGameView().updateVertexes(epsilon.getVertexes(), epsilon.getID());
            gameManager.getGameView().updateCerberuses(epsilon.getCerberusList(), epsilon.getID());
        }
    }
    public static void updateModel(GameManager gameManager) {
        if (gameManager.getGameModel() != null) {
            if (gameManager.isFinished()) {
                gameManager.getGameModel().getMyEpsilon().increaseSize();
                gameManager.destroyFrame();
            } else if (gameManager.isRunning()) {
                if (gameManager.getGameModel() != null) {
                    gameManager.update();
                    updateFrames(gameManager);
                }
            } else {
                EpsilonModel epsilon = gameManager.getGameModel().getMyEpsilon();
                epsilon.getUpTimer().stop();
                epsilon.getDownTimer().stop();
                epsilon.getRightTimer().stop();
                epsilon.getLeftTimer().stop();
            }
        }
    }
    private static void updateEnemies(GameManager gameManager) {
        Map<String, EntityView> enemiesView = gameManager.getGameView().getEnemiesMap();
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
                    enemiesView.get(enemy.getID()).updateSmiley((int)enemy.getWidth(), (int)enemy.getHeight(),
                            enemy.getCenter(), enemy.getAngle());
                }
                else {
                    enemiesView.get(enemy.getID()).update(enemy.getCenter(), enemy.getAngle());
                    if (enemy instanceof Archmire) {
                        updateAoEs((Archmire) enemy, gameManager);
                    }
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
        Map<String, EntityView> verticesView = gameManager.getGameView().getEnemiesMap();
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
        Map<String, EntityView> aoEViewMap = gameManager.getGameView().getAoEViewMap();
        for (int i = 0; i < aoes.size(); i++){
            if (aoEViewMap.get(aoes.get(i).getID()) != null) {
                aoEViewMap.get(aoes.get(i).getID()).updateArchmireAoE(aoes.get(i).getClarity());
            }
        }
    }
    private static void updateSmileyAoEs(Smiley smiley, GameManager gameManager){
        ArrayList<SmileyAoEAttack> aoes = smiley.getAoEAttacks();
        Map<String, EntityView> aoEViewMap = gameManager.getGameView().getAoEViewMap();
        for (int i = 0; i < aoes.size(); i++){
            if (aoEViewMap.get(aoes.get(i).getID()) != null) {
                aoEViewMap.get(aoes.get(i).getID()).updateAoE(aoes.get(i).getClarity());
            }
        }
    }

}
