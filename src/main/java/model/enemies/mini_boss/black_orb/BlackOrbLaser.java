package model.enemies.mini_boss.black_orb;

import controller.Controller;
import controller.GameManager;
import model.EpsilonModel;
import model.enemies.Enemy;
import model.Interference;
import model.enemies.normal.archmire.Archmire;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;

import java.util.ArrayList;
import java.util.UUID;

public class BlackOrbLaser {
    private final BlackOrbVertex vertex1;
    private final BlackOrbVertex vertex2;
    private String ID;
    private Point point1;
    private Point point2;
    private Point point3;
    private Point point4;
    ArrayList<Point> points;
    private long lastAttack;

    public BlackOrbLaser(BlackOrbVertex vertex1, BlackOrbVertex vertex2) {
        ID = UUID.randomUUID().toString();
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        Controller.addLaserView(this);
    }
    private void setPoints() {
        point1 = new Point(vertex1.getCenter().getX()-15, vertex1.getCenter().getY()-15);
        point2 = new Point(vertex2.getCenter().getX()-15, vertex2.getCenter().getY()-15);
        point3 = new Point(vertex2.getCenter().getX()+15, vertex2.getCenter().getY()+15);
        point4 = new Point(vertex1.getCenter().getX()+15, vertex1.getCenter().getY()+15);
        ArrayList<Point> points = new ArrayList<Point>() {{ add(point1); add(point2); add(point3); add(point4);}};
    }
    public void attack() {
        long currentTime = System.currentTimeMillis();
        if (currentTime-lastAttack >= 1000) {
            setPoints();
            ArrayList<Enemy> enemies = GameManager.getINSTANCE().getGameModel().getEnemies();
            for (int i = 0; i < enemies.size(); i++) {
                if (!(enemies.get(i) instanceof BlackOrb)) {
                    if (enemyCollidesLaser(enemies.get(i), points)) {
                        enemies.get(i).decreaseHP(12);
                    }
                }
            }
            EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
            if (Interference.pointDistanceFromLine(epsilon.getCenter(), point1, point2) < epsilon.getRadius() ||
                    Interference.pointDistanceFromLine(epsilon.getCenter(), point3, point4) < epsilon.getRadius()) {
                epsilon.decreaseHP(12);
            }
            lastAttack = currentTime;
        }

    }
    private boolean enemyCollidesLaser(Enemy enemy, ArrayList<Point> points) {
        for (int i = 0; i < enemy.getVertexes().size(); i++) {
            boolean isIn = true;
            for (int j = 0; j < points.size(); j++) {
                RotatablePoint point = enemy.getVertexes().get(i);
                double x = point.getRotatedX();
                double y = point.getRotatedY();
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
    public int getX1() {
        return (int)vertex1.getCenter().getX();
    }
    public int getY1() {
        return (int)vertex1.getCenter().getY();
    }
    public int getX2() {
        return (int)vertex2.getCenter().getX();
    }
    public int getY2() {
        return (int)vertex2.getCenter().getY();
    }

    public String getID() {
        return ID;
    }
}
