package model.enemies.normal;

import controller.save.Configs;
import log.EnemyLogger;
import model.frame.Frame;
import model.interfaces.collision.Impactable;
import controller.Controller;
import controller.GameManager;
import model.BulletModel;
import model.Collectible;
import model.enemies.Enemy;
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
    private Frame frame;
    private long lastShoot;
    private static int number;
    public Omenoct(Point center, double velocity, int hp, Frame frame) {
        super(center, velocity);
        number++;
        logger = Logger.getLogger(Omenoct.class.getName()+number);
        width = GameManager.configs.OMENOCT_WIDTH;
        height = GameManager.configs.OMENOCT_HEIGHT;
        addVertexes();
        this.HP = 20 + hp;
        damage = 8;
        this.frame = frame;
        this.frame.getEnemies().add(this);
        Controller.addEnemyView(this);
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
            y = GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter().getY();
            if (stuck) {
                Direction direction = new Direction();
                direction.setDy(0.5*Math.abs(y-center.getY())/(y-center.getY()));
                return direction;
            }
            x = chosenSide.getX();
        } else {
            x = GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter().getX();
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
            chosenSide = new Point(0, frame.getY()+height/2);
        }
        else if (side == 1) {
            chosenSide = new Point(frame.getWidth()+ frame.getX()-width/2, 0);
        }
        else if (side == 2) {
            chosenSide = new Point(0, frame.getHeight()+ frame.getY()-height/2);
        }
        else {
            chosenSide = new Point(width/2+ frame.getX(), 0);
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
            GameManager.getINSTANCE().getGameModel().getCollectives().add(collectible);
            Controller.addCollectibleView(collectible);
        }
    }

    @Override
    public void specialMove() {
        Point chosenSide = getChosenSide();
        if ((chosenSide.getY() == 0 && Math.abs(center.getX()-chosenSide.getX()) < 10) ||
                (chosenSide.getX() == 0 && Math.abs(center.getY()-chosenSide.getY()) < 10) && !stuck) {
            stuck = true;
            frame.getSides().get(side+1).getStuckOmenocts().add(this);
        }
        else if (stuck) {
            if (side == 0){
                center.setY(frame.getY()+height/2);
            }
            else if (side == 1) {
                center.setX(frame.getX()+ frame.getWidth()-width/2);
            }
            else if (side == 2){
                center.setY(frame.getY()+frame.getHeight()-height/2);
            }
            else {
                center.setX(frame.getX()+width/2);
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
            BulletModel bulletModel = new BulletModel(center, GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter(),
                    width/2, 4, true, frame);
            GameManager.getINSTANCE().getGameModel().getEnemiesBullets().add(bulletModel);
            lastShoot = currentTime;
            logger.debug("shot");
        }
    }
    public void run() {
        while (true) {
            move();
            checkCollision();
            frame = GameManager.getINSTANCE().getGameModel().getEpsilon().getFrame();
            if (stuck) {
                shoot();
            }
            EnemyLogger.getInfo(logger, this);
            try {
                sleep((long) Configs.MODEL_UPDATE_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
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
