package view.game.enemies;

import controller.GameManager;

public class OmenoctView extends EnemyView{

    public OmenoctView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, GameManager.configs.OMENOCT, ID);

    }
}
