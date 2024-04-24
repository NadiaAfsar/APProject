package model;

import collision.Collidable;
import movement.Point;
import movement.RotatablePoint;

public class XP implements Collidable {
    private int x;
    private int y;
    private String color;
    private Point center;

    public XP(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.center = new Point(x,y);
        this.color = color;
    }

    @Override
    public void impact(RotatablePoint collisionPoint, Collidable collidable) {
        EpsilonModel epsilon = (EpsilonModel)collidable;
        epsilon.setXP(epsilon.getXP()+5);
    }

    @Override
    public Point getCenter() {
        return center;
    }
}