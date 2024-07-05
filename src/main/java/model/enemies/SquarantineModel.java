package model.enemies;

import controller.Controller;
import model.game.GameModel;
import model.Collective;
import movement.Direction;
import movement.RotatablePoint;
import movement.Point;

import java.awt.*;
import java.util.ArrayList;

public class SquarantineModel extends Enemy {
    private boolean hasRandomAcceleration;
    private Direction direction;
    public SquarantineModel(Point center,  int hp, double velocity) {
        super(center, velocity);
        this.velocity = new Point(0,0);
        HP = 10+hp;
        hasRandomAcceleration = false;
    }
    @Override
    public void moveVertexes() {
        vertexes = new ArrayList<>();
        double[] angles = new double[]{5d/4*Math.PI, 7d/4*Math.PI, 1d/4*Math.PI, 3d/4*Math.PI};
        for (int i = 0; i < 4; i++) {
            RotatablePoint vertex = new RotatablePoint(center.getX(), center.getY(), angles[i], 18.4);
            vertexes.add(vertex);
            if (i == 0) {
                position = vertex;
            }
        }
    }


    @Override
    public void specialMove() {
        if (!hasRandomAcceleration && !impact) {
            int x = (int) (Math.random() * 200);
            if (x == 5) {
                direction = getDirection();
                acceleration = new Point(30*direction.getDx(), 30*direction.getDy());
                accelerationRate = new Point(-10*direction.getDx(), -10*direction.getDy());
                hasRandomAcceleration = true;
            }
        }
        if (hasRandomAcceleration && !impact) {
            if (acceleration.getX() <= 0 || acceleration.getY() <= 0) {
                velocity = new Point(0,0);
                acceleration = new Point(0, 0);
                accelerationRate = new Point(0, 0);
                hasRandomAcceleration = false;
            }
            else {
                Direction newDirection = getDirection();
                acceleration = new Point(acceleration.getX()/direction.getDx()*newDirection.getDx(),
                        acceleration.getY()/direction.getDy()*newDirection.getDy());
                accelerationRate = new Point(accelerationRate.getX()/direction.getDx()*newDirection.getDx(),
                        accelerationRate.getY()/direction.getDy()*newDirection.getDy());
                direction = newDirection;

            }
        }
    }
    public void setSpecialImpact() {
        hasRandomAcceleration = false;
        velocity = new Point(0,0);
    }

    @Override
    public void addCollective(GameModel gameModel) {
        Collective collective = new Collective((int)center.getX(), (int)center.getY(), Color.MAGENTA);
        gameModel.getCollectives().add(collective);
        Controller.addCollectiveView(collective);
    }

}