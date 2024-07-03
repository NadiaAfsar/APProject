package model;

import collision.Collidable;
import controller.Constants;
import controller.Controller;
import model.enemies.Enemy;
import model.game.GameModel;
import movement.Direction;
import movement.Movable;
import movement.Point;
import movement.RotatablePoint;

import java.util.UUID;

public class BulletModel implements Movable, Collidable {
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private double angle;
    private double cos;
    private double sin;
    private Direction direction;
    private final String ID;
    public BulletModel(int x, int y) {
        Controller.addBulletShotSound();
        ID = UUID.randomUUID().toString();
        this.direction = new Direction(new Point(EpsilonModel.getINSTANCE().getCenter().getX(), EpsilonModel.getINSTANCE().getCenter().getY()), new Point(x,y));
        angle = getAngle();
        sin = Math.sin(angle);
        cos = Math.cos(angle);
        if (direction.getDx() < 0 && direction.getDy() > 0) {
            cos *= -1;
            sin *= -1;
        }
        setX1();
        setY1();
        setX2();
        setY2();
    }
    public void setPosition(double x, double y) {
        this.x1 = x;
        this.y1 = y;
    }
    @Override
    public void move() {
        x1 += direction.getDx()*30;
        y1 += direction.getDy()*30;
        x2 += direction.getDx()*30;
        y2 += direction.getDy()*30;
    }
    private double getAngle() {
        double angle = Math.atan(direction.getDy()/direction.getDx());
        if (direction.getDx() < 0 && direction.getDy() < 0) {
            angle += Math.PI;
        }
        return angle;
    }
    private void setX2() {
        x2 = x1 + Math.cos(angle)*10;
    }
    private void setY2() {
        y2 = y1 + Math.sin(angle)*10;
    }

    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }

    private void setX1() {
        x1 = EpsilonModel.getINSTANCE().getCenter().getX() + cos * Constants.EPSILON_RADIUS;
        if (direction.getDx() < 0) {
            x1 += cos * 10;
        }
    }

    private void setY1() {
        y1 = EpsilonModel.getINSTANCE().getCenter().getY() + sin * Constants.EPSILON_RADIUS;
        if (direction.getDy() < 0) {
            y1 += sin * 10;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void impact(RotatablePoint collisionPoint, Collidable collidable) {
        Enemy.impactOnOthers(collisionPoint);
    }

    @Override
    public Point getCenter() {
        return null;
    }

    public String getID() {
        return ID;
    }
    public boolean checkFrameCollision(GameModel gameModel) {
        if (x2 <= 0) {
            gameModel.setWidth(gameModel.getWidth()+10);
            gameModel.setX(gameModel.getX()-10+ direction.getDx()*20);
            gameModel.setY(gameModel.getY()+ direction.getDy()*20);
            if (gameModel.checkPosition()) {
                gameModel.setWidth(gameModel.getWidth()-10);
            }
            return true;
        }
        else if (x2 >= gameModel.getWidth()) {
            gameModel.setWidth(gameModel.getWidth()+10);
            gameModel.setX(gameModel.getX()+10+ direction.getDx()*20);
            gameModel.setY(gameModel.getY()+ direction.getDy()*20);
            if (gameModel.checkPosition()) {
                gameModel.setWidth(gameModel.getWidth()-10);
            }
            return true;
        }
        else if (y2 <= 0) {
            gameModel.setHeight(gameModel.getHeight()+10);
            gameModel.setX(gameModel.getX()+ direction.getDx()*20);
            gameModel.setY(gameModel.getY()-10+ direction.getDy()*20);
            if (gameModel.checkPosition()) {
                gameModel.setHeight(gameModel.getHeight()-10);
            }
            return true;
        }
        else if (y2 >= gameModel.getHeight()) {
            gameModel.setHeight(gameModel.getHeight()+10);
            gameModel.setX(gameModel.getX()+ direction.getDx()*20);
            gameModel.setY(gameModel.getY()+10+ direction.getDy()*20);
            if (gameModel.checkPosition()) {
                gameModel.setHeight(gameModel.getHeight()-10);
            }
            return true;
        }
        return false;
    }
}
