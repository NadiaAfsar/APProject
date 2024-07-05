package model.game;

import java.util.HashMap;

public class EasyGame extends GameModel {
    public EasyGame() {
        super();
        waves = new HashMap<Integer, Integer>() {{
            put(1, 3);
            put(2, 4);
            put(3, 5);
        }};
        enemyVelocity = 1;
        enemyPower = 0;
        enemyHP = 0;
        enemyXP = 0;
    }
}
