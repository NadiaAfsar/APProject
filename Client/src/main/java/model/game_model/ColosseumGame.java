package model.game_model;

import controller.game_manager.GameManager;

import java.util.HashMap;

public class ColosseumGame extends GameModel{
    public ColosseumGame(GameManager gameManager, int epsilonNumber) {
        super(gameManager, epsilonNumber);
        enemiesToKill = new HashMap<Integer, Integer>() {{
            put(1, 1);
            put(2, 2);
            put(3, 3);
            put(4, 4);
            put(5, 4);
            put(6, 4);
        }};
        enemyVelocity = 1;
    }
}
