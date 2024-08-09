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

public class TrigorathModel extends Enemy implements Impactable, Movable {
    private int x;
    private int y;
    private boolean decreasedVelocity;
    private boolean increasedVelocity;
    private static int number;
    public TrigorathModel(Point center, int hp, double velocity, GameManager gameManager, EpsilonModel epsilon) {
        super(center, hp, velocity, gameManager, epsilon);
        number++;
        logger = Logger.getLogger(TrigorathModel.class.getName()+number);
        width = MyApplication.configs.TRIGORATH_WIDTH;
        height = MyApplication.configs.TRIGORATH_HEIGHT;
        this.myFrame = epsilon.getInitialFrame();
        increasedVelocity = true;
        decreasedVelocity = false;
        HP = 15+hp;
        damage = 10;
        addVertexes();
        this.myFrame.getEnemies().add(this);
        Controller.addEnemyView(this, gameManager);
        start();
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
        double x2 = epsilon.getCenter().getX();
        double y2 = epsilon.getCenter().getX();
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

    public void setSpecialImpact() {
        velocity = new Point(0, 0);
        decreasedVelocity = true;
        increasedVelocity = false;
    }

    @Override
    public void addCollective() {
        for (int i = -1; i < 2; i += 2) {
            Collectible collectible = new Collectible((int) center.getX()+i*13, (int) center.getY()+i*13, 5);
            gameManager.getGameModel().getCollectibles().add(collectible);
            Controller.addCollectibleView(collectible, gameManager);
        }
    }
    public Direction getDirection() {
        return new Direction(getCenter(), epsilon.getCenter());
    }


}


