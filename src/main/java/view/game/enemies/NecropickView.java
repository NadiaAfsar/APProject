package view.game.enemies;

import controller.GameManager;

public class NecropickView extends EnemyView{

    public NecropickView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, GameManager.configs.NECROPICK, ID);

    }
}
