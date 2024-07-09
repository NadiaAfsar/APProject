package model.enemies.archmire;

import controller.GameManager;
import model.enemies.Enemy;
import movement.Point;
import movement.RotatablePoint;

import java.util.ArrayList;

public class AoEAttack {
    private ArrayList<RotatablePoint> vertexes;
    private RotatablePoint position;
    private Point center;
    private int height;
    private int width;
    private Archmire archmire;
    private int clarity;
    public AoEAttack(Archmire archmire) {
        this.center = archmire.getCenter();
        this.archmire = archmire;
        addVertexes();
        clarity = 5;
    }
    protected void addVertexes(){
        vertexes = new ArrayList<>();
        double[] angles = new double[]{1.5*Math.PI, 1.7*Math.PI, 1.97*Math.PI, 0.12*Math.PI, 0.37*Math.PI,
                0.63*Math.PI, 0.88*Math.PI, 1.03*Math.PI, 1.3*Math.PI};
        double[] radius = new double[] {0.5*height, 9.2*height/22, 10*height/22, 10.8*height/22, 8.7*height/22,
                8.7*height/22, 10.8*height/22, 10*height/22, 9.2*height/22};
        for (int i = 0; i < 9; i++) {
            RotatablePoint vertex = new RotatablePoint(center.getX(), center.getY(), angles[i], radius[i]);
            vertexes.add(vertex);
        }
        position = new RotatablePoint(center.getX(), center.getY(), 1.2*Math.PI, 14.2);
    }
    public boolean update() {
        for (int i = 0; i < GameManager.getINSTANCE().getGameModel().getEnemies().size(); i++) {
            Enemy enemy = GameManager.getINSTANCE().getGameModel().getEnemies().get(i);
            if (!(enemy instanceof Archmire)) {
                if (Interference.enemyIsInArchmire(vertexes, enemy)) {
                    enemy.died(2);
                }
            }
        }
        if (Interference.epsilonIsInArchmire(vertexes, GameManager.getINSTANCE().getGameModel().getEpsilon())) {
            GameManager.getINSTANCE().getGameModel().getEpsilon().decreaseHP(2);
        }
        clarity--;
        if (clarity == 0) {
            archmire.getAoeAttacks().remove(this);
            return false;
        }
        return true;
    }

    public int getClarity() {
        return clarity;
    }
}
