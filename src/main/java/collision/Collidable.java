package collision;


import controller.Constants;
import model.BulletModel;
import model.EpsilonModel;
import model.GameModel;
import model.enemies.Enemy;
import movement.RotatablePoint;
import movement.Point;

import java.util.ArrayList;


public interface Collidable {
    static RotatablePoint collisionPoint(Collidable collidable1, Collidable collidable2) {
        if (collidable1 instanceof BulletModel) {
            BulletModel bullet = (BulletModel)collidable1;
            ArrayList<RotatablePoint> vertexes2 = ((Enemy) collidable2).getVertexes();
            for (int i = 0; i < vertexes2.size(); i++) {
                int x = (int)bullet.getX2(); int y = (int)bullet.getY2();
                int x1 = (int)vertexes2.get(i).getRotatedX(); int y1 = (int)vertexes2.get(i).getRotatedY();
                int x2, y2;
                if (i == vertexes2.size() - 1) {
                    x2 = (int)vertexes2.get(0).getRotatedX(); y2 = (int)vertexes2.get(0).getRotatedY();
                }
                else {
                    x2 = (int)vertexes2.get(i+1).getRotatedX(); y2 = (int)vertexes2.get(i+1).getRotatedY();
                }
                if (collides(x, y, x1, y1, x2, y2)) {
                    return new RotatablePoint(bullet.getX2(), bullet.getY2());
                }
            }
        }
        else {
            ArrayList<RotatablePoint> vertexes = ((Enemy) collidable1).getVertexes();
            if (collidable2 instanceof Enemy) {
                ArrayList<RotatablePoint> vertexes2 = ((Enemy) collidable2).getVertexes();
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
                        if (collides(x, y, x1, y1, x2, y2)) {
                            return vertexes.get(i);
                        }
                    }
                }
            } else {
                for (int i = 0; i < vertexes.size(); i++) {
                    RotatablePoint point1 = vertexes.get(i);
                    double x1 = point1.getRotatedX();
                    double y1 = point1.getRotatedY();
                    EpsilonModel epsilon = EpsilonModel.getINSTANCE();
                    double x2 = epsilon.getCenter().getX();
                    double y2 = epsilon.getCenter().getY();
                    if (Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)) <= Constants.EPSILON_RADIUS+5) {
                        epsilon.impact(vertexes.get(i), collidable1);
                        return vertexes.get(i);
                    }
                }
            }
        }
        return null;
    }
    static boolean collides(int x, int y, int x1, int y1, int x2, int y2) {
        if (((x >= x1 && x <= x2) || (x <= x1 && x >= x2)) && ((y >= y1 && y <= y2) || (y <= y1 && y >= y2))) {
            if ((x == x1 || x == x2) && (y == y1 || y == y2)) {
                return true;
            }
            return 1.0 * (y1 - y) / (x - x1) <= 1.0 * (y - y2) / (x2 - x);
        }
        return false;
    }
    static void collided(Collidable collidable, int w) {
        ArrayList<Enemy> enemies = GameModel.getINSTANCE().getEnemies();
        Point center = collidable.getCenter();
        for (int i = 0; i < enemies.size(); i++) {
            if (i != w) {
                if (Math.abs(enemies.get(i).getCenter().getX() - center.getX()) <= 50 && Math.abs(enemies.get(i).getCenter().getY() - center.getY()) <= 50) {
                    RotatablePoint collisionPoint = collisionPoint(collidable, enemies.get(i));
                    if (collisionPoint != null) {
                        collidable.impact(collisionPoint, enemies.get(i));
                    }
                }
            }
        }
        double x = EpsilonModel.getINSTANCE().getCenter().getX();
        double y = EpsilonModel.getINSTANCE().getCenter().getY();
        if (Math.abs(x - center.getX()) <= 50 && Math.abs(y - center.getY()) <= 50) {
            RotatablePoint collisionPoint = collisionPoint(collidable, EpsilonModel.getINSTANCE());
            if (collisionPoint != null) {
                collidable.impact(collisionPoint, EpsilonModel.getINSTANCE());
            }
        }
    }
    void impact(RotatablePoint collisionPoint, Collidable collidable);
    Point getCenter();

}
