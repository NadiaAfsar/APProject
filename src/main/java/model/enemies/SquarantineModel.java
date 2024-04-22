package model.enemies;

import collision.Collidable;
import controller.Constants;
import model.GameModel;
import movement.RotatablePoint;
import movement.Point;

import java.awt.*;
import java.util.ArrayList;

public class SquarantineModel extends Enemy {
    public SquarantineModel(Point center) {
        super(center);
        GameModel.getINSTANCE().getEnemies().add(this);
        velocity = 1;
    }
    @Override
    public void setVertexes() {
        vertexes = new ArrayList<>();
        double[] angles = new double[]{5d/4*Math.PI, 7d/4*Math.PI, 1d/4*Math.PI, 3d/4*Math.PI};
        for (int i = 0; i < 4; i++) {
            RotatablePoint vertex = new RotatablePoint(center.getX(), center.getY(), angles[i], 18.4);
            vertexes.add(vertex);
            if (i == 0) {
                position = vertex;
            }
        }
    }

    @Override
    protected void setVelocity() {
        int x = (int)(Math.random()*200);
//        if (x == 5 && acceleration == 0) {
//            acceleration = 3;
//            accelerationRate = -1;
//        }
        angularAcceleration += angularAccelerationRate/ Constants.UPS;
        angularVelocity += angularAcceleration/Constants.UPS;
        if (angularVelocity <= 0) {
            angularAcceleration = 0;
            angularAccelerationRate = 0;
        }
        angle += angularVelocity;
        acceleration += accelerationRate/Constants.UPS;
        velocity += acceleration/Constants.UPS;
        if (velocity <= 1 && (acceleration != 0 || accelerationRate != 0)) {
            acceleration = 0;
            accelerationRate = 0;
        }
    }


    @Override
    public void impact(java.awt.Point collisionPoint, Collidable collidable) {

    }
}