package view.game;

import controller.GameManager;
import controller.listeners.GameMouseListener;
import controller.listeners.GameMouseMotionListener;
import controller.listeners.InputListener;
import view.game.enemies.EnemyView;
import view.game.enemies.archmire.AoEView;
import view.game.enemies.archmire.ArchmireView;
import view.game.enemies.black_orb.BlackOrbLaserView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private int width;
    private int height;
    private int x;
    private int y;
    private JFrame frame;
    private String ID;

    public GamePanel(int x, int y, int width, int height, String ID) {
        this.x = x;
        this.y = y;
        this.width = width+10;
        this.height = height+30;
        this.ID = ID;
        addFrame();
        addPanel();
        new InputListener(this);
        addMouseListener(GameMouseListener.getINSTANCE());
        addMouseMotionListener(GameMouseMotionListener.getINSTANCE());
    }
    private void addFrame() {
        frame = new JFrame();
        frame.setBounds(x,y,width,height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLayout(null);
    }
    private void addPanel() {
        setBounds(x, y, width, height);
        setVisible(true);
        setFocusable(true);
        requestFocus();
        requestFocusInWindow();
        setLayout(null);
        setBackground(Color.BLACK);
        frame.setContentPane(this);
    }
    @Override
    public int getWidth() {
        return width;
    }


    @Override
    public int getHeight() {
        return height;
    }

    public void update() {
        revalidate();
        repaint();
    }
    public void update(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width+15;
        this.height = height+35;
        setBounds(0, 0, this.width, this.height);
        frame.setBounds(x, y, this.width, this.height);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawLasers(g);
        drawAoEs(g);
        drawArchmires(g);
        drawEnemies(g);
        drawCollectives(g);
        drawEpsilon(g);
        drawBullets(g);
    }
    private void drawEpsilon(Graphics g) {
        EpsilonView epsilon = GameManager.getINSTANCE().getGameView().getEpsilonView();
        g.drawImage(epsilon.getImage(), epsilon.getX()-x, epsilon.getY()-y, epsilon.getRadius()*2,
                epsilon.getRadius()*2, null);
    }
    private void drawAoEs(Graphics g) {
        ArrayList<AoEView> aoEViews = GameManager.getINSTANCE().getGameView().getAoEViews();
        for (int i = 0; i < aoEViews.size(); i++) {
            AoEView aoEView = aoEViews.get(i);
            g.drawImage(aoEView.getRotatedImage(), aoEView.getX()-x, aoEView.getY()-y,
                    aoEView.getWidth(), aoEView.getHeight(), null);
        }
    }
    private void drawArchmires(Graphics g) {
        ArrayList<ArchmireView> archmires = GameManager.getINSTANCE().getGameView().getArchmires();
        for (int i = 0; i < archmires.size(); i++) {
            ArchmireView archmireView = archmires.get(i);
            g.drawImage(archmireView.getRotatedImage(), archmireView.getX()-x, archmireView.getY()-y,
                    archmireView.getWidth(), archmireView.getHeight(), null);
        }
    }
    private void drawEnemies(Graphics g) {
        ArrayList<EnemyView> enemyViews = GameManager.getINSTANCE().getGameView().getEnemies();
        for (int i = 0; i < enemyViews.size(); i++) {
            EnemyView enemyView = enemyViews.get(i);
            g.drawImage(enemyView.getRotatedImage(), enemyView.getX()-x, enemyView.getY()-y,
                    enemyView.getWidth(), enemyView.getHeight(), null);
        }
    }
    private void drawBullets(Graphics g) {
        ArrayList<BulletView> bulletViews = GameManager.getINSTANCE().getGameView().getBullets();
        for (int i = 0; i < bulletViews.size(); i++) {
            BulletView bulletView = bulletViews.get(i);
            g.drawImage(bulletView.getImage(), bulletView.getX()-x, bulletView.getY()-y,
                    bulletView.getWidth(), bulletView.getHeight(), null);
        }
    }
    private void drawLasers(Graphics g) {
        ArrayList<BlackOrbLaserView> lasers = GameManager.getINSTANCE().getGameView().getLaserViews();
        for (int i = 0; i < lasers.size(); i++) {
            BlackOrbLaserView laser = lasers.get(i);
            BlackOrbLaserView.draw(laser.getX1()-x, laser.getY1()-y, laser.getX2()-x, laser.getY2()-y, g);
        }
    }
    private void drawCollectives(Graphics g) {
        ArrayList<CollectibleView> collectibleViews = GameManager.getINSTANCE().getGameView().getCollectiveViews();
        for (int i = 0; i < collectibleViews.size(); i++) {
            CollectibleView collective = collectibleViews.get(i);
            g.drawImage(collective.getImage(), collective.getX()-x, collective.getY()-y, collective.getWidth(),
                    collective.getHeight(), null);
        }
    }

    public JFrame getFrame() {
        return frame;
    }
}
