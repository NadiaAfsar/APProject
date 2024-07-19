package model.enemies.mini_boss;

import controller.Controller;
import controller.GameManager;
import model.Collectible;
import model.frame.Frame;
import model.enemies.Enemy;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class Barricados extends Enemy {
    private Frame frame;
    private static int number;
    public Barricados(Point center, double velocity, boolean isRigid) {
        super(center, velocity);
        number++;
        logger = Logger.getLogger(Barricados.class.getName()+number);
        width = GameManager.configs.BARRICODES_WIDTH;
        height = GameManager.configs.BARRICODES_HEIGHT;
        addVertexes();
        frame = new Frame(width+50, height+50, center.getX()-width/2-25, center.getY()-height/2-25,
                true, isRigid);
        frame.getEnemies().add(this);
        Controller.addEnemyView(this);
        GameManager.getINSTANCE().getGameModel().getFrames().add(frame);
        start();
    }


    @Override
    protected void addVertexes() {
        vertexes = new ArrayList<>();
        double[] angles = new double[]{5d/4*Math.PI, 7d/4*Math.PI, 1d/4*Math.PI, 3d/4*Math.PI};
        for (int i = 0; i < 4; i++) {
            RotatablePoint vertex = new RotatablePoint(center.getX(), center.getY(), angles[i]+angle, Math.sqrt(0.5)*width);
            vertexes.add(vertex);
        }
        position = new RotatablePoint(center.getX(), center.getY(), angles[0], Math.sqrt(0.5)*width);
    }

    @Override
    public void addCollective() {

    }
    public void run() {
        try {
            sleep(120000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        die();
    }

    @Override
    public Direction getDirection() {
        return null;
    }
}
