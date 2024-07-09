package model.enemies;

import collision.Collidable;
import collision.Impactable;
import controller.GameManager;
import model.EpsilonModel;
import model.game.GameModel;
import movement.Direction;
import movement.Movable;
import movement.Point;
import movement.RotatablePoint;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Enemy extends Thread {
    protected RotatablePoint position;
    protected Point center;
    protected ArrayList<RotatablePoint> vertexes;
    protected Point velocity;
    protected Point acceleration;
    protected Point accelerationRate;
    protected double angularVelocity;
    protected double angularAcceleration;
    protected double angularAccelerationRate;
    protected double angle;
    protected int HP;
    protected boolean impact;
    private final String ID;
    protected double velocityPower;
    protected int initialHP;
    public Enemy(Point center, double velocity) {
        ID = UUID.randomUUID().toString();
        this.center = center;
        this.moveVertexes();
        acceleration = new Point(0,0);
        accelerationRate = new Point(0,0);
        angularVelocity = 0;
        angularAcceleration = 0;
        angularAccelerationRate = 0;
        velocityPower = velocity;
    }

    public int getX() {
        return (int) position.getRotatedX();
    }

    public int getY() {
        return (int) position.getRotatedY();
    }

    public ArrayList<RotatablePoint> getVertexes() {
        return vertexes;
    }

    public Point getCenter() {
        return center;
    }

    public double getAngle() {
        return angle;
    }
    public void moveVertexes() {
        for (int i = 0; i < vertexes.size(); i++) {
            RotatablePoint vertex = vertexes.get(i);
            vertex.setX(center.getX());
            vertex.setY(center.getY());
            vertex.setAngle(vertex.getAngle()+angle);
        }
        position.setX(center.getX());
        position.setY(center.getY());
        position.setAngle(position.getAngle()+angle);
    }


    public boolean isImpact() {
        return impact;
    }


    public abstract Direction getDirection();


    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public String getID() {
        return ID;
    }
    public boolean died(int x) {
        HP -= x;
        if (getHP() <= 0) {
            addCollective(GameManager.getINSTANCE().getGameModel());
            return true;
        }
        return false;
    }
    protected abstract void addVertexes();


    public abstract void addCollective(GameModel gameModel);

    public int getInitialHP() {
        return initialHP;
    }
}
