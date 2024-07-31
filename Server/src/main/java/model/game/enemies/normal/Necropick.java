package model.game.enemies.normal;

import application.MyApplication;
import controller.Controller;
import controller.GameManager;
import controller.save.Configs;
import model.game.BulletModel;
import model.game.Collectible;
import model.game.EpsilonModel;
import model.game.enemies.Enemy;
import model.game.frame.MyFrame;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class Necropick extends Enemy {
    private static int number;
    private boolean appeared;
    private boolean disappeared;
    private boolean announced;
    private Point appearancePoint;
    public Necropick(Point center, double velocity, int hp, MyFrame myFrame, GameManager gameManager) {
        super(center, velocity, gameManager);
        number++;
        logger = Logger.getLogger(Necropick.class.getName()+number);
        height = MyApplication.configs.NECROPICK_HEIGHT;
        width = MyApplication.configs.NECROPICK_WIDTH;
        addVertexes();
        this.myFrame = myFrame;
        this.HP = 10 + hp;
        this.myFrame.getEnemies().add(this);
        Controller.addEnemyView(this, gameManager);
        appeared = true;
        start();
    }

    @Override
    public void addCollective() {
        int[] x = new int[]{-10, 10, -10, 10};
        int[] y = new int[]{-10, -10, 10, 10};
        for (int i = 0; i < 4; i++) {
            Collectible collectible = new Collectible((int)center.getX()+x[i], (int)center.getY()+y[i], 2);
            gameManager.getGameModel().getCollectibles().add(collectible);
            Controller.addCollectibleView(collectible, gameManager);
        }
    }
    protected void addVertexes() {
        vertexes = new ArrayList<>();
        double[] angles = new double[]{1.4*Math.PI, 1.6*Math.PI, 1.83*Math.PI, 1.9*Math.PI, 1.86*Math.PI,
        1.7*Math.PI, 1.6*Math.PI, 0.45*Math.PI, 0.55*Math.PI, 1.4*Math.PI, 1.3*Math.PI, 1.14*Math.PI,
        1.1*Math.PI, 1.17*Math.PI, 1.4*Math.PI};
        double[] radius = new double[] {11*height/21, 11*height/21, 11*height/21, 9.8*height/21, 6*height/21,
        6.5*height/21, 5.7*height/21, 10.6*height/21, 10.6*height/21, 5.7*height/21, 6.5*height/21, 6*height/21,
                9.8*height/21, 11*height/21, 11*height/21};
        for (int i = 0; i < 15; i++) {
            RotatablePoint vertex = new RotatablePoint(center.getX(), center.getY(), angles[i]+angle, radius[i]);
            vertexes.add(vertex);
        }
        position = new RotatablePoint(center.getX(), center.getY(), 1.26*Math.PI+angle, 14.2/21*height);
    }
    public void run() {
        while (!died) {
            if (!gameManager.isHypnos() && Controller.gameRunning) {
                disappear();
                sleepFor(3000);
                announceAppearance();
                sleepFor(1000);
                appear();
                sleepFor(1000);
                shootBullets(4);
                sleepFor(2000);
                shootBullets(4);
                sleepFor(1000);
                //EnemyLogger.getInfo(logger, this);
            }
            else {
                try {
                    sleep((long) Configs.MODEL_UPDATE_TIME);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        interrupt();
    }
    private void sleepFor(long time){
        try {
            sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void shootBullets(int n) {
        for (int i = 0; i < n; i++) {
            shoot();
        }
        logger.debug("shot");
    }

    @Override
    public Direction getDirection() {
        return new Direction();
    }

    private void disappear() {
        disappeared = true;
        Controller.removeEnemyView(this, gameManager);
        appeared = false;
        announced = false;
        logger.debug("disappeared");
    }
    private void announceAppearance(){
        appearancePoint = getRandomPosition();
        Controller.announceAppearance(appearancePoint, ID, gameManager);
        announced = true;
        logger.debug("announced");
    }

    private void appear() {
        Controller.removeAnnouncement(ID, gameManager);
        center = appearancePoint;
        moveVertexes();
        Controller.addEnemyView(this, gameManager);
        appeared = true;
        disappeared = false;
        logger.debug("appeared");
    }
    private Point getRandomPosition() {
        EpsilonModel epsilon = gameManager.getGameModel().getEpsilon();
        int randomX = (int)(Math.random()*1000);
        int randomY = (int)(Math.random()*1000);
        double angle = Math.atan2(randomY -epsilon.getCenter().getY(), randomX -epsilon.getCenter().getX());
        double x = 100*Math.cos(angle);
        double y = 100*Math.sin(angle);
        if (epsilon.getCenter().getX()+x-epsilon.getFrame().getX() < 10 ||
                epsilon.getCenter().getX()+x-epsilon.getFrame().getX() > epsilon.getFrame().getWidth()-10){
            x = -x+epsilon.getCenter().getX();
        }
        else {
            x = x+epsilon.getCenter().getX();
        }
        if (epsilon.getCenter().getY()+y-epsilon.getFrame().getY() < 10 ||
                epsilon.getCenter().getY()+y-epsilon.getFrame().getY() > epsilon.getFrame().getHeight()-10){
            y = -y+epsilon.getCenter().getY();
        }
        else {
            y = y+epsilon.getCenter().getY();
        }
        logger.debug(new Point(x,y).toString());
        return new Point(x, y);
    }

    private void shoot() {
        int x = (int)(Math.random()*1000);
        int y = (int)(Math.random()*1000);
        logger.debug("shot: "+new Point(x,y));
        BulletModel bulletModel = new BulletModel(center, new Point(x, y), (int)(2*height/21), 4, false, myFrame, gameManager);
        gameManager.getGameModel().getEnemiesBullets().add(bulletModel);
    }

    public boolean isAppeared() {
        return appeared;
    }

    public boolean isDisappeared() {
        return disappeared;
    }

    public boolean isAnnounced() {
        return announced;
    }
}
