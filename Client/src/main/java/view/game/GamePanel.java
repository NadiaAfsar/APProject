package view.game;

import controller.game_manager.GameManager;
import controller.listeners.InputListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Map;

public class GamePanel extends JPanel {
    private int width;
    private int height;
    private int x;
    private int y;
    private JFrame frame;
    private String ID;
    private GameManager gameManager;

    public GamePanel(int x, int y, int width, int height, String ID, GameManager gameManager) {
        this.x = x;
        this.y = y;
        this.width = width+10;
        this.height = height+30;
        this.ID = ID;
        this.gameManager = gameManager;
        addFrame();
        addPanel();
        new InputListener(gameManager, this);
        addMouseListener(gameManager.getGameMouseListener());
        addMouseMotionListener(gameManager.getGameMouseMotionListener());
    }
    private void addFrame() {
        frame = new JFrame();
        frame.setBounds(x,y,width,height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
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
        this.width = width+10;
        this.height = height+35;
        setBounds(0, 0, this.width, this.height);
        frame.setBounds(x, y, this.width, this.height);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCerberus(g);
        drawLasers(g);
        drawAoEs(g);
        drawArchmires(g);
        drawNecropickAnnouncements(g);
        drawEnemies(g);
        drawCollectibles(g);
        drawPortal(g);
        drawEpsilons(g);
        drawVertexes(g);
        drawBullets(g);
    }
    private void drawEpsilons(Graphics g) {
        ArrayList<String> epsilons = gameManager.getGameView().getEpsilons();
        Map<String, EntityView> epsilonMap = gameManager.getGameView().getEpsilonsMap();
        for (int i = 0; i < epsilons.size(); i++) {
            EntityView epsilon = epsilonMap.get(epsilons.get(i));
            g.drawImage(epsilon.getImage(), epsilon.getX() - x, epsilon.getY() - y, epsilon.getWidth(),
                    epsilon.getHeight(), null);
        }
    }
    private void drawAoEs(Graphics g) {
        ArrayList<String> aoEViews = gameManager.getGameView().getAoEViews();
        Map<String, EntityView> aoeMap = gameManager.getGameView().getAoEViewMap();
        for (int i = 0; i < aoEViews.size(); i++) {
            EntityView aoEView = aoeMap.get(aoEViews.get(i));
            g.drawImage(aoEView.getImage(), aoEView.getX()-x, aoEView.getY()-y,
                    aoEView.getWidth(), aoEView.getHeight(), null);
        }
    }
    private void drawArchmires(Graphics g) {
        ArrayList<String> archmires = gameManager.getGameView().getArchmires();
        Map<String, EntityView> archmireMap = gameManager.getGameView().getEnemiesMap();
        for (int i = 0; i < archmires.size(); i++) {
            EntityView archmireView = archmireMap.get(archmires.get(i));
            g.drawImage(archmireView.getImage(), archmireView.getX()-x, archmireView.getY()-y,
                    archmireView.getWidth(), archmireView.getHeight(), null);
        }
    }
    private void drawEnemies(Graphics g) {
        ArrayList<String> enemyViews = gameManager.getGameView().getEnemies();
        Map<String, EntityView> enemiesMap = gameManager.getGameView().getEnemiesMap();
        for (int i = 0; i < enemyViews.size(); i++) {
            EntityView enemyView = enemiesMap.get(enemyViews.get(i));
            g.drawImage(enemyView.getImage(), enemyView.getX()-x, enemyView.getY()-y,
                    enemyView.getWidth(), enemyView.getHeight(), null);
        }
    }
    private void drawBullets(Graphics g) {
        ArrayList<String> bulletViews = gameManager.getGameView().getBullets();
        Map<String, BulletView> bulletViewMap = gameManager.getGameView().getBulletsMap();
        for (int i = 0; i < bulletViews.size(); i++) {
            BulletView bulletView = bulletViewMap.get(bulletViews.get(i));
            g.drawImage(bulletView.getImage(), bulletView.getX()-x, bulletView.getY()-y,
                    bulletView.getWidth(), bulletView.getHeight(), null);
        }
    }
    private void drawLasers(Graphics g) {
        ArrayList<String> lasers = gameManager.getGameView().getLaserViews();
        Map<String, BlackOrbLaserView> laserViewMap = gameManager.getGameView().getLaserViewMap();
        for (int i = 0; i < lasers.size(); i++) {
            BlackOrbLaserView laser = laserViewMap.get(lasers.get(i));
            BlackOrbLaserView.draw(laser.getX1()-x, laser.getY1()-y, laser.getX2()-x, laser.getY2()-y, g);
        }
    }
    private void drawCollectibles(Graphics g) {
        ArrayList<String> collectibleViews = gameManager.getGameView().getCollectibleViews();
        Map<String, EntityView> collectibleMap = gameManager.getGameView().getCollectibleMap();
        for (int i = 0; i < collectibleViews.size(); i++) {
            EntityView collective = collectibleMap.get(collectibleViews.get(i));
            g.drawImage(collective.getImage(), collective.getX()-x, collective.getY()-y, collective.getWidth(),
                    collective.getHeight(), null);
        }
    }
    private void drawNecropickAnnouncements(Graphics g){
        ArrayList<String> neropicks = gameManager.getGameView().getNecropickAnnouncements();
        Map<String, EntityView> necripickMap = gameManager.getGameView().getNecropickAnnouncementMap();
        for (int i = 0; i < neropicks.size(); i++){
            EntityView necropick = necripickMap.get(neropicks.get(i));
            g.drawImage(necropick.getImage(), necropick.getX()-x, necropick.getY()-y, necropick.getWidth(),
                    necropick.getWidth(), null);
        }
    }
    private void drawVertexes(Graphics g){
        for (int i = 0; i < gameManager.getGameView().getEpsilons().size(); i++) {
            ArrayList<EntityView> vertices = gameManager.getGameView().getEpsilonsVertices().get(gameManager.getGameView().getEpsilons().get(i));
            for (int j = 0; j < vertices.size(); j++) {
                EntityView vertex = vertices.get(j);
                g.drawImage(vertex.getImage(), vertex.getX() - x, vertex.getY() - y, vertex.getWidth(), vertex.getHeight(), null);
            }
        }
    }
    private void drawCerberus(Graphics g){
        for (int i = 0; i < gameManager.getGameView().getEpsilons().size(); i++) {
            ArrayList<EntityView> vertices = gameManager.getGameView().getEpsilonsCerberus().get(gameManager.getGameView().getEpsilons().get(i));
            for (int j = 0; j < vertices.size(); j++) {
                EntityView vertex = vertices.get(j);
                g.drawImage(vertex.getImage(), vertex.getX() - x, vertex.getY() - y, vertex.getWidth(), vertex.getHeight(), null);
            }
        }
    }
    private void drawPortal(Graphics g){
        CheckPointView portal = gameManager.getGameView().getPortal();
        if (portal != null){
            g.drawImage(portal.getImage(), portal.getX()-x, portal.getY()-y, portal.getWidth(), portal.getHeight(), null);
        }
    }

    public String getID() {
        return ID;
    }

    public JFrame getFrame() {
        return frame;
    }
}
