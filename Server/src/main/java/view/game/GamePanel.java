package view.game;

import controller.GameManager;
import controller.listeners.InputListener;
import view.game.enemies.EnemyView;
import view.game.enemies.archmire.ArchmireView;
import view.game.enemies.black_orb.BlackOrbLaserView;
import view.game.enemies.necropick.NecropickAnnouncement;
import view.game.epsilon.EpsilonView;
import view.game.epsilon.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

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
        drawEpsilon(g);
        drawVertexes(g);
        drawBullets(g);
    }
    private void drawEpsilon(Graphics g) {
        EpsilonView epsilon = gameManager.getGameView().getEpsilonView();
        g.drawImage(epsilon.getImage(), epsilon.getX()-x, epsilon.getY()-y, epsilon.getRadius()*2,
                epsilon.getRadius()*2, null);
    }
    private void drawAoEs(Graphics g) {
        ArrayList<EnemyView> aoEViews = gameManager.getGameView().getAoEViews();
        for (int i = 0; i < aoEViews.size(); i++) {
            EnemyView aoEView = aoEViews.get(i);
            g.drawImage(aoEView.getImage(), aoEView.getX()-x, aoEView.getY()-y,
                    aoEView.getWidth(), aoEView.getHeight(), null);
        }
    }
    private void drawArchmires(Graphics g) {
        ArrayList<ArchmireView> archmires = gameManager.getGameView().getArchmires();
        for (int i = 0; i < archmires.size(); i++) {
            ArchmireView archmireView = archmires.get(i);
            g.drawImage(archmireView.getRotatedImage(), archmireView.getX()-x, archmireView.getY()-y,
                    archmireView.getWidth(), archmireView.getHeight(), null);
        }
    }
    private void drawEnemies(Graphics g) {
        ArrayList<EnemyView> enemyViews = gameManager.getGameView().getEnemies();
        for (int i = 0; i < enemyViews.size(); i++) {
            EnemyView enemyView = enemyViews.get(i);
            g.drawImage(enemyView.getRotatedImage(), enemyView.getX()-x, enemyView.getY()-y,
                    enemyView.getWidth(), enemyView.getHeight(), null);
        }
    }
    private void drawBullets(Graphics g) {
        ArrayList<BulletView> bulletViews = gameManager.getGameView().getBullets();
        for (int i = 0; i < bulletViews.size(); i++) {
            BulletView bulletView = bulletViews.get(i);
            g.drawImage(bulletView.getImage(), bulletView.getX()-x, bulletView.getY()-y,
                    bulletView.getWidth(), bulletView.getHeight(), null);
        }
    }
    private void drawLasers(Graphics g) {
        ArrayList<BlackOrbLaserView> lasers = gameManager.getGameView().getLaserViews();
        for (int i = 0; i < lasers.size(); i++) {
            BlackOrbLaserView laser = lasers.get(i);
            BlackOrbLaserView.draw(laser.getX1()-x, laser.getY1()-y, laser.getX2()-x, laser.getY2()-y, g);
        }
    }
    private void drawCollectibles(Graphics g) {
        ArrayList<CollectibleView> collectibleViews = gameManager.getGameView().getCollectiveViews();
        for (int i = 0; i < collectibleViews.size(); i++) {
            CollectibleView collective = collectibleViews.get(i);
            g.drawImage(collective.getImage(), collective.getX()-x, collective.getY()-y, collective.getWidth(),
                    collective.getHeight(), null);
        }
    }
    private void drawNecropickAnnouncements(Graphics g){
        ArrayList<NecropickAnnouncement> neropicks = gameManager.getGameView().getNecropickAnnouncements();
        for (int i = 0; i < neropicks.size(); i++){
            NecropickAnnouncement necropick = neropicks.get(i);
            g.drawImage(necropick.getImage(), necropick.getX()-x, necropick.getY()-y, necropick.getWidth(),
                    necropick.getWidth(), null);
        }
    }
    private void drawVertexes(Graphics g){
        ArrayList<Vertex> vertices = gameManager.getGameView().getEpsilonView().getVertexes();
        for (int i = 0; i < vertices.size(); i++){
            Vertex vertex = vertices.get(i);
            g.drawImage(vertex.getImage(), vertex.getX()-x, vertex.getY()-y, vertex.getWidth(), vertex.getHeight(), null);
        }
    }
    private void drawCerberus(Graphics g){
        ArrayList<Vertex> cerberuses = gameManager.getGameView().getEpsilonView().getCerberuses();
        for (int i = 0; i < cerberuses.size(); i++){
            Vertex cerberus = cerberuses.get(i);
            g.drawImage(cerberus.getImage(), cerberus.getX()-x, cerberus.getY()-y, cerberus.getWidth(),
                    cerberus.getHeight(), null);
        }
    }
    private void drawPortal(Graphics g){
        CheckPointView portal = gameManager.getGameView().getPortal();
        if (portal != null){
            g.drawImage(portal.getImage(), portal.getX()-x, portal.getY()-y, portal.getWidth(), portal.getHeight(), null);
        }
    }

    public JFrame getFrame() {
        return frame;
    }
}
