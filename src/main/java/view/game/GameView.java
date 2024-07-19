package view.game;

import controller.GameManager;
import model.skills.Skill;
import view.game.enemies.EnemyView;
import view.game.enemies.archmire.AoEView;
import view.game.enemies.archmire.ArchmireView;
import view.game.enemies.black_orb.BlackOrbLaserView;
import view.game.enemies.necropick.NecropickAnnouncement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameView {
    private Map<String, EnemyView> enemiesMap;
    private ArrayList<EnemyView> enemies;
    private Map<String, AoEView> aoEViewMap;
    private ArrayList<AoEView> aoEViews;
    private ArrayList<ArchmireView> archmires;
    private Map<String, BulletView> bulletsMap;
    private ArrayList<BulletView> bullets;
    private Map<String, CollectibleView> collectibleMap;
    private EpsilonView epsilonView;
    private ArrayList<GamePanel> gamePanels;
    private Map<String, GamePanel> gamePanelMap;
    private ArrayList<BlackOrbLaserView> laserViews;
    private Map<String, BlackOrbLaserView> laserViewMap;
    private ArrayList<CollectibleView> collectibleViews;
    private ArrayList<NecropickAnnouncement> necropickAnnouncements;
    private Map<String, NecropickAnnouncement> necropickAnnouncementMap;
    private HUI hui;
    public GameView() {
        enemiesMap = new HashMap<>();
        bulletsMap = new HashMap<>();
        collectibleMap = new HashMap<>();
        collectibleViews = new ArrayList<>();
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        archmires = new ArrayList<>();
        aoEViewMap = new HashMap<>();
        aoEViews = new ArrayList<>();
        gamePanels = new ArrayList<>();
        gamePanelMap = new HashMap<>();
        epsilonView = new EpsilonView();
        laserViews = new ArrayList<>();
        laserViewMap = new HashMap<>();
        necropickAnnouncements = new ArrayList<>();
        necropickAnnouncementMap = new HashMap<>();
        hui = new HUI();
    }

    public void update(int HP, int XP, int wave, long time, Skill skill) {
        if (hui.isVisible()) {
            hui.updateHP(HP);
            hui.updateXP(XP);
            hui.updateWave(wave-1);
            hui.updateTime(time);
            hui.updateSkill(skill);
        }
    }

    public Map<String, EnemyView> getEnemiesMap() {
        return enemiesMap;
    }

    public Map<String, BulletView> getBulletsMap() {
        return bulletsMap;
    }


    public Map<String, CollectibleView> getCollectibleMap() {
        return collectibleMap;
    }


    public EpsilonView getEpsilonView() {
        return epsilonView;
    }

    public HUI getHui() {
        return hui;
    }

    public ArrayList<EnemyView> getEnemies() {
        return enemies;
    }

    public ArrayList<BulletView> getBullets() {
        return bullets;
    }



    public ArrayList<ArchmireView> getArchmires() {
        return archmires;
    }

    public ArrayList<GamePanel> getGamePanels() {
        return gamePanels;
    }

    public Map<String, AoEView> getAoEViewMap() {
        return aoEViewMap;
    }

    public ArrayList<AoEView> getAoEViews() {
        return aoEViews;
    }

    public Map<String, GamePanel> getGamePanelMap() {
        return gamePanelMap;
    }
    public void addEnemyView(EnemyView enemyView) {
        enemies.add(enemyView);
        enemiesMap.put(enemyView.getID(), enemyView);
    }
    public void addBulletView(BulletView bulletView) {
        bullets.add(bulletView);
        bulletsMap.put(bulletView.getID(), bulletView);
    }
    public void addCollectivesView(CollectibleView collectibleView) {
        collectibleViews.add(collectibleView);
        collectibleMap.put(collectibleView.getID(), collectibleView);
    }
    public void addArchmireView(ArchmireView archmireView) {
        archmires.add(archmireView);
        enemiesMap.put(archmireView.getID(), archmireView);
    }
    public void addAoEView(AoEView aoEView) {
        aoEViews.add(aoEView);
        aoEViewMap.put(aoEView.getID(), aoEView);
    }
    public void removeEnemyView(String ID) {
        enemies.remove(enemiesMap.get(ID));
        enemiesMap.put(ID, null);
    }
    public void removeBulletView(String ID) {
        bullets.remove(bulletsMap.get(ID));
        bulletsMap.put(ID, null);
    }
    public void removeCollectibleView(String ID) {
        collectibleViews.remove(collectibleMap.get(ID));
        collectibleMap.put(ID, null);
    }
    public void removeArchmireView(String ID) {
        archmires.remove((ArchmireView) enemiesMap.get(ID));
        enemiesMap.put(ID, null);
    }
    public void removeAoEView(String ID) {
        aoEViews.remove(aoEViewMap.get(ID));
        aoEViewMap.put(ID, null);
    }
    public void addPanel(int x, int y, int width, int height, String ID) {
        GamePanel gamePanel = new GamePanel(x, y, width, height, ID);
        gamePanels.add(gamePanel);
        gamePanelMap.put(ID, gamePanel);
    }
    public void addLaser(BlackOrbLaserView laser) {
        laserViews.add(laser);
        laserViewMap.put(laser.getID(), laser);
    }
    public void removeLaser(String ID) {
        laserViews.remove(laserViewMap.get(ID));
        laserViewMap.put(ID, null);
    }

    public ArrayList<BlackOrbLaserView> getLaserViews() {
        return laserViews;
    }

    public Map<String, BlackOrbLaserView> getLaserViewMap() {
        return laserViewMap;
    }
    public void updatePanels() {
        for (int i = 0; i < gamePanels.size(); i++) {
            gamePanels.get(i).revalidate();
            gamePanels.get(i).repaint();
        }
    }
    public void setFocus() {
        GameManager.getINSTANCE().getGameFrame().setFocusable(true);
        GameManager.getINSTANCE().getGameFrame().requestFocus();
        gamePanels.get(0).getFrame().requestFocusInWindow();
    }

    public ArrayList<CollectibleView> getCollectiveViews() {
        return collectibleViews;
    }

    public ArrayList<NecropickAnnouncement> getNecropickAnnouncements() {
        return necropickAnnouncements;
    }
    public void addNecropickAnnouncement(NecropickAnnouncement necropickAnnouncement) {
        necropickAnnouncements.add(necropickAnnouncement);
        necropickAnnouncementMap.put(necropickAnnouncement.getID(), necropickAnnouncement);
    }
    public void removeNecropickAnnouncement(String ID){
        necropickAnnouncements.remove(necropickAnnouncementMap.get(ID));
        necropickAnnouncementMap.put(ID, null);
    }
}