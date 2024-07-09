package model.enemies;

import controller.Controller;
import controller.GameManager;
import model.BulletModel;
import model.game.GameModel;
import movement.Direction;
import movement.Point;
import movement.RotatablePoint;

import java.util.ArrayList;

public class Necropick extends Enemy{
    private final double height;
    private final double width;
    public Necropick(Point center, double velocity, int hp) {
        super(center, velocity);
        height = 21;
        width = 19;
        addVertexes();
        this.HP = 10 + hp;
        initialHP = HP;
    }


    @Override
    public Direction getDirection() {
        return null;
    }


    @Override
    public void addCollective(GameModel gameModel) {

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
        position = new RotatablePoint(center.getX(), center.getY(), 5d/4*Math.PI+angle, 0);
    }
    public void run() {
        while (true) {
            for (int i = 0; i < 4; i++) {
                shoot();
            }
            sleepFor(3000);
            for (int i = 0; i < 4; i++) {
                shoot();
            }
            sleepFor(5000);
            disappear();
            sleepFor(2000);
            appear();
        }
    }
    private void sleepFor(int time) {
        try {
            sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void disappear() {
        GameManager.getINSTANCE().getGameModel().getEnemies().remove(this);
        Controller.removeEnemy(this);
    }
    private void appear() {
        Point point = getRandomPosition();
        Controller.announceAppearance(point);
        sleepFor(2000);
        center = point;
        moveVertexes();
        GameManager.getINSTANCE().getGameModel().getEnemies().add(this);
        Controller.addEnemyView(this);
    }
    private Point getRandomPosition() {
        Point epsilonCenter = GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter();
        int randomX = (int)(Math.random()*100);
        int randomY = (int)(Math.random()*100);
        double angle = Math.atan2(randomY -epsilonCenter.getY(), randomX -epsilonCenter.getX());
        double x = 100*Math.cos(angle);
        double y = 100*Math.sin(angle);
        if (epsilonCenter.getX()+x < 10 || epsilonCenter.getX()+x > GameManager.getINSTANCE().getGameModel().getWidth()-10){
            x = -x+epsilonCenter.getX();
        }
        else {
            x = x+epsilonCenter.getX();
        }
        if (epsilonCenter.getY()+y < 10 || epsilonCenter.getY()+y > GameManager.getINSTANCE().getGameModel().getHeight()-10){
            y = -y+epsilonCenter.getY();
        }
        else {
            y = y+epsilonCenter.getY();
        }
        return new Point(x, y);
    }

    private void shoot() {
        int x = (int)(Math.random()*100);
        int y = (int)(Math.random()*100);
        BulletModel bulletModel = new BulletModel(center, new Point(x, y), (int)(2*height/21), 4, false);
        GameManager.getINSTANCE().getGameModel().getEnemiesBullets().add(bulletModel);
        Controller.addBulletView(bulletModel);
    }
}
