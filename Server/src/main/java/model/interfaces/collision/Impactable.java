package model.interfaces.collision;

import controller.GameManager;
import model.game.EpsilonModel;
import model.game.enemies.Enemy;
import model.game.enemies.normal.Wyrm;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Point;

import java.util.ArrayList;

public interface Impactable {
    default void impact(Point collisionPoint, Collidable collidable) {
        double slope1 = collisionPoint.getY() - getCenter().getY() / collisionPoint.getX() - getCenter().getX();
        double slope2 = collidable.getCenter().getY() - getCenter().getY() / collidable.getCenter().getX() - getCenter().getX();
        double slope3 = collisionPoint.getY() - collidable.getCenter().getY() / collisionPoint.getX() - collidable.getCenter().getX();
        int slope = 1;
        if (slope1 < slope2) {
            slope = -1;
        }
        setAngularVelocity(Math.PI / 64 * slope);
        setAngularAcceleration(Math.PI / 8 * slope);
        setAngularAccelerationRate(-Math.PI / 4 * slope);
        if (collidable instanceof Impactable) {
            Impactable impactable = (Impactable)collidable;
            if (!(impactable instanceof Wyrm)) {
                slope = 1;
                if (slope3 < slope2) {
                    slope = -1;
                }
                impactable.setAngularVelocity(Math.PI / 64 * slope);
                impactable.setAngularAcceleration(Math.PI / 8 * slope);
                impactable.setAngularAccelerationRate(-Math.PI / 4 * slope);
            }
            impactable.setSpecialImpact();
        }
        impactOnOthers(collisionPoint);
        setSpecialImpact();

    }
    Point getCenter();
    void setAngularVelocity(double velocity);
    void setAngularAcceleration(double acceleration);
    void setAngularAccelerationRate(double accelerationRate);
    static void impactOnOthers(Point collisionPoint) {
        ArrayList<Enemy> enemies = GameManager.getINSTANCE().getGameModel().getEnemies();
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i) instanceof Impactable && !(enemies.get(i) instanceof Wyrm)) {
                Impactable enemy = (Impactable) enemies.get(i);
                if (Math.abs(enemy.getCenter().getX() - collisionPoint.getX()) <= 100 && Math.abs(enemy.getCenter().getY() - collisionPoint.getY()) <= 100) {
                    double x = Math.sqrt(Math.pow(enemy.getCenter().getX() - collisionPoint.getX(), 2) + Math.pow(enemy.getCenter().getY() - collisionPoint.getY(), 2));
                    Direction direction = new Direction(new Point(enemy.getCenter().getX(), enemy.getCenter().getY()), new Point(collisionPoint.getX(), collisionPoint.getY()));
                    enemy.setImpactAcceleration(direction, 140 - x);
                }
            }
        }
        EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
        if (Math.abs(epsilon.getX() - collisionPoint.getX()) <= 100 && Math.abs(epsilon.getY() - collisionPoint.getY()) <= 100) {
            double x = Math.sqrt(Math.pow(epsilon.getX() - collisionPoint.getX(),2) + Math.pow(epsilon.getY() - collisionPoint.getY(),2));
            Direction direction = new Direction(new Point(epsilon.getCenter().getX(), epsilon.getCenter().getY()), new Point(collisionPoint.getX(), collisionPoint.getY()));
            epsilon.setImpactAcceleration(direction, 140-x);
        }
    }
    default void setImpactAcceleration(Direction direction, double distance) {
        setCenter(new Point(getCenter().getX() - direction.getDx() * distance/2 , getCenter().getY() - direction.getDy() * distance/2 ));
        if (this instanceof EpsilonModel) {
            setCenter(new Point(getCenter().getX()-12, getCenter().getY()-12));
        }
        moveVertexes();
        setAcceleration(new Point(-direction.getDx() * distance , -direction.getDy() * distance ));
        setAccelerationRate(new Point(direction.getDx() * distance * 5/3, direction.getDy() * distance * 5/3));
        setImpact(true);

    }
    void setCenter(Point center);
    void setImpact(boolean impact);
    void moveVertexes();
    void setAcceleration(Point acceleration);
    Point getAccelerationRate();
    void setAccelerationRate(Point accelerationRate);
    void setSpecialImpact();
}
