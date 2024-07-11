package model;

import collision.Collidable;
import collision.Impactable;
import controller.*;
import controller.audio.AudioController;
import controller.listeners.InputListener;
import model.game.GameModel;
import movement.Direction;
import movement.Movable;
import movement.Point;
import movement.RotatablePoint;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EpsilonModel implements Collidable, Movable, Impactable {
    private Timer upTimer;
    private Timer downTimer;
    private Timer leftTimer;
    private Timer rightTimer;
    private InputListener inputListener;
    private int radius;
    private movement.Point center;
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

    public EpsilonModel() {
        setCenter(Constants.FRAME_SIZE.width/2, Constants.FRAME_SIZE.height/2);
        radius = Constants.EPSILON_RADIUS;
        addMoveTimers();
        inputListener = new InputListener(this);
        HP = 100;
        velocity = new Point(0,0);
        acceleration = new Point(0,0);
        accelerationRate = new Point(0,0);
        vertexes = new ArrayList<>();
        frame = new Frame(700,700,0,0,false,false);
        this.sensitivity = GameManager.getSensitivity();
    }


    public void setInCenter() {
        setCenter((int)GameManager.getINSTANCE().getGameModel().getWidth()/2 - radius,
                (int)GameManager.getINSTANCE().getGameModel().getHeight()/2 - radius);
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
        upTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getY()-0.5*sensitivity >= 0) {
                    setCenter(getX(), getY()-(int)(0.5*sensitivity));
                }
            }
        });
        downTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getY() + 0.5*sensitivity < GameManager.getINSTANCE().getGameModel().getHeight()-25) {
                    setCenter(getX(), getY()+(int)(0.5*sensitivity));
                }
            }
        });
        rightTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getX() + 0.5*sensitivity < GameManager.getINSTANCE().getGameModel().getWidth() - 25) {
                    setCenter(getX() + (int) (0.5 * sensitivity), getY());
                }
            }
        });
        leftTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getX()-0.5*sensitivity >= 0) {
                    setCenter(getX()-(int)(0.5*sensitivity), getY());
                }
            }
        });
    }

    public int getX() {
        return (int)center.getX()-12;
    }

    @Override
    public boolean isImpact() {
        return impact;
    }

    public int getY() {
        return (int)center.getY()-12;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(int x, int y) {
        this.center = new Point(x+12, y+12);
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
        GameModel gameModel = GameManager.getINSTANCE().getGameModel();
        if (getX() <= 0) {
            setCenter(10,getY());
        }
        else if (getX() > gameModel.getWidth()-24) {
            setCenter((int)gameModel.getWidth()-34,getY());
        }
        if (getY() <= 0) {
            setCenter(getX(),10);
        }
        else if (getY() > gameModel.getHeight()-24) {
            setCenter(getX(),(int)gameModel.getHeight()-34);
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
            RotatablePoint vertex = new RotatablePoint(center.getX(), center.getY(), angle*i-Math.PI/2, Constants.EPSILON_RADIUS);
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
        if (radius*2 < GameManager.getINSTANCE().getGameModel().getWidth()) {
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
        BulletModel bulletModel = new BulletModel(getCenter(), new Point(x, y),
                Constants.EPSILON_RADIUS, 5, false);
        GameManager.getINSTANCE().getGameModel().getBullets().add(bulletModel);
        Controller.addBulletView(bulletModel);
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
}