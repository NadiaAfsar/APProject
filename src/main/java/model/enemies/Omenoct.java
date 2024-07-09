package model.enemies;

import collision.Collidable;
import collision.Impactable;
import controller.Controller;
import controller.GameManager;
import model.BulletModel;
import model.Collective;
import model.game.GameModel;
import movement.Direction;
import movement.Movable;
import movement.Point;
import movement.RotatablePoint;

import java.awt.*;
import java.util.ArrayList;

public class Omenoct extends Enemy implements Impactable, Collidable, Movable {
    private boolean stuck;
    private boolean sideChosen;
    private int side;
    public Omenoct(Point center, double velocity, int hp) {
        super(center, velocity);
        addVertexes();
        start();
        this.HP = 20 + hp;
        initialHP = HP;
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

    @Override
    public double getAngularVelocity() {
        return 0;
    }

    @Override
    public double getAngularAcceleration() {
        return 0;
    }

    @Override
    public void setAngle(double angle) {

    }

    private void choseSide() {
        side = (int)(Math.random()*4);
        sideChosen = true;
    }
    private Point getChosenSide() {
        Point chosenSide = null;
        if (side == 0) {
            chosenSide = new Point(0, 15);
        }
        else if (side == 1) {
            chosenSide = new Point(GameManager.getINSTANCE().getGameModel().getWidth()-15, 0);
        }
        else if (side == 2) {
            chosenSide = new Point(0, GameManager.getINSTANCE().getGameModel().getHeight()-15);
        }
        else {
            chosenSide = new Point(15, 0);
        }
        return chosenSide;
    }

    @Override
    public void setAngularVelocity(double velocity) {

    }

    @Override
    public void setAngularAcceleration(double acceleration) {

    }

    @Override
    public void setAngularAccelerationRate(double accelerationRate) {

    }

    @Override
    public double getAngularAccelerationRate() {
        return 0;
    }

    @Override
    public void setCenter(Point center) {

    }

    @Override
    public void setImpact(boolean impact) {

    }

    @Override
    public Point getAcceleration() {
        return null;
    }

    @Override
    public void setAcceleration(Point acceleration) {

    }

    @Override
    public Point getAccelerationRate() {
        return null;
    }

    @Override
    public void setAccelerationRate(Point accelerationRate) {

    }

    @Override
    public void setVelocity(Point velocity) {

    }

    @Override
    public Point getVelocity() {
        return null;
    }

    @Override
    public double getVelocityPower() {
        return 0;
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
    public void addCollective(GameModel gameModel) {
        int[] x = new int[]{0, 5, 10, 5, 0, -5, -10, -5};
        int[] y = new int[]{-10, -15, 0, 5, 10, 5, 0, -5};
        for (int i = 0; i < 8; i++) {
            Collective collective = new Collective((int)center.getX()+x[i], (int)center.getY()+y[i], Color.RED, 4);
            gameModel.getCollectives().add(collective);
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
            GameManager.getINSTANCE().getGameModel().getSides().get(side).getStuckOmenocts().add(this);
        }

    }
    protected void addVertexes() {
        vertexes = new ArrayList<>();
        double[] angles = new double[]{11d/8*Math.PI, 13d/8*Math.PI, 15d/8*Math.PI, 1d/8*Math.PI,
        3d/8*Math.PI, 5d/8*Math.PI, 7d/8*Math.PI, 9d/8*Math.PI};
        for (int i = 0; i < 8; i++) {
            RotatablePoint vertex = new RotatablePoint(center.getX(), center.getY(), angles[i]+angle, 18.4);
            vertexes.add(vertex);
        }
        position = new RotatablePoint(center.getX(), center.getY(), 5d/4*Math.PI+angle, 0);
    }
    public void separate() {
        stuck = false;
        sideChosen = false;
        velocityPower /= 2;
    }
    private void shoot() {
        BulletModel bulletModel = new BulletModel(center, GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter(),
                15, 4, true);
        GameManager.getINSTANCE().getGameModel().getEnemiesBullets().add(bulletModel);
        Controller.addBulletView(bulletModel);
    }
    public void run() {
        while (true) {
            if (stuck) {
                shoot();
            }
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
