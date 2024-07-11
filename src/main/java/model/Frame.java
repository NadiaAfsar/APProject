package model;

import controller.Constants;
import controller.GameManager;
import model.game.GameModel;
import model.game.Side;

import java.util.HashMap;
import java.util.Map;

public class Frame {
    private double width;
    private double height;
    private double x;
    private double y;
    private final boolean isIsometric;
    private final boolean isRigid;
    private Map<Integer, Side> sides;

    public Frame(double width, double height, double x, double y, boolean isIsometric, boolean isRigid) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.isIsometric = isIsometric;
        this.isRigid = isRigid;
        sides = new HashMap<Integer, Side>() {{
            put(1, new Side());
            put(2, new Side());
            put(3, new Side());
            put(4, new Side());
        }};
    }
    public Map<Integer, Side> getSides() {
        return sides;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    public void changeWidth(BulletModel bullet, int x) {
        if (!isRigid) {
            setX(getX() + x + bullet.getDirection().getDx() * 20);
            setY(getY() + bullet.getDirection().getDy() * 20);
        }
        if (!isIsometric) {
            setWidth(getWidth() + 10);
            if (checkPosition()) {
                setWidth(getWidth() - 10);
            }
        }
    }
    public void changeHeight(BulletModel bullet, int y) {
        if (!isRigid) {
            setX(getX() + bullet.getDirection().getDx() * 20);
            setY(getY() + y + bullet.getDirection().getDy() * 20);
        }
        if (!isIsometric) {
            setHeight(getHeight() + 10);
            if (checkPosition()) {
                setHeight(getHeight() - 10);
            }
        }
    }
    private boolean checkPosition() {
        if (getX() < 0) {
            setX(0);
            return true;
        }
        else if (getX() > Constants.FRAME_SIZE.getWidth()-getWidth()) {
            setX(Constants.FRAME_SIZE.getWidth()-getWidth());
            return true;
        }
        if (getY() < 0) {
            setY(0);
            return true;
        }
        else if (getY() > Constants.FRAME_SIZE.getHeight()-getHeight()) {
            setY(Constants.FRAME_SIZE.getHeight()-getHeight());
            return true;
        }
        return false;
    }
}
