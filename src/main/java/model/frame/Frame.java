package model.frame;

import controller.Controller;
import controller.GameManager;
import controller.save.Configs;
import model.BulletModel;
import model.Collective;
import model.EpsilonModel;
import model.enemies.Enemy;
import model.enemies.mini_boss.black_orb.BlackOrbLaser;
import model.enemies.mini_boss.black_orb.BlackOrbVertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Frame {
    private double width;
    private double height;
    private double x;
    private double y;
    private final boolean isIsometric;
    private final boolean isRigid;
    private final Map<Integer, Side> sides;
    private ArrayList<Enemy> enemies;
    private ArrayList<BlackOrbVertex> blackOrbVertices;
    private ArrayList<BlackOrbLaser> blackOrbLasers;
    private EpsilonModel epsilon;
    private ArrayList<BulletModel> bulletModels;
    private ArrayList<Collective> collectives;
    private final String ID;
    private boolean isWyrmFrame;

    public Frame(double width, double height, double x, double y, boolean isIsometric, boolean isRigid, boolean isWyrmFrame) {
        ID = UUID.randomUUID().toString();
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.isIsometric = isIsometric;
        this.isRigid = isRigid;
        this.isWyrmFrame = isWyrmFrame;
        enemies = new ArrayList<>();
        blackOrbVertices = new ArrayList<>();
        blackOrbLasers = new ArrayList<>();
        bulletModels = new ArrayList<>();
        collectives = new ArrayList<>();
        sides = new HashMap<Integer, Side>() {{
            put(1, new Side());
            put(2, new Side());
            put(3, new Side());
            put(4, new Side());
        }};
        Controller.addFrameView(this);
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
        else if (getX() > Configs.FRAME_SIZE.getWidth()-getWidth()) {
            setX(Configs.FRAME_SIZE.getWidth()-getWidth());
            return true;
        }
        if (getY() < 0) {
            setY(0);
            return true;
        }
        else if (getY() > Configs.FRAME_SIZE.getHeight()-getHeight()) {
            setY(Configs.FRAME_SIZE.getHeight()-getHeight());
            return true;
        }
        return false;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<BlackOrbVertex> getBlackOrbVertices() {
        return blackOrbVertices;
    }

    public ArrayList<BlackOrbLaser> getBlackOrbLasers() {
        return blackOrbLasers;
    }

    public EpsilonModel getEpsilon() {
        return epsilon;
    }

    public ArrayList<BulletModel> getBulletModels() {
        return bulletModels;
    }

    public void setEpsilon(EpsilonModel epsilon) {
        this.epsilon = epsilon;
    }

    public String getID() {
        return ID;
    }

    public ArrayList<Collective> getCollectives() {
        return collectives;
    }

    public boolean isWyrmFrame() {
        return isWyrmFrame;
    }
}
