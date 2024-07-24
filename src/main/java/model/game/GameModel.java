package model.game;

import model.BulletModel;
import model.EpsilonModel;
import model.Collectible;
import model.Wave;
import model.enemies.smiley.Smiley;
import model.frame.Frame;
import model.enemies.Enemy;

import java.util.ArrayList;
import java.util.Map;

public abstract class GameModel {
    private ArrayList<Enemy> enemies;
    private ArrayList<BulletModel> bullets;
    protected Map<Integer, Integer> enemiesToKill;
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
    private boolean gameStarted;
    private ArrayList<Enemy> diedEnemies;
    private ArrayList<BulletModel> vanishedBullets;
    private ArrayList<Collectible> takenCollectibles;
    private boolean wait;
    private boolean decreaseSize;
    private int wave;
    private long lastSavedTime;
    private long timePlayed;
    private Wave currentWave;
    private ArrayList<BulletModel> vanishedEnemiesBullets;
    private boolean quake;
    public Smiley smiley;
    private int astarpe;
    private int melampus;
    private int chiron;
    private double writOfAthena;
    private int HPtoIncrease;
    private int cerberuses;
    private int totalBullets;
    private int successfulBullets;
    private int killedEnemies;

    public GameModel() {
        enemyLock = new Object();
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        collectibles = new ArrayList<>();
        enemiesBullets = new ArrayList<>();
        frames = new ArrayList<>();
        initialFrame = new Frame(700, 700,0,0,false,false, 400,
                400);
        epsilon = new EpsilonModel(initialFrame);
        frames.add(epsilon.getFrame());
        diedEnemies = new ArrayList<>();
        vanishedBullets = new ArrayList<>();
        vanishedEnemiesBullets = new ArrayList<>();
        lastSavedTime = System.currentTimeMillis();
        writOfAthena = 100;
        decreaseSize = true;
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

    public Map<Integer, Integer> getEnemiesToKill() {
        return enemiesToKill;
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

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public ArrayList<Enemy> getDiedEnemies() {
        return diedEnemies;
    }

    public void setDiedEnemies(ArrayList<Enemy> diedEnemies) {
        this.diedEnemies = diedEnemies;
    }

    public ArrayList<BulletModel> getVanishedBullets() {
        return vanishedBullets;
    }

    public void setVanishedBullets(ArrayList<BulletModel> vanishedBullets) {
        this.vanishedBullets = vanishedBullets;
    }

    public ArrayList<Collectible> getTakenCollectibles() {
        return takenCollectibles;
    }

    public void setTakenCollectibles(ArrayList<Collectible> takenCollectibles) {
        this.takenCollectibles = takenCollectibles;
    }

    public boolean isWait() {
        return wait;
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }

    public boolean isDecreaseSize() {
        return decreaseSize;
    }

    public void setDecreaseSize(boolean decreaseSize) {
        this.decreaseSize = decreaseSize;
    }

    public int getWave() {
        return wave;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }

    public long getLastSavedTime() {
        return lastSavedTime;
    }

    public void setLastSavedTime(long lastSavedTime) {
        this.lastSavedTime = lastSavedTime;
    }

    public long getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(long timePlayed) {
        this.timePlayed = timePlayed;
    }

    public Wave getCurrentWave() {
        return currentWave;
    }

    public void setCurrentWave(Wave currentWave) {
        this.currentWave = currentWave;
    }

    public ArrayList<BulletModel> getVanishedEnemiesBullets() {
        return vanishedEnemiesBullets;
    }

    public void setVanishedEnemiesBullets(ArrayList<BulletModel> vanishedEnemiesBullets) {
        this.vanishedEnemiesBullets = vanishedEnemiesBullets;
    }

    public boolean isQuake() {
        return quake;
    }

    public void setQuake(boolean quake) {
        this.quake = quake;
    }

    public Smiley getSmiley() {
        return smiley;
    }

    public void setSmiley(Smiley smiley) {
        this.smiley = smiley;
    }

    public int getAstarpe() {
        return astarpe;
    }

    public void setAstarpe(int astarpe) {
        this.astarpe = astarpe;
    }

    public int getMelampus() {
        return melampus;
    }

    public void setMelampus(int melampus) {
        this.melampus = melampus;
    }

    public int getChiron() {
        return chiron;
    }

    public void setChiron(int chiron) {
        this.chiron = chiron;
    }

    public double getWritOfAthena() {
        return writOfAthena;
    }

    public void setWritOfAthena(double writOfAthena) {
        this.writOfAthena = writOfAthena;
    }

    public int getHPtoIncrease() {
        return HPtoIncrease;
    }

    public void setHPtoIncrease(int HPtoIncrease) {
        this.HPtoIncrease = HPtoIncrease;
    }

    public int getCerberuses() {
        return cerberuses;
    }

    public void setCerberuses(int cerberuses) {
        this.cerberuses = cerberuses;
    }

    public boolean isFinished() {
        return finished;
    }

    public int getTotalBullets() {
        return totalBullets;
    }

    public void addBullet() {
        totalBullets++;
    }

    public int getSuccessfulBullets() {
        return successfulBullets;
    }

    public void addSuccessfulBullet() {
        successfulBullets++;
    }

    public int getKilledEnemies() {
        return killedEnemies;
    }

    public void setKilledEnemies(int killedEnemies) {
        this.killedEnemies = killedEnemies;
    }
}
