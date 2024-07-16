package model;

import controller.GameManager;
import model.enemies.Enemy;
import model.enemies.mini_boss.black_orb.BlackOrbVertex;
import model.frame.Frame;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;

import java.util.ArrayList;

public class Interference {
    public static boolean enemyIsInArchmire(ArrayList<RotatablePoint> vertexes, Enemy enemy) {
        for (int i = 0; i < enemy.getVertexes().size(); i++) {
            for (int j = 0; j < vertexes.size(); i++) {
                double x = enemy.getVertexes().get(i).getRotatedX();
                double y = enemy.getVertexes().get(i).getRotatedY();
                double x1 = vertexes.get(j).getRotatedX();
                double y1 = vertexes.get(j).getRotatedY();
                double x2, y2;
                if (j == vertexes.size() - 1) {
                    x2 = vertexes.get(0).getRotatedX();
                    y2 = vertexes.get(0).getRotatedY();
                } else {
                    x2 = vertexes.get(j + 1).getRotatedX();
                    y2 = vertexes.get(j + 1).getRotatedY();
                }
                if (!isUnderLine(x, y, x1, y1, x2, y2)) {
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean epsilonIsInArchmire(ArrayList<RotatablePoint> vertexes, EpsilonModel epsilonModel) {
        for (int i = 0; i < vertexes.size(); i++) {
            double x = epsilonModel.getCenter().getX();
            double y = epsilonModel.getCenter().getY();
            double x1 = vertexes.get(i).getRotatedX();
            double y1 = vertexes.get(i).getRotatedY();
            double x2, y2;
            if (i == vertexes.size() - 1) {
                x2 = vertexes.get(0).getRotatedX();
                y2 = vertexes.get(0).getRotatedY();
            } else {
                x2 = vertexes.get(i + 1).getRotatedX();
                y2 = vertexes.get(i + 1).getRotatedY();
            }
            if (!isUnderLine(x, y, x1, y1, x2, y2)) {
                return false;
            }
            else if (getDistance(x, y, x1, y1) < GameManager.getINSTANCE().getGameModel().getEpsilon().getRadius()) {
                return false;
            }
        }
        return true;
    }
    private static double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2));
    }
    public static boolean isUnderLine(double x, double y, double x1, double y1, double x2, double y2) {
        double slope1 = (y1-y)/(x-x1);
        double slope2 = (y-y2)/(x2-x);
        if ((x > x1 && x < x2) || (x < x1 && x > x2)) {
            return slope1 > slope2;
        }
        return slope1 < slope2;
    }
    public static double pointDistanceFromLine(Point point1, Point point2, Point point3) {
        double a = getDistance(point1.getX(), point1.getY(), point2.getX(), point2.getY());
        double b = getDistance(point1.getX(), point1.getY(), point3.getX(), point3.getY());
        double c = getDistance(point2.getX(), point2.getY(), point3.getX(), point3.getY());
        double cos = (Math.pow(a, 2)+Math.pow(b, 2)-Math.pow(c, 2)) / (2 * a * b);
        double sin = Math.sqrt(1-Math.pow(cos, 2));
        return (a * b * sin)/c;
    }
    public boolean[] isInFrame(Enemy enemy, Frame frame) {
        boolean[] result = new boolean[2];
        result[1] = true;
        ArrayList<RotatablePoint> vertexes = enemy.getVertexes();
        for (int i = 0; i < vertexes.size(); i++) {
            if (vertexes.get(i).getRotatedX() >= frame.getX() && vertexes.get(i).getRotatedX() <= frame.getX()+frame.getWidth()
            && vertexes.get(i).getRotatedY() >= frame.getY() && vertexes.get(i).getRotatedY() <= frame.getY()+frame.getHeight()){
                result[0] = true;
            }
            else {
                result[1] = false;
            }
        }
        return result;
    }
    public boolean[] isEpsilonInFrame(EpsilonModel epsilon, Frame frame) {
        return isCircleInFrame(epsilon.getCenter(), epsilon.getRadius(), frame);
    }
    public boolean[] isBlackOrnVertexInFrame(BlackOrbVertex vertex, Frame frame) {
        return isCircleInFrame(vertex.getCenter(), vertex.getWidth()/2, frame);
    }
    private boolean[] isCircleInFrame(Point center, double radius, Frame frame) {
        double[] x = new double[]{0, frame.getWidth(), 0, frame.getWidth()};
        double[] y = new double[]{0, 0, frame.getHeight(), frame.getHeight()};
        boolean[] result = new boolean[2];
        if (center.getX() >= frame.getX() && center.getX() <= frame.getX()+frame.getWidth()
                && center.getY() >= frame.getY() && center.getY() <= frame.getY()+frame.getHeight()){
            result[1] = true;
        }
        for (int i = 0; i < 4; i++) {
            Point point1 = new Point(frame.getX()+x[i], frame.getY()+y[i]);
            Point point2 = null;
            if (i < 3) {
                point2 = new Point(frame.getX()+x[i+1], frame.getY()+y[i+1]);
            }
            else {
                point2 = new Point(frame.getX()+x[0], frame.getY()+y[0]);
            }
            if (pointDistanceFromLine(center, point1, point2) < radius) {
                result[0] = true;
                result[1] = false;
            }
        }
        if (result[1]) {
            result[0] = true;
        }
        return result;
    }


}
