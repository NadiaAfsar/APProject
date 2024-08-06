package model.game_model;

import controller.game_manager.GameManager;
import model.game_model.GameModel;

import java.util.HashMap;

public class EasyGame extends GameModel {
    public EasyGame(GameManager gameManager) {
        super(gameManager, 0);
        enemiesToKill = new HashMap<Integer, Integer>() {{
            put(1, 1);
            put(2, 1);
            put(3, 1);
            put(4, 1);
            put(5, 1);
            put(6, 1);
        }};
        enemyVelocity = 1;
        enemyPower = 0;
        enemyHP = 0;
        enemyXP = 0;
    }
}
