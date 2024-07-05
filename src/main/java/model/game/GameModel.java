package model.game;

import model.BulletModel;
import model.EpsilonModel;
import model.Collective;
import model.enemies.Enemy;

import java.util.ArrayList;
import java.util.Map;

public abstract class GameModel {
    private double width;
    private double height;
    private double x;
    private double y;
    private ArrayList<Enemy> enemies;
    private ArrayList<BulletModel> bullets;
    protected Map<Integer, Integer> waves;
    protected double enemyVelocity;
    protected int enemyPower;
    protected int enemyHP;
    protected int enemyXP;
    private ArrayList<Collective> Collectives;
    private EpsilonModel epsilon;

    public GameModel() {
        x = 0;
        y = 0;
        width = 700;
        height = 700;
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        Collectives = new ArrayList<>();
        epsilon = new EpsilonModel();
    }


    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int getX() {
        return (int)x;
    }

    public int getY() {
        return (int)y;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<BulletModel> getBullets() {
        return bullets;
    }


    public ArrayList<Collective> getCollectives() {
        return Collectives;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
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
}
