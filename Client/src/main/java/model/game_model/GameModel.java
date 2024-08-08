package model.game_model;

import controller.game_manager.GameManager;
import controller.save.Configs;
import model.game.BulletModel;
import model.game.Collectible;
import model.game.EpsilonModel;
import model.game.Wave;
import model.game.enemies.Enemy;
import model.game.enemies.smiley.Smiley;
import model.game.frame.MyFrame;

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
    private ArrayList<EpsilonModel> epsilons;
    private EpsilonModel myEpsilon;
    private int ares;
    private boolean athena;
    private long athenaActivationTime;
    private boolean finished;
    private double totalPR;
    private ArrayList<BulletModel> enemiesBullets;
    private ArrayList<MyFrame> myFrames;
    private final Object enemyLock;
    private MyFrame initialMyFrame;
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
    private GameManager gameManager;
    private int epsilonNumber;

    public GameModel(GameManager gameManager, int epsilonNumber) {
        this.gameManager = gameManager;
        enemyLock = new Object();
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        collectibles = new ArrayList<>();
        enemiesBullets = new ArrayList<>();
        myFrames = new ArrayList<>();
        epsilons = new ArrayList<>();
        this.epsilonNumber = epsilonNumber;
        addEpsilons(epsilonNumber);
        diedEnemies = new ArrayList<>();
        vanishedBullets = new ArrayList<>();
        vanishedEnemiesBullets = new ArrayList<>();
        lastSavedTime = System.currentTimeMillis();
        writOfAthena = 100;
        decreaseSize = true;
    }
    private void addEpsilons(int epsilon){
        if (gameManager.isOnline()) {
            EpsilonModel epsilon1 = new EpsilonModel(new MyFrame(500, 500, 100, 0, false, false, 400,
                    400, gameManager), gameManager, 0);
            myFrames.add(epsilon1.getFrame());
            EpsilonModel epsilon2 = new EpsilonModel(new MyFrame(500, 500, 700, 0, false, false, 400,
                    400, gameManager), gameManager, 1);
            myFrames.add(epsilon2.getFrame());
            epsilons = new ArrayList<>();
            epsilons.add(epsilon1);
            epsilons.add(epsilon2);
        }
        else {
            EpsilonModel epsilon1 = new EpsilonModel(new MyFrame(700, 700, Configs.FRAME_SIZE.width/2-350,
                    Configs.FRAME_SIZE.height/2-350, false, false, 400, 400, gameManager), gameManager, 0);
            myFrames.add(epsilon1.getFrame());
            epsilons.add(epsilon1);
        }
        myEpsilon = epsilons.get(epsilon);
        initialMyFrame = myEpsilon.getFrame();
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

    public ArrayList<EpsilonModel> getEpsilons() {
        return epsilons;
    }

    public EpsilonModel getMyEpsilon() {
        return myEpsilon;
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

    public ArrayList<MyFrame> getFrames() {
        return myFrames;
    }

    public MyFrame getInitialFrame() {
        return initialMyFrame;
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

    public int getEpsilonNumber() {
        return epsilonNumber;
    }
}
