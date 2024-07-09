package model.enemies;

import collision.Collidable;
import collision.Impactable;
import controller.Controller;
import controller.GameManager;
import model.game.GameModel;
import model.Collective;
import movement.Direction;
import movement.Movable;
import movement.RotatablePoint;
import movement.Point;

import java.awt.*;
import java.util.ArrayList;

public class SquarantineModel extends Enemy implements Impactable, Collidable, Movable {
    private boolean hasRandomAcceleration;
    private Direction direction;
    public SquarantineModel(Point center,  int hp, double velocity) {
        super(center, velocity);
        this.velocity = new Point(0,0);
        HP = 10+hp;
        initialHP = HP;
        hasRandomAcceleration = false;
        addVertexes();
    }
    protected void addVertexes() {
        vertexes = new ArrayList<>();
        double[] angles = new double[]{5d/4*Math.PI, 7d/4*Math.PI, 1d/4*Math.PI, 3d/4*Math.PI};
        for (int i = 0; i < 4; i++) {
            RotatablePoint vertex = new RotatablePoint(center.getX(), center.getY(), angles[i]+angle, 18.4);
            vertexes.add(vertex);
            if (i == 0) {
                position = vertex;
            }
        }
    }


    @Override
    public void specialMove() {
        if (!hasRandomAcceleration && !impact) {
            int x = (int) (Math.random() * 200);
            if (x == 5) {
                direction = getDirection();
                acceleration = new Point(30*direction.getDx(), 30*direction.getDy());
                accelerationRate = new Point(-10*direction.getDx(), -10*direction.getDy());
                hasRandomAcceleration = true;
            }
        }
        if (hasRandomAcceleration && !impact) {
            if (acceleration.getX() <= 0 || acceleration.getY() <= 0) {
                velocity = new Point(0,0);
                acceleration = new Point(0, 0);
                accelerationRate = new Point(0, 0);
                hasRandomAcceleration = false;
            }
            else {
                Direction newDirection = getDirection();
                acceleration = new Point(acceleration.getX()/direction.getDx()*newDirection.getDx(),
                        acceleration.getY()/direction.getDy()*newDirection.getDy());
                accelerationRate = new Point(accelerationRate.getX()/direction.getDx()*newDirection.getDx(),
                        accelerationRate.getY()/direction.getDy()*newDirection.getDy());
                direction = newDirection;

            }
        }
    }

    @Override
    public void setAngularVelocity(double velocity) {

    }

    @Override
    public void setAngularAcceleration(double acceleration) {

    }

    @Override
    public void setAngularAccelerationRate(double accelerationRate) {

    }

    @Override
    public double getAngularAccelerationRate() {
        return 0;
    }

    @Override
    public void setCenter(Point center) {

    }

    @Override
    public void setImpact(boolean impact) {

    }

    @Override
    public Point getAcceleration() {
        return null;
    }

    @Override
    public void setAcceleration(Point acceleration) {

    }

    @Override
    public Point getAccelerationRate() {
        return null;
    }

    @Override
    public void setAccelerationRate(Point accelerationRate) {

    }

    @Override
    public void setVelocity(Point velocity) {

    }

    @Override
    public Point getVelocity() {
        return null;
    }

    @Override
    public double getVelocityPower() {
        return 0;
    }

    public void setSpecialImpact() {
        hasRandomAcceleration = false;
        velocity = new Point(0,0);
    }

    @Override
    public void addCollective(GameModel gameModel) {
        Collective collective = new Collective((int)center.getX(), (int)center.getY(), Color.MAGENTA, 5);
        gameModel.getCollectives().add(collective);
        Controller.addCollectiveView(collective);
    }
    public Direction getDirection() {
        return new Direction(getCenter(), GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter());
    }

    @Override
    public double getAngularVelocity() {
        return 0;
    }

    @Override
    public double getAngularAcceleration() {
        return 0;
    }

    @Override
    public void setAngle(double angle) {

    }

}