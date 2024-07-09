package model.enemies;

import controller.GameManager;
import model.game.GameModel;
import movement.Direction;
import movement.Movable;
import movement.Point;
import movement.RotatablePoint;

import java.util.ArrayList;

public class Wyrm extends Enemy implements Movable {
    private int direction;
    public Wyrm(Point center, double velocity, int hp) {
        super(center, velocity);
        this.HP = 10 + hp;
        initialHP = HP;
        direction = 1;
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

    @Override
    public Direction getDirection() {
        Point epsilonCenter = GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter();
        Direction direction = new Direction(epsilonCenter, center);
        if (getDistance(center.getX(), center.getY(), epsilonCenter.getX(), epsilonCenter.getY()) > 100) {
            return direction;
        }
        Direction direction1 = new Direction();
        direction1.setDx(direction.getDy()*this.direction);
        direction1.setDy(-direction.getDy()*this.direction);
        return direction1;
    }
    private static double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2));
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

    @Override
    public void setAngularVelocity(double velocity) {

    }

    @Override
    public void setAngularAcceleration(double acceleration) {

    }

    @Override
    public void setAngularAccelerationRate(double rate) {

    }

    @Override
    public double getAngularAccelerationRate() {
        return 0;
    }

    @Override
    public void specialMove() {

    }


    @Override
    public void addCollective(GameModel gameModel) {

    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
