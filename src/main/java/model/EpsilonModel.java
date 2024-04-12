package model;

import collision.Collidable;
import controller.Constants;
import controller.InputListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

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
    private Point center;

    public EpsilonModel() {
        x = Constants.FRAME_SIZE.width/2;
        y = Constants.FRAME_SIZE.height/2;
        radius = Constants.EPSILON_RADIUS;
        addMoveTimers();
        inputListener = new InputListener(this);
        setCenter(x,y);
    }


    public void setInCenter() {
        x = GameModel.getINSTANCE().getWidth()/2 - radius;
        y = GameModel.getINSTANCE().getHeight()/2 - radius;
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
                y -= 1;
                setCenter(x,y);
                if (y <= 0) {
                    y = 0;
                    upTimer.stop();
                }
            }
        });
        downTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                y += 1;
                setCenter(x,y);
                if (y >= GameModel.getINSTANCE().getHeight()-25) {
                    y = GameModel.getINSTANCE().getHeight()-25;
                    downTimer.stop();
                }
            }
        });
        rightTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                x += 1;
                setCenter(x,y);
                if (x >= GameModel.getINSTANCE().getWidth()-25) {
                    x = GameModel.getINSTANCE().getWidth()-25;
                    rightTimer.stop();
                }
            }
        });
        leftTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                x -= 1;
                setCenter(x,y);
                if (x <= 0) {
                    x = 0;
                    leftTimer.stop();
                }
            }
        });
    }

    public static EpsilonModel getINSTANCE(){
        if (INSTANCE == null) {
            INSTANCE = new EpsilonModel();
        }
        return INSTANCE;
    }

    public int getX() {
        return x;
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
}