package model.game;


public class MediumGame extends GameModel {
    public MediumGame() {
        super();
        waves = new int[]{5,7,9};
        enemyVelocity = 1.5;
        enemyPower = 2;
        enemyHP = 0;
        enemyXP = 0;
    }
}
