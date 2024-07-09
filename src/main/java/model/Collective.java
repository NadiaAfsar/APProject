package model;

import collision.Collidable;
import controller.Controller;
import model.game.GameModel;
import movement.Point;
import movement.RotatablePoint;

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

    public Collective(int x, int y, Color color, int xp) {
        ID = UUID.randomUUID().toString();
        this.x = x;
        this.y = y;
        this.center = new Point(x,y);
        this.color = color;
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

    public Color getColor() {
        return color;
    }

    public long getTime() {
        return time;
    }

    public int getXp() {
        return xp;
    }
}