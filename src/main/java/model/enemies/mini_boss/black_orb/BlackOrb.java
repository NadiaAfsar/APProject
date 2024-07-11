package model.enemies.mini_boss.black_orb;

import model.enemies.Enemy;
import movement.Point;

import java.util.ArrayList;

public class BlackOrb extends Enemy {
    private ArrayList<BlackOrbVertex> blackOrbVertices;
    private ArrayList<BlackOrbLaser> lasers;
    public BlackOrb(Point center, double velocity) {
        super(center, velocity);
        setBlackOrbVertices();
        setLasers();
    }
    private void setBlackOrbVertices() {
        blackOrbVertices = new ArrayList<>();
    }
    private void setLasers() {
        lasers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = i+1; j < 5; j++) {
                BlackOrbLaser laser = new BlackOrbLaser(blackOrbVertices.get(i), blackOrbVertices.get(j));
                lasers.add(laser);
            }
        }
    }


    @Override
    protected void addVertexes() {

    }

    @Override
    public void addCollective() {

    }
}
