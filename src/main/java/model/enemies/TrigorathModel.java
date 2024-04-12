package model.enemies;

import collision.Collidable;
import model.EpsilonModel;
import model.GameModel;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrigorathModel extends Enemy{
    private int x;
    private int y;
    private boolean decreasedVelocity;
    private boolean increasedVelocity;
    public TrigorathModel(int x, int y) {
        super(x,y);
        setCenter(x,y);
        GameModel.getINSTANCE().getEnemies().add(this);
        velocity = 3;
        increasedVelocity = true;
        decreasedVelocity = false;
    }
    @Override
    public void setVertexes() {
        vertexes = new ArrayList<>();
        int[] x = new int[]{13,26,0};
        int[] y = new int[]{0,24,24};
        for (int i = 0; i < 3; i++) {
            Point vertex = new Point((int)this.x + x[i], (int)this.y + y[i]);
            vertexes.add(vertex);
        }
    }

    @Override
    public void setCenter(double x, double y) {
        center = new Point((int)x+13, (int)y+15);
    }
    private double distanceFromEpsilon() {
        double x1 = center.getX();
        double y1 = center.getY();
        double x2 = EpsilonModel.getINSTANCE().getCenter().getX();
        double y2 = EpsilonModel.getINSTANCE().getCenter().getX();
        return Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
    }
    @Override
    public void setVelocity() {
        if (distanceFromEpsilon() <= 100 && !decreasedVelocity) {
            velocity -= 2;
            decreasedVelocity = true;
            increasedVelocity = false;
        }
        else if (distanceFromEpsilon() > 100 && !increasedVelocity) {
            velocity += 2;
            increasedVelocity = true;
            decreasedVelocity = false;
        }
        acceleration += accelerationRate;
        velocity += acceleration;
    }
}
