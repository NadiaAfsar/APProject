package model.game_model;

import controller.game_manager.GameManager;

public class ColosseumGame extends GameModel{
    public ColosseumGame(GameManager gameManager, int epsilonNumber) {
        super(gameManager, epsilonNumber);
        enemyVelocity = 1;
    }
}
