package collision;


import controller.Constants;
import model.EpsilonModel;
import model.GameModel;
import model.enemies.Enemy;
import movement.RotatablePoint;

import java.awt.*;
import java.util.ArrayList;


public interface Collidable {
    static Point collisionPoint(ArrayList<Point> vertexes, Collidable collidable) {
        if (collidable instanceof Enemy) {
            ArrayList<RotatablePoint> vertexes2 = ((Enemy) collidable).getVertexes();
            for (int i = 0; i < vertexes.size(); i++) {
                for (int j = 0; j < vertexes2.size(); j++) {
                    int x = (int)vertexes.get(i).getX(); int y = (int)vertexes.get(i).getY();
                    int x1 = (int)vertexes2.get(j).getX(); int y1 = (int)vertexes2.get(j).getY();
                    int x2, y2;
                    if (j == vertexes2.size() - 1) {
                        x2 = (int)vertexes2.get(0).getX(); y2 = (int)vertexes2.get(0).getY();
                    }
                    else {
                        x2 = (int)vertexes2.get(j+1).getX(); y2 = (int)vertexes2.get(j+1).getY();
                    }
                    if (collides(x, y, x1, y1, x2, y2)) {
                        return new Point(x, y);
                    }
                }
            }
        }
        else {
            for (int i = 0; i < vertexes.size(); i++) {
                double x1 = vertexes.get(i).getX();
                double y1 = vertexes.get(i).getX();
                double x2 = EpsilonModel.getINSTANCE().getCenter().getX();
                double y2 = EpsilonModel.getINSTANCE().getCenter().getY();
                if (Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2)) <= Constants.EPSILON_RADIUS+5) {
                    return vertexes.get(i);
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
    static void collided(ArrayList<Point> vertexes, Point center) {
        ArrayList<Enemy> enemies = GameModel.getINSTANCE().getEnemies();
        for (int i = 0; i < enemies.size(); i++) {
            if (Math.abs(enemies.get(i).getCenter().getX()- center.getX()) <= 50 && Math.abs(enemies.get(i).getCenter().getY()- center.getY()) <= 50) {
                if (collisionPoint(vertexes, enemies.get(i)) != null) {

                }
            }
        }
        double x = EpsilonModel.getINSTANCE().getCenter().getX();
        double y = EpsilonModel.getINSTANCE().getCenter().getY();
        if (Math.abs(x - center.getX()) <= 50 && Math.abs(y - center.getY()) <= 50) {
            if (collisionPoint(vertexes, EpsilonModel.getINSTANCE()) != null) {

            }
        }
    }
    void impact(Point collisionPoint, Collidable collidable);
}
