package model;

import java.util.UUID;

public class Entity {
    private int x;
    private int y;
    private int enemyType;
    private String ID;

    public Entity(int x, int y, int enemyType) {
        ID = UUID.randomUUID().toString();
        this.x = x;
        this.y = y;
        this.enemyType = enemyType;
    }

    public String getID() {
        return ID;
    }
}
