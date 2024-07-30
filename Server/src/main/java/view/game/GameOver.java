package view.game;

import controller.GameManager;
import controller.save.Configs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GameOver extends JFrame {
    private JLabel collectedXP;
    private JLabel gameOver;
    private JButton mainMenu;
    private JLabel time;
    private JLabel bullets;
    private JLabel successfulBullets;
    private JLabel enemies;
    private JPanel panel;
    private int width;
    private int height;
    private int x;
    private int y;
    private int xp;
    public GameOver(int xp, long time, int bullets, int successfulBullets, int enemies) {
        this.xp = xp;
        width = 700;
        height = 600;
        x = (int) Configs.FRAME_SIZE.getWidth()/2-350;
        y = (int)Configs.FRAME_SIZE.getHeight()/2-250;
        addFrame();
        addPanel();
        addGameOver();
        addXP();
        addTime(time);
        addBullets(bullets);
        addSBullets(successfulBullets);
        addEnemies(enemies);
        addMainMenu();
    }
    private void addFrame() {
        setBounds(x,y,width,height);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setVisible(true);
    }
    private void addBullets(int bullets) {
        collectedXP = new JLabel("Bullets: "+bullets);
        collectedXP.setFont(new Font("Elephant", Font.BOLD, 20));
        collectedXP.setBounds(100,200,500,200);
        panel.add(collectedXP);
    }
    private void addSBullets(int bullets) {
        collectedXP = new JLabel("Successful Bullets: "+bullets);
        collectedXP.setFont(new Font("Elephant", Font.BOLD, 20));
        collectedXP.setBounds(400,200,500,200);
        panel.add(collectedXP);
    }
    private void addEnemies(int enemies) {
        collectedXP = new JLabel("Killed Enemies: "+enemies);
        collectedXP.setFont(new Font("Elephant", Font.BOLD, 20));
        collectedXP.setBounds(180,300,500,200);
        panel.add(collectedXP);
    }
    private void addTime(long time) {
        collectedXP = new JLabel("Time: "+getElapsedTime(time));
        collectedXP.setFont(new Font("Elephant", Font.BOLD, 20));
        collectedXP.setBounds(100,100,500,200);
        panel.add(collectedXP);
    }
    private String getElapsedTime(long time1) {
        int hour = (int)time1 / 3600;
        time1 %= 3600;
        int minute = (int)time1 / 60;
        int second = (int)time1 % 60;
        String time = "";
        if (hour < 10) {
            time += 0;
        }
        time += hour+":";
        if (minute < 10) {
            time += 0;
        }
        time += minute+":";
        if (second < 10) {
            time += 0;
        }
        time += second;
        return time;

    }

    private void addPanel() {
        panel = new JPanel();
        panel.setBounds(0, 0, width, height);
        panel.setVisible(true);
        panel.setFocusable(true);
        panel.requestFocus();
        panel.requestFocusInWindow();
        panel.setLayout(null);
        panel.setBackground(Color.BLACK);
        setContentPane(panel);
    }
    private void addGameOver() {
        gameOver = new JLabel("Game Over");
        gameOver.setFont(new Font("Elephant", Font.BOLD, 40));
        gameOver.setBounds(180,0,500,200);
        gameOver.setForeground(Color.RED);
        panel.add(gameOver);
    }
    private void addXP() {
        collectedXP = new JLabel("Collected XP: "+xp);
        collectedXP.setFont(new Font("Elephant", Font.BOLD, 20));
        collectedXP.setBounds(400,100,500,200);
        panel.add(collectedXP);
    }
    private void addMainMenu() {
        mainMenu = new JButton("Main Menu");
        mainMenu.setFont(new Font("Elephant", Font.BOLD, 30));
        mainMenu.setBackground(Color.WHITE);
        mainMenu.setBounds(200,450,300,100);
        mainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //GameManager.getINSTANCE().getGameView().dispose();
                GameManager.getINSTANCE().getGameFrame().setVisible(true);
                dispose();
            }
        });
        add(mainMenu);
    }
}
