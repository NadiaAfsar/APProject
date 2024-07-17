package model.enemies.normal;

import model.frame.Frame;
import model.interfaces.collision.Collidable;
import model.interfaces.collision.Impactable;
import controller.Controller;
import controller.GameManager;
import model.BulletModel;
import model.Collective;
import model.enemies.Enemy;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Movable;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Omenoct extends Enemy implements Impactable, Movable {
    private boolean stuck;
    private boolean sideChosen;
    private int side;
    private Frame frame;
    private long lastShoot;
    public Omenoct(Point center, double velocity, int hp, Frame frame) {
        super(center, velocity);
        width = GameManager.configs.OMENOCT_WIDTH;
        height = GameManager.configs.OMENOCT_HEIGHT;
        addVertexes();
        this.HP = 20 + hp;
        damage = 8;
        this.frame = frame;
        this.frame.getEnemies().add(this);
        Controller.addEnemyView(this);
    }

    @Override
    public Direction getDirection() {
        if (!sideChosen) {
            choseSide();
        }
        Point chosenSide = getChosenSide();
        double x = 0;
        double y = 0;
        if (chosenSide.getY() == 0) {
            y = GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter().getY();
            x = chosenSide.getX();
        } else {
            x = GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter().getX();
            y = chosenSide.getY();
        }
        return new Direction(center, new Point(x, y));
    }

    private void choseSide() {
        side = (int)(Math.random()*4);
        sideChosen = true;
    }
    private Point getChosenSide() {
        Point chosenSide = null;
        if (side == 0) {
            chosenSide = new Point(0, 15+ frame.getY());
        }
        else if (side == 1) {
            chosenSide = new Point(frame.getWidth()+ frame.getX()-width, 0);
        }
        else if (side == 2) {
            chosenSide = new Point(0, frame.getHeight()+ frame.getY()-height);
        }
        else {
            chosenSide = new Point(15+ frame.getX(), 0);
        }
        return chosenSide;
    }

    @Override
    public void setSpecialImpact() {
        if (stuck) {
            velocityPower /= 2;
        }
        stuck = false;
        sideChosen = false;
    }

    @Override
    public void addCollective() {
        int[] x = new int[]{0, 5, 10, 5, 0, -5, -10, -5};
        int[] y = new int[]{-10, -15, 0, 5, 10, 5, 0, -5};
        for (int i = 0; i < 8; i++) {
            Collective collective = new Collective((int)center.getX()+x[i], (int)center.getY()+y[i],4);
            GameManager.getINSTANCE().getGameModel().getCollectives().add(collective);
            Controller.addCollectiveView(collective);
        }
    }

    @Override
    public void specialMove() {
        Point chosenSide = getChosenSide();
        if ((chosenSide.getY() == 0 && center.getX() == chosenSide.getX()) ||
                (chosenSide.getX() == 0 && center.getY() == chosenSide.getY())) {
            stuck = true;
            velocityPower *= 2;
            frame.getSides().get(side).getStuckOmenocts().add(this);
        }

    }
    protected void addVertexes() {
        vertexes = new ArrayList<>();
        double[] angles = new double[]{11d/8*Math.PI, 13d/8*Math.PI, 15d/8*Math.PI, 1d/8*Math.PI,
        3d/8*Math.PI, 5d/8*Math.PI, 7d/8*Math.PI, 9d/8*Math.PI};
        for (int i = 0; i < 8; i++) {
            RotatablePoint vertex = new RotatablePoint(center.getX(), center.getY(), angles[i]+angle, 0.54*width);
            vertexes.add(vertex);
        }
        position = new RotatablePoint(center.getX(), center.getY(), 5d/4*Math.PI+angle, Math.sqrt(0.5)*width);
    }
    public void separate() {
        stuck = false;
        sideChosen = false;
        velocityPower /= 2;
    }
    private void shoot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime-lastShoot >= 1500) {
            BulletModel bulletModel = new BulletModel(center, GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter(),
                    width/2, 4, true, frame);
            GameManager.getINSTANCE().getGameModel().getEnemiesBullets().add(bulletModel);
            lastShoot = currentTime;
        }
    }
    public void nextMove() {
            move();
            checkCollision();
            if (stuck) {
                shoot();
            }
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
