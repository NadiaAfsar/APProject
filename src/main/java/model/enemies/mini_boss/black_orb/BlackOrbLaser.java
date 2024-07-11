package model.enemies.mini_boss.black_orb;

import controller.GameManager;
import model.enemies.Enemy;
import model.enemies.normal.archmire.Interference;
import movement.Point;
import movement.RotatablePoint;

import java.util.ArrayList;

public class BlackOrbLaser {
    private final BlackOrbVertex vertex1;
    private final BlackOrbVertex vertex2;

    public BlackOrbLaser(BlackOrbVertex vertex1, BlackOrbVertex vertex2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
    }
    public void attack() {
        Point point1 = new Point(vertex1.getCenter().getX(), vertex1.getCenter().getY()-10);
        Point point2 = new Point(vertex2.getCenter().getX(), vertex2.getCenter().getY()-10);
        Point point3 = new Point(vertex2.getCenter().getX(), vertex2.getCenter().getY()+10);
        Point point4 = new Point(vertex1.getCenter().getX(), vertex1.getCenter().getY()+10);
        ArrayList<Point> points = new ArrayList<Point>() {{ add(point1); add(point2); add(point3); add(point4);}};
        ArrayList<Enemy> enemies = GameManager.getINSTANCE().getGameModel().getEnemies();
        for (int i = 0; i < enemies.size(); i++) {
            if (enemyCollidesLaser(enemies.get(i), points)) {
                enemies.get(i).died(12);
            }
        }

    }
    private boolean enemyCollidesLaser(Enemy enemy, ArrayList<Point> points) {
        for (int i = 0; i < enemy.getVertexes().size(); i++) {
            boolean isIn = true;
            for (int j = 0; j < points.size(); j++) {
                RotatablePoint point = enemy.getVertexes().get(i);
                double x = enemy.getVertexes().get(i).getRotatedX();
                double y = enemy.getVertexes().get(i).getRotatedY();
                double x1 = points.get(j).getX();
                double y1 = points.get(j).getY();
                double x2, y2;
                if (j == points.size() - 1) {
                    x2 = points.get(0).getX();
                    y2 = points.get(0).getY();
                } else {
                    x2 = points.get(j + 1).getX();
                    y2 = points.get(j + 1).getY();
                }
                if (!Interference.isUnderLine(x, y, x1, y1, x2, y2)) {
                    isIn = false;
                }
            }
            if (isIn) {
                return true;
            }
        }
        return false;
    }


}
