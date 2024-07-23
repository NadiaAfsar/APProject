package model.frame;

import controller.Controller;
import controller.GameManager;
import controller.save.Configs;
import controller.update.Update;
import model.*;
import model.enemies.Enemy;
import model.enemies.mini_boss.black_orb.BlackOrbLaser;
import model.enemies.mini_boss.black_orb.BlackOrbVertex;
import model.interfaces.movement.Point;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Frame {
    private double width;
    private double height;
    private double minWidth;
    private double minHeight;
    private double x;
    private double y;
    private final boolean isIsometric;
    private boolean isRigid;
    private final Map<Integer, Side> sides;
    private ArrayList<Enemy> enemies;
    private ArrayList<BlackOrbVertex> blackOrbVertices;
    private final String ID;
    private ArrayList<Map<String, double[]>> overlaps;
    private final Object overlapLock;
    private Logger logger;
    private static int number;
    private boolean stopMoving;

    public Frame(double width, double height, double x, double y, boolean isIsometric, boolean isRigid, double minWidth,
                 double minHeight) {
        ID = UUID.randomUUID().toString();
        number++;
        logger = Logger.getLogger(Frame.class.getName()+number);
        this.width = width;
        this.height = height;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.x = x;
        this.y = y;
        this.isIsometric = isIsometric;
        this.isRigid = isRigid;
        enemies = new ArrayList<>();
        blackOrbVertices = new ArrayList<>();
        overlaps = new ArrayList<>();
        overlapLock = new Object();
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
        if (!stopMoving && !checkRigidFramesX(x)) {
            if (!isRigid) {
                    if (x < 0) {
                        sides.get(4).separateAll();
                    } else {
                        sides.get(2).separateAll();
                    }
                    setX(getX() + x + bullet.getDirection().getDx() * 20);
                    setY(getY() + bullet.getDirection().getDy() * 20);
                    moveEntities(x + bullet.getDirection().getDx() * 20, bullet.getDirection().getDy() * 20);
            }
            if (!isIsometric) {
                setWidth(getWidth() + 10);
                if (checkPosition()) {
                    setWidth(getWidth() - 10);
                }
            }
            if (x < 0) {
                sides.get(4).shootAll();
            } else {
                sides.get(2).shootAll();
            }
        }
    }
    public void changeHeight(BulletModel bullet, int y) {
        if (!stopMoving && !checkRigidFramesY(y)) {
            if (!isRigid) {
                    if (y < 0) {
                        sides.get(1).separateAll();
                    } else {
                        sides.get(3).separateAll();
                    }
                    setX(getX() + bullet.getDirection().getDx() * 20);
                    setY(getY() + y + bullet.getDirection().getDy() * 20);
                    moveEntities(bullet.getDirection().getDx() * 20, y + bullet.getDirection().getDy() * 20);
            }
            if (!isIsometric) {
                setHeight(getHeight() + 10);
                if (checkPosition()) {
                    setHeight(getHeight() - 10);
                }
            }
            if (y < 0) {
                sides.get(1).shootAll();
            } else {
                sides.get(3).shootAll();
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
    private boolean checkRigidFramesX(int x) {
        ArrayList<Frame> frames = GameManager.getINSTANCE().getGameModel().getFrames();
        for (int i = 0; i < frames.size(); i++){
            if (frames.get(i).isRigid && !frames.get(i).equals(this)){
                Frame frame = frames.get(i);
                if (x < 0) {
                    if (Math.abs(getX() - frame.getX() - frame.getWidth()) < 10 && (Calculations.isInDomain(getY(), frame.getY(), frame.getY() + frame.getHeight())
                            || Calculations.isInDomain(getY() + getHeight(), frame.getY(), frame.getY() + frame.getHeight()) ||
                            Calculations.isInDomain(frame.getY(), getY(), getY() + getHeight()))) {
                        logger.debug(1);
                        return true;
                    }
                }
                else if (Math.abs(getX()+getWidth()-frame.getX()) < 10 && (Calculations.isInDomain(getY(), frame.getY(), frame.getY()+frame.getHeight())
                        || Calculations.isInDomain(getY()+getHeight(), frame.getY(), frame.getY()+frame.getHeight()) ||
                        Calculations.isInDomain(frame.getY() , getY(), getY() + getHeight()))){
                    logger.debug(2);
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkRigidFramesY(int y) {
        ArrayList<Frame> frames = GameManager.getINSTANCE().getGameModel().getFrames();
        for (int i = 0; i < frames.size(); i++) {
            if (frames.get(i).isRigid && !frames.get(i).equals(this)) {
                Frame frame = frames.get(i);
                if (y < 0) {
                    if (Math.abs(getY() - frame.getY() - frame.getHeight()) < 10 && (Calculations.isInDomain(getX(), frame.getX(), frame.getX() + frame.getWidth())
                            || Calculations.isInDomain(getX() + getWidth(), frame.getX(), frame.getX() + frame.getWidth()) ||
                            Calculations.isInDomain(frame.getX(), getX(), getX() + getWidth()))) {
                        return true;
                    }
                }else if (Math.abs(getY() + getHeight()-frame.getY()) < 10 && (Calculations.isInDomain(getX(), frame.getX(), frame.getX() + frame.getWidth())
                        || Calculations.isInDomain(getX() + getWidth(), frame.getX(), frame.getX() + frame.getWidth()) ||
                        Calculations.isInDomain(frame.getX() , getX(), getX() + getWidth()))) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<BlackOrbVertex> getBlackOrbVertices() {
        return blackOrbVertices;
    }

    public String getID() {
        return ID;
    }

    public void decreaseSize() {
        if (width > minWidth) {
            width -= 0.1;
            sides.get(2).separateAll();
        }
        if (height > minHeight) {
            height -= 0.1;
            sides.get(3).separateAll();
        }
    }
    private void moveEntities(double x, double y){
        for (int i = 0; i < enemies.size(); i++){
            enemies.get(i).setCenter(new Point(enemies.get(i).getCenter().getX()+x, enemies.get(i).getCenter().getY()+y));
        }
        for (int i = 0; i < blackOrbVertices.size(); i++){
            blackOrbVertices.get(i).setCenter(new Point(blackOrbVertices.get(i).getCenter().getX()+x,
                    blackOrbVertices.get(i).getCenter().getY()+y));
        }
    }
    public void update(){
        if (!isRigid) {
            overlaps = new ArrayList<>();
            ArrayList<Frame> frames = GameManager.getINSTANCE().getGameModel().getFrames();
            for (int i = 0; i < frames.size(); i++) {
                if (!frames.get(i).isRigid && !frames.get(i).equals(this)) {
                        Interference.getOverlaps(this, frames.get(i));
                }
            }
            //logger.debug(overlaps.size());
        }
    }
    public boolean isInOverLap(double x, double y){
            for (int i = 0; i < overlaps.size(); i++){
                if (Calculations.isInDomain(x, overlaps.get(i).get("x")[0], overlaps.get(i).get("x")[1]) &&
                        Calculations.isInDomain(y, overlaps.get(i).get("y")[0], overlaps.get(i).get("y")[1])) {
                    return true;
                }
            }
            return false;
    }

    public void setOverlaps(ArrayList<Map<String, double[]>> overlaps) {
        this.overlaps = overlaps;
    }

    public ArrayList<Map<String, double[]>> getOverlaps() {
        return overlaps;
    }

    public void setStopMoving(boolean stopMoving) {
        this.stopMoving = stopMoving;
    }

    public void setRigid(boolean rigid) {
        isRigid = rigid;
    }

    public double getMinWidth() {
        return minWidth;
    }

    public double getMinHeight() {
        return minHeight;
    }
}
