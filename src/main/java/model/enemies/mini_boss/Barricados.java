package model.enemies.mini_boss;

import controller.Controller;
import controller.GameManager;
import model.Frame;
import model.enemies.Enemy;
import movement.Direction;
import movement.Point;

public class Barricados extends Enemy {
    private Frame frame;
    private double width;
    private double height;
    private long startTime;
    public Barricados(Point center, double velocity, boolean isRigid) {
        super(center, velocity);
        frame = new Frame(width+10, height+10, center.getX()-width/2-5, center.getY()-height/2-5,
                true, isRigid);
    }

    @Override
    public Direction getDirection() {
        return null;
    }

    @Override
    protected void addVertexes() {

    }

    @Override
    public void addCollective() {

    }
    public void run() {
        while (true) {
            if (System.currentTimeMillis()-startTime >= 120000) {
                die();
                interrupt();
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void die() {
        GameManager.getINSTANCE().getGameModel().getEnemies().remove(this);
        Controller.removeEnemy(this);
    }
}
