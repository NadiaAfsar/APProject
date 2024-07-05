package model.game;

import java.util.HashMap;

public class HardGame extends GameModel {
    public HardGame() {
        super();
        waves = new HashMap<Integer, Integer>() {{
            put(1, 6);
            put(2, 10);
            put(3, 15);
        }};
        enemyVelocity = 2;
        enemyPower = 5;
        enemyHP = 5;
        enemyXP = 5;
    }
}
