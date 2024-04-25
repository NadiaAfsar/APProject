package model;

import collision.Collidable;
import controller.Constants;
import controller.Controller;
import controller.InputListener;
import model.enemies.SquarantineModel;
import movement.Direction;
import movement.Point;
import movement.RotatablePoint;

import javax.swing.*;
import java.awt.event.*;

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
                if (y-1 >= 0) {
                    y -= 1;
                    setCenter(x, y);
                }
            }
        });
        downTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (y + 1 < GameModel.getINSTANCE().getHeight()-25) {
                    y += 1;
                    setCenter(x, y);
                }
            }
        });
        rightTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (x + 1 < GameModel.getINSTANCE().getWidth() - 25) {
                    x += 1;
                setCenter(x, y);
            }
            }
        });
        leftTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (x-1 >= 0) {
                    x -= 1;
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
            HP -= 6;
        }
        else {
            HP -= 10;
        }
        if (HP <= 0) {
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
}