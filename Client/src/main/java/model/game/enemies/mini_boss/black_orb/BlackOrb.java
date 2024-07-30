package model.game.enemies.mini_boss.black_orb;

import controller.Controller;
import controller.GameManager;
import controller.save.Configs;
import model.game.enemies.Enemy;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Point;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class BlackOrb extends Enemy {
    private ArrayList<BlackOrbVertex> blackOrbVertices;
    private ArrayList<BlackOrbLaser> lasers;
    private boolean vertexesSet;
    private static int number;
    public BlackOrb(Point center, double velocity, GameManager gameManager) {
        super(center, velocity, gameManager);
        number++;
        logger = Logger.getLogger(BlackOrb.class.getName()+number);
        blackOrbVertices = new ArrayList<>();
        lasers = new ArrayList<>();
        start();
    }
    private void setBlackOrbVertices() {
        int[] x = new int[]{-100, 100, -120, 0, 120};
        int[] y = new int[]{-110, -110, 60, 130, 60};
        for (int i = 0; i < 5; i++) {
            BlackOrbVertex vertex = new BlackOrbVertex(new Point(center.getX()+x[i], center.getY()+y[i]), this);
            blackOrbVertices.add(vertex);
            try {
                sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void setLasers() {
        for (int i = 0; i < 5; i++) {
            for (int j = i+1; j < 5; j++) {
                BlackOrbLaser laser = new BlackOrbLaser(blackOrbVertices.get(i), blackOrbVertices.get(j));
                lasers.add(laser);
                blackOrbVertices.get(i).getLasers().add(laser);
                blackOrbVertices.get(j).getLasers().add(laser);
            }
        }
    }


    @Override
    protected void addVertexes() {

    }

    @Override
    public void addCollective() {

    }
    public void run() {
        while (!died) {
            if (!gameManager.isHypnos() && Controller.gameRunning) {
                if (!vertexesSet) {
                    setBlackOrbVertices();
                    setLasers();
                    vertexesSet = true;
                }
                for (int i = 0; i < lasers.size(); i++) {
                    lasers.get(i).attack();
                }
            }
            try {
                sleep((long) Configs.MODEL_UPDATE_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        interrupt();
        gameManager.getGameModel().getDiedEnemies().add(this);
    }

    @Override
    public Direction getDirection() {
        return null;
    }

    public ArrayList<BlackOrbVertex> getBlackOrbVertices() {
        return blackOrbVertices;
    }

    public ArrayList<BlackOrbLaser> getLasers() {
        return lasers;
    }

    public void setDied(boolean died) {
        this.died = died;
    }
}
