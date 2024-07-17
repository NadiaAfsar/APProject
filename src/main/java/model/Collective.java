package model;

import model.frame.Frame;
import model.interfaces.collision.Collidable;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

public class Collective implements Collidable {
    private int x;
    private int y;
    private Point center;
    private final long time;
    private final String ID;
    private int xp;

    public Collective(int x, int y,int xp) {
        ID = UUID.randomUUID().toString();
        this.x = x;
        this.y = y;
        this.center = new Point(x,y);
        time = System.currentTimeMillis();
        this.xp = xp;
    }

    @Override
    public ArrayList<RotatablePoint> getVertexes() {
        return null;
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


    public long getTime() {
        return time;
    }

    public int getXp() {
        return xp;
    }

}