package Model;

import View.Game;
import View.InputListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class Epsilon extends JLabel {
    private Game gamePanel;
    private int x;
    private int y;
    private Timer upTimer;
    private Timer downTimer;
    private Timer leftTimer;
    private Timer rightTimer;
    private InputListener inputListener;

    public Epsilon(Game gamePanel) throws IOException {
        this.gamePanel = gamePanel;
        x = 337;
        y = 337;
        addMoveTimers();
        addEpsilon();
        inputListener = new InputListener(this);
    }


    public void decreaseSize() {
        x = gamePanel.getWidth()/2-13;
        y = gamePanel.getHeight()/2-13;
        update();
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
                if (y <= 0) {
                    y = 0;
                    upTimer.stop();
                }
                update();
            }
        });
        downTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                y += 1;
                if (y >= gamePanel.getHeight()-25) {
                    y = gamePanel.getHeight()-25;
                    downTimer.stop();
                }
                update();
            }
        });
        rightTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                x += 1;
                if (x >= gamePanel.getWidth()-25) {
                    x = gamePanel.getWidth()-25;
                    rightTimer.stop();
                }
                update();
            }
        });
        leftTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                x -= 1;
                if (x <= 0) {
                    x = 0;
                    leftTimer.stop();
                }
                update();
            }
        });
    }
    private void addEpsilon() throws IOException {
        setIcon(new ImageIcon(ImageIO.read(new File("pics/epsilon.png"))));
        setBounds(x,y,25,25);
        gamePanel.add(this);
    }
    private void update() {
        setBounds(x,y,25,25);
        gamePanel.revalidate();
        gamePanel.repaint();
    }


}