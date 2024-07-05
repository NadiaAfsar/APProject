package view;

import controller.Constants;
import controller.GameManager;

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
    private JPanel panel;
    private int width;
    private int height;
    private int x;
    private int y;
    private int xp;
    public GameOver(int xp) {
        this.xp = xp;
        width = 700;
        height = 500;
        x = (int)Constants.FRAME_SIZE.getWidth()/2-350;
        y = (int)Constants.FRAME_SIZE.getHeight()/2-250;
        addFrame();
        addPanel();
        try {
            setIconImage(new ImageIcon(ImageIO.read(new File("src/main/resources/icon.png"))).getImage());
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        addGameOver();
        addXP();
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

    private void addPanel() {
        panel = new JPanel();
        panel.setBounds(x, y, width, height);
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
        gameOver.setFont(new Font("Elephant", Font.BOLD, 50));
        gameOver.setBounds(180,20,500,200);
        gameOver.setForeground(Color.RED);
        panel.add(gameOver);
    }
    private void addXP() {
        collectedXP = new JLabel("Collected XP: "+xp);
        collectedXP.setFont(new Font("Elephant", Font.BOLD, 36));
        collectedXP.setBounds(180,100,500,200);
        panel.add(collectedXP);
    }
    private void addMainMenu() {
        mainMenu = new JButton("Main Menu");
        mainMenu.setFont(new Font("Elephant", Font.BOLD, 30));
        mainMenu.setBackground(Color.WHITE);
        mainMenu.setBounds(200,300,300,100);
        mainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameManager.getINSTANCE().getGameView().dispose();
                GameManager.getINSTANCE().getGameFrame().setVisible(true);
                dispose();
            }
        });
        add(mainMenu);
    }
}
