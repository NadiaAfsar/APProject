package model.enemies.smiley;

import controller.GameManager;
import model.enemies.Enemy;
import model.frame.Frame;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Point;

public class RightHand extends Hand {
    public RightHand(Point center, double velocity) {
        super(center, velocity);
    }

    @Override
    protected void addVertexes() {

    }


    @Override
    public Direction getDirection() {
        return null;
    }


}
