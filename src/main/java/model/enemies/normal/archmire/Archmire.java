package model.enemies.normal.archmire;

import controller.Controller;
import controller.GameManager;
import controller.audio.AudioController;
import model.Collective;
import model.Interference;
import model.enemies.Enemy;
import model.frame.Frame;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Movable;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;

import java.awt.*;
import java.util.ArrayList;

public class Archmire extends Enemy implements Movable {
    private long lastCheckedTime;
    private ArrayList<AoEAttack> aoeAttacks;
    private int initialHP;
    public Archmire(Point center, double velocity, int hp, Frame frame) {
        super(center, velocity);
        this.HP = 30 + hp;
        initialHP = HP;
        aoeAttacks = new ArrayList<>();
        width = GameManager.configs.ARCHMIRE_WIDTH;
        height = GameManager.configs.ARCHMIRE_HEIGHT;
        this.frame = frame;
        Controller.addArchmireView(this);
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
    public void nextMove() {
            move();
            aoeAttacks.add(new AoEAttack(this));
            if (System.currentTimeMillis() - lastCheckedTime >= 1000) {
                for (int i = 0; i < GameManager.getINSTANCE().getGameModel().getEnemies().size(); i++) {
                    Enemy enemy = GameManager.getINSTANCE().getGameModel().getEnemies().get(i);
                    if (!(enemy instanceof Archmire)) {
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
                lastCheckedTime = System.currentTimeMillis();
            }
    }
    protected void die() {
        addCollective();
        new MiniArchmire(new Point(center.getX(), center.getY()-height/2), velocityPower, initialHP/2,frame);
        new MiniArchmire(new Point(center.getX(), center.getY()+height/2), velocityPower, initialHP/2, frame);
        GameManager.getINSTANCE().getDiedEnemies().add(this);
        Controller.removeArchmireView(this);
        AudioController.addEnemyDyingSound();
    }

    public ArrayList<AoEAttack> getAoeAttacks() {
        return aoeAttacks;
    }


    @Override
    public Direction getDirection() {
        return new Direction(getCenter(), GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter());
    }

    @Override
    public void addCollective() {
        int[] x = new int[]{-10, 10, -10, 10, 0};
        int[] y = new int[]{-10, -10, 10, 10, 0};
        for (int i = 0; i < 5; i++) {
            Collective collective = new Collective((int)center.getX()+x[i], (int)center.getY()+y[i],6);
            GameManager.getINSTANCE().getGameModel().getCollectives().add(collective);
            Controller.addCollectiveView(collective);
        }
    }

    @Override
    public void setAngularVelocity(double velocity) {
        angularVelocity = velocity;
    }

    @Override
    public void setAngularAcceleration(double acceleration) {
        angularAcceleration = acceleration;
    }

    @Override
    public void setAngularAccelerationRate(double accelerationRate) {
        angularAccelerationRate = accelerationRate;
    }

    @Override
    public double getAngularAccelerationRate() {
        return angularAccelerationRate;
    }

    @Override
    public void specialMove() {

    }

    @Override
    public void setCenter(Point center) {
        this.center = center;
    }

    @Override
    public void setImpact(boolean impact) {
        this.impact = impact;
    }

    @Override
    public Point getAcceleration() {
        return acceleration;
    }

    @Override
    public void setAcceleration(Point acceleration) {
        this.acceleration = acceleration;
    }

    @Override
    public Point getAccelerationRate() {
        return accelerationRate;
    }

    @Override
    public void setAccelerationRate(Point accelerationRate) {
        this.accelerationRate = accelerationRate;
    }

    @Override
    public void setVelocity(Point velocity) {
        this.velocity = velocity;
    }

    @Override
    public Point getVelocity() {
        return velocity;
    }

    @Override
    public double getVelocityPower() {
        return velocityPower;
    }
    @Override
    public double getAngularVelocity() {
        return angularVelocity;
    }

    @Override
    public double getAngularAcceleration() {
        return angularAcceleration;
    }

    @Override
    public void setAngle(double angle) {
        this.angle = angle;
    }
}
