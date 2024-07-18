package model.enemies.normal;

import controller.Controller;
import controller.GameManager;
import model.BulletModel;
import model.Collectible;
import model.EpsilonModel;
import model.enemies.Enemy;
import model.frame.Frame;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;

import java.util.ArrayList;

public class Necropick extends Enemy {
    public Necropick(Point center, double velocity, int hp, Frame frame) {
        super(center, velocity);
        height = GameManager.configs.NECROPICK_HEIGHT;
        width = GameManager.configs.NECROPICK_WIDTH;
        addVertexes();
        this.frame = frame;
        this.HP = 10 + hp;
        this.frame.getEnemies().add(this);
        Controller.addEnemyView(this);
    }

    @Override
    public void addCollective() {
        int[] x = new int[]{-10, 10, -10, 10};
        int[] y = new int[]{-10, -10, 10, 10};
        for (int i = 0; i < 4; i++) {
            Collectible collectible = new Collectible((int)center.getX()+x[i], (int)center.getY()+y[i], 2);
            GameManager.getINSTANCE().getGameModel().getCollectives().add(collectible);
            Controller.addCollectiveView(collectible);
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
    public void nextMove() {
        while (true) {
            for (int i = 0; i < 4; i++) {
                shoot();
            }
//            sleepFor(3000);
//            for (int i = 0; i < 4; i++) {
//                shoot();
//            }
//            sleepFor(5000);
//            disappear();
//            sleepFor(2000);
//            appear();
        }
    }

    @Override
    public Direction getDirection() {
        return null;
    }

    private void disappear() {
        GameManager.getINSTANCE().getGameModel().getEnemies().remove(this);
        Controller.removeEnemyView(this);
    }
    private void appear() {
        Point point = getRandomPosition();
        Controller.announceAppearance(point);
//        sleepFor(2000);
        center = point;
        moveVertexes();
        GameManager.getINSTANCE().getGameModel().getEnemies().add(this);
        Controller.addEnemyView(this);
    }
    private Point getRandomPosition() {
        EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
        int randomX = (int)(Math.random()*100);
        int randomY = (int)(Math.random()*100);
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
        return new Point(x, y);
    }

    private void shoot() {
        int x = (int)(Math.random()*100);
        int y = (int)(Math.random()*100);
        BulletModel bulletModel = new BulletModel(center, new Point(x, y), (int)(2*height/21), 4, false, frame);
        GameManager.getINSTANCE().getGameModel().getEnemiesBullets().add(bulletModel);
    }
}
