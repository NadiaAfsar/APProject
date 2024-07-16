package model.enemies;

import controller.Controller;
import controller.GameManager;
import controller.audio.AudioController;
import model.enemies.mini_boss.black_orb.BlackOrb;
import model.enemies.mini_boss.black_orb.BlackOrbVertex;
import model.enemies.normal.Wyrm;
import model.enemies.normal.archmire.Archmire;
import model.enemies.normal.archmire.MiniArchmire;
import model.frame.Frame;
import model.interfaces.collision.Collidable;
import model.interfaces.collision.Impactable;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Enemy implements Collidable{
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
    protected int damage;
    protected double width;
    protected double height;
    protected Frame frame;
    public Enemy(Point center, double velocity) {
        ID = UUID.randomUUID().toString();
        this.center = center;
        this.velocity = new Point(0,0);
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

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public String getID() {
        return ID;
    }

    public void decreaseHP(int x) {
        HP -= x;
        if (HP <= 0) {
            die();
        }
    }
    protected void die() {
        addCollective();
        GameManager.getINSTANCE().getDiedEnemies().add(this);
        Controller.removeEnemyView(this);
        AudioController.addEnemyDyingSound();
    }
    protected void checkCollision() {
        synchronized (GameManager.getINSTANCE().getGameModel().getEnemyLock()) {
            ArrayList<Enemy> enemies = GameManager.getINSTANCE().getGameModel().getEnemies();
            for (int i = 0; i < enemies.size(); i++) {
                if (!enemies.get(i).equals(this)) {
                    if (!(enemies.get(i) instanceof Wyrm) && !(enemies.get(i) instanceof Archmire)) {
                        if (enemies.get(i) instanceof BlackOrb) {
                            ArrayList<BlackOrbVertex> vertices = ((BlackOrb) enemies.get(i)).getBlackOrbVertices();
                            for (int j = 0; j < vertices.size(); j++) {
                                Point collisionPoint = this.getCollisionPoint(vertices.get(j));
                                if (collisionPoint != null) {
                                    ((Impactable) this).impact(collisionPoint, vertices.get(j));
                                }
                            }
                        } else {
                            Collidable collidable = enemies.get(i);
                            Point collisionPoint = this.getCollisionPoint(collidable);
                            if (collisionPoint != null) {
                                ((Impactable) this).impact(collisionPoint, collidable);
                            }
                        }
                    }
                }
            }
        }
    }
    protected abstract void addVertexes();


    public abstract void addCollective();

    public int getDamage() {
        return damage;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
    public abstract void nextMove();
}
