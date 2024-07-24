package model.enemies;

import controller.save.Configs;
import log.EnemyLogger;
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

public class SquarantineModel extends Enemy implements Impactable, Movable {
    private boolean hasRandomAcceleration;
    private Direction direction;
    private static int number;
    public SquarantineModel(Point center, int hp, double velocity, Frame frame) {
        super(center, velocity);
        number++;
        logger = Logger.getLogger(SquarantineModel.class.getName()+number);
        width = GameManager.configs.SQUARANTINE_WIDTH;
        height = GameManager.configs.SQUARANTINE_WIDTH;
        this.frame = frame;
        HP = 10+hp;
        damage = 6;
        hasRandomAcceleration = false;
        addVertexes();
        this.frame.getEnemies().add(this);
        Controller.addEnemyView(this);
        start();
    }
    protected void addVertexes() {
        vertexes = new ArrayList<>();
        double[] angles = new double[]{5d/4*Math.PI, 7d/4*Math.PI, 1d/4*Math.PI, 3d/4*Math.PI};
        for (int i = 0; i < 4; i++) {
            RotatablePoint vertex = new RotatablePoint(center.getX(), center.getY(), angles[i]+angle,
                    width*Math.sqrt(0.5));
            vertexes.add(vertex);
            if (i == 0) {
                position = vertex;
            }
        }
    }


    @Override
    public void specialMove() {
        if (!hasRandomAcceleration && !impact) {
            int x = (int) (Math.random() * 200);
            if (x == 5) {
                direction = getDirection();
                acceleration = new Point(30*direction.getDx(), 30*direction.getDy());
                accelerationRate = new Point(-10*direction.getDx(), -10*direction.getDy());
                hasRandomAcceleration = true;
            }
        }
        if (hasRandomAcceleration && !impact) {
            if (acceleration.getX() <= 0 || acceleration.getY() <= 0) {
                velocity = new Point(0,0);
                acceleration = new Point(0, 0);
                accelerationRate = new Point(0, 0);
                hasRandomAcceleration = false;
            }
            else {
                Direction newDirection = getDirection();
                acceleration = new Point(acceleration.getX()/direction.getDx()*newDirection.getDx(),
                        acceleration.getY()/direction.getDy()*newDirection.getDy());
                accelerationRate = new Point(accelerationRate.getX()/direction.getDx()*newDirection.getDx(),
                        accelerationRate.getY()/direction.getDy()*newDirection.getDy());
                direction = newDirection;

            }
        }
    }

    public void setSpecialImpact() {
        hasRandomAcceleration = false;
        velocity = new Point(0,0);
    }

    @Override
    public void addCollective() {
        Collectible collectible = new Collectible((int)center.getX(), (int)center.getY(),5);
        GameManager.getINSTANCE().getGameModel().getCollectibles().add(collectible);
        Controller.addCollectibleView(collectible);
    }
    public Direction getDirection() {
        Direction direction1 = new Direction(getCenter(), GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter());
        return direction1;
    }
    public void run() {
        while (!died) {
            if (!GameManager.getINSTANCE().isHypnos() && Controller.gameRunning) {
                move();
                checkCollision();
                //EnemyLogger.getInfo(logger, this);
            }
            try {
                sleep((long) Configs.MODEL_UPDATE_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        interrupt();
    }


}