package model;

import movement.Direction;
import movement.Movable;

public class BulletModel implements Movable {
    private int x;
    private int y;
    private Direction direction;
    public BulletModel(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    @Override
    public void move() {
        x += direction.getDx();
        y += direction.getDy();
    }
}
