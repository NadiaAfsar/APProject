package model.enemies;

import model.frame.Frame;
import model.interfaces.collision.Collidable;
import model.interfaces.collision.Impactable;
import controller.Controller;
import controller.GameManager;
import model.Collective;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Movable;
import model.interfaces.movement.RotatablePoint;
import model.interfaces.movement.Point;

import java.awt.*;
import java.util.ArrayList;

public class SquarantineModel extends Enemy implements Impactable, Movable {
    private boolean hasRandomAcceleration;
    private Direction direction;
    public SquarantineModel(Point center, int hp, double velocity, Frame frame) {
        super(center, velocity);
        width = GameManager.configs.SQUARANTINE_WIDTH;
        height = GameManager.configs.SQUARANTINE_WIDTH;
        this.frame = frame;
        HP = 10+hp;
        damage = 6;
        hasRandomAcceleration = false;
        addVertexes();
        this.frame.getEnemies().add(this);
        Controller.addEnemyView(this);
    }
    protected void addVertexes() {
        vertexes = new ArrayList<>();
        double[] angles = new double[]{5d/4*Math.PI, 7d/4*Math.PI, 1d/4*Math.PI, 3d/4*Math.PI};
        for (int i = 0; i < 4; i++) {
            RotatablePoint vertex = new RotatablePoint(center.getX(), center.getY(), angles[i]+angle,
                    width*Math.sqrt(0.5));
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

    public void setSpecialImpact() {
        hasRandomAcceleration = false;
        velocity = new Point(0,0);
    }

    @Override
    public void addCollective() {
        Collective collective = new Collective((int)center.getX(), (int)center.getY(),5);
        GameManager.getINSTANCE().getGameModel().getCollectives().add(collective);
        Controller.addCollectiveView(collective);
    }
    public Direction getDirection() {
        Direction direction1 = new Direction(getCenter(), GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter());
//        System.out.println(direction1.getDx());
//        System.out.println(direction1.getDy());
        return direction1;
        //return new Direction(getCenter(), GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter());
    }
    public void nextMove() {
            move();
            checkCollision();
    }

    @Override
    public void setAngularVelocity(double velocity) {
        angularVelocity = velocity;
    }

    @Override
    public void setAngularAcceleration(double acceleration) {
        angularAcceleration = acceleration;
    }

    @Override
    public void setAngularAccelerationRate(double accelerationRate) {
        angularAccelerationRate = accelerationRate;
    }

    @Override
    public double getAngularAccelerationRate() {
        return angularAccelerationRate;
    }

    @Override
    public void setCenter(Point center) {
        this.center = center;
    }

    @Override
    public void setImpact(boolean impact) {
        this.impact = impact;
    }

    @Override
    public Point getAcceleration() {
        return acceleration;
    }

    @Override
    public void setAcceleration(Point acceleration) {
        this.acceleration = acceleration;
    }

    @Override
    public Point getAccelerationRate() {
        return accelerationRate;
    }

    @Override
    public void setAccelerationRate(Point accelerationRate) {
        this.accelerationRate = accelerationRate;
    }

    @Override
    public void setVelocity(Point velocity) {
        this.velocity = velocity;
    }

    @Override
    public Point getVelocity() {
        return velocity;
    }

    @Override
    public double getVelocityPower() {
        return velocityPower;
    }
    @Override
    public double getAngularVelocity() {
        return angularVelocity;
    }

    @Override
    public double getAngularAcceleration() {
        return angularAcceleration;
    }

    @Override
    public void setAngle(double angle) {
        this.angle = angle;
    }

}