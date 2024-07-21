package model;

import controller.GameManager;
import model.enemies.Enemy;
import model.enemies.mini_boss.black_orb.BlackOrbVertex;
import model.frame.Frame;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
                if (!Calculations.isUnderLine(x, y, x1, y1, x2, y2)) {
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
            if (!Calculations.isUnderLine(x, y, x1, y1, x2, y2)) {
                return false;
            }
            else if (Calculations.getDistance(x, y, x1, y1) < GameManager.getINSTANCE().getGameModel().getEpsilon().getRadius()) {
                return false;
            }
        }
        return true;
    }
    public static boolean isInFrame(double x, double y, double width, double height, Frame frame) {
        double[] a = new double[]{x, x+width, y, y+height};
        double[] b = new double[]{frame.getX()-15, frame.getX()-15, frame.getY()-10, frame.getY()-10};
        double[] c = new double[]{frame.getX()+ frame.getWidth()+10, frame.getX()+ frame.getWidth()+10,
                frame.getY()+ frame.getHeight()+10, frame.getY()+ frame.getHeight()+10};
        for (int i = 0; i < 4; i++){
            if (!Calculations.isInDomain(a[i], b[i], c[i])){
                return false;
            }
        }
        return true;
    }
    private static boolean overlaps(Frame frame1, Frame frame2){
        double[] xs = new double[]{0, frame1.getWidth(), frame1.getWidth(), 0};
        double[] ys = new double[]{0, 0, frame1.getHeight(), frame1.getHeight()};
        for (int i = 0; i < 4; i++){
            double x = frame1.getX()+xs[i];
            double y = frame1.getY()+ys[i];
            if (Calculations.isInDomain(x, frame2.getX(), frame2.getX()+frame2.getWidth()) &&
            Calculations.isInDomain(y, frame2.getY(), frame2.getY()+frame2.getHeight())){
                return true;
            }
        }
        return false;
    }
    public static void getOverlaps(Frame frame1, Frame frame2){
        if (overlaps(frame1, frame2) || overlaps(frame2, frame1)){
            Map<String, double[]> overlap = new HashMap<String, double[]>(){{put("x", new double[]{frame2.getX(), frame2.getX()+ frame2.getWidth()});
            put("y", new double[]{frame2.getY(), frame2.getY()+ frame2.getHeight()});}};
            frame1.getOverlaps().add(overlap);
        }
    }


}
