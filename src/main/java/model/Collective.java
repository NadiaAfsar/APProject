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
    private Color color;
    private Point center;
    private final long time;
    private final String ID;
    private int xp;
    private Frame frame;

    public Collective(int x, int y, Color color, int xp, Frame frame) {
        ID = UUID.randomUUID().toString();
        this.x = x;
        this.y = y;
        this.center = new Point(x,y);
        this.color = color;
        this.frame = frame;
        time = System.currentTimeMillis();
        this.xp = xp;
        this.frame.getCollectives().add(this);
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

    public Color getColor() {
        return color;
    }

    public long getTime() {
        return time;
    }

    public int getXp() {
        return xp;
    }

    public Frame getFrame() {
        return frame;
    }
}