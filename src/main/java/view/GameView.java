package view;

import view.enemies.EnemyView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GameView extends JFrame {
    private JPanel panel;
    private int width;
    private int height;
    private int x;
    private int y;
    public static GameView INSTANCE;
    private ArrayList<EnemyView> enemies;
    public GameView() {
        x = 0;
        y = 0;
        width = 700;
        height = 700;
        addFrame();
        addPanel();
        enemies = new ArrayList<>();
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

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    public void update(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setBounds(x,y,width,height);
    }

    public static GameView getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new GameView();
        }
        return INSTANCE;
    }

    public ArrayList<EnemyView> getEnemies() {
        return enemies;
    }
}