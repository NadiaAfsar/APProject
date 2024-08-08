package view.game;

import controller.Controller;
import controller.game_manager.GameManager;
import controller.game_manager.Monomachia;
import model.game.skills.Skill;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameView {
    private Map<String, EntityView> enemiesMap;
    private ArrayList<String> enemies;
    private Map<String, EntityView> aoEViewMap;
    private ArrayList<String> aoEViews;
    private ArrayList<String> archmires;
    private Map<String, BulletView> bulletsMap;
    private ArrayList<String> bullets;
    private Map<String, EntityView> collectibleMap;
    private Map<String, EntityView> epsilonsMap;
    private ArrayList<String> epsilons;
    private ArrayList<String> laserViews;
    private Map<String, BlackOrbLaserView> laserViewMap;
    private ArrayList<String> collectibleViews;
    private ArrayList<String> necropickAnnouncements;
    private Map<String, EntityView> necropickAnnouncementMap;
    private CheckPointView portal;
    private Map<String, ArrayList<EntityView>> epsilonsVertices;
    private Map<String, ArrayList<EntityView>> epsilonsCerberus;
    private ArrayList<String> gamePanels;
    private Map<String, GamePanel> gamePanelMap;
    private GameManager gameManager;
    private HUI hui;
    public GameView(GameManager gameManager) {
        this.gameManager = gameManager;
        enemiesMap = new HashMap<>();
        bulletsMap = new HashMap<>();
        collectibleMap = new HashMap<>();
        collectibleViews = new ArrayList<>();
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        archmires = new ArrayList<>();
        aoEViewMap = new HashMap<>();
        aoEViews = new ArrayList<>();
        epsilons = new ArrayList<>();
        epsilonsMap = new HashMap<>();
        laserViews = new ArrayList<>();
        laserViewMap = new HashMap<>();
        epsilonsVertices = new HashMap<>();
        necropickAnnouncements = new ArrayList<>();
        necropickAnnouncementMap = new HashMap<>();
        epsilonsCerberus = new HashMap<>();
        gamePanels = new ArrayList<>();
        gamePanelMap = new HashMap<>();
        hui = new HUI(gameManager);
    }

    public void update(int HP, int XP, int wave, long time, Skill skill, int cHP, int cXP) {
        if (hui.isVisible()) {
            hui.updateHP(HP);
            hui.updateXP(XP);
            hui.updateWave(wave);
            hui.updateTime(time);
            hui.updateSkill(skill);
            if (gameManager instanceof Monomachia){
                hui.updateCHP(cHP);
                hui.updateCXP(cXP);
            }
        }
    }

    public void addEnemyView(EntityView entityView) {
        enemies.add(entityView.getID());
        enemiesMap.put(entityView.getID(), entityView);
    }
    public void addBulletView(BulletView bulletView) {
        bullets.add(bulletView.getID());
        bulletsMap.put(bulletView.getID(), bulletView);
    }
    public void addCollectivesView(EntityView collectibleView) {
        collectibleViews.add(collectibleView.getID());
        collectibleMap.put(collectibleView.getID(), collectibleView);
    }
    public void addArchmireView(EntityView archmireView) {
        archmires.add(archmireView.getID());
        enemiesMap.put(archmireView.getID(), archmireView);
    }
    public void addAoEView(EntityView aoEView) {
        aoEViews.add(aoEView.getID());
        aoEViewMap.put(aoEView.getID(), aoEView);
    }
    public void removeEnemyView(String ID) {
        enemies.remove(ID);
        enemiesMap.put(ID, null);
    }
    public void removeBulletView(String ID) {
        bullets.remove(ID);
        bulletsMap.put(ID, null);
    }
    public void removeCollectibleView(String ID) {
        collectibleViews.remove(ID);
        collectibleMap.put(ID, null);
    }
    public void removeArchmireView(String ID) {
        archmires.remove(ID);
        enemiesMap.put(ID, null);
    }
    public void removeAoEView(String ID) {
        aoEViews.remove(ID);
        aoEViewMap.put(ID, null);
    }
    public void addPanel(int x, int y, int width, int height, String ID) {
        GamePanel panel = new GamePanel(x, y, width, height, ID, gameManager);
        gamePanels.add(ID);
        gamePanelMap.put(ID, panel);
    }
    public void addGamePanel(int x, int y, int width, int height, String ID) {
        GamePanel gamePanel = new GamePanel(x, y, width, height, ID, gameManager);
        gamePanels.add(ID);
        gamePanelMap.put(ID, gamePanel);
    }
    public void addLaser(BlackOrbLaserView laser) {
        laserViews.add(laser.getID());
        laserViewMap.put(laser.getID(), laser);
    }
    public void removeLaser(String ID) {
        laserViews.remove(ID);
        laserViewMap.put(ID, null);
    }
    public Map<String, BlackOrbLaserView> getLaserViewMap() {
        return laserViewMap;
    }
    public void updatePanels() {
        for (int i = 0; i < gamePanels.size(); i++) {
            gamePanelMap.get(gamePanels.get(i)).revalidate();
            gamePanelMap.get(gamePanels.get(i)).repaint();
        }
    }

    public void addNecropickAnnouncement(EntityView necropickAnnouncement) {
        necropickAnnouncements.add(necropickAnnouncement.getID());
        necropickAnnouncementMap.put(necropickAnnouncement.getID(), necropickAnnouncement);
    }
    public void removeNecropickAnnouncement(String ID){
        necropickAnnouncements.remove(ID);
        necropickAnnouncementMap.put(ID, null);
    }
    public void removeFrames(){
        for (int i = 0; i < gamePanels.size(); i++){
            gamePanelMap.get(gamePanels.get(i)).getFrame().dispose();
        }
    }
        public void addPortal(Point point){
        portal = new CheckPointView(point);
    }
    public void removePortal(){
        portal = null;
    }

    public CheckPointView getPortal() {
        return portal;
    }

    public Map<String, EntityView> getEnemiesMap() {
        return enemiesMap;
    }

    public ArrayList<String> getEnemies() {
        return enemies;
    }
    public Map<String, EntityView> getAoEViewMap() {
        return aoEViewMap;
    }

    public ArrayList<String> getAoEViews() {
        return aoEViews;
    }

    public ArrayList<String> getArchmires() {
        return archmires;
    }
    public Map<String, BulletView> getBulletsMap() {
        return bulletsMap;
    }
    public ArrayList<String> getBullets() {
        return bullets;
    }
    public Map<String, EntityView> getCollectibleMap() {
        return collectibleMap;
    }
    public Map<String, EntityView> getEpsilonsMap() {
        return epsilonsMap;
    }

    public ArrayList<String> getEpsilons() {
        return epsilons;
    }

    public ArrayList<String> getLaserViews() {
        return laserViews;
    }

    public ArrayList<String> getCollectibleViews() {
        return collectibleViews;
    }
    public ArrayList<String> getNecropickAnnouncements() {
        return necropickAnnouncements;
    }


    public Map<String, EntityView> getNecropickAnnouncementMap() {
        return necropickAnnouncementMap;
    }

    public void addEpsilonView(EntityView epsilon){
        epsilons.add(epsilon.getID());
        epsilonsMap.put(epsilon.getID(), epsilon);
        epsilonsVertices.put(epsilon.getID(), new ArrayList<>());
        epsilonsCerberus.put(epsilon.getID(), new ArrayList<>());
    }
    public void addVertex(EntityView vertex, String ID){
        epsilonsVertices.get(ID).add(vertex);
    }
    public void removeVertexes(String ID){
        epsilonsVertices.put(ID, new ArrayList<>());
    }
    public void addCerberus(EntityView cerberus, String ID){
        epsilonsCerberus.get(ID).add(cerberus);
    }
    public void removeCerberus(String ID){
        epsilonsCerberus.put(ID, new ArrayList<>());
    }
    public void updateVertexes(ArrayList<RotatablePoint> vertexes, String ID) {
        synchronized (Controller.epsilonLock) {
            for (int i = 0; i < vertexes.size(); i++) {
                RotatablePoint vertex = vertexes.get(i);
                EntityView epsilonVertex = epsilonsVertices.get(ID).get(i);
                epsilonVertex.update(new Point((int) vertex.getRotatedX()-3, (int) vertex.getRotatedY()-3), 0);
            }
        }
    }
    public void updateCerberuses(ArrayList<RotatablePoint> cerberuces, String ID) {
        synchronized (Controller.cerberusLock) {
            for (int i = 0; i < cerberuces.size(); i++) {
                RotatablePoint vertex = cerberuces.get(i);
                EntityView cerberus = epsilonsCerberus.get(ID).get(i);
                cerberus.update(new Point((int) vertex.getRotatedX()-10, (int) vertex.getRotatedY()-10), 0);
            }
        }
    }

    public Map<String, ArrayList<EntityView>> getEpsilonsVertices() {
        return epsilonsVertices;
    }

    public Map<String, ArrayList<EntityView>> getEpsilonsCerberus() {
        return epsilonsCerberus;
    }
    public HUI getHui(){
        return hui;
    }

    public Map<String, GamePanel> getGamePanelMap() {
        return gamePanelMap;
    }
}