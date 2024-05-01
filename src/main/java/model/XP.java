package model;

import collision.Collidable;
import controller.Controller;
import movement.Point;
import movement.RotatablePoint;

import java.awt.*;
import java.util.UUID;

public class XP implements Collidable {
    private int x;
    private int y;
    private Color color;
    private Point center;
    private final long time;
    private final String ID;

    public XP(int x, int y, Color color) {
        ID = UUID.randomUUID().toString();
        this.x = x;
        this.y = y;
        this.center = new Point(x,y);
        this.color = color;
        time = System.currentTimeMillis();
    }

    @Override
    public void impact(RotatablePoint collisionPoint, Collidable collidable) {
        EpsilonModel epsilon = (EpsilonModel)collidable;
        epsilon.setXP(epsilon.getXP()+5);
        Controller.removeXP(this);
    }

    @Override
    public Point getCenter() {
        return center;
    }

    public String getID() {
        return ID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public long getTime() {
        return time;
    }
}