package model.game.frame;

import controller.Controller;
import controller.game_manager.GameManager;
import controller.save.Configs;
import model.Calculations;
import model.game.BulletModel;
import model.game.Interference;
import model.game.enemies.Enemy;
import model.game.enemies.mini_boss.black_orb.BlackOrbVertex;
import model.interfaces.movement.Point;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MyFrame {
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
    private GameManager gameManager;

    public MyFrame(double width, double height, double x, double y, boolean isIsometric, boolean isRigid, double minWidth,
                   double minHeight, GameManager gameManager) {
        ID = UUID.randomUUID().toString();
        number++;
        logger = Logger.getLogger(MyFrame.class.getName()+number);
        this.width = width;
        this.gameManager = gameManager;
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
        Controller.addFrameView(this, gameManager);
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
        ArrayList<MyFrame> myFrames = gameManager.getGameModel().getFrames();
        for (int i = 0; i < myFrames.size(); i++){
            if (myFrames.get(i).isRigid && !myFrames.get(i).equals(this)){
                MyFrame myFrame = myFrames.get(i);
                if (x < 0) {
                    if (Math.abs(getX() - myFrame.getX() - myFrame.getWidth()) < 10 && (Calculations.isInDomain(getY(), myFrame.getY(), myFrame.getY() + myFrame.getHeight())
                            || Calculations.isInDomain(getY() + getHeight(), myFrame.getY(), myFrame.getY() + myFrame.getHeight()) ||
                            Calculations.isInDomain(myFrame.getY(), getY(), getY() + getHeight()))) {
                        logger.debug(1);
                        return true;
                    }
                }
                else if (Math.abs(getX()+getWidth()- myFrame.getX()) < 10 && (Calculations.isInDomain(getY(), myFrame.getY(), myFrame.getY()+ myFrame.getHeight())
                        || Calculations.isInDomain(getY()+getHeight(), myFrame.getY(), myFrame.getY()+ myFrame.getHeight()) ||
                        Calculations.isInDomain(myFrame.getY() , getY(), getY() + getHeight()))){
                    logger.debug(2);
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkRigidFramesY(int y) {
        ArrayList<MyFrame> myFrames = gameManager.getGameModel().getFrames();
        for (int i = 0; i < myFrames.size(); i++) {
            if (myFrames.get(i).isRigid && !myFrames.get(i).equals(this)) {
                MyFrame myFrame = myFrames.get(i);
                if (y < 0) {
                    if (Math.abs(getY() - myFrame.getY() - myFrame.getHeight()) < 10 && (Calculations.isInDomain(getX(), myFrame.getX(), myFrame.getX() + myFrame.getWidth())
                            || Calculations.isInDomain(getX() + getWidth(), myFrame.getX(), myFrame.getX() + myFrame.getWidth()) ||
                            Calculations.isInDomain(myFrame.getX(), getX(), getX() + getWidth()))) {
                        return true;
                    }
                }else if (Math.abs(getY() + getHeight()- myFrame.getY()) < 10 && (Calculations.isInDomain(getX(), myFrame.getX(), myFrame.getX() + myFrame.getWidth())
                        || Calculations.isInDomain(getX() + getWidth(), myFrame.getX(), myFrame.getX() + myFrame.getWidth()) ||
                        Calculations.isInDomain(myFrame.getX() , getX(), getX() + getWidth()))) {
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

    public void shrinkage() {
        if (width > minWidth) {
            width -= 0.1*gameManager.getGameModel().getWritOfAthena()/100;
            sides.get(2).separateAll();
        }
        if (height > minHeight) {
            height -= 0.1*gameManager.getGameModel().getWritOfAthena()/100;
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
            overlaps = new ArrayList<>();
            ArrayList<MyFrame> myFrames = gameManager.getGameModel().getFrames();
            for (int i = 0; i < myFrames.size(); i++) {
                if (!myFrames.get(i).equals(this)) {
                        Interference.getOverlaps(this, myFrames.get(i));
                }
            }
            //logger.debug(overlaps.size());
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

    public ArrayList<Map<String, double[]>> getOverlaps() {
        return overlaps;
    }

    public void setStopMoving(boolean stopMoving) {
        this.stopMoving = stopMoving;
    }

    public void setRigid(boolean rigid) {
        isRigid = rigid;
    }

}
