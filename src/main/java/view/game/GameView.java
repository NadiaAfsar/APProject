package view.game;

import model.skills.Skill;
import view.game.enemies.EnemyView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameView extends JFrame {
    private JPanel panel;
    private int width;
    private int height;
    private int x;
    private int y;
    private Map<String, EnemyView> enemies;
    private Map<String, BulletView> bullets;
    private Map<String, CollectiveView> Collectives;
    private final long startTime;
    private EpsilonView epsilonView;
    private HUI hui;
    public GameView() {
        x = 0;
        y = 0;
        width = 700;
        height = 700;
        addFrame();
        addPanel();
        enemies = new HashMap<>();
        bullets = new HashMap<>();
        Collectives = new HashMap<>();
        try {
            setIconImage(new ImageIcon(ImageIO.read(new File("src/main/resources/icon.png"))).getImage());
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        startTime = System.currentTimeMillis();
        epsilonView = new EpsilonView(this);
        add(epsilonView);
        hui = new HUI();
        setFocusable(true);
        requestFocus();
        requestFocusInWindow();
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


    @Override
    public int getHeight() {
        return height;
    }

    public void update(int x, int y, int width, int height, int HP, int XP, int wave, long time, Skill skill) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setBounds(x,y,width,height);
        if (hui.isVisible()) {
            hui.updateHP(HP);
            hui.updateXP(XP);
            hui.updateWave(wave-1);
            hui.updateTime(time);
            hui.updateSkill(skill);
        }
    }

    public Map<String, EnemyView> getEnemies() {
        return enemies;
    }

    public Map<String, BulletView> getBullets() {
        return bullets;
    }

    public void update() {
        panel.revalidate();
        panel.repaint();
    }

    public Map<String, CollectiveView> getCollectives() {
        return Collectives;
    }

    public void destroy(int width, int height) {
        setBounds(x,y,width,height);
    }

    public EpsilonView getEpsilonView() {
        return epsilonView;
    }

    public HUI getHui() {
        return hui;
    }
}