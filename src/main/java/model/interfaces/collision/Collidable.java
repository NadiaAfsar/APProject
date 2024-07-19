package model.interfaces.collision;


import controller.GameManager;
import model.BulletModel;
import model.EpsilonModel;
import model.Collectible;
import model.Interference;
import model.enemies.Enemy;
import model.enemies.mini_boss.Barricados;
import model.enemies.mini_boss.black_orb.BlackOrbVertex;
import model.enemies.normal.Necropick;
import model.enemies.normal.Wyrm;
import model.interfaces.movement.RotatablePoint;
import model.interfaces.movement.Point;

import java.util.ArrayList;


public interface Collidable {
    ArrayList<RotatablePoint> getVertexes();

    default Point getCollisionPoint(Collidable collidable) {
        if (this instanceof BulletModel) {
            if (collidable instanceof Enemy) {
                if (!(collidable instanceof Necropick)) {
                    return checkVertexes(getVertexes(), collidable.getVertexes());
                }
                else if (!((Necropick)collidable).isDisappeared()) {
                    System.out.println(1);
                    return checkVertexes(getVertexes(), collidable.getVertexes());
                }
            }
            else if (collidable instanceof BlackOrbVertex) {
                return getCircleCollisionWithBullet(collidable.getCenter(), ((BlackOrbVertex)collidable).getWidth()/2);
            }
            else {
                return getCircleCollisionWithBullet(collidable.getCenter(), ((EpsilonModel)collidable).getRadius());
            }
        }
        else if (this instanceof BlackOrbVertex) {
            if (collidable instanceof Enemy) {
                return getBlackOrbCollisionWithEnemy((Enemy) collidable);
            } else if (collidable instanceof EpsilonModel) {
                return getBlackOrbCollisionWithEpsilon((EpsilonModel) collidable);
            }
        }
        else if (collidable instanceof Enemy) {
            if (!(collidable instanceof Necropick)) {
                return checkVertexes(getVertexes(), collidable.getVertexes());
            }
            else if (!((Necropick)collidable).isDisappeared()) {
                return checkVertexes(getVertexes(), collidable.getVertexes());
            }
        }
        else if (collidable instanceof EpsilonModel) {
            return getCollisionWithEpsilon((EpsilonModel) collidable, getVertexes());
        } else if (collidable instanceof Collectible) {
            return getCollisionWithCollective((Collectible) collidable, getCenter());
        }
        return null;
    }

    default Point getBlackOrbCollisionWithEpsilon(EpsilonModel epsilon) {
        double distance = getDistance(getCenter().getX(), getCenter().getY(), epsilon.getCenter().getX(), epsilon.getCenter().getY());
        if (distance <= epsilon.getRadius()+((BlackOrbVertex)this).getWidth()/2) {
            return new Point((epsilon.getCenter().getX()+getCenter().getX())/2,
                    (epsilon.getCenter().getY()+getCenter().getY())/2);
        }
        return null;
    }

    default Point getBlackOrbCollisionWithEnemy(Enemy enemy) {
        ArrayList<RotatablePoint> vertexes = enemy.getVertexes();
        for (int i = 0; i < vertexes.size(); i++) {
            Point collisionPoint = getCollisionWithSide(getCenter(), vertexes, i, GameManager.configs.BLACKORBVERTEX_RADIUS);
            if (collisionPoint != null) {
                return collisionPoint;
            }
            collisionPoint = checkCollisionWithVertex(getCenter(), vertexes.get(i));
            if (collisionPoint != null) {
                return collisionPoint;
            }
        }
        return null;
    }
    default Point getCircleCollisionWithBullet(Point center, double radius) {
        if (getDistance(center.getX(), center.getY(), getVertexes().get(0).getRotatedX(),
                getVertexes().get(0).getRotatedY()) <= radius) {
            return new Point(getVertexes().get(0).getX(), getVertexes().get(0).getY());
        }
        return null;
    }

    default Point getCollisionWithEpsilon(EpsilonModel epsilonModel, ArrayList<RotatablePoint> vertexes) {
        if (epsilonModel.getVertexes().size() != 0) {
            Point collisionPoint = checkVertexes(epsilonModel.getVertexes(), vertexes);
            if (collisionPoint != null) {
                if (!(this instanceof Wyrm) && !(this instanceof Barricados)) {
                    Enemy enemy = (Enemy) this;
                    enemy.decreaseHP(5 + GameManager.getINSTANCE().getGameModel().getAres());
                }
                return collisionPoint;
            }
        }
        for (int i = 0; i < vertexes.size(); i++) {
            Point collisionPoint = getCollisionWithSide(epsilonModel.getCenter(), vertexes, i, epsilonModel.getRadius());
            if (collisionPoint != null) {
                return collisionPoint;
            }
            collisionPoint = checkCollisionWithVertex(epsilonModel.getCenter(), vertexes.get(i));
            if (collisionPoint != null) {
                if (!(this instanceof Necropick) && !(this instanceof Wyrm) && !(this instanceof Barricados)) {
                    epsilonModel.decreaseHP(((Enemy) this).getDamage());
                }
                return collisionPoint;
            }
        }
        return null;
    }

    default Point getCollisionWithSide(Point center, ArrayList<RotatablePoint> vertexes, int i, double radius) {
        RotatablePoint point1 = vertexes.get(i);
        RotatablePoint point2;
        if (i == vertexes.size() - 1) {
            point2 = vertexes.get(0);
        } else {
            point2 = vertexes.get(i + 1);
        }
        if (Interference.pointDistanceFromLine(center, new Point(point1.getRotatedX(), point1.getRotatedY()),
                new Point(point2.getRotatedX(), point2.getRotatedY())) <= radius) {
            return new Point((point1.getRotatedX() + point2.getRotatedX()) / 2, (point1.getRotatedY() + point2.getRotatedY()) / 2);
        }
        return null;
    }

    default Point checkCollisionWithVertex(Point center, RotatablePoint point) {
        double x1 = point.getRotatedX();
        double y1 = point.getRotatedY();
        double x2 = center.getX();
        double y2 = center.getY();
        double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        if (distance <= GameManager.getINSTANCE().getGameModel().getEpsilon().getRadius()) {
            return new Point(point.getRotatedX(), point.getRotatedY());
        }
        return null;
    }

    default Point getCollisionWithCollective(Collectible collectible, Point point) {
        double distance = getDistance(collectible.getX(), collectible.getY(), point.getX(), point.getY());
        if (distance <= GameManager.getINSTANCE().getGameModel().getEpsilon().getRadius() + 5) {
            return new Point(collectible.getX(), collectible.getY());
        }
        return null;
    }
    default double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2));
    }

    default boolean collides(int x, int y, int x1, int y1, int x2, int y2) {
        if (((x >= x1 && x <= x2) || (x <= x1 && x >= x2)) && ((y >= y1 && y <= y2) || (y <= y1 && y >= y2))) {
            if ((x == x1 || x == x2) && (y == y1 || y == y2)) {
                return true;
            }
            return 1.0 * (y1 - y) / (x - x1) >= 1.0 * (y - y2) / (x2 - x);
        }
        return false;
    }


    Point getCenter();

    default Point checkVertexes(ArrayList<RotatablePoint> vertexes, ArrayList<RotatablePoint> vertexes2) {
        for (int i = 0; i < vertexes.size(); i++) {
            for (int j = 0; j < vertexes2.size(); j++) {
                int x = (int) vertexes.get(i).getRotatedX();
                int y = (int) vertexes.get(i).getRotatedY();
                int x1 = (int) vertexes2.get(j).getRotatedX();
                int y1 = (int) vertexes2.get(j).getRotatedY();
                int x2, y2;
                if (j == vertexes2.size() - 1) {
                    x2 = (int) vertexes2.get(0).getRotatedX();
                    y2 = (int) vertexes2.get(0).getRotatedY();
                } else {
                    x2 = (int) vertexes2.get(j + 1).getRotatedX();
                    y2 = (int) vertexes2.get(j + 1).getRotatedY();
                }
                if (Interference.pointDistanceFromLine(new Point(x, y), new Point(x1, y1), new Point(x2, y2)) <= 5) {
                    return new Point(vertexes.get(i).getRotatedX(), vertexes.get(i).getRotatedY());
                }
            }
        }
        return null;
    }

}
