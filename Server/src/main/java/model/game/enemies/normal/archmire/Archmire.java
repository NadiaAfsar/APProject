package model.game.enemies.normal.archmire;

import controller.Controller;
import controller.GameManager;
import controller.audio.AudioController;
import controller.save.Configs;
import model.game.Collectible;
import model.game.Interference;
import model.game.enemies.Enemy;
import model.game.enemies.mini_boss.Barricados;
import model.game.frame.MyFrame;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Movable;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class Archmire extends Enemy implements Movable {
    private long lastCheckedTime;
    private ArrayList<AoEAttack> aoeAttacks;
    private int initialHP;
    private static int number;
    private long lastAoEAdded;
    private long timeBetweenAoEAttacks;
    public Archmire(Point center, double velocity, int hp, MyFrame myFrame) {
        super(center, velocity);
        number++;
        logger = Logger.getLogger(Archmire.class.getName()+number);
        this.HP = 30 + hp;
        initialHP = HP;
        aoeAttacks = new ArrayList<>();
        this.myFrame = myFrame;
        velocityPower *= 2;
        setArchmire();
    }
    protected void setArchmire() {
        width = GameManager.configs.ARCHMIRE_WIDTH;
        height = GameManager.configs.ARCHMIRE_HEIGHT;
        addVertexes();
        Controller.addArchmireView(this);
        start();
    }
    protected void addVertexes(){
        vertexes = new ArrayList<>();
        double[] angles = new double[]{1.5*Math.PI, 1.7*Math.PI, 1.97*Math.PI, 0.12*Math.PI, 0.37*Math.PI,
        0.63*Math.PI, 0.88*Math.PI, 1.03*Math.PI, 1.3*Math.PI};
        double[] radius = new double[] {0.5*height, 9.2*height/22, 10*height/22, 10.8*height/22, 8.7*height/22,
                8.7*height/22, 10.8*height/22, 10*height/22, 9.2*height/22};
        for (int i = 0; i < 9; i++) {
            RotatablePoint vertex = new RotatablePoint(center.getX(), center.getY(), angles[i]+angle, radius[i]);
            vertexes.add(vertex);
        }
        position = new RotatablePoint(center.getX(), center.getY(), 1.2*Math.PI+angle, 14.2/22*width);
    }
    public void run() {
        while (!died) {
            if (!GameManager.getINSTANCE().isHypnos() && Controller.gameRunning) {
                move();
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastAoEAdded > 500) {
                    aoeAttacks.add(new AoEAttack(this));
                    lastAoEAdded = currentTime;
                }
                if (currentTime - lastCheckedTime >= 1000) {
                    for (int i = 0; i < GameManager.getINSTANCE().getGameModel().getEnemies().size(); i++) {
                        Enemy enemy = GameManager.getINSTANCE().getGameModel().getEnemies().get(i);
                        if (!(enemy instanceof Archmire) && !(enemy instanceof Barricados)) {
                            if (Interference.enemyIsInArchmire(vertexes, enemy)) {
                                enemy.decreaseHP(10);
                            }
                        }
                    }
                    if (Interference.epsilonIsInArchmire(vertexes, GameManager.getINSTANCE().getGameModel().getEpsilon())) {
                        GameManager.getINSTANCE().getGameModel().getEpsilon().decreaseHP(10);
                    }
                    for (int i = 0; i < aoeAttacks.size(); i++) {
                        if (!aoeAttacks.get(i).update()) {
                            i--;
                        }
                    }
                    lastCheckedTime = currentTime;
                }
            }
            try {
                sleep((long) Configs.MODEL_UPDATE_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        interrupt();
    }
    protected void die() {
        removeAll();
        addCollective();
        new MiniArchmire(new Point(center.getX()-width, center.getY()-height), velocityPower, initialHP/2, myFrame);
        new MiniArchmire(new Point(center.getX()+width, center.getY()+height), velocityPower, initialHP/2, myFrame);
        GameManager.getINSTANCE().getGameModel().getDiedEnemies().add(this);
        Controller.removeArchmireView(this);
        AudioController.addEnemyDyingSound();
        GameManager.getINSTANCE().getGameModel().getCurrentWave().newEnemyDied();
        died = true;
    }
    protected void removeAll(){
        for (int i = 0; i < aoeAttacks.size(); i++){
           Controller.removeAoEAttackView(aoeAttacks.get(i).getID());
        }
    }

    public ArrayList<AoEAttack> getAoeAttacks() {
        return aoeAttacks;
    }


    @Override
    public Direction getDirection() {
        return new Direction(getCenter(), GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter());
    }

    @Override
    public void specialMove() {

    }

    @Override
    public void addCollective() {
        int[] x = new int[]{-10, 10, -10, 10, 0};
        int[] y = new int[]{-10, -10, 10, 10, 0};
        for (int i = 0; i < 5; i++) {
            Collectible collectible = new Collectible((int)center.getX()+x[i], (int)center.getY()+y[i],6);
            GameManager.getINSTANCE().getGameModel().getCollectibles().add(collectible);
            Controller.addCollectibleView(collectible);
        }
    }

}
