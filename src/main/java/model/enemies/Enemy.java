package model.enemies;

import collision.Collidable;
import controller.Constants;
import model.EpsilonModel;
import movement.Direction;
import movement.Movable;
import movement.Point;
import movement.RotatablePoint;

import java.awt.*;
import java.util.ArrayList;

public abstract class Enemy implements Collidable, Movable {
    protected RotatablePoint position;
    protected Point center;
    protected ArrayList<RotatablePoint> vertexes;
    protected double velocity;
    protected double acceleration;
    protected double accelerationRate;
    protected double angularVelocity;
    protected double angularAcceleration;
    protected double angularAccelerationRate;
    protected double angle;
    public Enemy(Point center) {
        this.center = center;
        setVertexes();
        acceleration = 0;
        accelerationRate = 0;
        angularVelocity = 0.01;
        angularAcceleration = 0;
        angularAccelerationRate = 0;
        angle = 30;
    }
    public abstract void setVertexes();

    public int getX() {
        return (int) position.getRotatedX();
    }

    public int getY() {
        return (int) position.getRotatedY();
    }

    public ArrayList<RotatablePoint> getVertexes() {
        return vertexes;
    }

    public Point getCenter() {
        return center;
    }
    @Override
    public void move() {
        setVelocity();
        Direction direction = new Direction(getCenter(), EpsilonModel.getINSTANCE().getCenter());
        angle += angularVelocity / Constants.UPS;
        angle %= 2*Math.PI;
        center = new Point(center.getX()+direction.getDx()*velocity, center.getY()+direction.getDy()*velocity);
        moveVertexes();
    }
    protected abstract void setVelocity();
    public void impact(Point collisionPoint, Collidable collidable) {

    }

    public double getAngle() {
        return angle;
    }
    private void moveVertexes() {
        for (int i = 0; i < vertexes.size(); i++) {
            RotatablePoint vertex = vertexes.get(i);
            vertex.setX(center.getX());
            vertex.setY(center.getY());
            vertex.setAngle(vertex.getAngle()+angle);
        }
        position.setX(center.getX());
        position.setY(center.getY());
        position.setAngle(position.getAngle()+angle);
    }

    public RotatablePoint getPosition() {
        return position;
    }
}
