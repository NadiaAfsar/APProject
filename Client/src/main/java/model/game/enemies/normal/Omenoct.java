package model.game.enemies.normal;

import application.MyApplication;
import controller.Controller;
import controller.game_manager.GameManager;
import controller.save.Configs;
import model.game.BulletModel;
import model.game.Collectible;
import model.game.EpsilonModel;
import model.game.enemies.Enemy;
import model.game.frame.MyFrame;
import model.interfaces.collision.Impactable;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Movable;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class Omenoct extends Enemy implements Impactable, Movable {
    private boolean stuck;
    private boolean sideChosen;
    private int side;
    private long lastShoot;
    private static int number;
    public Omenoct(Point center, int hp, double velocity, GameManager gameManager, EpsilonModel epsilon) {
        super(center, hp, velocity, gameManager, epsilon);
        number++;
        logger = Logger.getLogger(Omenoct.class.getName()+number);
        width = MyApplication.configs.OMENOCT_WIDTH;
        height = MyApplication.configs.OMENOCT_HEIGHT;
        addVertexes();
        this.HP = 20 + hp;
        damage = 8;
        this.myFrame = epsilon.getInitialFrame();
        this.myFrame.getEnemies().add(this);
        Controller.addEnemyView(this, gameManager);
        start();
    }

    @Override
    public Direction getDirection() {
        if (!sideChosen) {
            choseSide();
        }
        Point chosenSide = getChosenSide();
        double x = 0;
        double y = 0;
        if (chosenSide.getY() == 0) {
            y = epsilon.getCenter().getY();
            if (stuck) {
                Direction direction = new Direction();
                direction.setDy(0.5*Math.abs(y-center.getY())/(y-center.getY()));
                return direction;
            }
            x = chosenSide.getX();
        } else {
            x = epsilon.getCenter().getX();
            if (stuck) {
                Direction direction = new Direction();
                direction.setDx(0.5*Math.abs(x-center.getX())/(x-center.getX()));
                return direction;
            }
            y = chosenSide.getY();
        }
        return new Direction(center, new Point(x, y));
    }

    private void choseSide() {
        side = (int)(Math.random()*4);
        sideChosen = true;
    }
    private Point getChosenSide() {
        Point chosenSide = null;
        if (side == 0) {
            chosenSide = new Point(0, myFrame.getY()+height/2);
        }
        else if (side == 1) {
            chosenSide = new Point(myFrame.getWidth()+ myFrame.getX()-width/2, 0);
        }
        else if (side == 2) {
            chosenSide = new Point(0, myFrame.getHeight()+ myFrame.getY()-height/2);
        }
        else {
            chosenSide = new Point(width/2+ myFrame.getX(), 0);
        }
        return chosenSide;
    }

    @Override
    public void setSpecialImpact() {
        stuck = false;
        sideChosen = false;
    }

    @Override
    public void addCollective() {
        for (int i = 0; i < 8; i++) {
            Collectible collectible = new Collectible((int)vertexes.get(i).getRotatedX(), (int)vertexes.get(i).getRotatedY(),4);
            gameManager.getGameModel().getCollectibles().add(collectible);
            Controller.addCollectibleView(collectible, gameManager);
        }
    }

    @Override
    public void specialMove() {
        myFrame = epsilon.getFrame();
        Point chosenSide = getChosenSide();
        if ((chosenSide.getY() == 0 && Math.abs(center.getX()-chosenSide.getX()) < 10) ||
                (chosenSide.getX() == 0 && Math.abs(center.getY()-chosenSide.getY()) < 10) && !stuck) {
            stuck = true;
            myFrame.getSides().get(side+1).getStuckOmenocts().add(this);
        }
        else if (stuck) {
            if (side == 0){
                center.setY(myFrame.getY()+height/2);
            }
            else if (side == 1) {
                center.setX(myFrame.getX()+ myFrame.getWidth()-width/2);
            }
            else if (side == 2){
                center.setY(myFrame.getY()+ myFrame.getHeight()-height/2);
            }
            else {
                center.setX(myFrame.getX()+width/2);
            }
        }

    }
    protected void addVertexes() {
        vertexes = new ArrayList<>();
        double[] angles = new double[]{11d/8*Math.PI, 13d/8*Math.PI, 15d/8*Math.PI, 1d/8*Math.PI,
        3d/8*Math.PI, 5d/8*Math.PI, 7d/8*Math.PI, 9d/8*Math.PI};
        for (int i = 0; i < 8; i++) {
            RotatablePoint vertex = new RotatablePoint(center.getX(), center.getY(), angles[i]+angle, 0.54*width);
            vertexes.add(vertex);
        }
        position = new RotatablePoint(center.getX(), center.getY(), 5d/4*Math.PI+angle, Math.sqrt(0.5)*width);
    }
    public void separate() {
        stuck = false;
        sideChosen = false;
        logger.debug("separated");
    }
    private void shoot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime-lastShoot >= 1500) {
            BulletModel bulletModel = new BulletModel(center, epsilon.getCenter(),
                    width/2, 4, true, myFrame, gameManager);
            gameManager.getGameModel().getEnemiesBullets().add(bulletModel);
            lastShoot = currentTime;
            logger.debug("shot");
        }
    }
    public void run() {
        while (!died) {
            if (!gameManager.isHypnos() && gameManager.isRunning()) {
                move();
                checkCollision();
                myFrame = epsilon.getFrame();
                if (stuck) {
                    shoot();
                }
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

    public boolean isStuck() {
        return stuck;
    }

    public boolean isSideChosen() {
        return sideChosen;
    }

    public int getSide() {
        return side;
    }
}
