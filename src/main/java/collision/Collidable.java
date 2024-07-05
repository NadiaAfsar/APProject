package collision;


import controller.Constants;
import controller.Controller;
import controller.GameManager;
import controller.SoundController;
import model.BulletModel;
import model.EpsilonModel;
import model.Collective;
import model.enemies.Enemy;
import movement.RotatablePoint;
import movement.Point;

import java.util.ArrayList;


public interface Collidable {
    ArrayList<RotatablePoint> getVertexes();
    //checks enemies' collision with other collidables and epsilon's collision with collectives
    default Point getCollisionPoint(Collidable collidable, ArrayList<RotatablePoint> vertexes) {
        if (collidable instanceof BulletModel){
            ArrayList<RotatablePoint> vertexes2 = collidable.getVertexes();
            return checkVertexes(vertexes2, vertexes);
        }
        else if (collidable instanceof Enemy) {
            return checkVertexes(vertexes, collidable.getVertexes());
        }
        else if (collidable instanceof EpsilonModel) {
            return getCollisionWithEpsilon((EpsilonModel) collidable, vertexes);
        }
        else if (collidable instanceof Collective) {
            return getCollisionWithCollective((Collective) collidable, getCenter());
        }
        return null;
    }
    default Point getCollisionWithEpsilon(EpsilonModel epsilonModel, ArrayList<RotatablePoint> vertexes) {
        if (epsilonModel.getVertexes().size() != 0) {
            Point collisionPoint = checkVertexes(epsilonModel.getVertexes(), vertexes);
            if (collisionPoint != null) {
                Enemy enemy = (Enemy) this;
                if ((enemy).died(5+ GameManager.getINSTANCE().getAres())) {
                    GameManager.getINSTANCE().getDiedEnemies().add(enemy);
                    Controller.removeEnemy(enemy);
                    SoundController.addEnemyDyingSound();
                }
                return collisionPoint;
            }
        }
        for (int i = 0; i < vertexes.size(); i++) {
            Point collisionPoint = getCollisionWithSide(epsilonModel, vertexes, i);
            if (collisionPoint != null) {
                return collisionPoint;
            }
            collisionPoint = checkCollisionWithVertex(epsilonModel, vertexes.get(i));
            if (collisionPoint != null) {
                epsilonModel.decreaseHP(this);
                return collisionPoint;
            }
        }
        return null;
    }
    default Point getCollisionWithSide(EpsilonModel epsilonModel, ArrayList<RotatablePoint> vertexes, int i) {
        RotatablePoint point1 = vertexes.get(i);
        RotatablePoint point2;
        if (i == vertexes.size()-1) {
            point2 = vertexes.get(0);
        }
        else {
            point2 = vertexes.get(i+1);
        }
        double d1 = Math.sqrt(Math.pow(point1.getRotatedX()-epsilonModel.getCenter().getX(), 2)+Math.pow(point1.getRotatedY()-epsilonModel.getCenter().getY(),2));
        double d2 = Math.sqrt(Math.pow(point2.getRotatedX()-epsilonModel.getCenter().getX(), 2)+Math.pow(point2.getRotatedY()-epsilonModel.getCenter().getY(),2));
        if (d1 <= 15 && d2 <= 15) {
            return new Point((point1.getRotatedX()+ point2.getRotatedX())/2,(point1.getRotatedY()+ point2.getRotatedY())/2);
        }
        return null;
    }
    default Point checkCollisionWithVertex(EpsilonModel epsilonModel, RotatablePoint point) {
        double x1 = point.getRotatedX();
        double y1 = point.getRotatedY();
        double x2 = epsilonModel.getCenter().getX();
        double y2 = epsilonModel.getCenter().getY();
        double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        if (distance <= Constants.EPSILON_RADIUS) {
            return new Point(point.getRotatedX(), point.getRotatedY());
        }
        return null;
    }

//    static Point getCollisionWithBullet(BulletModel bulletModel, ArrayList<RotatablePoint> vertexes) {
//        for (int i = 0; i < vertexes.size(); i++) {
//            int x = (int)bulletModel.getX2(); int y = (int)bulletModel.getY2();
//            int x1 = (int)vertexes.get(i).getRotatedX(); int y1 = (int)vertexes.get(i).getRotatedY();
//            int x2, y2;
//            if (i == vertexes.size() - 1) {
//                x2 = (int)vertexes.get(0).getRotatedX(); y2 = (int)vertexes.get(0).getRotatedY();
//            }
//            else {
//                x2 = (int)vertexes.get(i+1).getRotatedX(); y2 = (int)vertexes.get(i+1).getRotatedY();
//            }
//            if (collides(x, y, x1, y1, x2, y2)) {
//                return new Point(bulletModel.getX2(), bulletModel.getY2());
//            }
//        }
//        return null;
//    }
default Point getCollisionWithCollective(Collective collective, Point point) {
        double d = Math.sqrt(Math.pow(collective.getX()-point.getX(),2)+Math.pow(collective.getY()-point.getY(),2));
        if (d <= Constants.EPSILON_RADIUS+5) {
            return new Point(collective.getX(), collective.getY());
        }
        return null;
    }


//    static RotatablePoint collisionPoint(Collidable collidable1, Collidable collidable2) {
//
//        else if (collidable1 instanceof Collective) {
//                Collective collective = (Collective)collidable1;
//                EpsilonModel epsilon = (EpsilonModel)collidable2;
//                double d = Math.sqrt(Math.pow(collective.getX()-epsilon.getCenter().getX(),2)+Math.pow(collective.getY()-epsilon.getCenter().getY(),2));
//                if (d <= Constants.EPSILON_RADIUS+5) {
//                    Controller.addXPCollectingSound();
//                    collective.impact(null, epsilon);
//                    return new RotatablePoint(collective.getX(), collective.getY());
//                }
//        }
//        else {
//            ArrayList<RotatablePoint> vertexes = ((Enemy) collidable1).getVertexes();
//            if (collidable2 instanceof Enemy) {
//                ArrayList<RotatablePoint> vertexes2 = ((Enemy) collidable2).getVertexes();
//                return checkVertexes(vertexes, vertexes2);
//            } else {
//                EpsilonModel epsilon = EpsilonModel.getINSTANCE();
//                if (epsilon.getVertexes().size() != 0) {
//                    RotatablePoint collisionPoint = checkVertexes(epsilon.getVertexes(), vertexes);
//                    if (collisionPoint != null) {
//                        ((Enemy)collidable1).decreaseHP();
//                        Controller.addCollisionSound();
//                        return collisionPoint;
//                    }
//                }
//                for (int i = 0; i < vertexes.size(); i++) {
//                    RotatablePoint point1 = vertexes.get(i);
//                    RotatablePoint point2;
//                    if (i == vertexes.size()-1) {
//                        point2 = vertexes.get(0);
//                    }
//                    else {
//                        point2 = vertexes.get(i+1);
//                    }
//                    double d1 = Math.sqrt(Math.pow(point1.getRotatedX()-epsilon.getCenter().getX(), 2)+Math.pow(point1.getRotatedY()-epsilon.getCenter().getY(),2));
//                    double d2 = Math.sqrt(Math.pow(point2.getRotatedX()-epsilon.getCenter().getX(), 2)+Math.pow(point2.getRotatedY()-epsilon.getCenter().getY(),2));
//                    if (d1 <= 15 && d2 <= 15) {
//                        Controller.addCollisionSound();
//                        return new RotatablePoint((point1.getRotatedX()+ point2.getRotatedX())/2,(point1.getRotatedY()+ point2.getRotatedY())/2);
//                    }
//                    else {
//                        double x1 = point1.getRotatedX();
//                        double y1 = point1.getRotatedY();
//                        double x2 = epsilon.getCenter().getX();
//                        double y2 = epsilon.getCenter().getY();
//                        double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
//                        if (distance <= Constants.EPSILON_RADIUS) {
//                            epsilon.impact(vertexes.get(i), collidable1);
//                            Controller.addCollisionSound();
//                            return vertexes.get(i);
//                        }
//                    }
//                }
//            }
//        }
//        return null;
//    }
default boolean collides(int x, int y, int x1, int y1, int x2, int y2) {
        if (((x >= x1 && x <= x2) || (x <= x1 && x >= x2)) && ((y >= y1 && y <= y2) || (y <= y1 && y >= y2))) {
            if ((x == x1 || x == x2) && (y == y1 || y == y2)) {
                return true;
            }
            return 1.0 * (y1 - y) / (x - x1) <= 1.0 * (y - y2) / (x2 - x);
        }
        return false;
    }
//    static void collided(Collidable collidable, int w) {
//        EpsilonModel epsilon = EpsilonModel.getINSTANCE();
//            ArrayList<Enemy> enemies = GameModel.getINSTANCE().getEnemies();
//            Point center = collidable.getCenter();
//            for (int i = 0; i < enemies.size(); i++) {
//                if (i != w) {
//                    if (Math.abs(enemies.get(i).getCenter().getX() - center.getX()) <= 50 && Math.abs(enemies.get(i).getCenter().getY() - center.getY()) <= 50) {
//                        RotatablePoint collisionPoint = collisionPoint(collidable, enemies.get(i));
//                        if (collisionPoint != null) {
//                            collidable.impact(collisionPoint, enemies.get(i));
//                        }
//                    }
//                }
//            }
//            double x = epsilon.getCenter().getX();
//            double y = epsilon.getCenter().getY();
//            if (Math.abs(x - center.getX()) <= 50 && Math.abs(y - center.getY()) <= 50) {
//                RotatablePoint collisionPoint = collisionPoint(collidable, epsilon);
//                if (collisionPoint != null) {
//                    collidable.impact(collisionPoint, epsilon);
//                }
//            }
//    }

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
                if (collides(x, y, x1, y1, x2, y2)) {
                    return new Point(vertexes.get(i).getRotatedX(), vertexes.get(i).getRotatedY());
                }
            }
        }
        return null;
    }

}
