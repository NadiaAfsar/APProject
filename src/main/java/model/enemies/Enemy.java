package model.enemies;

import collision.Collidable;
import model.EpsilonModel;
import movement.Direction;
import movement.Moveable;

import java.awt.*;
import java.util.ArrayList;

public abstract class Enemy implements Collidable, Moveable {
    protected double x;
    protected double y;
    protected Point center;
    protected ArrayList<Point> vertexes;
    protected double velocity;
    protected double acceleration;
    protected double accelerationRate;
    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        setVertexes();
        acceleration = 0;
        accelerationRate = 0;
    }
    public abstract void setVertexes();

    public int getX() {
        return (int)x;
    }

    public int getY() {
        return (int)y;
    }

    public ArrayList<Point> getVertexes() {
        return vertexes;
    }

    public Point getCenter() {
        return center;
    }
    protected abstract void setCenter(double x, double y);
    @Override
    public void move() {
        setVelocity();
        Direction direction = new Direction(getCenter(), EpsilonModel.getINSTANCE().getCenter());
        x += direction.getDx()*velocity;
        y += direction.getDy()*velocity;
        setCenter(x,y);
    }
    protected abstract void setVelocity();
    public void impact(Point collisionPoint, Collidable collidable) {

    }
}
