package model.enemies;

import collision.Collidable;
import controller.Constants;
import model.EpsilonModel;
import model.GameModel;
import movement.RotatablePoint;
import movement.Point;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class TrigorathModel extends Enemy{
    private int x;
    private int y;
    private boolean decreasedVelocity;
    private boolean increasedVelocity;
    public TrigorathModel(Point center) {
        super(center);
        GameModel.getINSTANCE().getEnemies().add(this);
        increasedVelocity = true;
        decreasedVelocity = false;
        velocity = new Point(0.2,0.2);
        HP = 15;
    }
    @Override
    public void setVertexes() {
        vertexes = new ArrayList<>();
        double[] angles = new double[]{1d/6*Math.PI, 5d/6*Math.PI, 9d/6*Math.PI};
        for (int i = 0; i < 3; i++) {
            RotatablePoint vertex = new RotatablePoint(center.getX(), center.getY(), angles[i], 15);
            vertexes.add(vertex);
        }
        position = new RotatablePoint(center.getX(), center.getY(), 41d/180*Math.PI, 19.8);
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
        super.setVelocity();
        if (distanceFromEpsilon() <= 100 && !decreasedVelocity) {
            velocity.setX(0);
            velocity.setY(0);
            decreasedVelocity = true;
            increasedVelocity = false;
        }
        else if (distanceFromEpsilon() > 100 && !increasedVelocity) {
            velocity.setX(0.2);
            velocity.setY(0.2);
            increasedVelocity = true;
            decreasedVelocity = false;
        }
    }

}
