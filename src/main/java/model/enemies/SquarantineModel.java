package model.enemies;

import collision.Collidable;
import controller.Constants;
import model.GameModel;
import movement.Direction;
import movement.RotatablePoint;
import movement.Point;

import java.util.ArrayList;

public class SquarantineModel extends Enemy {
    private boolean hasRandomAcceleration;
    public SquarantineModel(Point center) {
        super(center);
        GameModel.getINSTANCE().getEnemies().add(this);
        velocity = new Point(1,1);
        HP = 10;
        hasRandomAcceleration = false;
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
        if (x == 5 && !hasRandomAcceleration) {
            acceleration.setX(3);
            acceleration.setY(3);
            accelerationRate.setX(-1);
            accelerationRate.setY(-1);
            hasRandomAcceleration = true;
        }
        super.setVelocity();
        if (hasRandomAcceleration) {
            acceleration.setX(Math.abs(acceleration.getX() * direction.getxSign()));
            acceleration.setY(Math.abs(acceleration.getY() * direction.getySign()));
        }
        if (hasRandomAcceleration) {
            if ((velocity.getX() <= 1 || velocity.getY() <= 1) && ((acceleration.getX() != 0 || acceleration.getY() != 0))) {
                acceleration = new Point(0, 0);
                accelerationRate = new Point(0, 0);
                if (hasRandomAcceleration) {
                    hasRandomAcceleration = false;
                }
            }
        }
    }


}