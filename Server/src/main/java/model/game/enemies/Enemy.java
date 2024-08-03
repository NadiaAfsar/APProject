package model.game.enemies;

import controller.Controller;
import controller.game_manager.GameManager;
import controller.audio.AudioController;
import model.game.EpsilonModel;
import model.game.GameModel;
import model.game.enemies.mini_boss.Barricados;
import model.game.enemies.mini_boss.black_orb.BlackOrb;
import model.game.enemies.mini_boss.black_orb.BlackOrbVertex;
import model.game.enemies.normal.Wyrm;
import model.game.enemies.normal.archmire.Archmire;
import model.game.frame.MyFrame;
import model.interfaces.collision.Collidable;
import model.interfaces.collision.Impactable;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Enemy extends Thread implements Collidable{
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
    protected final String ID;
    protected double velocityPower;
    protected int damage;
    protected double width;
    protected double height;
    protected MyFrame myFrame;
    protected Logger logger;
    protected  boolean died;
    protected GameManager gameManager;
    protected EpsilonModel epsilon;
    public Enemy(Point center, double velocity, GameManager gameManager, EpsilonModel epsilon) {
        ID = UUID.randomUUID().toString();
        this.gameManager = gameManager;
        this.center = center;
        this.epsilon = epsilon;
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
        position.setAngle(position.getInitialAngle()+angle);
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
        if (x != 0) {
            GameModel gameModel = gameManager.getGameModel();
            epsilon.setHP(epsilon.getHP() + gameModel.getChiron());
        }
        if (HP <= 0 && !(this instanceof Barricados)) {
            die();
        }
        logger.debug(HP);
    }
    protected void die() {
        addCollective();
        gameManager.getGameModel().getDiedEnemies().add(this);
        Controller.removeEnemyView(this, gameManager);
        AudioController.addEnemyDyingSound();
        gameManager.getGameModel().getCurrentWave().newEnemyDied();
        died = true;
    }
    protected void checkCollision() {
        synchronized (gameManager.getGameModel().getEnemyLock()) {
            ArrayList<Enemy> enemies = gameManager.getGameModel().getEnemies();
            for (int i = 0; i < enemies.size(); i++) {
                if (!enemies.get(i).equals(this)) {
                    if (!(enemies.get(i) instanceof Wyrm) && !(enemies.get(i) instanceof Archmire)) {
                        if (enemies.get(i) instanceof BlackOrb) {
                            ArrayList<BlackOrbVertex> vertices = ((BlackOrb) enemies.get(i)).getBlackOrbVertices();
                                for (int j = 0; j < vertices.size(); j++) {
                                    Point collisionPoint = this.getCollisionPoint(vertices.get(j));
                                    if (collisionPoint != null) {
                                        logger.debug("collided");
                                        ((Impactable) this).impact(collisionPoint, vertices.get(j));
                                    }
                                }
                        } else {
                            Collidable collidable = enemies.get(i);
                            Point collisionPoint = this.getCollisionPoint(collidable);
                            if (collisionPoint != null) {
                                logger.debug("collided");
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

    public Point getVelocity() {
        return velocity;
    }

    public Point getAcceleration() {
        return acceleration;
    }

    public Point getAccelerationRate() {
        return accelerationRate;
    }

    public double getAngularVelocity() {
        return angularVelocity;
    }

    public double getAngularAcceleration() {
        return angularAcceleration;
    }

    public double getAngularAccelerationRate() {
        return angularAccelerationRate;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public void setVelocity(Point velocity) {
        this.velocity = velocity;
    }

    public void setAcceleration(Point acceleration) {
        this.acceleration = acceleration;
    }

    public void setAccelerationRate(Point accelerationRate) {
        this.accelerationRate = accelerationRate;
    }

    public void setAngularVelocity(double angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    public void setAngularAcceleration(double angularAcceleration) {
        this.angularAcceleration = angularAcceleration;
    }

    public void setAngularAccelerationRate(double angularAccelerationRate) {
        this.angularAccelerationRate = angularAccelerationRate;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setImpact(boolean impact) {
        this.impact = impact;
    }

    public double getVelocityPower() {
        return velocityPower;
    }
    public abstract Direction getDirection();

    public MyFrame getFrame() {
        return myFrame;
    }

    public void setDied(boolean died) {
        this.died = died;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public EpsilonModel getEpsilon() {
        return epsilon;
    }
}
