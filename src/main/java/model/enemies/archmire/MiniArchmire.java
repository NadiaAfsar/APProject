package model.enemies.archmire;

import movement.Point;

public class MiniArchmire extends Archmire{
    public MiniArchmire(Point center, double velocity, int hp) {
        super(center, velocity, hp);
        this.HP = hp;
    }
}
