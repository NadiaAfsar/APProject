package model;

import collision.Collidable;
import controller.Constants;
import controller.Controller;
import controller.InputListener;
import model.enemies.SquarantineModel;
import model.game.GameModel;
import movement.Direction;
import movement.Point;
import movement.RotatablePoint;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EpsilonModel implements Collidable {
    private int x;
    private int y;
    private Timer upTimer;
    private Timer downTimer;
    private Timer leftTimer;
    private Timer rightTimer;
    private InputListener inputListener;
    private int radius;
    public static EpsilonModel INSTANCE;
    private movement.Point center;
    private int HP;
    private int XP;
    private Point velocity;
    private Point acceleration;
    private Point accelerationRate;
    private ArrayList<RotatablePoint> vertexes;
    private int sensitivity;

    public EpsilonModel() {
        x = Constants.FRAME_SIZE.width/2;
        y = Constants.FRAME_SIZE.height/2;
        radius = Constants.EPSILON_RADIUS;
        addMoveTimers();
        inputListener = new InputListener(this);
        setCenter(x,y);
        HP = 100;
        velocity = new Point(0,0);
        acceleration = new Point(0,0);
        accelerationRate = new Point(0,0);
        vertexes = new ArrayList<>();
        this.sensitivity = GameManager.getSensitivity();
    }


    public void setInCenter() {
        x = GameModel.getINSTANCE().getWidth()/2 - radius;
        y = GameModel.getINSTANCE().getHeight()/2 - radius;
        setCenter(x,y);
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
                if (y-0.5*sensitivity >= 0) {
                    y -= 0.5*sensitivity;
                    setCenter(x, y);
                }
            }
        });
        downTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (y + 0.5*sensitivity < GameModel.getINSTANCE().getHeight()-25) {
                    y += 0.5*sensitivity;
                    setCenter(x, y);
                }
            }
        });
        rightTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (x + 0.5*sensitivity < GameModel.getINSTANCE().getWidth() - 25) {
                    x += 0.5*sensitivity;
                setCenter(x, y);
            }
            }
        });
        leftTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (x-0.5*sensitivity >= 0) {
                    x -= 0.5*sensitivity;
                    setCenter(x, y);
                }
            }
        });
    }

    public static EpsilonModel getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new EpsilonModel();
        }
        return INSTANCE;
    }

    public int getX() {
        return x;
    }
    public void move() {
        acceleration.setX(acceleration.getX() + accelerationRate.getX() /Constants.UPS);
        acceleration.setY(acceleration.getY() + accelerationRate.getY() /Constants.UPS);
        velocity.setX(velocity.getX()+acceleration.getX()*0.1/ Constants.UPS);
        velocity.setY(velocity.getY()+acceleration.getY()*0.1/ Constants.UPS);
        GameModel gameModel = GameModel.getINSTANCE();
        if (x+velocity.getX() >= 0 && x+velocity.getX() <= gameModel.getWidth()-24 && y+velocity.getY() >= 0 && y+velocity.getY() <= gameModel.getHeight()-24) {
            center = new Point(center.getX() + velocity.getX(), center.getY() + velocity.getY());
            x = (int) center.getX() - 12;
            y = (int) center.getY() - 12;
        }
        if ((velocity.getX() * accelerationRate.getX() >= 0 || velocity.getY() * accelerationRate.getY() >= 0)) {
            velocity.setX(0);
            velocity.setY(0);
            acceleration = new Point(0, 0);
            accelerationRate = new Point(0, 0);
        }
        moveVertexes();
    }

    public int getY() {
        return y;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(int x, int y) {
        this.center = new Point(x+12, y+12);
    }

    @Override
    public void impact(RotatablePoint collisionPoint, Collidable collidable) {
        if (collidable instanceof SquarantineModel) {
            HP -= 6+GameModel.getINSTANCE().getEnemyPower();
        }
        else {
            HP -= 10+GameModel.getINSTANCE().getEnemyPower();
        }
        if (HP <= 0) {
            Controller.addGameOverSound();
            Controller.gameOver(XP);
        }
    }
    public void setImpactAcceleration(Direction direction, double distance) {
        velocity = new Point(0,0);
        center.setX(center.getX() - direction.getDx() * distance);
        center.setY(center.getY() - direction.getDy() * distance);
        x = (int)center.getX()-12;
        y = (int)center.getY()-12;
        setInFrame();
        acceleration.setX(-direction.getDx()*distance*1.5);
        acceleration.setY(-direction.getDy()*distance*1.5);
        accelerationRate.setX(direction.getDx()*distance*5/3);
        accelerationRate.setY(direction.getDy()*distance*5/3);
    }

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }
    public void setInFrame() {
        GameModel gameModel = GameModel.getINSTANCE();
        if (x < 0) {
            x = 0;
            setCenter(x,y);
        }
        else if (x > gameModel.getWidth()-24) {
            x = gameModel.getWidth()-24;
            setCenter(x,y);
        }
        if (y < 0) {
            y = 0;
            setCenter(x,y);
        }
        if (y > gameModel.getHeight()-24) {
            y = gameModel.getHeight()-24;
            setCenter(x,y);
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

    public void setVertexes(ArrayList<RotatablePoint> vertexes) {
        this.vertexes = vertexes;
    }
    private void moveVertexes() {
        for (int i = 0; i < vertexes.size(); i++) {
            RotatablePoint vertex = vertexes.get(i);
            vertex.setX(center.getX());
            vertex.setY(center.getY());
        }
    }
    private double getAngle(Direction direction) {
        double angle = Math.atan(direction.getDy()/direction.getDx());
        if (direction.getDx() < 0) {
            angle += Math.PI;
        }
        return angle;
    }
    public void rotateVertexes(int x, int y) {
        Direction direction = new Direction(new Point(getCenter().getX(),getCenter().getY()), new Point(x,y));
        double angle = getAngle(direction)+Math.PI/2;
        for (int i = 0; i < vertexes.size(); i++) {
            RotatablePoint vertex = vertexes.get(i);
            double a = angle+vertex.getInitialAngle();
            vertex.setAngle(a);
        }
    }
    public void increaseSize() {
        if (radius*2 < GameModel.getINSTANCE().getWidth()) {
            radius += 2;
            if (x >= 0) {
                x -= 2;
            }
            if (y >= 0) {
                y -= 2;
            }
        }
        else {
            GameModel.getINSTANCE().setFinished(true);
        }
    }

    public int getRadius() {
        return radius;
    }
}