package model.game;

import controller.GameManager;
import model.Calculations;
import model.game.enemies.Enemy;
import model.game.frame.MyFrame;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Interference {
    public static boolean enemyIsInArchmire(ArrayList<RotatablePoint> vertexes, Enemy enemy) {
        for (int i = 0; i < enemy.getVertexes().size(); i++) {
            for (int j = 0; j < vertexes.size(); j++) {
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
    public static boolean isInFrame(double x, double y, double width, double height, MyFrame myFrame) {
        double[] a = new double[]{x, x+width, y, y+height};
        double[] b = new double[]{myFrame.getX()-20, myFrame.getX()-20, myFrame.getY()-20, myFrame.getY()-20};
        double[] c = new double[]{myFrame.getX()+ myFrame.getWidth()+20, myFrame.getX()+ myFrame.getWidth()+20,
                myFrame.getY()+ myFrame.getHeight()+20, myFrame.getY()+ myFrame.getHeight()+20};
        for (int i = 0; i < 4; i++){
            if (!Calculations.isInDomain(a[i], b[i], c[i])){
                return false;
            }
        }
        return true;
    }
    private static boolean overlaps(MyFrame myFrame1, MyFrame myFrame2){
        double[] xs = new double[]{0, myFrame1.getWidth(), myFrame1.getWidth(), 0};
        double[] ys = new double[]{0, 0, myFrame1.getHeight(), myFrame1.getHeight()};
        for (int i = 0; i < 4; i++){
            double x = myFrame1.getX()+xs[i];
            double y = myFrame1.getY()+ys[i];
            if (Calculations.isInDomain(x, myFrame2.getX(), myFrame2.getX()+ myFrame2.getWidth()) &&
            Calculations.isInDomain(y, myFrame2.getY(), myFrame2.getY()+ myFrame2.getHeight())){
                return true;
            }
        }
        return false;
    }
    public static void getOverlaps(MyFrame myFrame1, MyFrame myFrame2){
        if (overlaps(myFrame1, myFrame2) || overlaps(myFrame2, myFrame1)){
            Map<String, double[]> overlap = new HashMap<String, double[]>(){{put("x", new double[]{myFrame2.getX(), myFrame2.getX()+ myFrame2.getWidth()});
            put("y", new double[]{myFrame2.getY(), myFrame2.getY()+ myFrame2.getHeight()});}};
            myFrame1.getOverlaps().add(overlap);
        }
    }
    public static boolean circleIsInCircle(Point center1, double radius1, Point center2, double radius2){
        return (Calculations.getDistance(center1.getX(), center1.getY(), center2.getX(), center2.getY()) <= radius2-radius1);
    }


}
