package model;

import collision.Collidable;
import controller.Constants;
import controller.Controller;
import controller.GameManager;
import controller.SoundController;
import model.enemies.Enemy;
import model.game.GameModel;
import movement.Direction;
import movement.Movable;
import movement.Point;
import movement.RotatablePoint;

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
    public BulletModel(int x, int y) {
        SoundController.addBulletShotSound();
        ID = UUID.randomUUID().toString();
        this.direction = new Direction(new Point(GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter().getX(),
                GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter().getY()), new Point(x,y));
        angle = getAngle();
        sin = Math.sin(angle);
        cos = Math.cos(angle);
        if (direction.getDx() < 0 && direction.getDy() > 0) {
            cos *= -1;
            sin *= -1;
        }
        radius = 10;
        setX1();
        setY1();
        setEnd();
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
    }
    private double getAngle() {
        double angle = Math.atan(direction.getDy()/direction.getDx());
        if (direction.getDx() < 0 && direction.getDy() < 0) {
            angle += Math.PI;
        }
        return angle;
    }
    private void setEnd() {
        end = new RotatablePoint(x1, y1, angle, radius);
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

    private void setX1() {
        x1 = GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter().getX() + cos * Constants.EPSILON_RADIUS;
        if (direction.getDx() < 0) {
            x1 += cos * 10;
        }
    }

    private void setY1() {
        y1 = GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter().getY() + sin * Constants.EPSILON_RADIUS;
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
        return null;
    }

    public String getID() {
        return ID;
    }
    public boolean checkFrameCollision(GameModel gameModel) {
        if (getX2() <= 0) {
            gameModel.setWidth(gameModel.getWidth()+10);
            gameModel.setX(gameModel.getX()-10+ direction.getDx()*20);
            gameModel.setY(gameModel.getY()+ direction.getDy()*20);
            if (GameManager.getINSTANCE().checkPosition()) {
                gameModel.setWidth(gameModel.getWidth()-10);
            }
            return true;
        }
        else if (getX2() >= gameModel.getWidth()) {
            gameModel.setWidth(gameModel.getWidth()+10);
            gameModel.setX(gameModel.getX()+10+ direction.getDx()*20);
            gameModel.setY(gameModel.getY()+ direction.getDy()*20);
            if (GameManager.getINSTANCE().checkPosition()) {
                gameModel.setWidth(gameModel.getWidth()-10);
            }
            return true;
        }
        else if (getY2() <= 0) {
            gameModel.setHeight(gameModel.getHeight()+10);
            gameModel.setX(gameModel.getX()+ direction.getDx()*20);
            gameModel.setY(gameModel.getY()-10+ direction.getDy()*20);
            if (GameManager.getINSTANCE().checkPosition()) {
                gameModel.setHeight(gameModel.getHeight()-10);
            }
            return true;
        }
        else if (getY2() >= gameModel.getHeight()) {
            gameModel.setHeight(gameModel.getHeight()+10);
            gameModel.setX(gameModel.getX()+ direction.getDx()*20);
            gameModel.setY(gameModel.getY()+10+ direction.getDy()*20);
            if (GameManager.getINSTANCE().checkPosition()) {
                gameModel.setHeight(gameModel.getHeight()-10);
            }
            return true;
        }
        return false;
    }

}
