package model.enemies.mini_boss.black_orb;

import model.enemies.Enemy;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlackOrb extends Enemy {
    private ArrayList<BlackOrbVertex> blackOrbVertices;
    private ArrayList<BlackOrbLaser> lasers;
    private boolean vertexesSet;
    public BlackOrb(Point center, double velocity) {
        super(center, velocity);
    }
    private void setBlackOrbVertices() {
        blackOrbVertices = new ArrayList<>();
        int[] x = new int[]{-50, 50, -60, 0, 60};
        int[] y = new int[]{-50, -50, 40, 60, 40};
        for (int i = 0; i < 5; i++) {
            BlackOrbVertex vertex = new BlackOrbVertex(new Point(center.getX()+x[i], center.getY()+y[i]), this);
            blackOrbVertices.add(vertex);
//            try {
//                sleep(500);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        }
    }
    private void setLasers() {
        lasers = new ArrayList<>();
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
    public void nextMove() {
            if (!vertexesSet) {
                setBlackOrbVertices();
                setLasers();
                vertexesSet = true;
            }
            for (int i = 0; i < lasers.size(); i++) {
                lasers.get(i).attack();
            }

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
}
