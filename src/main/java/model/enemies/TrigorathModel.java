package model.enemies;

import model.frame.Frame;
import model.interfaces.collision.Impactable;
import controller.Controller;
import controller.GameManager;
import model.Collectible;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Movable;
import model.interfaces.movement.RotatablePoint;
import model.interfaces.movement.Point;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class TrigorathModel extends Enemy implements Impactable, Movable {
    private int x;
    private int y;
    private boolean decreasedVelocity;
    private boolean increasedVelocity;
    private static int number;
    public TrigorathModel(Point center, int hp, double velocity, Frame frame) {
        super(center, velocity);
        number++;
        logger = Logger.getLogger(TrigorathModel.class.getName()+number);
        width = GameManager.configs.TRIGORATH_WIDTH;
        height = GameManager.configs.TRIGORATH_HEIGHT;
        this.frame = frame;
        increasedVelocity = true;
        decreasedVelocity = false;
        HP = 15+hp;
        damage = 10;
        addVertexes();
        this.frame.getEnemies().add(this);
        Controller.addEnemyView(this);
    }
    protected void addVertexes() {
        vertexes = new ArrayList<>();
        double[] angles = new double[]{1d/6*Math.PI, 5d/6*Math.PI, 9d/6*Math.PI};
        for (int i = 0; i < 3; i++) {
            RotatablePoint vertex = new RotatablePoint(center.getX(), center.getY(), angles[i]+angle, 15);
            vertexes.add(vertex);
        }
        position = new RotatablePoint(center.getX(), center.getY(), 41d/180*Math.PI, 19.8);
    }

    private double distanceFromEpsilon() {
        double x1 = center.getX();
        double y1 = center.getY();
        double x2 = GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter().getX();
        double y2 = GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter().getX();
        return Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
    }
    @Override
    public void specialMove() {
        double dx = getDirection().getDx()*getVelocityPower();
        double dy = getDirection().getDx()*getVelocityPower();
        if (distanceFromEpsilon() <= 100 && !decreasedVelocity && !impact) {
            velocity = new Point(0, 0);
            decreasedVelocity = true;
            increasedVelocity = false;
        }
        else if (distanceFromEpsilon() > 100 && !increasedVelocity && !impact) {
            velocity = new Point(2*dx, 2*dy);
            increasedVelocity = true;
            decreasedVelocity = false;
        }
        if (increasedVelocity) {
            velocity = new Point(2*dx, 2*dy);
        }
    }
    public void nextMove() {
        move();
        checkCollision();
        super.nextMove();
    }

    public void setSpecialImpact() {
        velocity = new Point(0, 0);
        decreasedVelocity = true;
        increasedVelocity = false;
    }

    @Override
    public void addCollective() {
        for (int i = -1; i < 2; i += 2) {
            Collectible collectible = new Collectible((int) center.getX()+i*13, (int) center.getY()+i*13, 5);
            GameManager.getINSTANCE().getGameModel().getCollectives().add(collectible);
            Controller.addCollectiveView(collectible);
        }
    }
    public Direction getDirection() {
        return new Direction(getCenter(), GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter());
    }


}


