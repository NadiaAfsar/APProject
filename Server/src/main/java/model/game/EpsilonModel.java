package model.game;

import application.MyApplication;
import controller.Controller;
import controller.game_manager.GameManager;
import controller.audio.AudioController;
import model.Calculations;
import model.Client;
import model.game.enemies.Enemy;
import model.game.enemies.mini_boss.black_orb.BlackOrb;
import model.game.enemies.mini_boss.black_orb.BlackOrbVertex;
import model.game.enemies.normal.Necropick;
import model.game.enemies.normal.archmire.Archmire;
import model.game.enemies.smiley.Fist;
import model.game.frame.MyFrame;
import model.interfaces.collision.Collidable;
import model.interfaces.collision.Impactable;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Movable;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.util.ArrayList;
import java.util.UUID;

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
    private MyFrame myFrame;
    private Logger logger;
    private MyFrame prevMyFrame;
    private ArrayList<RotatablePoint> cerberusList;
    private long lastCerberusAttack;
    private GameManager gameManager;
    private int totalBullets;
    private int successfulBullets;
    private int killedEnemies;
    private Client client;
    private MyFrame initialMyFrame;
    private String ID;

    public EpsilonModel(MyFrame myFrame, GameManager gameManager, Client client) {
        this.gameManager = gameManager;
        this.client = client;
        logger = Logger.getLogger(EpsilonModel.class);
        ID = UUID.randomUUID().toString();
        setCenter((int)(myFrame.getX()+ myFrame.getWidth()/2), (int)(myFrame.getY()+ myFrame.getHeight()/2));
        radius = MyApplication.configs.EPSILON_RADIUS;
        addMoveTimers();
        HP = 100;
        XP = 1000;
        velocity = new Point(0,0);
        acceleration = new Point(0,0);
        accelerationRate = new Point(0,0);
        vertexes = new ArrayList<>();
        cerberusList = new ArrayList<>();
        this.myFrame = myFrame;
        initialMyFrame = myFrame;
        this.sensitivity = gameManager.getSensitivity();
        Controller.addEpsilonView(this, gameManager);
    }


    public void setInCenter() {
        setCenter(myFrame.getX()+ myFrame.getWidth()/2, myFrame.getY()+ myFrame.getHeight()/2);
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
            if (gameManager.getGameModel().isQuake()){
                moveRandom();
            }
            else {
                center = new Point(center.getX(), center.getY() - 0.5 * sensitivity);
            }
                setInFrame();
        });
        downTimer = new Timer(10, e -> {
            if (gameManager.getGameModel().isQuake()){
                moveRandom();
            }
            else {
                center = new Point(center.getX(), center.getY() + 0.5 * sensitivity);
            }
                setInFrame();
        });
        rightTimer = new Timer(10, e -> {
            if (gameManager.getGameModel().isQuake()){
                moveRandom();
            }
            else {
                center = new Point(center.getX() + 0.5 * sensitivity, center.getY());
            }
                setInFrame();
        });
        leftTimer = new Timer(10, e -> {
            if (gameManager.getGameModel().isQuake()){
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
            if( gameManager.isSaved()){
                HP = 10;
                gameManager.setSaved(false);
            }
            else {
                AudioController.addGameOverSound();
                GameModel gameModel = gameManager.getGameModel();
                Controller.gameOver(XP, gameModel.getTimePlayed(), totalBullets, successfulBullets,
                        killedEnemies, gameManager);
            }
        }
    }

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }
    public void setInFrame() {
        if (myFrame != null) {
            if (getX() - myFrame.getX() <= -10 && !myFrame.isInOverLap(getX(), getY())) {
                setCenter(10 + myFrame.getX(), getY());
            } else if (getX() - myFrame.getX() >= myFrame.getWidth() - 2 * radius && !myFrame.isInOverLap(getX() + 2 * radius, getY())) {
                setCenter((int) myFrame.getWidth() + myFrame.getX() - 10 - 2 * radius, getY());
            }
            if (getY() - myFrame.getY() <= -10 && !myFrame.isInOverLap(getX(), getY())) {
                setCenter(getX(), 10 + myFrame.getY());
            } else if (getY() - myFrame.getY() > myFrame.getHeight() - 2 * radius && !myFrame.isInOverLap(getX(), getY() + 2 * radius)) {
                setCenter(getX(), (int) myFrame.getHeight() + myFrame.getY() - 10 - 2 * radius);
            }
        }
    }
    private void checkFrame() {
        if (myFrame != null){
            if (!Interference.isInFrame(getX(), getY(), 2*radius, 2*radius, myFrame)){
                prevMyFrame = myFrame;
                prevMyFrame.setStopMoving(true);
                myFrame = null;
                //logger.debug("out");
            }
        }
        if (myFrame == null){
            ArrayList<MyFrame> myFrames = gameManager.getGameModel().getFrames();
            for (int i = 0; i < myFrames.size(); i++){
                if (Interference.isInFrame(getX(), getY(), 2*radius, 2*radius, myFrames.get(i))){
                    myFrame = myFrames.get(i);
                    prevMyFrame.setStopMoving(false);
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
        Controller.addVertexesToEpsilon(gameManager, this);
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
        for (int i = 0; i < cerberusList.size(); i++){
            RotatablePoint cerberus = cerberusList.get(i);
            cerberus.setX(center.getX());
            cerberus.setY(center.getY());
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
        if (radius*2 < myFrame.getWidth()) {
            radius += 2;
            if (getX() >= 0) {
                setCenter(getX()-2, getY());
            }
            if (getY() >= 0) {
                setCenter(getX(), getY()-2);
            }
        }
        else {
            gameManager.getGameModel().setFinished(true);
        }
    }
    public void shootBullet(int x, int y) {
        GameModel gameModel = gameManager.getGameModel();
        gameManager.checkAthenaTime();
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
        BulletModel bulletModel = null;
        if (gameManager.isPhonoi()){
            bulletModel = new BulletModel(getCenter(), new Point(x, y), radius,
                    50 + gameManager.getGameModel().getAres(), false, myFrame, gameManager);
            gameManager.setPhonoi(false);
        }
        else {
            bulletModel = new BulletModel(getCenter(), new Point(x, y), radius,
                    5 + gameManager.getGameModel().getAres(), false, myFrame, gameManager);
        }
        successfulBullets++;
        gameManager.getGameModel().getBullets().add(bulletModel);
        if (gameManager.getGameModel().getWave() == 6){
            gameManager.getGameModel().getSmiley().bulletShot(bulletModel, this);
        }
    }
    public void nextMove() {
            move();
            cerberusAttack();
            checkCollisions();
            checkFrame();
            //EpsilonLogger.getInfo(logger, this);
    }
    private void checkCollisions() {
        synchronized (gameManager.getGameModel().getEnemyLock()) {
            ArrayList<Enemy> enemies = gameManager.getGameModel().getEnemies();
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
                ((Enemy)collidable).decreaseHP(gameManager.getGameModel().getAstarpe());
            }
            else {
                ((BlackOrbVertex)collidable).decreaseHP(gameManager.getGameModel().getAstarpe());
            }
            this.impact(collisionPoint, collidable);
        }
    }

    public MyFrame getFrame() {
        return myFrame;
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

    public void setFrame(MyFrame myFrame) {
        this.myFrame = myFrame;
    }

    public ArrayList<RotatablePoint> getCerberusList() {
        return cerberusList;
    }

    public void setCerberusList(ArrayList<RotatablePoint> cerberusList) {
        this.cerberusList = cerberusList;
    }
    private void cerberusAttack() {
        ArrayList<Enemy> enemies = gameManager.getGameModel().getEnemies();
        if (System.currentTimeMillis() - lastCerberusAttack >= 15000) {
            for (int i = 0; i < cerberusList.size(); i++) {
                if (System.currentTimeMillis() - lastCerberusAttack >= 15000) {
                    for (int j = 0; j < enemies.size(); j++) {
                        Enemy enemy = enemies.get(j);
                        if (enemy instanceof BlackOrb) {
                            ArrayList<BlackOrbVertex> vertices = ((BlackOrb) enemy).getBlackOrbVertices();
                            for (int k = 0; k < vertices.size(); k++) {
                                if (Calculations.getDistance(cerberusList.get(i).getRotatedX(), cerberusList.get(i).getRotatedY(),
                                        vertices.get(k).getCenter().getX(), vertices.get(k).getCenter().getY()) <
                                        vertices.get(k).getWidth() / 2 - 10) {
                                    vertices.get(k).decreaseHP(10);
                                    lastCerberusAttack = System.currentTimeMillis();
                                    return;
                                }
                            }
                        } else {
                            if (Calculations.getDistance(cerberusList.get(i).getRotatedX(), cerberusList.get(i).getRotatedY(),
                                    enemy.getCenter().getX(), enemy.getCenter().getY()) < enemy.getWidth() / 2 - 10) {
                                enemy.decreaseHP(10);
                                lastCerberusAttack = System.currentTimeMillis();
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Timer getUpTimer() {
        return upTimer;
    }

    public Timer getDownTimer() {
        return downTimer;
    }

    public Timer getLeftTimer() {
        return leftTimer;
    }

    public Timer getRightTimer() {
        return rightTimer;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public int getTotalBullets() {
        return totalBullets;
    }

    public void setTotalBullets(int totalBullets) {
        this.totalBullets = totalBullets;
    }

    public int getSuccessfulBullets() {
        return successfulBullets;
    }

    public void setSuccessfulBullets(int successfulBullets) {
        this.successfulBullets = successfulBullets;
    }

    public int getKilledEnemies() {
        return killedEnemies;
    }

    public void setKilledEnemies(int killedEnemies) {
        this.killedEnemies = killedEnemies;
    }

    public MyFrame getInitialMyFrame() {
        return initialMyFrame;
    }

    public String getID() {
        return ID;
    }
}