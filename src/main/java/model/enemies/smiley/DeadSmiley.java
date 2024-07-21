package model.enemies.smiley;

import model.enemies.Enemy;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Point;

public class DeadSmiley extends Enemy {
    public DeadSmiley(Point center, double velocity) {
        super(center, velocity);
    }

    @Override
    protected void addVertexes() {

    }

    @Override
    public void addCollective() {

    }

    @Override
    public Direction getDirection() {
        return null;
    }
}
