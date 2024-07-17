package model.enemies;

import model.frame.Frame;
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

public class TrigorathModel extends Enemy implements Impactable, Movable {
    private int x;
    private int y;
    private boolean decreasedVelocity;
    private boolean increasedVelocity;
    public TrigorathModel(Point center, int hp, double velocity, Frame frame) {
        super(center, velocity);
        width = GameManager.configs.TRIGORATH_WIDTH;
        height = GameManager.configs.TRIGORATH_HEIGHT;
        this.frame = frame;
        increasedVelocity = true;
        decreasedVelocity = false;
        HP = 15+hp;
        damage = 10;
        addVertexes();
        this.frame.getEnemies().add(this);
        Controller.addEnemyView(this);
    }
    protected void addVertexes() {
        vertexes = new ArrayList<>();
        double[] angles = new double[]{1d/6*Math.PI, 5d/6*Math.PI, 9d/6*Math.PI};
        for (int i = 0; i < 3; i++) {
            RotatablePoint vertex = new RotatablePoint(center.getX(), center.getY(), angles[i]+angle, 15);
            vertexes.add(vertex);
        }
        position = new RotatablePoint(center.getX(), center.getY(), 41d/180*Math.PI, 19.8);
    }

    private double distanceFromEpsilon() {
        double x1 = center.getX();
        double y1 = center.getY();
        double x2 = GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter().getX();
        double y2 = GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter().getX();
        return Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
    }
    @Override
    public void specialMove() {
        double dx = getDirection().getDx()*getVelocityPower();
        double dy = getDirection().getDx()*getVelocityPower();
        if (distanceFromEpsilon() <= 100 && !decreasedVelocity && !impact) {
            velocity = new Point(0, 0);
            decreasedVelocity = true;
            increasedVelocity = false;
        }
        else if (distanceFromEpsilon() > 100 && !increasedVelocity && !impact) {
            velocity = new Point(2*dx, 2*dy);
            increasedVelocity = true;
            decreasedVelocity = false;
        }
        if (increasedVelocity) {
            velocity = new Point(2*dx, 2*dy);
        }
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

    public void setSpecialImpact() {
        velocity = new Point(0, 0);
        decreasedVelocity = true;
        increasedVelocity = false;
    }

    @Override
    public void addCollective() {
        for (int i = -1; i < 2; i += 2) {
            Collective collective = new Collective((int) center.getX()+i*13, (int) center.getY()+i*13, 5);
            GameManager.getINSTANCE().getGameModel().getCollectives().add(collective);
            Controller.addCollectiveView(collective);
        }
    }
    public Direction getDirection() {
        return new Direction(getCenter(), GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter());
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


