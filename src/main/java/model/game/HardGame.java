package model.game;

import java.util.HashMap;

public class HardGame extends GameModel {
    public HardGame() {
        super();
        enemiesToKill = new HashMap<Integer, Integer>() {{
            put(1, 3);
            put(2, 5);
            put(3, 7);
            put(4, 8);
            put(5, 8);
            put(6, 1);
        }};
        enemyVelocity = 2;
        enemyPower = 5;
        enemyHP = 5;
        enemyXP = 5;
    }
}
