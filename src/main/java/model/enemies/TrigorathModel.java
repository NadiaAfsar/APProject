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

public class TrigorathModel extends Enemy implements Impactable, Collidable, Movable {
    private int x;
    private int y;
    private boolean decreasedVelocity;
    private boolean increasedVelocity;
    public TrigorathModel(Point center, int hp, double velocity) {
        super(center, velocity);
        increasedVelocity = true;
        decreasedVelocity = false;
        this.velocity = new Point(0,0);
        HP = 15+hp;
        initialHP = HP;
        addVertexes();
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
        velocity = new Point(0, 0);
        decreasedVelocity = true;
        increasedVelocity = false;
    }

    @Override
    public void addCollective(GameModel gameModel) {
        for (int i = -1; i < 2; i += 2) {
            Collective collective = new Collective((int) center.getX()+i*13, (int) center.getY()+i*13, Color.BLUE, 5);
            gameModel.getCollectives().add(collective);
            Controller.addCollectiveView(collective);
        }
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


