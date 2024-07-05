package model.game;


import java.util.HashMap;

public class MediumGame extends GameModel {
    public MediumGame() {
        super();
        waves = new HashMap<Integer, Integer>() {{
            put(1, 5);
            put(2, 7);
            put(3, 9);
        }};
        enemyVelocity = 1.5;
        enemyPower = 2;
        enemyHP = 0;
        enemyXP = 0;
    }
}
