package model.enemies;

import controller.Controller;
import controller.GameManager;
import model.game.GameModel;
import model.Collective;
import movement.RotatablePoint;
import movement.Point;

import java.awt.*;
import java.util.ArrayList;

public class TrigorathModel extends Enemy{
    private int x;
    private int y;
    private boolean decreasedVelocity;
    private boolean increasedVelocity;
    public TrigorathModel(Point center, int hp, double velocity) {
        super(center, velocity);
        increasedVelocity = true;
        decreasedVelocity = false;
        this.velocity = new Point(0,0);
        HP = 15+hp;
    }
    @Override
    public void moveVertexes() {
        vertexes = new ArrayList<>();
        double[] angles = new double[]{1d/6*Math.PI, 5d/6*Math.PI, 9d/6*Math.PI};
        for (int i = 0; i < 3; i++) {
            RotatablePoint vertex = new RotatablePoint(center.getX(), center.getY(), angles[i], 15);
            vertexes.add(vertex);
        }
        position = new RotatablePoint(center.getX(), center.getY(), 41d/180*Math.PI, 19.8);
    }

    private double distanceFromEpsilon() {
        double x1 = center.getX();
        double y1 = center.getY();
        double x2 = GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter().getX();
        double y2 = GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter().getX();
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
    public void setSpecialImpact() {
        velocity = new Point(0, 0);
        decreasedVelocity = true;
        increasedVelocity = false;
    }

    @Override
    public void addCollective(GameModel gameModel) {
        for (int i = -1; i < 2; i += 2) {
            Collective collective = new Collective((int) center.getX()+i*13, (int) center.getY()+i*13, Color.BLUE);
            gameModel.getCollectives().add(collective);
            Controller.addCollectiveView(collective);
        }
    }
    }


