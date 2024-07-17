package model.enemies.normal;

import controller.Controller;
import controller.GameManager;
import model.BulletModel;
import model.Collective;
import model.enemies.mini_boss.black_orb.BlackOrb;
import model.enemies.mini_boss.black_orb.BlackOrbVertex;
import model.enemies.normal.archmire.Archmire;
import model.frame.Frame;
import model.enemies.Enemy;
import model.interfaces.collision.Collidable;
import model.interfaces.collision.Impactable;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Movable;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;

import java.awt.*;
import java.util.ArrayList;

public class Wyrm extends Enemy implements Movable, Impactable {
    private int direction;
    private long lastShotTime;
    public Wyrm(Point center, double velocity, int hp) {
        super(center, velocity);
        this.HP = 12 + hp;
        direction = 1;
        width = GameManager.configs.WYRM_WIDTH;
        height = GameManager.configs.WYRM_HEIGHT;
        frame = new Frame(width+20, height+20, center.getX()-width/2-10, center.getY()-height/2-10, true, false);
        addVertexes();
        frame.getEnemies().add(this);
        Controller.addEnemyView(this);
        GameManager.getINSTANCE().getGameModel().getFrames().add(frame);
    }
    protected void addVertexes() {
        vertexes = new ArrayList<>();
        double[] angles = new double[]{1.1*Math.PI, 1.17*Math.PI, 1.36*Math.PI, 1.5*Math.PI, 1.7*Math.PI,
        0.04*Math.PI, 0.1*Math.PI, 0.17*Math.PI, 0.36*Math.PI, 0.5*Math.PI, 0.7*Math.PI, 1.04*Math.PI};
        double[] radius = new double[]{15.8*width/30, 11.6*width/30, 12.08*width/30, 12.5*width/30, 8.6*width/30,
        13.15*width/30, 15.8*width/30, 11.6*width/30, 12.08*width/30, 12.5*width/30, 8.6*width/30, 13.15*width/30};
        for (int i = 0; i < 12; i++) {
            RotatablePoint vertex = new RotatablePoint(center.getX(), center.getY(), angles[i]+angle, radius[i]);
            vertexes.add(vertex);
        }
        position = new RotatablePoint(center.getX(), center.getY(), 1.22*Math.PI, 19.5*width/30);
    }
    public void nextMove() {
        move();
        frame.setX(center.getX()-width/2-10);
        frame.setY(center.getY()-height/2-10);
        shoot();
    }
    private void shoot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= 1500 && isNearEpsilon()) {
            BulletModel bulletModel = new BulletModel(center, GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter(),
                    height/2, 8, false, frame, frame);
            GameManager.getINSTANCE().getGameModel().getEnemiesBullets().add(bulletModel);
            lastShotTime = currentTime;
        }
    }

    @Override
    public Direction getDirection() {
        Point epsilonCenter = GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter();
        Direction direction = new Direction(center, epsilonCenter);
        if (isTooNearEpsilon()) {
            Direction direction1 = new Direction();
            direction1.setDx(-direction.getDx());
            direction1.setDy(-direction.getDy());
            return direction1;
        }
        else if (!isNearEpsilon()) {
            return direction;
        }
        Direction direction1 = new Direction();
        direction1.setDx(direction.getDy()*this.direction*3);
        direction1.setDy(-direction.getDx()*this.direction*3);
        return direction1;
    }
    private boolean isNearEpsilon() {
        Point epsilonCenter = GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter();
        return getDistance(center.getX(), center.getY(), epsilonCenter.getX(), epsilonCenter.getY()) <= 150;
    }
    private boolean isTooNearEpsilon() {
        Point epsilonCenter = GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter();
        return getDistance(center.getX(), center.getY(), epsilonCenter.getX(), epsilonCenter.getY()) < 140;
    }

    @Override
    public void specialMove() {

    }
    protected void checkCollision() {
        synchronized (GameManager.getINSTANCE().getGameModel().getEnemyLock()) {
            ArrayList<Enemy> enemies = GameManager.getINSTANCE().getGameModel().getEnemies();
            for (int i = 0; i < enemies.size(); i++) {
                if (enemies.get(i) instanceof BlackOrb) {
                    ArrayList<BlackOrbVertex> vertices = ((BlackOrb)enemies.get(i)).getBlackOrbVertices();
                    for (int j = 0; j < vertices.size(); j++) {
                        Point collisionPoint = (this).getCollisionPoint(vertices.get(j));
                        if (collisionPoint != null) {
                            (this).impact(collisionPoint, vertices.get(j));
                        }
                    }
                }
                else if (!(enemies.get(i) instanceof Wyrm) && !(enemies.get(i) instanceof Archmire)) {
                        Collidable collidable = enemies.get(i);
                        Point collisionPoint = (this).getCollisionPoint(collidable);
                        if (collisionPoint != null) {
                            (this).impact(collisionPoint, collidable);
                        }
                }
            }
        }
    }

    @Override
    public void addCollective() {
        int[] x = new int[]{-10, 10};
        int[] y = new int[]{-10, 10};
        for (int i = 0; i < 2; i++) {
            Collective collective = new Collective((int)center.getX()+x[i], (int)center.getY()+y[i], Color.RED,
                    8, frame);
            GameManager.getINSTANCE().getGameModel().getCollectives().add(collective);
            Controller.addCollectiveView(collective);
        }
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public void setAngularVelocity(double velocity) {
        angularVelocity = velocity;
    }

    @Override
    public void setAngularAcceleration(double acceleration) {
        angularAcceleration = acceleration;
    }

    @Override
    public void setAngularAccelerationRate(double accelerationRate) {
        angularAccelerationRate = accelerationRate;
    }

    @Override
    public double getAngularAccelerationRate() {
        return angularAccelerationRate;
    }

    @Override
    public void setCenter(Point center) {
        this.center = center;
    }

    @Override
    public void setImpact(boolean impact) {
        this.impact = impact;
    }

    @Override
    public Point getAcceleration() {
        return acceleration;
    }

    @Override
    public void setAcceleration(Point acceleration) {
        this.acceleration = acceleration;
    }

    @Override
    public Point getAccelerationRate() {
        return accelerationRate;
    }

    @Override
    public void setAccelerationRate(Point accelerationRate) {
        this.accelerationRate = accelerationRate;
    }

    @Override
    public void setSpecialImpact() {
        direction *= -1;
    }

    @Override
    public void setVelocity(Point velocity) {
        this.velocity = velocity;
    }

    @Override
    public Point getVelocity() {
        return velocity;
    }

    @Override
    public double getVelocityPower() {
        return velocityPower;
    }
    @Override
    public double getAngularVelocity() {
        return angularVelocity;
    }

    @Override
    public double getAngularAcceleration() {
        return angularAcceleration;
    }

    @Override
    public void setAngle(double angle) {
        this.angle = angle;
    }
}
