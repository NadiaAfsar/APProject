package model.game;


import java.util.HashMap;

public class MediumGame extends GameModel {
    public MediumGame() {
        super();
        enemiesToKill = new HashMap<Integer, Integer>() {{
            put(1, 2);
            put(2, 3);
            put(3, 4);
            put(4, 5);
            put(5, 6);
            put(6, 1);
        }};
        enemyVelocity = 1.5;
        enemyPower = 2;
        enemyHP = 0;
        enemyXP = 0;
    }
}
