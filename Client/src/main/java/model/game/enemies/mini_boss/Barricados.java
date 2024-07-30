package model.game.enemies.mini_boss;

import application.MyApplication;
import controller.Controller;
import controller.GameManager;
import model.game.enemies.Enemy;
import model.game.frame.MyFrame;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class Barricados extends Enemy {
    private MyFrame myFrame;
    private static int number;
    public Barricados(Point center, double velocity, boolean isRigid, GameManager gameManager) {
        super(center, velocity, gameManager);
        number++;
        logger = Logger.getLogger(Barricados.class.getName()+number);
        width = MyApplication.configs.BARRICODES_WIDTH;
        height = MyApplication.configs.BARRICODES_HEIGHT;
        addVertexes();
        myFrame = new MyFrame(width+50, height+50, center.getX()-width/2-25, center.getY()-height/2-25,
                true, isRigid, width+50, height+50, gameManager);
        myFrame.getEnemies().add(this);
        Controller.addEnemyView(this, gameManager);
        gameManager.getGameModel().getFrames().add(myFrame);
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
