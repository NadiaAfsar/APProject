package model.game_model;

import controller.game_manager.GameManager;

public class MonomachiaGame extends GameModel{
    public MonomachiaGame(GameManager gameManager, int epsilon) {
        super(gameManager, epsilon);
        enemyVelocity = 1;
    }
}
