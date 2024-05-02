package model.game;

public class EasyGame extends GameModel {
    public EasyGame() {
        super();
        waves = new int[]{3,4,5};
        enemyVelocity = 1;
        enemyPower = 0;
        enemyHP = 0;
        enemyXP = 0;
    }
}
