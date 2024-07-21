package model.game;

import model.BulletModel;
import model.EpsilonModel;
import model.Collectible;
import model.frame.Frame;
import model.enemies.Enemy;

import java.util.ArrayList;
import java.util.Map;

public abstract class GameModel {
    private ArrayList<Enemy> enemies;
    private ArrayList<BulletModel> bullets;
    protected Map<Integer, Integer> waves;
    protected double enemyVelocity;
    protected int enemyPower;
    protected int enemyHP;
    protected int enemyXP;
    private ArrayList<Collectible> collectibles;
    private EpsilonModel epsilon;
    private int ares;
    private boolean athena;
    private long athenaActivationTime;
    private boolean finished;
    private double totalPR;
    private ArrayList<BulletModel> enemiesBullets;
    private ArrayList<Frame> frames;
    private final Object enemyLock;
    private Frame initialFrame;

    public GameModel() {
        enemyLock = new Object();
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        collectibles = new ArrayList<>();
        enemiesBullets = new ArrayList<>();
        frames = new ArrayList<>();
        epsilon = new EpsilonModel(new Frame(700, 700,0,0,false,false));
        frames.add(epsilon.getFrame());
        initialFrame = epsilon.getFrame();

    }

    public ArrayList<Enemy> getEnemies() {
            return enemies;
    }

    public ArrayList<BulletModel> getBullets() {
        return bullets;
    }


    public ArrayList<Collectible> getCollectibles() {
        return collectibles;
    }


    public int getEnemyPower() {
        return enemyPower;
    }

    public int getEnemyXP() {
        return enemyXP;
    }

    public EpsilonModel getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(EpsilonModel epsilon) {
        this.epsilon = epsilon;
    }

    public int getEnemyHP() {
        return enemyHP;
    }

    public double getEnemyVelocity() {
        return enemyVelocity;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
            this.enemies = enemies;
    }

    public Map<Integer, Integer> getWaves() {
        return waves;
    }

    public int getAres() {
        return ares;
    }

    public void setAres(int ares) {
        this.ares = ares;
    }

    public boolean isAthena() {
        return athena;
    }

    public void setAthena(boolean athena) {
        this.athena = athena;
    }

    public long getAthenaActivationTime() {
        return athenaActivationTime;
    }

    public void setAthenaActivationTime(long athenaActivationTime) {
        this.athenaActivationTime = athenaActivationTime;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public double getTotalPR() {
        return totalPR;
    }

    public void setTotalPR(double totalPR) {
        this.totalPR = totalPR;
    }


    public ArrayList<BulletModel> getEnemiesBullets() {
        return enemiesBullets;
    }

    public Object getEnemyLock() {
        return enemyLock;
    }

    public ArrayList<Frame> getFrames() {
        return frames;
    }

    public Frame getInitialFrame() {
        return initialFrame;
    }
}
