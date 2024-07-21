package model.enemies.smiley;

import model.interfaces.movement.Direction;
import model.interfaces.movement.Point;

public class LeftHand extends Hand {
    public LeftHand(Point center, double velocity) {
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
