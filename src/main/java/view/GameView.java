package view;

import view.enemies.EnemyView;

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
    private JLabel HP;
    private JLabel collective;
    private JLabel wave;
    private JLabel time;
    private long hour;
    private long minute;
    private long second;
    private final long startTime;
    private EpsilonView epsilonView;
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

    public void update(int x, int y, int width, int height, int HP, int XP, int wave) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setBounds(x,y,width,height);
        if (this.HP != null) {
            updateHP(HP);
            updateXP(XP);
            updateWave(wave-1);
            updateTime();
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
    private String getElapsedTime() {
        setTime();
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
    private void setTime() {
        long currentTime = System.currentTimeMillis();
        long time = (currentTime-startTime)/1000;
        hour = time / 3600;
        time %= 3600;
        minute = time / 60;
        second = time % 60;
    }
    private void setHP() {
        HP = new JLabel("HP: "+100);
        HP.setBounds(5,5,80,10);
        HP.setForeground(Color.WHITE);
        panel.add(HP);
    }
    private void setXP() {
        collective = new JLabel("collective: "+0);
        collective.setBounds(85,5,80,10);
        collective.setForeground(Color.WHITE);
        panel.add(collective);
    }
    private void setWave() {
        wave = new JLabel("1/3");
        wave.setBounds(165,5,40,10);
        wave.setForeground(Color.WHITE);
        panel.add(wave);
    }
    private void setTimeJLabel() {
        time = new JLabel("00:00:00");
        time.setBounds(210,5,50,10);
        time.setForeground(Color.WHITE);
        panel.add(time);
    }
    public void setHUI() {
        setHP();
        setXP();
        setWave();
        setTimeJLabel();
    }
    private void updateHP(int hp) {
        HP.setText("HP: "+hp);
    }
    private void updateXP(int xp) {
        collective.setText("collective: "+xp);
    }
    private void updateWave(int wave) {
        this.wave.setText(wave+"/3");
    }
    private void updateTime() {
        time.setText(getElapsedTime());
    }
    public void destroy(int width, int height) {
        setBounds(x,y,width,height);
    }

    public EpsilonView getEpsilonView() {
        return epsilonView;
    }
}