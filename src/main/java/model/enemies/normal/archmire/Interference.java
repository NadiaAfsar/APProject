package model.enemies.normal.archmire;

import controller.Constants;
import model.EpsilonModel;
import model.enemies.Enemy;
import movement.RotatablePoint;

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
            else if (getDistance(x, y, x1, y1) < Constants.EPSILON_RADIUS) {
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


}
