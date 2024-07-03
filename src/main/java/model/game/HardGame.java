package model.game;

public class HardGame extends GameModel {
    public HardGame() {
        super();
        waves = new int[]{6,10,15};
        enemyVelocity = 2;
        enemyPower = 5;
        enemyHP = 5;
        enemyXP = 5;
    }
}
