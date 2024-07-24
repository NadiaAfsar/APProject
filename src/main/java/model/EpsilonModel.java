package model;

import model.enemies.Enemy;
import model.enemies.mini_boss.black_orb.BlackOrb;
import model.enemies.mini_boss.black_orb.BlackOrbVertex;
import model.enemies.normal.Necropick;
import model.enemies.normal.archmire.Archmire;
import model.enemies.smiley.Fist;
import model.enemies.smiley.Smiley;
import model.frame.Frame;
import model.interfaces.collision.Collidable;
import model.interfaces.collision.Impactable;
import controller.*;
import controller.audio.AudioController;
import model.game.GameModel;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Movable;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.util.ArrayList;

public class EpsilonModel implements Collidable, Movable, Impactable {
    private Timer upTimer;
    private Timer downTimer;
    private Timer leftTimer;
    private Timer rightTimer;
    private int radius;
    private Point center;
    private int HP;
    private int XP;
    private Point velocity;
    private Point acceleration;
    private Point accelerationRate;
    private double angle;
    private double angularVelocity;
    private double angularAcceleration;
    private double angularAccelerationRate;
    private ArrayList<RotatablePoint> vertexes;
    private final int sensitivity;
    private boolean impact;
    private Frame frame;
    private Logger logger;
    private Frame prevFrame;
    private ArrayList<RotatablePoint> cerberusList;

    public EpsilonModel(Frame frame) {
        logger = Logger.getLogger(EpsilonModel.class);
        setCenter((int)(frame.getX()+frame.getWidth()/2), (int)(frame.getY()+frame.getHeight()/2));
        radius = GameManager.configs.EPSILON_RADIUS;
        addMoveTimers();
        HP = 100;
        XP = 1000;
        velocity = new Point(0,0);
        acceleration = new Point(0,0);
        accelerationRate = new Point(0,0);
        vertexes = new ArrayList<>();
        this.frame = frame;
        this.sensitivity = GameManager.getSensitivity();
    }


    public void setInCenter() {
        setCenter(frame.getX()+frame.getWidth()/2, frame.getY()+frame.getHeight()/2);
    }
    public void moveUp(boolean move) {
        if (move) {
            upTimer.start();
        }
        else {
            upTimer.stop();
        }
    }
    public void moveDown(boolean move) {
        if (move) {
            downTimer.start();
        }
        else {
            downTimer.stop();
        }
    }
    public void moveRight(boolean move) {
        if (move) {
            rightTimer.start();
        }
        else {
            rightTimer.stop();
        }
    }
    public void moveLeft(boolean move) {
        if (move) {
            leftTimer.start();
        }
        else {
            leftTimer.stop();
        }
    }
    private void addMoveTimers() {
        upTimer = new Timer(10, e -> {
            if (GameManager.getINSTANCE().isQuake()){
                moveRandom();
            }
            else {
                center = new Point(center.getX(), center.getY() - 0.5 * sensitivity);
            }
                setInFrame();
        });
        downTimer = new Timer(10, e -> {
            if (GameManager.getINSTANCE().isQuake()){
                moveRandom();
            }
            else {
                center = new Point(center.getX(), center.getY() + 0.5 * sensitivity);
            }
                setInFrame();
        });
        rightTimer = new Timer(10, e -> {
            if (GameManager.getINSTANCE().isQuake()){
                moveRandom();
            }
            else {
                center = new Point(center.getX() + 0.5 * sensitivity, center.getY());
            }
                setInFrame();
        });
        leftTimer = new Timer(10, e -> {
            if (GameManager.getINSTANCE().isQuake()){
                moveRandom();
            }
            else {
                center = new Point(center.getX() - 0.5 * sensitivity, center.getY());
            }
                setInFrame();
        });
    }
    private void moveRandom(){
        double x = (-1+(Math.random()*3))*0.5;
        double y = (-1+(Math.random()*3))*0.5;
        center = new Point(center.getX()+x, center.getY()+y);
    }

    public int getX() {
        return (int)center.getX()-radius;
    }

    @Override
    public boolean isImpact() {
        return impact;
    }

    public int getY() {
        return (int)center.getY()-radius;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(double x, double y) {
        this.center = new Point(x+radius, y+radius);
    }

    public void decreaseHP(int hp) {
        HP -= hp;
        if (HP <= 0) {
            AudioController.addGameOverSound();
            Controller.gameOver(XP);
        }
    }

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }
    public void setInFrame() {
        if (frame != null) {
            if (getX() - frame.getX() <= -10 && !frame.isInOverLap(getX(), getY())) {
                setCenter(10 + frame.getX(), getY());
            } else if (getX() - frame.getX() >= frame.getWidth() - 2 * radius && !frame.isInOverLap(getX() + 2 * radius, getY())) {
                setCenter((int) frame.getWidth() + frame.getX() - 10 - 2 * radius, getY());
            }
            if (getY() - frame.getY() <= -10 && !frame.isInOverLap(getX(), getY())) {
                setCenter(getX(), 10 + frame.getY());
            } else if (getY() - frame.getY() > frame.getHeight() - 2 * radius && !frame.isInOverLap(getX(), getY() + 2 * radius)) {
                setCenter(getX(), (int) frame.getHeight() + frame.getY() - 10 - 2 * radius);
            }
        }
    }
    private void checkFrame() {
        if (frame != null){
            if (!Interference.isInFrame(getX(), getY(), 2*radius, 2*radius, frame)){
                prevFrame = frame;
                prevFrame.setStopMoving(true);
                frame = null;
                //logger.debug("out");
            }
        }
        if (frame == null){
            ArrayList<Frame> frames = GameManager.getINSTANCE().getGameModel().getFrames();
            for (int i = 0; i < frames.size(); i++){
                if (Interference.isInFrame(getX(), getY(), 2*radius, 2*radius, frames.get(i))){
                    frame = frames.get(i);
                    prevFrame.setStopMoving(false);
                    //logger.debug(i);
                    return;
                }
            }
        }
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }
    public void addVertex() {
        int v = vertexes.size()+1;
        vertexes = new ArrayList<>();
        double angle = 2*Math.PI/v;
        for (int i = 0; i < v; i++) {
            RotatablePoint vertex = new RotatablePoint(center.getX(), center.getY(), angle*i-Math.PI/2, radius);
            vertexes.add(vertex);
        }
        Controller.addVertexesToEpsilon();
    }

    public ArrayList<RotatablePoint> getVertexes() {
        return vertexes;
    }

    public void moveVertexes() {
        for (int i = 0; i < vertexes.size(); i++) {
            RotatablePoint vertex = vertexes.get(i);
            vertex.setX(center.getX());
            vertex.setY(center.getY());
        }
    }
    private double calculateAngle(Direction direction) {
        double angle = Math.atan(direction.getDy()/direction.getDx());
        if (direction.getDx() < 0) {
            angle += Math.PI;
        }
        return angle;
    }
    public void rotateVertexes(int x, int y) {
        Direction direction = new Direction(new Point(getCenter().getX(),getCenter().getY()), new Point(x,y));
        double angle = calculateAngle(direction)+Math.PI/2;
        for (int i = 0; i < vertexes.size(); i++) {
            RotatablePoint vertex = vertexes.get(i);
            double a = angle+vertex.getInitialAngle();
            vertex.setAngle(a);
        }
    }
    public void increaseSize() {
        if (radius*2 < frame.getWidth()) {
            radius += 2;
            if (getX() >= 0) {
                setCenter(getX()-2, getY());
            }
            if (getY() >= 0) {
                setCenter(getX(), getY()-2);
            }
        }
        else {
            GameManager.getINSTANCE().getGameModel().setFinished(true);
        }
    }
    public void shootBullet(int x, int y) {
        GameModel gameModel = GameManager.getINSTANCE().getGameModel();
        GameManager.getINSTANCE().checkAthenaTime();
        if (gameModel.isAthena()) {
            addBullet(x,y);
            double dx = gameModel.getBullets().get(0).getDirection().getDx();
            double dy = gameModel.getBullets().get(0).getDirection().getDy();
            addBullet(x,y);
            BulletModel bullet = gameModel.getBullets().get(1);
            bullet.setPosition(bullet.getX1()+dx*100, bullet.getY1()+dy*100);
            addBullet(x,y);
            bullet = gameModel.getBullets().get(2);
            bullet.setPosition(bullet.getX1()+dx*200, bullet.getY1()+dy*200);
        }
        else {
            addBullet(x,y);
        }
    }
    private void addBullet(int x, int y) {
        BulletModel bulletModel = new BulletModel(getCenter(), new Point(x, y), radius,
                5 + GameManager.getINSTANCE().getGameModel().getAres(), false, frame);
        GameManager.getINSTANCE().getGameModel().getBullets().add(bulletModel);
        if (GameManager.getINSTANCE().getWave() == 2){
            GameManager.getINSTANCE().getSmiley().bulletShot(bulletModel);
        }
    }
    public void nextMove() {
            move();
            checkCollisions();
            checkFrame();
            //EpsilonLogger.getInfo(logger, this);
    }
    private void checkCollisions() {
        synchronized (GameManager.getINSTANCE().getGameModel().getEnemyLock()) {
            ArrayList<Enemy> enemies = GameManager.getINSTANCE().getGameModel().getEnemies();
            for (int i = 0; i < enemies.size(); i++) {
                if ( !(enemies.get(i) instanceof Archmire)) {
                    if (enemies.get(i) instanceof BlackOrb) {
                        ArrayList<BlackOrbVertex> vertices = ((BlackOrb)enemies.get(i)).getBlackOrbVertices();
                            for (int j = 0; j < vertices.size(); j++) {
                                checkCollisionWithCollidable(vertices.get(j));
                            }
                    }
                    else {
                        Collidable collidable = enemies.get(i);
                        if (!(collidable instanceof Necropick)) {
                            checkCollisionWithCollidable(collidable);
                        }
                        else if (!((Necropick)collidable).isDisappeared()){
                            checkCollisionWithCollidable(collidable);
                        }
                    }
                }
            }
        }
    }
    private void checkCollisionWithCollidable(Collidable collidable){
        Point collisionPoint = collidable.getCollisionPoint(this);
        if (collisionPoint != null) {
            logger.debug("collided");
            if (collidable instanceof Fist){
                ((Fist)collidable).slapped();
            }
            if (collidable instanceof Enemy){
                ((Enemy)collidable).decreaseHP(GameManager.getINSTANCE().getAstarpe());
            }
            else {
                ((BlackOrbVertex)collidable).decreaseHP(GameManager.getINSTANCE().getAstarpe());
            }
            this.impact(collisionPoint, collidable);
        }
    }

    public Frame getFrame() {
        return frame;
    }

    public int getRadius() {
        return radius;
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
    public Point getVelocity() {
        return velocity;
    }

    @Override
    public double getVelocityPower() {
        return 0;
    }

    @Override
    public Direction getDirection() {
        return new Direction(new Point(0, 0), new Point(0, 0));
    }

    @Override
    public void setVelocity(Point velocity) {
        this.velocity = velocity;
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

    }

    @Override
    public double getAngle() {
        return angle;
    }

    @Override
    public void setAngle(double angle) {
        this.angle = angle;
    }

    @Override
    public double getAngularVelocity() {
        return angularVelocity;
    }

    @Override
    public void setAngularVelocity(double angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    @Override
    public double getAngularAcceleration() {
        return angularAcceleration;
    }

    @Override
    public void setAngularAcceleration(double angularAcceleration) {
        this.angularAcceleration = angularAcceleration;
    }

    @Override
    public double getAngularAccelerationRate() {
        return angularAccelerationRate;
    }

    @Override
    public void specialMove() {
        setInFrame();
    }

    @Override
    public void setAngularAccelerationRate(double angularAccelerationRate) {
        this.angularAccelerationRate = angularAccelerationRate;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public ArrayList<RotatablePoint> getCerberusList() {
        return cerberusList;
    }

    public void setCerberusList(ArrayList<RotatablePoint> cerberusList) {
        this.cerberusList = cerberusList;
    }
}