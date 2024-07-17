package model.enemies.mini_boss;

import controller.Controller;
import controller.GameManager;
import model.frame.Frame;
import model.enemies.Enemy;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;

public class Barricados extends Enemy {
    private Frame frame;
    private long startTime;
    public Barricados(Point center, double velocity, boolean isRigid) {
        super(center, velocity);
        width = GameManager.configs.BARRICODES_WIDTH;
        height = GameManager.configs.BARRICODES_HEIGHT;
        frame = new Frame(width+10, height+10, center.getX()-width/2-5, center.getY()-height/2-5,
                true, isRigid, false);
        frame.getEnemies().add(this);
        Controller.addEnemyView(this);
        startTime = System.currentTimeMillis();
    }


    @Override
    protected void addVertexes() {
        double[] angles = new double[]{5d/4*Math.PI, 7d/4*Math.PI, 1d/4*Math.PI, 3d/4*Math.PI};
        for (int i = 0; i < 9; i++) {
            RotatablePoint vertex = new RotatablePoint(center.getX(), center.getY(), angles[i]+angle, Math.sqrt(0.5)*width);
            vertexes.add(vertex);
        }
        position = new RotatablePoint(center.getX(), center.getY(), angles[0], Math.sqrt(0.5)*width);
    }

    @Override
    public void addCollective() {

    }
    public void nextMove() {
            if (System.currentTimeMillis()-startTime >= 120000) {
                die();
            }
    }
}
