package model.game;

import controller.Controller;
import controller.GameManager;
import controller.audio.AudioController;
import model.game.frame.MyFrame;
import model.interfaces.collision.Collidable;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.UUID;

public class BulletModel implements Collidable {
    private double x1;
    private double y1;
    private double angle;
    private double cos;
    private double sin;
    private Direction direction;
    private final String ID;
    private RotatablePoint end;
    private double radius;
    private int damage;
    private boolean stable;
    private MyFrame myFrame;
    private static int number;
    private Logger logger;
    private GameManager gameManager;
    public BulletModel(Point point1, Point point2, double radius, int damage, boolean stable, MyFrame myFrame, GameManager gameManager) {
        this.stable = stable;
        number++;
        logger = Logger.getLogger(BulletModel.class.getName()+number);
        AudioController.addBulletShotSound();
        ID = UUID.randomUUID().toString();
        this.direction = new Direction(point1, point2);
        setAngle();
        this.damage = damage;
        this.radius = 10;
        setX1(point1, radius);
        setY1(point1, radius);
        setEnd();
        Controller.addBulletView(this, gameManager);
        this.myFrame = myFrame;
    }
    private void setAngle() {
        angle = getAngle();
        sin = Math.sin(angle);
        cos = Math.cos(angle);
        if (direction.getDx() < 0 && direction.getDy() > 0) {
            cos *= -1;
            sin *= -1;
        }
    }
    public void setPosition(double x, double y) {
        this.x1 = x;
        this.y1 = y;
    }
    public void move() {
        x1 += direction.getDx()*30;
        y1 += direction.getDy()*30;
        end.setX(end.getX() + direction.getDx()*30);
        end.setY(end.getY() + direction.getDy()*30);
        checkFrame();
    }
    private double getAngle() {
        double angle = Math.atan(direction.getDy()/direction.getDx());
        if (direction.getDx() < 0 && direction.getDy() < 0) {
            angle += Math.PI;
        }
        return angle;
    }
    private void setEnd() {
        end = new RotatablePoint(x1, y1, angle, 10);
    }

    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public double getX2() {
        return end.getRotatedX();
    }

    public double getY2() {
        return end.getRotatedY();
    }

    private void setX1(Point point, double radius) {
        x1 = point.getX() + cos * radius;
        if (direction.getDx() < 0) {
            x1 += cos * 10;
        }
    }

    private void setY1(Point point, double radius) {
        y1 = point.getY() + sin * radius;
        if (direction.getDy() < 0) {
            y1 += sin * 10;
        }
    }

    public Direction getDirection() {
        return direction;
    }


    @Override
    public ArrayList<RotatablePoint> getVertexes() {
        return new ArrayList<RotatablePoint>(){{add(end);}};
    }

    @Override
    public Point getCenter() {
        return new Point(end.getRotatedX(), end.getRotatedY());
    }

    @Override
    public GameManager getGameManager() {
        return gameManager;
    }

    public String getID() {
        return ID;
    }

    public int getDamage() {
        return damage;
    }

    public MyFrame getFrame() {
        return myFrame;
    }
    private void checkFrame() {
        if (myFrame != null){
            if (!Interference.isInFrame(x1, y1, end.getRotatedX()-x1, end.getRotatedY()-y1, myFrame)){
                myFrame = null;
                //logger.debug("out");
            }
        }
        if (myFrame == null){
            ArrayList<MyFrame> myFrames = gameManager.getGameModel().getFrames();
            for (int i = 0; i < myFrames.size(); i++){
                if (Interference.isInFrame(x1, y1, end.getRotatedX()-x1, end.getRotatedY()-y1, myFrames.get(i))){
                    myFrame = myFrames.get(i);
                    //logger.debug(i);
                    return;
                }
            }
        }
    }
}
