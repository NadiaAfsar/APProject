package Model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MainCharacter extends JFrame {
    private JPanel panel;
    private int width;
    private int x;
    private int y;
    private int epsilonX;
    private int epsilonY;
    private JLabel epsilon;
    private Timer timer;
    private Timer upTimer;
    private Timer downTimer;
    private Timer leftTimer;
    private Timer rightTimer;

    public MainCharacter() throws IOException {
        x = 0;
        y = 0;
        width = 700;
        epsilonX = 337;
        epsilonY = 337;
        addFrame();
        addPanel();
        addTimer();
        addMoveTimers();
        addEpsilon();
    }

    private void addFrame() {
        setBounds(x,y,width,width);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setVisible(true);
    }

    private void addPanel() {
        panel = new JPanel();
        panel.setBounds(x, y, width, width);
        panel.setVisible(true);
        panel.setFocusable(true);
        panel.requestFocus();
        panel.requestFocusInWindow();
        panel.setLayout(null);
        panel.setBackground(Color.BLACK);
        setContentPane(panel);
    }

    private void addTimer() {
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decreaseSize();
            }
        });
        timer.start();
    }
    private void decreaseSize() {
        width --;
        x = (700-width)/2;
        y = (700-width)/2;
        epsilonX = width/2-13;
        epsilonY = width/2-13;
        setBounds(x,y,width,width);
        update();
        if (width == 200) {
            timer.stop();
        }
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
                epsilonY -= 1;
                if (epsilonY <= 0) {
                    epsilonY = 0;
                    upTimer.stop();
                }
                update();
            }
        });
        downTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                epsilonY += 1;
                if (epsilonY >= width-25) {
                    epsilonY = width-25;
                    downTimer.stop();
                }
                update();
            }
        });
        rightTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                epsilonX += 1;
                if (epsilonX >= width-25) {
                    epsilonX = width-25;
                    rightTimer.stop();
                }
                update();
            }
        });
        leftTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                epsilonX -= 1;
                if (epsilonX <= 0) {
                    epsilonX = 0;
                    leftTimer.stop();
                }
                update();
            }
        });
    }
    private void addEpsilon() throws IOException {
        epsilon = new JLabel(new ImageIcon(ImageIO.read(new File("pics/epsilon.png"))));
        epsilon.setBounds(epsilonX,epsilonY,25,25);
        add(epsilon);
    }
    private void update() {
        epsilon.setBounds(epsilonX,epsilonY,25,25);
        revalidate();
        repaint();
    }

}