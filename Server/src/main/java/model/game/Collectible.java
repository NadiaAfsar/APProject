package model.game;

import controller.GameManager;
import model.interfaces.collision.Collidable;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;

import java.util.ArrayList;
import java.util.UUID;

public class Collectible implements Collidable {
    private int x;
    private int y;
    private Point center;
    private final long time;
    private final String ID;
    private int xp;

    public Collectible(int x, int y, int xp) {
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

    @Override
    public GameManager getGameManager() {
        return null;
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