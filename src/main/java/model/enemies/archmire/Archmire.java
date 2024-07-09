package model.enemies.archmire;

import controller.GameManager;
import model.enemies.Enemy;
import model.game.GameModel;
import movement.Direction;
import movement.Movable;
import movement.Point;
import movement.RotatablePoint;

import java.util.ArrayList;

public class Archmire extends Enemy implements Movable {
    protected double width;
    protected double height;
    private long lastCheckedTime;
    private ArrayList<AoEAttack> aoeAttacks;
    public Archmire(Point center, double velocity, int hp) {
        super(center, velocity);
        this.HP = 30 + hp;
        initialHP = HP;
        aoeAttacks = new ArrayList<>();
        width = 22;
        height = 18;
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
        position = new RotatablePoint(center.getX(), center.getY(), 1.2*Math.PI+angle, 14.2);
    }
    public void run() {
        while (true) {
            move();
            aoeAttacks.add(new AoEAttack(this));
            if (System.currentTimeMillis() - lastCheckedTime >= 1000) {
                for (int i = 0; i < GameManager.getINSTANCE().getGameModel().getEnemies().size(); i++) {
                    Enemy enemy = GameManager.getINSTANCE().getGameModel().getEnemies().get(i);
                    if (!(enemy instanceof Archmire)) {
                        if (Interference.enemyIsInArchmire(vertexes, enemy)) {
                            enemy.died(10);
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
            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void die() {
        new MiniArchmire(new Point(center.getX(), center.getY()-height/2), velocityPower, initialHP/2);
        new MiniArchmire(new Point(center.getX(), center.getY()+height/2), velocityPower, initialHP/2);
    }

    public ArrayList<AoEAttack> getAoeAttacks() {
        return aoeAttacks;
    }

    @Override
    public void setCenter(Point center) {

    }

    @Override
    public void setImpact(boolean impact) {

    }

    @Override
    public Point getAcceleration() {
        return acceleration;
    }

    @Override
    public void setAcceleration(Point acceleration) {

    }

    @Override
    public Point getAccelerationRate() {
        return accelerationRate;
    }

    @Override
    public void setAccelerationRate(Point accelerationRate) {

    }

    @Override
    public void setVelocity(Point velocity) {

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
    public Direction getDirection() {
        return new Direction(getCenter(), GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter());
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

    }

    @Override
    public void setAngularVelocity(double velocity) {

    }

    @Override
    public void setAngularAcceleration(double acceleration) {

    }

    @Override
    public void setAngularAccelerationRate(double rate) {

    }

    @Override
    public double getAngularAccelerationRate() {
        return 0;
    }

    @Override
    public void specialMove() {

    }

    @Override
    public void addCollective(GameModel gameModel) {

    }
}
