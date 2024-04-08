package model.enemies;

import collision.Collidable;

public class SquarantineModel implements Collidable {
    private int x;
    private int y;
    public SquarantineModel(int x, int y) {
        this.x = x;
        this.y = y;
    }

}