package model.enemies;

import controller.Controller;
import model.EpsilonModel;
import model.game.GameModel;
import model.XP;
import movement.Direction;
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
        GameModel.getINSTANCE().getEnemies().add(this);
        increasedVelocity = true;
        decreasedVelocity = false;
        this.velocity = new Point(0,0);
        HP = 15+hp;
    }
    @Override
    public void setVertexes() {
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
        double x2 = EpsilonModel.getINSTANCE().getCenter().getX();
        double y2 = EpsilonModel.getINSTANCE().getCenter().getX();
        return Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
    }
    @Override
    public void setVelocity() {
        super.setVelocity();
        if (distanceFromEpsilon() <= 100 && !decreasedVelocity && !impact) {
            velocity.setX(0);
            velocity.setY(0);
            decreasedVelocity = true;
            increasedVelocity = false;
        }
        else if (distanceFromEpsilon() > 100 && !increasedVelocity && !impact) {
            velocity.setX(2*dx);
            velocity.setY(2*dy);
            increasedVelocity = true;
            decreasedVelocity = false;
        }
        if (increasedVelocity) {
            velocity.setX(2*dx);
            velocity.setY(2*dy);
        }
    }
    protected void setImpactAcceleration(Direction direction, double distance) {
        velocity.setX(0);
        velocity.setY(0);
        decreasedVelocity = true;
        increasedVelocity = false;
        super.setImpactAcceleration(direction, distance);
    }

    @Override
    public void addXP() {
        GameModel gameModel = GameModel.getINSTANCE();
        for (int i = -1; i < 2; i += 2) {
            XP xp = new XP((int) center.getX()+i*13, (int) center.getY()+i*13, Color.BLUE);
            gameModel.getXPs().add(xp);
            Controller.addXPView(xp);
        }
    }
    }


