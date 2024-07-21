package model;

import model.interfaces.movement.Point;

public class Calculations {
    public static double pointDistanceFromLine(Point point1, Point point2, Point point3) {
        double a = getDistance(point1.getX(), point1.getY(), point2.getX(), point2.getY());
        double b = getDistance(point1.getX(), point1.getY(), point3.getX(), point3.getY());
        double c = getDistance(point2.getX(), point2.getY(), point3.getX(), point3.getY());
        double cos = (Math.pow(a, 2)+Math.pow(b, 2)-Math.pow(c, 2)) / (2 * a * b);
        double sin = Math.sqrt(1-Math.pow(cos, 2));
        if (sin <= 0.3) {
            return a;
        }
        return (a * b * sin)/c;
    }
    public static double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2));
    }
    public static boolean isUnderLine(double x, double y, double x1, double y1, double x2, double y2) {
        double slope1 = (y1-y)/(x-x1);
        double slope2 = (y-y2)/(x2-x);
        if ((x > x1 && x < x2) || (x < x1 && x > x2)) {
            return slope1 < slope2;
        }
        return slope1 > slope2;
    }
    public static boolean isInDomain(double a, double b, double c){
        return (a >= b && a <= c);
    }
}
