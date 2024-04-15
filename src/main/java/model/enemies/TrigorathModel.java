package model.enemies;

import collision.Collidable;
import controller.Constants;
import model.EpsilonModel;
import model.GameModel;
import movement.RotatablePoint;
import movement.Point;

import java.awt.*;
import java.util.ArrayList;

public class TrigorathModel extends Enemy{
    private int x;
    private int y;
    private boolean decreasedVelocity;
    private boolean increasedVelocity;
    public TrigorathModel(Point center) {
        super(center);
        GameModel.getINSTANCE().getEnemies().add(this);
        velocity = 3;
        increasedVelocity = true;
        decreasedVelocity = false;
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
        angularAcceleration += angularAccelerationRate/ Constants.UPS;
        angularVelocity += angularAcceleration/Constants.UPS;
        angle += angularVelocity;
    }

    @Override
    public void impact(java.awt.Point collisionPoint, Collidable collidable) {

    }
}
