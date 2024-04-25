package model.enemies;

import collision.Collidable;
import controller.Constants;
import controller.Controller;
import model.GameModel;
import model.XP;
import movement.Direction;
import movement.RotatablePoint;
import movement.Point;

import java.awt.*;
import java.util.ArrayList;

public class SquarantineModel extends Enemy {
    private boolean hasRandomAcceleration;
    private double velocityX;
    private double velocityY;
    public SquarantineModel(Point center) {
        super(center);
        GameModel.getINSTANCE().getEnemies().add(this);
        velocity = new Point(0,0);
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
        if (!hasRandomAcceleration && !impact) {
            int x = (int) (Math.random() * 200);
            if (x == 5) {
                acceleration.setX(3);
                acceleration.setY(3);
                accelerationRate.setX(-1);
                accelerationRate.setY(-1);
                hasRandomAcceleration = true;
            }
        }
        if (impact) {
            super.setVelocity();
        }
        else if (hasRandomAcceleration) {
            velocityX += acceleration.getX() / Constants.UPS;
            velocityY += acceleration.getY() / Constants.UPS;
            velocity.setX(velocityX* direction.getDx());
            velocity.setY(velocityY* direction.getDy());
            if (velocityX <= 0 || velocityY <= 0) {
                velocityX = 0;
                velocityY = 0;
                velocity = new Point(0,0);
                acceleration = new Point(0, 0);
                accelerationRate = new Point(0, 0);
                hasRandomAcceleration = false;
            }
        }
    }
    protected void setImpactAcceleration(Direction direction, double distance) {
        hasRandomAcceleration = false;
        velocityX = 0;
        velocityY = 0;
        velocity = new Point(0,0);
        super.setImpactAcceleration(direction, distance);
    }

    @Override
    public void addXP() {
        XP xp = new XP((int)center.getX(), (int)center.getY(), Color.MAGENTA);
        GameModel.getINSTANCE().getXPs().add(xp);
        Controller.addXPView(xp);
    }

}