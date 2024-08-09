package model.game.enemies;

import application.MyApplication;
import controller.Controller;
import controller.game_manager.GameManager;
import controller.save.Configs;
import model.game.Collectible;
import model.game.EpsilonModel;
import model.game.frame.MyFrame;
import model.interfaces.collision.Impactable;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Movable;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class SquarantineModel extends Enemy implements Impactable, Movable {
    private boolean hasRandomAcceleration;
    private Direction direction;
    private static int number;
    public SquarantineModel(Point center, int hp, double velocity, GameManager gameManager, EpsilonModel epsilon) {
        super(center, hp, velocity, gameManager, epsilon);
        number++;
        logger = Logger.getLogger(SquarantineModel.class.getName()+number);
        width = MyApplication.configs.SQUARANTINE_WIDTH;
        height = MyApplication.configs.SQUARANTINE_WIDTH;
        this.myFrame = epsilon.getInitialFrame();
        HP = 10+hp;
        damage = 6;
        hasRandomAcceleration = false;
        addVertexes();
        this.myFrame.getEnemies().add(this);
        Controller.addEnemyView(this, gameManager);
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
        if (!gameManager.isOnline()) {
            if (!hasRandomAcceleration && !impact) {
                int x = (int) (Math.random() * 200);
                if (x == 5) {
                    direction = getDirection();
                    acceleration = new Point(30 * direction.getDx(), 30 * direction.getDy());
                    accelerationRate = new Point(-10 * direction.getDx(), -10 * direction.getDy());
                    hasRandomAcceleration = true;
                }
            }
            if (hasRandomAcceleration && !impact) {
                if (acceleration.getX() <= 0 || acceleration.getY() <= 0) {
                    velocity = new Point(0, 0);
                    acceleration = new Point(0, 0);
                    accelerationRate = new Point(0, 0);
                    hasRandomAcceleration = false;
                } else {
                    Direction newDirection = getDirection();
                    acceleration = new Point(acceleration.getX() / direction.getDx() * newDirection.getDx(),
                            acceleration.getY() / direction.getDy() * newDirection.getDy());
                    accelerationRate = new Point(accelerationRate.getX() / direction.getDx() * newDirection.getDx(),
                            accelerationRate.getY() / direction.getDy() * newDirection.getDy());
                    direction = newDirection;

                }
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
        gameManager.getGameModel().getCollectibles().add(collectible);
        Controller.addCollectibleView(collectible, gameManager);
    }
    public Direction getDirection() {
        Direction direction1 = new Direction(getCenter(), epsilon.getCenter());
        return direction1;
    }
    public void run() {
        while (!died) {
            if (!gameManager.isHypnos() && gameManager.isRunning()) {
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